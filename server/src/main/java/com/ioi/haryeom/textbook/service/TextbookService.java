package com.ioi.haryeom.textbook.service;

import com.ioi.haryeom.aws.S3Upload;
import com.ioi.haryeom.aws.exception.S3UploadException;
import com.ioi.haryeom.common.domain.Subject;
import com.ioi.haryeom.common.repository.SubjectRepository;
import com.ioi.haryeom.member.domain.Member;
import com.ioi.haryeom.member.domain.Teacher;
import com.ioi.haryeom.member.domain.TeacherSubject;
import com.ioi.haryeom.member.dto.SubjectInfo;
import com.ioi.haryeom.member.exception.MemberNotFoundException;
import com.ioi.haryeom.member.exception.NoTeacherException;
import com.ioi.haryeom.member.exception.SubjectNotFoundException;
import com.ioi.haryeom.member.repository.MemberRepository;
import com.ioi.haryeom.member.repository.TeacherRepository;
import com.ioi.haryeom.member.repository.TeacherSubjectRepository;
import com.ioi.haryeom.textbook.domain.Assignment;
import com.ioi.haryeom.textbook.domain.Textbook;
import com.ioi.haryeom.textbook.dto.*;
import com.ioi.haryeom.textbook.exception.*;
import com.ioi.haryeom.textbook.repository.AssignmentRespository;
import com.ioi.haryeom.textbook.repository.TextbookRepository;
import com.ioi.haryeom.tutoring.domain.Tutoring;
import com.ioi.haryeom.tutoring.domain.TutoringStatus;
import com.ioi.haryeom.tutoring.exception.TutoringNotFoundException;
import com.ioi.haryeom.tutoring.repository.TutoringRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class TextbookService {

    private final S3Upload s3Upload;

    private final TextbookRepository textbookRepository;
    private final MemberRepository memberRepository;
    private final AssignmentRespository assignmentRespository;
    private final SubjectRepository subjectRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final TeacherRepository teacherRepository;

    private final TutoringRepository tutoringRepository;

    // 학습자료 추가
    @Transactional
    public Long createTextbook(MultipartFile file, TextbookRequest request, Long teacherMemberId) {

        String allowedExtensions = "pdf"; // pdf만 허용

        try {
            String fileName = file.getOriginalFilename();

            // 파일 Validation
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if(!allowedExtensions.contains(fileExtension)) {
                throw new FileValidationException(allowedExtensions);
            }

            // S3 업로드
            String fileUrl = s3Upload.uploadFile(fileName, file.getInputStream(), file.getSize(), file.getContentType());

            // PDF 첫 페이지 PNG 저장 로직
            // 파일 로드해서
            PDDocument document = PDDocument.load(file.getInputStream());

            String coverImg = null;
            int totalPage = document.getNumberOfPages();

            if(request.isFirstPageCover() == true){
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                // 0번 인덱스 가져오기(표지)
                PDPage firstPage = document.getPage(0);
                // 이미지 해상도 설정
                int dpi =300;
                BufferedImage image = pdfRenderer.renderImageWithDPI(0, dpi);

                // BufferedImage 를 InputStream 으로 변환
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "png", os);
                byte[] buffer = os.toByteArray();
                InputStream is = new ByteArrayInputStream(buffer);

                // 적당한 이름 설정 후 S3 업로드
                String coverImageFileName = teacherMemberId + "_" + request.getTextbookName() + "_cover.png";

                coverImg = s3Upload.uploadFile(coverImageFileName, is, buffer.length, "image/png");

                os.close();
                is.close();
                document.close();
            } else {
                float MARGIN = 40; // 페이지 여백
                int FONT_SIZE = 50; // 기본 폰트 크기
                float LEADING = 1.5f * FONT_SIZE; // 줄 간격
                PDDocument doc = new PDDocument();

                try {
                    PDPage page = new PDPage();
                    doc.addPage(page);

                    PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                    // 텍스트 폰트 및 크기 설정
                    PDType0Font font = PDType0Font.load(doc, new ClassPathResource("NanumGothicBold.ttf").getInputStream());
                    contentStream.setFont(font, FONT_SIZE);

                    float pageWidth = page.getMediaBox().getWidth();
                    float pageHeight = page.getMediaBox().getHeight();

                    // 자동 줄바꿈 및 가운데 정렬 처리
                    String[] words = request.getTextbookName().split(" ");
                    StringBuilder line = new StringBuilder();
                    float startX = MARGIN;
                    float startY = pageHeight - MARGIN - FONT_SIZE*5;

                    for(String word : words) {
                        float wordWidth = font.getStringWidth(line + word + " ") / 1000 * FONT_SIZE;
                        if(startX + wordWidth < pageWidth - MARGIN) line.append(word).append(" ");
                        else {
                            float lineWidth = font.getStringWidth(line.toString()) / 1000 * FONT_SIZE;
                            float centerX = (pageWidth - lineWidth) / 2;

                            contentStream.beginText();
                            contentStream.newLineAtOffset(centerX, startY);
                            contentStream.showText(line.toString());
                            contentStream.endText();

                            line = new StringBuilder(word).append(" ");
                            startY -= LEADING;
                        }
                    }

                    if(line.length() > 0) {
                        float lineWidth = font.getStringWidth(line.toString()) / 1000 * FONT_SIZE;
                        float centerX = (pageWidth - lineWidth) / 2;
                        contentStream.beginText();
                        contentStream.newLineAtOffset(centerX, startY);
                        contentStream.showText(line.toString());
                        contentStream.endText();
                    }
                    contentStream.close();

                    // 이미지로 변환
                    PDFRenderer pdfRenderer = new PDFRenderer(doc);
                    int dpi = 300;
                    BufferedImage image = pdfRenderer.renderImageWithDPI(0, dpi);

                    // BufferedImage 를 InputStream 으로 변환
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", os);
                    byte[] buffer = os.toByteArray();
                    InputStream is = new ByteArrayInputStream(buffer);

                    // 적당한 이름 설정 후 S3 업로드
                    String coverImageFileName = teacherMemberId + "_" + request.getTextbookName() + "_cover.png";

                    coverImg = s3Upload.uploadFile(coverImageFileName, is, buffer.length, "image/png");
                    os.close();
                    is.close();

                } finally {
                    if(doc != null) doc.close();
                    document.close();
                }
            }

            Member teacherMember = findMemberById(teacherMemberId);
            if(teacherMember == null) throw new RuntimeException("선생님이없어요");
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new SubjectNotFoundException(request.getSubjectId()));

            Textbook textbook = Textbook.builder()
                    .teacherMember(teacherMember)
                    .subject(subject)
                    .textbookName(request.getTextbookName())
                    .textbookUrl(fileUrl)
                    .firstPageCover(request.isFirstPageCover())
                    .totalPage(totalPage)
                    .coverImg(coverImg)
                    .build();

            Textbook savedTextbook = textbookRepository.save(textbook);
            return savedTextbook.getId();

        } catch (IOException e) {
            e.printStackTrace();
            throw new S3UploadException();
        }
    }

    // 과외별 학습자료 리스트 조회
    public List<TextbookListByTutoringResponse> getTextbookListByTutoring(Long tutoringId) {
        Tutoring tutoring = tutoringRepository.findByIdAndStatus(tutoringId, TutoringStatus.IN_PROGRESS)
                .orElseThrow(() -> new TutoringNotFoundException(tutoringId));

        List<Assignment> assignments = assignmentRespository.findByTutoringId(tutoring.getId());
        if(assignments.size() == 0) throw new SelectedTextbookNotFoundException(tutoringId);
        return assignments.stream()
                .map(Assignment::getTextbook)
                .distinct()
                .map(TextbookListByTutoringResponse::new)
                .collect(Collectors.toList());
    }

    // 학습자료 불러오기
    public TextbookResponse getTextbook(Long textbookId) {
        Textbook textbook = findTextbookById(textbookId);
        return new TextbookResponse(textbook);
    }

    // 학습자료 삭제
    public void deleteTextbook(List<Long> textbookIds, Long teacherMemberId) {
        for(Long textbookId : textbookIds){
            Textbook textbook = findTextbookById(textbookId);
            Member teacherMember = memberRepository.findById(teacherMemberId)
                    .orElseThrow(() -> new MemberNotFoundException(teacherMemberId));
            if(textbook.getTeacherMember() != teacherMember) {
                throw new RuntimeException("해당 학습자료를 등록한 선생님이 아닙니다.");
            }

            textbook.delete();
        }
    }

    // 선생님 학습자료 리스트 조회
    public List<TextbookWithStudentsResponse> getTextbooksWithStudents(Long memberId, Long teacherMemberId) {
//        if(memberId != teacherMemberId) throw new NoTeacherException();

        Member teacherMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        List<Textbook> textbooks = textbookRepository.findAllByTeacherMemberAndIsDeletedFalse(teacherMember);
        if(textbooks.size() == 0) throw new RegisteredTextbookNotFoundException(teacherMemberId);

        return textbooks.stream().map(textbook -> {
            List<Assignment> assignments = assignmentRespository.findByTextbook(textbook);
            List<TextbookWithStudentsResponse.StudentInfo> studentInfos = assignments.stream()
                    .map(assignment -> assignment.getTutoring().getStudent())
                    .map(TextbookWithStudentsResponse.StudentInfo::new)
                    .collect(Collectors.toList());
            return new TextbookWithStudentsResponse(textbook, studentInfos);
        }).collect(Collectors.toList());
    }

    // 학습자료별 지정 가능 학생 리스트 조회
    public List<TextbookWithStudentsResponse.StudentInfo> getAssignableStudent(Long textbookId,Long teacherMemberId) {

        List<Tutoring> tutorings = tutoringRepository.findAllByTeacherIdAndStatus(teacherMemberId, TutoringStatus.IN_PROGRESS);

        Textbook currentTextbook = findTextbookById(textbookId);
        List<Assignment> currentAssignments = assignmentRespository.findByTextbook(currentTextbook);
        List<Long> assignedStudentIds = currentAssignments.stream()
                .map(assignment -> assignment.getTutoring().getStudent().getId())
                .collect(Collectors.toList());

        List<TextbookWithStudentsResponse.StudentInfo> assignableStudents = tutorings.stream()
                .map(Tutoring::getStudent)
                .filter(student -> !assignedStudentIds.contains(student.getId()))
                .distinct()
                .map(TextbookWithStudentsResponse.StudentInfo::new)
                .collect(Collectors.toList());

        if(assignableStudents.isEmpty()) throw new AssignStudentNotFoundException();

        return assignableStudents;
    }

    // 학습자료 학생 지정
    public void putAssignment(Long textbookId, List<Long> studentMemberIds, Long teacherMemberId) {
        Textbook textbook = findTextbookById(textbookId);

        for(Long studentMemberId : studentMemberIds) {
            Tutoring tutoring = tutoringRepository.findAllByTeacherIdAndStudentId(teacherMemberId, studentMemberId);

            Assignment assignment = Assignment.builder()
                    .tutoring(tutoring)
                    .textbook(textbook)
                    .build();

            assignmentRespository.save(assignment);
        }
    }

    // 선생님 과목 불러오기
    public List<SubjectInfo> getTeacherSubjects(Long teacherMemberId) {

        Member teacherMember = memberRepository.findById(teacherMemberId)
                .orElseThrow(() -> new MemberNotFoundException(teacherMemberId));
        Teacher teacher = teacherRepository.findByMember(teacherMember)
                .orElseThrow(() -> new NoTeacherException());

        return teacherSubjectRepository
                .findByTeacherId(teacher.getId())
                .stream()
                .map(TeacherSubject::getSubject)
                .map(SubjectInfo::from)
                .collect(Collectors.toList());
    }

    // 학습자료 학생 지정 해제
    public void deleteAssignment(Long textbookId, List<Long> studentMemberIds, Long teacherMemberId) {

        for(Long studentMemberId : studentMemberIds){
            Tutoring tutoring = tutoringRepository.findAllByTeacherIdAndStudentId(teacherMemberId, studentMemberId);
            if(tutoring == null) throw new TutoringNotFoundException(tutoring.getId());
            Assignment assignment = assignmentRespository.findByTextbookIdAndTutoringId(textbookId, tutoring.getId());
            if(assignment == null) throw new AssignmentNotFoundException();

            assignmentRespository.deleteById(assignment.getId());
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private Textbook findTextbookById(Long textbookId) {
        return textbookRepository.findByIdAndIsDeletedFalse(textbookId)
                .orElseThrow(() -> new TextbookNotFoundException(textbookId));
    }
}

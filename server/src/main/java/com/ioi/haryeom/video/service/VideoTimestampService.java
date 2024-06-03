package com.ioi.haryeom.video.service;

import com.ioi.haryeom.auth.exception.AuthorizationException;
import com.ioi.haryeom.member.domain.Member;
import com.ioi.haryeom.member.exception.MemberNotFoundException;
import com.ioi.haryeom.member.repository.MemberRepository;
import com.ioi.haryeom.tutoring.domain.Tutoring;
import com.ioi.haryeom.tutoring.repository.TutoringScheduleRepository;
import com.ioi.haryeom.video.domain.Video;
import com.ioi.haryeom.video.domain.VideoTimestamp;
import com.ioi.haryeom.video.dto.VideoTimestampRequest;
import com.ioi.haryeom.video.dto.VideoTimestampResponse;
import com.ioi.haryeom.video.exception.VideoTimestampNotFoundException;
import com.ioi.haryeom.video.exception.VideoTutoringNotFoundException;
import com.ioi.haryeom.video.repository.VideoRepository;
import com.ioi.haryeom.video.repository.VideoTimestampRepository;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VideoTimestampService {

    private final MemberRepository memberRepository;
    private final VideoTimestampRepository videoTimestampRepository;
    private final TutoringScheduleRepository tutoringScheduleRepository;
    private final VideoRepository videoRepository;

    public List<VideoTimestampResponse> getTimestampList(Long tutoringScheduleId, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

        Video video = videoRepository.findByTutoringScheduleId(tutoringScheduleId)
            .orElseThrow(() -> new VideoTutoringNotFoundException(tutoringScheduleId));

        Tutoring tutoring = video.getTutoringSchedule().getTutoring();

        if (!tutoring.isMemberPartOfTutoring(member)) {
            throw new AuthorizationException(member.getId());
        }

        List<VideoTimestamp> timestampList = videoTimestampRepository.findAllByVideo(video);
        List<VideoTimestampResponse> videoTimestampResponseList = new ArrayList<>();
        for (VideoTimestamp timestamp : timestampList) {
            videoTimestampResponseList.add(new VideoTimestampResponse(timestamp));
        }
        return videoTimestampResponseList;
    }

    @Transactional
    public Long createVideoTimestamp(Long tutoringScheduleId, VideoTimestampRequest timestampRequest, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

        Video video = videoRepository.findByTutoringScheduleId(tutoringScheduleId)
            .orElseThrow(() -> new VideoTutoringNotFoundException(tutoringScheduleId));

        Tutoring tutoring = video.getTutoringSchedule().getTutoring();

        if (!tutoring.isMemberPartOfTutoring(member)) {
            throw new AuthorizationException(member.getId());
        }

        LocalTime stampTime = parseStampTime(timestampRequest.getStampTime());
        VideoTimestamp timestamp = VideoTimestamp.builder()
            .video(video).stampTime(stampTime).content(timestampRequest.getContent())
            .build();

        VideoTimestamp savedTimestamp = videoTimestampRepository.save(timestamp);
        return savedTimestamp.getId();

        //Todo: auth exception
    }

    @Transactional
    public void updateVideoTimestamp(Long videoTimestampId, VideoTimestampRequest timestampRequest, Long memberId) {
        VideoTimestamp videoTimestamp = videoTimestampRepository.findById(videoTimestampId)
            .orElseThrow(() -> new VideoTimestampNotFoundException(videoTimestampId));

        if (videoTimestamp.getVideo().getTutoringSchedule().getTutoring().getStudent().getId() != memberId
            && videoTimestamp.getVideo().getTutoringSchedule().getTutoring().getTeacher().getId() != memberId) {
            throw new AuthorizationException(memberId);
        }
        LocalTime stampTime = parseStampTime(timestampRequest.getStampTime());
        videoTimestamp.update(stampTime, timestampRequest.getContent());
    }

    @Transactional
    public void deleteTimestamp(Long videoTimestampId, Long memberId) {
        VideoTimestamp videoTimestamp = videoTimestampRepository.findById(videoTimestampId)
            .orElseThrow(() -> new VideoTimestampNotFoundException(videoTimestampId));
        if (videoTimestamp.getVideo().getTutoringSchedule().getTutoring().getStudent().getId() != memberId
            && videoTimestamp.getVideo().getTutoringSchedule().getTutoring().getTeacher().getId() != memberId) {
            throw new AuthorizationException(memberId);
        }
        videoTimestampRepository.delete(videoTimestamp);
    }

    private Optional<VideoTimestamp> findVideoById(Long videoTimestampId) {
        return videoTimestampRepository.findById(videoTimestampId);
    }

    private LocalTime parseStampTime(String inputStampTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime stampTime = LocalTime.parse(inputStampTime, formatter);
        return stampTime;
    }
}

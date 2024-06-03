package com.ioi.haryeom.chat.controller;

import com.ioi.haryeom.chat.dto.ChatMessageResponse;
import com.ioi.haryeom.chat.dto.ChatRoomRequest;
import com.ioi.haryeom.chat.dto.ChatRoomResponse;
import com.ioi.haryeom.chat.service.ChatRoomService;
import com.ioi.haryeom.common.dto.SubjectResponse;
import com.ioi.haryeom.common.util.AuthMemberId;
import com.ioi.haryeom.tutoring.dto.TutoringResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/chatrooms")
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성 또는 조회
    @PostMapping("")
    public ResponseEntity<Void> createOrGetChatRoom(@RequestBody @Validated ChatRoomRequest request, @AuthMemberId Long memberId) {
        Long chatRoomId = chatRoomService.createOrGetChatRoom(request.getTeacherId(), memberId);
        return ResponseEntity.created(URI.create("/chatrooms/" + chatRoomId)).build();
    }

    // 채팅방 리스트 조회
    @GetMapping("")
    public ResponseEntity<List<ChatRoomResponse>> getChatRoomList(@AuthMemberId Long memberId) {
        List<ChatRoomResponse> response = chatRoomService.getChatRoomList(memberId);
        return ResponseEntity.ok(response);
    }

    // 채팅방 나가기
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Void> exitChatRoom(@PathVariable Long chatRoomId, @AuthMemberId Long memberId) {
        chatRoomService.exitChatRoom(memberId, chatRoomId);
        return ResponseEntity.noContent().build();
    }

    // 채팅방 메시지 목록 조회
    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessageList(@PathVariable Long chatRoomId,
        @RequestParam(required = false) String lastMessageId,
        @RequestParam(defaultValue = "20") Integer size, @AuthMemberId Long memberId) {
        List<ChatMessageResponse> response = chatRoomService.getChatMessageList(chatRoomId, lastMessageId, size, memberId);
        return ResponseEntity.ok(response);
    }


    // 채팅방 구성원 과외 조회
    @GetMapping("/{chatRoomId}/tutoring")
    public ResponseEntity<List<TutoringResponse>> getChatRoomMembersTutoringList(@PathVariable Long chatRoomId, @AuthMemberId Long memberId) {
        List<TutoringResponse> response = chatRoomService.getChatRoomMembersTutoringList(chatRoomId, memberId);
        return ResponseEntity.ok(response);
    }

    // 신청 가능한 과목 조회
    @GetMapping("/{chatRoomId}/subjects")
    public ResponseEntity<List<SubjectResponse>> getAvailableSubjectsForEnrollment(@PathVariable Long chatRoomId, @AuthMemberId Long memberId) {
        List<SubjectResponse> response = chatRoomService.getAvailableSubjectsForEnrollment(chatRoomId, memberId);
        return ResponseEntity.ok(response);
    }
}

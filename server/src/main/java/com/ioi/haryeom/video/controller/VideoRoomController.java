package com.ioi.haryeom.video.controller;

import com.ioi.haryeom.common.util.AuthMemberId;
import com.ioi.haryeom.video.dto.VideoRoomResponse;
import com.ioi.haryeom.video.service.VideoRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class VideoRoomController {

    private final VideoRoomService videoRoomService;

    // 방 입장하기 위해 방 코드 찾기
    @GetMapping("/{scheduleId}")
    public ResponseEntity<VideoRoomResponse> enterRoom(@PathVariable("scheduleId") Long scheduleId, @AuthMemberId Long memberId) {
        String roomCode = videoRoomService.getVideoRoomByScheduleId(scheduleId, memberId);
        VideoRoomResponse response = new VideoRoomResponse(roomCode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package com.ioi.haryeom.video.domain;

import com.ioi.haryeom.common.domain.BaseTimeEntity;
import com.ioi.haryeom.tutoring.domain.TutoringSchedule;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutoring_schedule_id")
    private TutoringSchedule tutoringSchedule;

    @Column(length = 100)
    private String roomCode;

    @Builder
    public VideoRoom(TutoringSchedule tutoringSchedule, String roomCode){
        this.tutoringSchedule = tutoringSchedule;
        this.roomCode = roomCode;
    }
}

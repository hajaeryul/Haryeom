package com.ioi.haryeom.video.repository;

import com.ioi.haryeom.video.domain.Video;
import com.ioi.haryeom.video.domain.VideoTimestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoTimestampRepository extends JpaRepository<VideoTimestamp, Long> {

    List<VideoTimestamp> findAllByVideo(Video video);
}

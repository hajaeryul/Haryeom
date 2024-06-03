package com.ioi.haryeom.common.dto;

import com.ioi.haryeom.common.domain.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {

    private Long subjectId;
    private String name;

    public SubjectResponse(Subject subject) {
        this.subjectId = subject.getId();
        this.name = subject.getName();
    }
}

package com.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class VisitDto {

    private String userId;
    private String reservationId;
    private LocalDateTime visitDt;

}

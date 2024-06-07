package com.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class InitReservationDto {

    private Long customerId;
    private Long storeId;
    private LocalDateTime reserveDt;
    private String contact;

}

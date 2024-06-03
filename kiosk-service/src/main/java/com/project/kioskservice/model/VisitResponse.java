package com.project.kioskservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class VisitResponse {

    private final String customerName;
    private final LocalDateTime visitDt;

    public static VisitResponse success(String customerName) {
        return VisitResponse.builder()
                .customerName(customerName)
                .visitDt(LocalDateTime.now())
                .build();
    }
}

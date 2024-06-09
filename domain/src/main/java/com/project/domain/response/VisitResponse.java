package com.project.domain.response;

import com.project.domain.dto.ReservationDto;
import com.project.domain.dto.VisitDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class VisitResponse {

    private final String result;
    private final String contact;
    private final LocalDateTime visitDt;

    public static VisitResponse success(VisitDto visitDto) {
        return VisitResponse.builder()
                .result("Entry allowed")
                .contact(visitDto.getContact())
                .visitDt(visitDto.getVisitDt())
                .build();
    }

    public static VisitResponse fail(String contact) {
        return VisitResponse.builder()
                .result("Entry denied")
                .contact(contact)
                .visitDt(null)
                .build();
    }

}

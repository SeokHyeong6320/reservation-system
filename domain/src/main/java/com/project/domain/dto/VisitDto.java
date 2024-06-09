package com.project.domain.dto;

import com.project.domain.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class VisitDto {

//    private boolean success;
    private Long reservationId;
    private String contact;
    private LocalDateTime visitDt;

    public static VisitDto fromReservation(/*boolean success, */Reservation reservation) {
        return VisitDto.builder()
//                .success(success)
                .reservationId(reservation.getId())
                .contact(reservation.getContactNumber())
                .visitDt(reservation.getVisitDt())
                .build();

    }

}

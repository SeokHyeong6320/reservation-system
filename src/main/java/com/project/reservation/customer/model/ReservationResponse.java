package com.project.reservation.customer.model;

import com.project.reservation.customer.dto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ReservationResponse {

    private final String storeName;
    private final String address;

    private final String customerContact;

    private final LocalDateTime reserveDt;
    private final LocalDateTime visitAvailDt;

    private boolean isApprove;

    public static ReservationResponse fromDto(ReservationDto reservationDto) {
        return ReservationResponse.builder()
                .storeName(reservationDto.getStore().getName())
                .address(reservationDto.getStore().getAddress().getDetailAddress())
                .customerContact(reservationDto.getContactNumber())
                .reserveDt(reservationDto.getReserveDt())
                .visitAvailDt(reservationDto.getVisitDt())
                .isApprove(reservationDto.getIsApprove())
                .build();
    }
}

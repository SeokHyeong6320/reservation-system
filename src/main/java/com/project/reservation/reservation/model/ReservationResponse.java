package com.project.reservation.reservation.model;

import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.entity.ReservationApproveStatus;
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

    private final String code;

    private ReservationApproveStatus approveStatus;

    public static ReservationResponse fromDto(ReservationDto reservationDto) {
        return ReservationResponse.builder()
                .storeName(reservationDto.getStore().getName())
                .address(reservationDto.getStore().getAddress().getDetailAddress())
                .customerContact(reservationDto.getContactNumber())
                .reserveDt(reservationDto.getReserveDt())
                .code(reservationDto.getCode())
                .visitAvailDt(reservationDto.getVisitAvailDt())
                .approveStatus(reservationDto.getApproveStatus())
                .build();
    }
}

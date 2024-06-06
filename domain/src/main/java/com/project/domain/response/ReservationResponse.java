package com.project.domain.response;

import com.project.domain.dto.ReservationDto;
import com.project.domain.type.ReservationApproveStatus;
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

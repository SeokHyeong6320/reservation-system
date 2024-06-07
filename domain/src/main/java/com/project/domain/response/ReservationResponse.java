package com.project.domain.response;

import com.project.domain.dto.InitReservationDto;
import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.Store;
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

    private ReservationApproveStatus approveStatus;

    public static ReservationResponse fromDto(ReservationDto reservationDto) {
        return ReservationResponse.builder()
//                .storeName(reservationDto.getStore.getName())
//                .address(reservationDto.getStore().getAddress().getDetailAddress())
                .customerContact(reservationDto.getContactNumber())
                .reserveDt(reservationDto.getReserveDt())
                .visitAvailDt(reservationDto.getVisitAvailDt())
                .approveStatus(reservationDto.getApproveStatus())
                .build();
    }

    public static ReservationResponse fromDto(InitReservationDto dto, Store store) {
        return ReservationResponse.builder()
                .storeName(store.getName())
                .address(store.getAddress().getDetailAddress())
                .customerContact(dto.getContact())
                .reserveDt(dto.getReserveDt())
                .visitAvailDt(dto.getReserveDt().minusMinutes(10))
                .approveStatus(ReservationApproveStatus.PENDING)
                .build();
    }
}

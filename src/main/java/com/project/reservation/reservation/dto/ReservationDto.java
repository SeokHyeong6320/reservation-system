package com.project.reservation.reservation.dto;

import com.project.reservation.auth.entity.User;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.reservation.entity.ReservationApproveStatus;
import com.project.reservation.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {

    private Long id;

    private User customer;

    private Store store;

    private String contactNumber;

    private LocalDateTime reserveDt;

    private ReservationApproveStatus approveStatus;

    private Boolean visitYn;

    private LocalDateTime visitAvailDt;

    private String code;



    public static ReservationDto fromEntity(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .customer(reservation.getCustomer())
                .store(reservation.getStore())
                .contactNumber(reservation.getContactNumber())
                .reserveDt(reservation.getReserveDt())
                .approveStatus(reservation.getApproveStatus())
                .visitYn(reservation.isVisitYn())
                .visitAvailDt(reservation.getVisitAvailDt())
                .code(reservation.getCode())
                .build();
    }
}

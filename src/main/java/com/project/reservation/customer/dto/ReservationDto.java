package com.project.reservation.customer.dto;

import com.project.reservation.customer.entity.Reservation;
import com.project.reservation.store.entity.Store;
import com.project.reservation.auth.entity.User;
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

    private LocalDateTime reserveDt;

    private Boolean isApprove;

    private Boolean isVisit;

    private LocalDateTime visitDt;



    public static ReservationDto fromEntity(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .customer(reservation.getCustomer())
                .store(reservation.getStore())
                .reserveDt(reservation.getReserveDt())
                .isApprove(reservation.getIsApprove())
                .isVisit(reservation.getIsVisit())
                .visitDt(reservation.getVisitDt())
                .build();
    }
}

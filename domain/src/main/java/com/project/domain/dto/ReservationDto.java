package com.project.domain.dto;

import com.project.domain.entity.Reservation;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.type.ReservationApproveStatus;
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

//    private Long customerId;;
//
//    private String customerEmail;

    private User customer;

//    private Long storeId;
//
//    private String storeName;

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

package com.project.reservation.reservation.entity;

import com.project.reservation.auth.entity.User;
import com.project.reservation.common.entity.BaseEntity;
import com.project.reservation.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserv_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "reserv_contact")
    private String contactNumber;

    @Column(name = "reserv_dt")
    private LocalDateTime reserveDt;

    @Column(name = "reserv_approv")
    @Enumerated(value = EnumType.STRING)
    private ReservationApproveStatus approveStatus;

    @Column(name = "reserv_visit_yn")
    private Boolean visitYn;

    @Column(name = "reserv_visit_avail_dt")
    private LocalDateTime visitAvailDt;

    @Column(name = "reserv_code")
    private String code;

    public boolean availVisit() {
        return !visitAvailDt.isBefore(LocalDateTime.now());
    }

    public void approve() {
        this.approveStatus = ReservationApproveStatus.APPROVE;
    }

    public void decline() {
        this.approveStatus = ReservationApproveStatus.DECLINE;
    }

    public void visit() {
        this.visitYn = true;
    }
}

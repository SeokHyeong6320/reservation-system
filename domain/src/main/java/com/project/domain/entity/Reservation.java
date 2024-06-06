package com.project.domain.entity;

import com.project.domain.model.ReservationDomainForm;
import com.project.domain.type.ReservationApproveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.project.domain.type.ReservationApproveStatus.PENDING;

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
    private boolean visitYn;

    @Column(name = "reserv_visit_avail_dt")
    private LocalDateTime visitAvailDt;

    @Column(name = "reserv_code")
    private String code;

    @Column(name = "reserv_review_yn")
    private boolean reviewYn;

    public static Reservation makeReservation
            (User customer, Store store, ReservationDomainForm form) {

        return Reservation.builder()
                .customer(customer)
                .store(store)
                .contactNumber(form.getContact())
                .reserveDt(form.getReserveDt())
                .visitAvailDt(form.getReserveDt().minusMinutes(10))
                .approveStatus(PENDING)
                .visitYn(false)
                .reviewYn(false)
                .build();
    }

    public void setReservationCode(String code) {
        this.code = code;
    }

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

    public void writeReview() {
        reviewYn = true;
    }
}

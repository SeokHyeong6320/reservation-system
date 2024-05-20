package com.project.reservation.entity;

import com.project.reservation.entity.baseentity.BaseEntity;
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

    @Column(name = "reserv_dt")
    private LocalDateTime reserveDt;

    @Column(name = "reserv_approv")
    private Boolean isApprove;

    @Column(name = "reserv_visit")
    private Boolean isVisit;

    @Column(name = "reserv_visit_dt")
    private LocalDateTime visitDt;
}

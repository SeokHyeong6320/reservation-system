package com.project.reservation.customer.entity;

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
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserv_id")
    private Reservation reservation;

    @Column(name = "review_title")
    private String title;

    @Column(name = "review_content")
    private String content;

    @Column(name = "review_star")
    private Integer star;

    @Column(name = "review_reg_dt")
    private LocalDateTime regDt;

    @Column(name = "review_upd_dt")
    private LocalDateTime updateDt;



}

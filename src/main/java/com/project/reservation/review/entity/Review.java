package com.project.reservation.review.entity;

import com.project.reservation.auth.entity.User;
import com.project.reservation.common.entity.BaseEntity;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.review.model.ReviewForm;
import com.project.reservation.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.project.reservation.review.model.ReviewForm.*;

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
    @JoinColumn(name = "user_id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

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

    public static Review fromCreateForm(CreateReviewForm form, Reservation reservation) {
        return Review.builder()
                .customer(reservation.getCustomer())
                .reservation(reservation)
                .title(form.getTitle())
                .content(form.getContent())
                .star(form.getStar())
                .regDt(LocalDateTime.now())
                .build();

    }


}

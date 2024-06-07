package com.project.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.project.domain.model.ReviewDomainForm.CreateReviewForm;
import static com.project.domain.model.ReviewDomainForm.UpdateReviewForm;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User customer;

    @Column(name = "customer_id")
    private Long customerId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id")
//    private Store store;

    @Column(name = "store_id")
    private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
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
                .customerId(reservation.getCustomerId())
                .storeId(reservation.getStoreId())
                .reservation(reservation)
                .title(form.getTitle())
                .content(form.getContent())
                .star(form.getStar())
                .regDt(LocalDateTime.now())
                .build();
    }

    public void updateReview(UpdateReviewForm form) {
        this.title = form.getTitle();
        this.content = form.getContent();
        this.star = form.getStar();
        this.updateDt = LocalDateTime.now();
    }


}

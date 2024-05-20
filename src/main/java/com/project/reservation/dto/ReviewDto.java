package com.project.reservation.dto;

import com.project.reservation.entity.Reservation;
import com.project.reservation.entity.Review;
import com.project.reservation.entity.Store;
import com.project.reservation.entity.User;
import com.project.reservation.entity.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {

    private Long id;

    private Store store;

    private User customer;

    private Reservation reservation;

    private String title;

    private String content;

    private Integer star;

    private LocalDateTime regDt;

    private LocalDateTime updateDt;




    public static ReviewDto fromEntity(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .store(review.getStore())
                .customer(review.getCustomer())
                .reservation(review.getReservation())
                .title(review.getTitle())
                .content(review.getContent())
                .star(review.getStar())
                .regDt(review.getRegDt())
                .updateDt(review.getUpdateDt())
                .build();
    }
}

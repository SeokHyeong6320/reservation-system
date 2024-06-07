package com.project.domain.dto;

import com.project.domain.entity.Reservation;
import com.project.domain.entity.Review;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
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

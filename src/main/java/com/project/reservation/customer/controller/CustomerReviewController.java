package com.project.reservation.customer.controller;

import com.project.reservation.auth.entity.QUser;
import com.project.reservation.common.model.SuccessResponse;
import com.project.reservation.customer.service.CustomerReviewService;
import com.project.reservation.reservation.entity.QReservation;
import com.project.reservation.review.dto.ReviewDto;
import com.project.reservation.review.model.ReviewForm;
import com.project.reservation.review.model.ReviewResponse;
import com.project.reservation.security.util.TokenValidator;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.project.reservation.auth.entity.QUser.*;
import static com.project.reservation.reservation.entity.QReservation.*;
import static com.project.reservation.review.model.ReviewForm.*;
import static com.project.reservation.security.constant.SecurityConst.*;

@RestController
@RequestMapping("/customer/{id}/review")
@RequiredArgsConstructor
public class CustomerReviewController {

    private final CustomerReviewService reviewService;
    private final TokenValidator tokenValidator;


    @PostMapping
    public ResponseEntity<?> postReview(
            @PathVariable Long id,
            @RequestHeader(TOKEN_HEADER) String header,
            @RequestBody @Validated CreateReviewForm form
            ) {

        tokenValidator.validateUser(id, header);

        ReviewDto reviewDto = reviewService.createReview(id, form);

        return ResponseEntity.ok(
                SuccessResponse.of(ReviewResponse.fromDto(reviewDto))
        );
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long id,
            @PathVariable Long reviewId,
            @RequestHeader(TOKEN_HEADER) String header,
            @RequestBody @Validated UpdateReviewForm form
    ) {

        tokenValidator.validateUser(id, header);

        ReviewDto reviewDto = reviewService.updateReview(id, reviewId, form);

        return ResponseEntity.ok(
                SuccessResponse.of(ReviewResponse.fromDto(reviewDto))
        );
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable Long id,
            @PathVariable Long reviewId,
            @RequestHeader(TOKEN_HEADER) String header
    ) {

        tokenValidator.validateUser(id, header);

        reviewService.deleteReview(id, reviewId);

        return ResponseEntity.ok(
                SuccessResponse.of("deleteComplete")
        );
    }






}

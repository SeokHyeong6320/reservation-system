package com.project.reservation.customer.controller;

import com.project.reservation.auth.entity.QUser;
import com.project.reservation.customer.service.CustomerReviewService;
import com.project.reservation.reservation.entity.QReservation;
import com.project.reservation.review.dto.ReviewDto;
import com.project.reservation.review.model.ReviewForm;
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
    private final JPAQueryFactory queryFactory;

    @PostMapping
    public ResponseEntity<?> postReview(
            @PathVariable Long id,
            @RequestHeader(TOKEN_HEADER) String header,
            @RequestBody @Validated CreateReviewForm form
            ) {

        tokenValidator.validateUser(id, header);

        ReviewDto reviewDto = reviewService.createReview(id, form);

        return null;

    }




}

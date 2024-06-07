package com.project.api.controller.customer;

import com.project.common.model.SuccessResponse;
import com.project.customerservice.service.CustomerReviewService;
import com.project.domain.dto.ReviewDto;
import com.project.domain.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.project.customerservice.model.CustomerReviewForm.CreateReviewForm;
import static com.project.customerservice.model.CustomerReviewForm.UpdateReviewForm;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("customer/{customerEmail}/review")
@RequiredArgsConstructor
public class CustomerReviewController {

    private final CustomerReviewService reviewService;
//    private final TokenValidator tokenValidator;


    /**
     * 리뷰 작성 엔드포인트
     */
    @PostMapping
    public ResponseEntity<?> postReview(
            @PathVariable String customerEmail,
//            @RequestHeader(TOKEN_HEADER) String header,
            @RequestBody @Validated CreateReviewForm form
            ) {

//        tokenValidator.validateUser(id, header);

        ReviewDto reviewDto = reviewService.createReview(customerEmail, form);

        return ResponseEntity.status(CREATED).body(
                SuccessResponse.of(ReviewResponse.fromDto(reviewDto))
        );
    }


    /**
     * 리뷰 수정 엔드포인트
     */
    @PatchMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable String customerEmail,
            @PathVariable Long reviewId,
//            @RequestHeader(TOKEN_HEADER) String header,
            @RequestBody @Validated UpdateReviewForm form
    ) {

//        tokenValidator.validateUser(id, header);

        ReviewDto reviewDto = reviewService.updateReview(customerEmail, reviewId, form);

        return ResponseEntity.ok(
                SuccessResponse.of(ReviewResponse.fromDto(reviewDto))
        );
    }

    /**
     * 리뷰 삭제 엔드포인트
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable String customerEmail,
            @PathVariable Long reviewId
//            @RequestHeader(TOKEN_HEADER) String header
    ) {

//        tokenValidator.validateUser(id, header);

        reviewService.deleteReview(customerEmail, reviewId);

        return ResponseEntity.ok(
                SuccessResponse.of("deleteComplete")
        );
    }






}

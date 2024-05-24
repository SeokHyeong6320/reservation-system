package com.project.reservation.customer.service;

import com.project.reservation.review.dto.ReviewDto;

import static com.project.reservation.review.model.ReviewForm.CreateReviewForm;
import static com.project.reservation.review.model.ReviewForm.UpdateReviewForm;

public interface CustomerReviewService {
    ReviewDto createReview(Long id, CreateReviewForm form);

    ReviewDto updateReview(Long id, Long reviewId, UpdateReviewForm form);

    void deleteReview(Long id, Long reviewId);
}

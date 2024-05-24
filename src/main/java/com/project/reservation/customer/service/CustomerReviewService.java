package com.project.reservation.customer.service;

import com.project.reservation.review.dto.ReviewDto;
import com.project.reservation.review.model.ReviewForm;

public interface CustomerReviewService {
    ReviewDto createReview(Long id, ReviewForm.CreateReviewForm form);

}

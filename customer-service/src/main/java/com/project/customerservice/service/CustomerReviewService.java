package com.project.customerservice.service;

import com.project.domain.dto.ReviewDto;

import static com.project.customerservice.model.CustomerReviewForm.CreateReviewForm;
import static com.project.customerservice.model.CustomerReviewForm.UpdateReviewForm;


public interface CustomerReviewService {

    ReviewDto createReview(Long userId, CreateReviewForm form);

    ReviewDto updateReview(Long userId, Long reviewId, UpdateReviewForm form);

    void deleteReview(Long userId, Long reviewId);

}

package com.project.customerservice.service;

import com.project.customerservice.model.CustomerReviewForm;
import com.project.domain.dto.ReviewDto;
import com.project.reviewservice.model.ReviewForm;

import static com.project.customerservice.model.CustomerReviewForm.*;


public interface CustomerReviewService {

    ReviewDto createReview(Long userId, CreateReviewForm form);

    ReviewDto updateReview(Long userId, Long reviewId, UpdateReviewForm form);

    void deleteReview(Long userId, Long reviewId);

}

package com.project.customerservice.service;

import com.project.domain.dto.ReviewDto;

import static com.project.customerservice.model.CustomerReviewForm.CreateReviewForm;
import static com.project.customerservice.model.CustomerReviewForm.UpdateReviewForm;


public interface CustomerReviewService {

    ReviewDto createReview(String customerEmail, CreateReviewForm form);

    ReviewDto updateReview(String customerEmail, Long reviewId, UpdateReviewForm form);

    void deleteReview(String customerEmail, Long reviewId);

}

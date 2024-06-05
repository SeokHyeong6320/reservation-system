package com.project.customerservice.service;


import com.project.domain.dto.ReviewDto;

import static com.project.domain.model.ReviewDomainForm.*;

public interface CustomerReviewService {
    ReviewDto createReview(Long id, CreateReviewForm form);

    ReviewDto updateReview(Long id, Long reviewId, UpdateReviewForm form);

    void deleteReview(Long id, Long reviewId);
}

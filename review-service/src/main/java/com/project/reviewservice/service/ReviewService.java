package com.project.reviewservice.service;


import com.project.domain.dto.ReviewDto;
import com.project.domain.entity.Reservation;

import static com.project.domain.model.ReviewDomainForm.*;

public interface ReviewService {
    ReviewDto createReview
            (Long userId, Reservation reservation, CreateReviewForm form);

    ReviewDto updateReview(Long id, Long reviewId, UpdateReviewForm form);

    void deleteReview(Long id, Long reviewId);
}

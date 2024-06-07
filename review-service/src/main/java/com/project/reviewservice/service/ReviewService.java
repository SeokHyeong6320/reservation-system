package com.project.reviewservice.service;


import com.project.domain.dto.ReviewDto;
import com.project.domain.entity.Reservation;
import com.project.domain.entity.User;

import static com.project.domain.model.ReviewDomainForm.CreateReviewForm;
import static com.project.domain.model.ReviewDomainForm.UpdateReviewForm;

public interface ReviewService {
    ReviewDto createReview
            (Reservation reservation, CreateReviewForm form);

    ReviewDto updateReview(User customer, Long reviewId, UpdateReviewForm form);

    void deleteReview(User user, Long reviewId);
}

package com.project.partnerservice.service;

import com.project.domain.entity.User;

public interface PartnerReviewService {

    void deleteReview(String partnerEmail, Long reviewId);
}

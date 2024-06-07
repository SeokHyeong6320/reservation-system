package com.project.partnerservice.service.impl;

import com.project.partnerservice.service.PartnerReviewService;
import com.project.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class PartnerReviewServiceImpl implements PartnerReviewService {

    private final ReviewService reviewService;


    /**
     * review-service 모듈로 넘겨 리뷰 삭제 처리
     */
    @Override
    public void deleteReview(Long userId, Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }
}

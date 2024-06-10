package com.project.partnerservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.entity.User;
import com.project.domain.repository.UserRepository;
import com.project.partnerservice.service.PartnerReviewService;
import com.project.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.common.exception.ErrorCode.USER_NOT_FOUND;


@Service
@Transactional
@RequiredArgsConstructor
public class PartnerReviewServiceImpl implements PartnerReviewService {

    private final ReviewService reviewService;
    private final UserRepository userRepository;


    /**
     * review-service 모듈로 넘겨 리뷰 삭제 처리
     */
    @Override
    public void deleteReview(String partnerEmail, Long reviewId) {

        User findUser = findUserById(partnerEmail);

        reviewService.deleteReview(findUser, reviewId);
    }

    private User findUserById(String partnerEmail) {
        return userRepository.findByEmail(partnerEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}

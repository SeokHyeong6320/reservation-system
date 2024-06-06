package com.project.reviewservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.ReviewDto;
import com.project.domain.entity.Reservation;
import com.project.domain.entity.Review;
import com.project.domain.entity.Store;
import com.project.domain.repository.ReservationRepository;
import com.project.domain.repository.ReviewRepository;
import com.project.domain.repository.StoreRepository;
import com.project.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.common.exception.ErrorCode.*;
import static com.project.domain.model.ReviewDomainForm.*;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;


    /**
     * 리뷰 작성
     */
    @Override
    public ReviewDto createReview(Long userId, Reservation reservation, CreateReviewForm form) {

        Review newReview = Review.fromCreateForm(form, reservation);

        // 상점 별점 업데이트
        updateStar(newReview.getStore(), newReview.getStar());

        Review savedReview = reviewRepository.save(newReview);

        // 해당 예약건 리뷰 작성 표시
        reservation.writeReview();


        return ReviewDto.fromEntity(savedReview);
    }

    /**
     * 리뷰 수정
     */
    @Override
    public ReviewDto updateReview(Long id, Long reviewId, UpdateReviewForm form) {

        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        // 리뷰 작성자인지 확인
        checkDeleteAuthority(id, findReview);

        // 리뷰 업데이트
        findReview.updateReview(form);

        // 상점 별점 업데이트
        Store findStore = findReview.getStore();
        updateStar(findStore, findReview.getStar());

        return ReviewDto.fromEntity(findReview);
    }

    /**
     * 리뷰 삭제
     */
    @Override
    public void deleteReview(Long id, Long reviewId) {

        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        // 리뷰 삭제 가능한 유저인지 확인 (작성자, 상점 주인)
        checkDeleteAuthority(id, findReview);

        // 상점 별점 업데이트
        Store findStore = findReview.getStore();
        updateStar(findStore, 0);

        reviewRepository.delete(findReview);
    }


    /**
     * 상점 별점 업데이트
     */
    private void updateStar(Store store, int star) {
        Long count = reviewRepository.countByStore(store);
        store.calculateStar(star, count);
    }

    /**
     * 리뷰 작성자인지 확인
     */
    private void checkDeleteAuthority(Long id, Review findReview) {
        if (!Objects.equals(id, findReview.getCustomer().getId())) {
            throw new CustomException(REVIEW_CUSTOMER_NOT_MATCH);
        }
        if (!Objects.equals(id, findReview.getStore().getOwner().getId())) {
            throw new CustomException(STORE_OWNER_NOT_MATCH);
        }
    }
}

package com.project.reviewservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.ReviewDto;
import com.project.domain.entity.Reservation;
import com.project.domain.entity.Review;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.repository.ReviewRepository;
import com.project.domain.repository.StoreRepository;
import com.project.domain.type.UserType;
import com.project.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.common.exception.ErrorCode.*;
import static com.project.domain.model.ReviewDomainForm.CreateReviewForm;
import static com.project.domain.model.ReviewDomainForm.UpdateReviewForm;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;


    /**
     * 리뷰 작성
     */
    @Override
    public ReviewDto createReview(Reservation reservation, CreateReviewForm form) {

        Review newReview = Review.fromCreateForm(form, reservation);
        Store findStore = storeRepository.findById(reservation.getStore().getId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        // 상점 별점 업데이트
        updateStar(findStore, newReview.getStar());

        Review savedReview = reviewRepository.save(newReview);

        // 해당 예약건 리뷰 작성 표시
        reservation.writeReview();

        return ReviewDto.fromEntity(savedReview);
    }

    /**
     * 리뷰 수정
     */
    @Override
    public ReviewDto updateReview(User customer, Long reviewId, UpdateReviewForm form) {

        Review findReview = findReviewById(reviewId);

        // 리뷰 작성자인지 확인
        checkReviewWriter(customer.getId(), findReview);

        // 리뷰 업데이트
        findReview.updateReview(form);

        // 상점 별점 업데이트
        updateStar(findReview.getStore(), findReview.getStar());

        return ReviewDto.fromEntity(findReview);
    }

    /**
     * 리뷰 삭제
     */
    @Override
    public void deleteReview(User user, Long reviewId) {

        Review findReview = findReviewById(reviewId);

        // 리뷰 삭제 가능한 유저인지 확인 (작성자, 상점 주인)
        checkDeleteAuthority(user, findReview);

        // 상점 별점 업데이트
        updateStar(findReview.getStore(), 0);

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
     * 리뷰 삭제 가능한 권한인지 확인
     */
    private void checkDeleteAuthority(User user, Review findReview) {

        // 고객일 경우 리뷰 작성자여야 함
        if (user.getUserType() == UserType.CUSTOMER) {
            checkReviewWriter(user.getId(), findReview);

        } else {
            // 파트너일 경우 리뷰 작성된 상점 주인이여야 함
            checkStoreOwner(user.getId(), findReview.getStore());
        }

    }

    /**
     * 상점 주인인지 확인
     */
    private void checkStoreOwner(Long id, Store store) {
        if (!Objects.equals(id, store.getOwner().getId())) {
            throw new CustomException(STORE_OWNER_NOT_MATCH);
        }
    }

    /**
     * 리뷰 작성자인지 확인
     */
    private void checkReviewWriter(Long id, Review findReview) {
        if (!Objects.equals(id, findReview.getCustomer().getId())) {
            throw new CustomException(REVIEW_CUSTOMER_NOT_MATCH);
        }
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));
    }
}

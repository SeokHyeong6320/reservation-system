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
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;


    @Override
    public ReviewDto createReview(Long userId, Reservation reservation, CreateReviewForm form) {

//        Reservation findReservation =
//                reservationRepository.findById(form.getReservationId())
//                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
//
//
//
//        // 해당 예약이 유저의 것인지 확인
//        if(!Objects.equals(userId, findReservation.getCustomer().getId())) {
//            throw new CustomException(RESERVATION_CUSTOMER_NOT_MATCH);
//        }
//
//        // 방문완료한 예약인지 확인
//        if (!findReservation.isVisitYn()) {
//            throw new CustomException(RESERVATION_NOT_VISIT);
//        }
//
//        // 리뷰 이미 작성한 예약인지
//        if (findReservation.isReviewYn()) {
//            throw new CustomException(REVIEW_ALREADY_WRITTEN);
//        }

        Review newReview = Review.fromCreateForm(form, reservation);

        // 상점 별점 업데이트
        updateStar(newReview.getStore(), newReview.getStar());

        Review savedReview = reviewRepository.save(newReview);

        // 해당 예약건 리뷰 작성 표시
        reservation.writeReview();


        return ReviewDto.fromEntity(savedReview);
    }

    @Override
    public ReviewDto updateReview(Long id, Long reviewId, UpdateReviewForm form) {

        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        // 리뷰 작성자인지 확인
        checkReviewWriter(id, findReview);

        // 리뷰 업데이트
        findReview.updateReview(form);

        // 상점 별점 업데이트
        Store findStore = findReview.getStore();
        updateStar(findStore, findReview.getStar());

        return ReviewDto.fromEntity(findReview);
    }

    @Override
    public void deleteReview(Long id, Long reviewId) {

        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        // 리뷰 작성자인지 확인
        checkReviewWriter(id, findReview);

        // 상점 별점 업데이트
        Store findStore = findReview.getStore();
        updateStar(findStore, 0);

        reviewRepository.delete(findReview);
    }


    // 상점 별점 업데이트
    private void updateStar(Store store, int star) {
        Long count = reviewRepository.countByStore(store);
        store.calculateStar(star, count);
    }

    /**
     * 리뷰 작성자인지 확인
     */
    private void checkReviewWriter(Long id, Review findReview) {
        if(!Objects.equals(id, findReview.getCustomer().getId())) {
            throw new CustomException(REVIEW_CUSTOMER_NOT_MATCH);
        }
    }
}

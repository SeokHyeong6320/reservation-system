package com.project.reservation.customer.service.impl;

import com.project.reservation.auth.entity.User;
import com.project.reservation.common.exception.CustomException;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.reservation.repository.ReservationRepository;
import com.project.reservation.review.dto.ReviewDto;
import com.project.reservation.review.entity.Review;
import com.project.reservation.review.repository.ReviewRepository;
import com.project.reservation.customer.service.CustomerReviewService;
import com.project.reservation.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.reservation.common.exception.ErrorCode.*;
import static com.project.reservation.review.model.ReviewForm.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerReviewServiceImpl implements CustomerReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;


    @Override
    public ReviewDto createReview(Long id, CreateReviewForm form) {

        Reservation findReservation =
                reservationRepository.findById(form.getReservationId())
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));



        // 해당 예약이 유저의 것인지 확인
        if(!Objects.equals(id, findReservation.getCustomer().getId())) {
            throw new CustomException(RESERVATION_CUSTOMER_NOT_MATCH);
        }

        // 방문완료한 예약인지 확인
        if (!findReservation.isVisitYn()) {
            throw new CustomException(RESERVATION_NOT_VISIT);
        }

        // 리뷰 이미 작성한 예약인지
        if (findReservation.isReviewYn()) {
            throw new CustomException(REVIEW_ALREADY_WRITTEN);
        }

        Review newReview = Review.fromCreateForm(form, findReservation);

        // 상점 별점 업데이트
        updateStar(newReview.getStore(), newReview.getStar());

        Review savedReview = reviewRepository.save(newReview);

        // 해당 예약건 리뷰 작성 표시
        findReservation.writeReview();


        return ReviewDto.fromEntity(savedReview);
    }

    private void updateStar(Store store, int star) {

        Long count = reviewRepository.countByStore(store);
        store.calculateStar(star, count);

    }
}

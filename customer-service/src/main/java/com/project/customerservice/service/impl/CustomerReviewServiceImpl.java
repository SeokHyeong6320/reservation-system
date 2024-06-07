package com.project.customerservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.customerservice.service.CustomerReviewService;
import com.project.domain.dto.ReviewDto;
import com.project.domain.entity.Reservation;
import com.project.domain.repository.ReservationRepository;
import com.project.reservationservice.service.ReservationRegisterService;
import com.project.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.common.exception.ErrorCode.RESERVATION_NOT_FOUND;
import static com.project.customerservice.model.CustomerReviewForm.CreateReviewForm;
import static com.project.customerservice.model.CustomerReviewForm.UpdateReviewForm;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerReviewServiceImpl implements CustomerReviewService {

    private final ReservationRepository reservationRepository;
    private final ReservationRegisterService reservationRegisterService;
    private final ReviewService reviewService;


    @Override
    public ReviewDto createReview(Long userId, CreateReviewForm form) {

        Reservation findReservation =
                reservationRepository.findById(form.getReservationId())
                        .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        // 리뷰 작성 가능한지 체크
        reservationRegisterService
                .checkAvailWriteReview(userId, findReservation);

        // 리뷰 서비스 모듈로 넘겨서 리뷰 생성
        return reviewService
                .createReview(userId, findReservation, form.toDomainForm());

    }

    @Override
    public ReviewDto updateReview(Long userId, Long reviewId, UpdateReviewForm form) {

        // 리뷰 서비스 모듈로 넘겨서 리뷰 수정
        return reviewService.updateReview(userId, reviewId, form.toDomainForm());
    }

    @Override
    public void deleteReview(Long userId, Long reviewId) {

        // 리뷰 서비스 모듈로 넘겨서 리뷰 삭제
        reviewService.deleteReview(userId, reviewId);
    }
}

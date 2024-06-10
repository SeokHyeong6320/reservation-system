package com.project.reservationservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.InitReservationDto;
import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.Reservation;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.repository.ReservationRepository;
import com.project.domain.repository.StoreRepository;
import com.project.domain.repository.UserRepository;
import com.project.reservationservice.service.ReservationRegisterService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;

import static com.project.common.exception.ErrorCode.*;


@Service
@Transactional
@RequiredArgsConstructor
public class ReservationRegisterServiceImpl implements ReservationRegisterService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    public ReservationDto makeReservation(InitReservationDto initReservationDto) {

//        User findUser = userRepository
//                .findById(id)
//                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

//        Store findStore = storeRepository
//                .findById(form.getStoreId())
//                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
//
//        // 현재 예약 가능한 상태인지 확인
//        if (!findStore.isAvail()) {
//           throw new CustomException(STORE_UNAVAILABLE);
//        }

//        // 예약 가능 시간인지 확인 (키오스크 방문 확인이 예약 10분 전까지만 가능 하므로 10분 더해 주었음)
//        if (form.getReserveDt().minusMinutes(10).isBefore(LocalDateTime.now())) {
//            throw new CustomException(RESERVATION_DATE_INVALID);
//        }

        User findUser = userRepository.findById(initReservationDto.getCustomerId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Store findStore = storeRepository.findById(initReservationDto.getStoreId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        Reservation reservation =
                Reservation.makeReservation(initReservationDto, findUser, findStore);

        setReservationCode(reservation);

        Reservation savedReservation = reservationRepository.save(reservation);

        // 고객의 예약 리스트에 추가
//        customer.getReservationList().add(reservation);

        return ReservationDto.fromEntity(savedReservation);
    }

    /**
     * 리뷰 작성 가능한지 체크하는 로직
     */
    @Override
    @Transactional(readOnly = true)
    public void checkAvailWriteReview(User customer, Reservation reservation) {

        // 해당 예약이 유저의 것인지 확인
//        if(!Objects.equals(userId, reservation.getCustomer().getId())) {
        if(!Objects.equals(customer.getId(), reservation.getCustomer().getId())) {
            throw new CustomException(RESERVATION_CUSTOMER_NOT_MATCH);
        }

        // 방문완료한 예약인지 확인
        if (!reservation.isVisitYn()) {
            throw new CustomException(RESERVATION_NOT_VISIT);
        }

        // 리뷰 이미 작성한 예약인지
        if (reservation.isReviewYn()) {
            throw new CustomException(REVIEW_ALREADY_WRITTEN);
        }

    }

    /**
     * 추후 키오스크에서 예약 확인 시 사용될 8자리 코드 생성
     */
    private void setReservationCode(Reservation reservation) {

        String randomCode = RandomStringUtils
                .random(8, true, true)
                .toUpperCase(Locale.ROOT);

        reservation.setReservationCode(randomCode);
    }
}

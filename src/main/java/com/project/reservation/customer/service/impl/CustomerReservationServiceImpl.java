package com.project.reservation.customer.service.impl;

import com.project.reservation.auth.entity.User;
import com.project.reservation.auth.repository.UserRepository;
import com.project.reservation.common.exception.CustomException;
import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.reservation.model.ReservationForm;
import com.project.reservation.reservation.repository.ReservationRepository;
import com.project.reservation.customer.service.CustomerReservationService;
import com.project.reservation.store.entity.Store;
import com.project.reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

import static com.project.reservation.common.exception.ErrorCode.*;
import static com.project.reservation.reservation.entity.ReservationApproveStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerReservationServiceImpl implements CustomerReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    public ReservationDto reservation(Long id, ReservationForm form) {

        User findUser = userRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Store findStore = storeRepository
                .findById(form.getStoreId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        // 현재 예약 가능한 상태인지 확인
        if (!findStore.isAvail()) {
           throw new CustomException(STORE_UNAVAILABLE);
        }

        // 예약 가능 시간인지 확인 (키오스크 방문 확인이 예약 10분 전까지만 가능 하므로 10분 더해 주었음)
        if (form.getReserveDt().minusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new CustomException(RESERVATION_DATE_INVALID);
        }

        Reservation reservation = makeReservation(findUser, findStore, form);

        Reservation savedReservation = reservationRepository.save(reservation);

        // 고객의 예약 리스트에 추가
        findUser.getReservationList().add(reservation);

        return ReservationDto.fromEntity(savedReservation);
    }

    private Reservation makeReservation
            (User customer, Store store, ReservationForm form) {

        String randomCode = RandomStringUtils.random(8, true, true).toUpperCase(Locale.ROOT);

        return Reservation.builder()
                .customer(customer)
                .store(store)
                .contactNumber(form.getContact())
                .reserveDt(form.getReserveDt())
                .visitAvailDt(form.getReserveDt().minusMinutes(10))
                .code(randomCode)
                .approveStatus(PENDING)
                .visitYn(false)
                .reviewYn(false)
                .build();
    }
}

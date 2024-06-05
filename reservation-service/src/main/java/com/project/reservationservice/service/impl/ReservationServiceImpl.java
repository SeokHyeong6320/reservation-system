package com.project.reservationservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.Reservation;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.repository.ReservationRepository;
import com.project.domain.repository.StoreRepository;
import com.project.domain.repository.UserRepository;
import com.project.reservationservice.model.ReservationForm;
import com.project.reservationservice.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

import static com.project.common.exception.ErrorCode.*;
import static com.project.domain.type.ReservationApproveStatus.*;


@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

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

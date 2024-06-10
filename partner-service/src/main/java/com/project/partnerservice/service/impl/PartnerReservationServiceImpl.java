package com.project.partnerservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.User;
import com.project.domain.repository.UserRepository;
import com.project.partnerservice.service.PartnerReservationService;
import com.project.reservationservice.service.ReservationManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.project.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class PartnerReservationServiceImpl implements PartnerReservationService {

    private final UserRepository userRepository;
    private final ReservationManagementService reservationManagementService;

    /**
     * 예약 타임테이블 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReservationDto> getReservationTimeTable(String partnerEmail, LocalDate date) {

        User findUser = findUserByEmail(partnerEmail);

        // reservation-service 모듈로 넘겨서 처리
        return reservationManagementService
                .getReservationTimeTable(findUser, date);

    }

    /**
     * 예약 승인
     */
    @Override
    public ReservationDto confirmReservation(String partnerEmail, Long reservationId) {

        User findUser = findUserByEmail(partnerEmail);

        // reservation-service 모듈로 넘겨서 처리
        return reservationManagementService
                .confirmReservation(findUser.getId(), reservationId);
    }

    /**
     * 예약 거절
     */
    @Override
    public ReservationDto declineReservation(String partnerEmail, Long reservationId) {

        User findUser = findUserByEmail(partnerEmail);

        // reservation-service 모듈로 넘겨서 처리
        return reservationManagementService
                .declineReservation(findUser.getId(), reservationId);
    }



    private User findUserByEmail(String partnerEmail) {
        return userRepository
                .findByEmail(partnerEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}

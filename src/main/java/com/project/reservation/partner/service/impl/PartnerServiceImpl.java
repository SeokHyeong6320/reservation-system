package com.project.reservation.partner.service.impl;

import com.project.reservation.auth.dto.UserDto;
import com.project.reservation.auth.entity.User;
import com.project.reservation.auth.entity.UserType;
import com.project.reservation.auth.repository.UserRepository;
import com.project.reservation.common.exception.CustomException;
import com.project.reservation.partner.service.PartnerService;
import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.reservation.entity.ReservationApproveStatus;
import com.project.reservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.project.reservation.common.exception.ErrorCode.*;
import static com.project.reservation.reservation.entity.ReservationApproveStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;


    // 파트너 가입 서비스
    @Override
    public UserDto enrollPartner(Long id) {

        User findUser = userRepository
                .findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 이미 파트너인 경우 에러 발생
        if (findUser.getUserType() == UserType.PARTNER) {
            throw new CustomException(PARTNER_ALREADY_ENROLLED);
        }

        // userType을 PARTNER로 변경
        findUser.enrollPartner();

        // dto로 변환해서 반환
        return UserDto.fromEntity(findUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservationDto> getReservationTimeTable(Long id, LocalDate date) {

        User findUser = userRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return reservationRepository.findAllByTime(findUser, year, month, day)
                .stream()
                .map(ReservationDto::fromEntity)
                .toList();
    }

    @Override
    public ReservationDto confirmReservation(Long id, Long reservationId) {

        Reservation findReservation =
                reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        // 예약한 식당의 소유자가 일치하는지 확인
        Long storeOwnerId = reservationRepository.findStoreOwnerId(reservationId);
        if (!id.equals(storeOwnerId)) {
            throw new CustomException(RESERVATION_OWNER_NOT_MATCH);
        }

        // 예약이 이미 승인된 경우 검증
        if (findReservation.getApproveStatus() == APPROVE) {
            throw new CustomException(RESERVATION_ALREADY_APPROVED);
        }

        // 입장 가능 시간이 지나서 예약이 만료된 경우 검증
        if(!findReservation.checkTime()) {
            throw new CustomException(RESERVATION_ALREADY_EXPIRED);
        }

        // 예약 상태를 승인으로 변경
        findReservation.approve();

        return ReservationDto.fromEntity(findReservation);
    }

    @Override
    public ReservationDto declineReservation(Long id, Long reservationId) {
        Reservation findReservation =
                reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        // 예약한 식당의 소유자가 일치하는지 확인
        Long storeOwnerId = reservationRepository.findStoreOwnerId(reservationId);
        if (!id.equals(storeOwnerId)) {
            throw new CustomException(RESERVATION_OWNER_NOT_MATCH);
        }

        // 예약이 이미 거절된 경우 검증
        if (findReservation.getApproveStatus() == APPROVE) {
            throw new CustomException(RESERVATION_ALREADY_DECLINED);
        }

        // 입장 가능 시간이 지나서 예약이 만료된 경우 검증
        if(!findReservation.checkTime()) {
            throw new CustomException(RESERVATION_ALREADY_EXPIRED);
        }

        // 예약 상태를 거절으로 변경
        findReservation.decline();

        return ReservationDto.fromEntity(findReservation);
    }
}

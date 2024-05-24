package com.project.reservation.partner.service.impl;

import com.project.reservation.auth.entity.User;
import com.project.reservation.auth.repository.UserRepository;
import com.project.reservation.common.exception.CustomException;
import com.project.reservation.partner.service.PartnerReservationService;
import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.project.reservation.common.exception.ErrorCode.*;
import static com.project.reservation.reservation.entity.ReservationApproveStatus.APPROVE;

@Service
@Transactional
@RequiredArgsConstructor
public class PartnerReservationServiceImpl implements PartnerReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

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

    /**
     * 예약 승인 기능
     */
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
        if(!findReservation.availVisit()) {
            throw new CustomException(RESERVATION_ALREADY_EXPIRED);
        }

        // 예약 상태를 승인으로 변경
        findReservation.approve();

        return ReservationDto.fromEntity(findReservation);
    }

    /**
     * 예약 거절 기능
     */
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
        if(!findReservation.availVisit()) {
            throw new CustomException(RESERVATION_ALREADY_EXPIRED);
        }

        // 예약 상태를 거절으로 변경
        findReservation.decline();

        return ReservationDto.fromEntity(findReservation);
    }

}

package com.project.reservationservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.Reservation;
import com.project.domain.entity.User;
import com.project.domain.repository.ReservationRepository;
import com.project.reservationservice.service.ReservationManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.project.common.exception.ErrorCode.*;
import static com.project.domain.type.ReservationApproveStatus.APPROVE;
import static com.project.domain.type.ReservationApproveStatus.DECLINE;


@Service
@Transactional
@RequiredArgsConstructor
public class ReservationManagementServiceImpl implements ReservationManagementService {

    private final ReservationRepository reservationRepository;

    /**
     * 예약 타임테이블 조회
     */
    @Transactional(readOnly = true)
    @Override
    public List<ReservationDto> getReservationTimeTable(User partner, LocalDate date) {

        // date에서 년, 월, 일 가져옴
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        // 해당 일자의 예약들 조회
        return reservationRepository.findAllByTime(partner, year, month, day)
                .stream()
                .map(ReservationDto::fromEntity)
                .toList();
    }

    /**
     * 예약 승인 기능
     */
    @Override
    public ReservationDto confirmReservation(Long userId, Long reservationId) {

        Reservation findReservation = findReservationById(reservationId);

        // 예약한 식당의 소유자가 일치하는지 확인
        checkStoreOwner(userId, reservationId);

        // 예약이 이미 승인된 경우 검증
        checkApproveStatus(findReservation);

        // 입장 가능 시간이 지나서 예약이 만료된 경우 검증
        checkAvailableEntryTime(findReservation);

        // 예약 상태를 승인으로 변경
        findReservation.approve();

        return ReservationDto.fromEntity(findReservation);
    }

    /**
     * 예약 거절 기능
     */
    @Override
    public ReservationDto declineReservation(Long userId, Long reservationId) {
        Reservation findReservation = findReservationById(reservationId);

        // 예약한 식당의 소유자가 일치하는지 확인
        checkStoreOwner(userId, reservationId);

        // 예약이 이미 거절된 경우 검증
        checkDeclineStatus(findReservation);

        // 입장 가능 시간이 지나서 예약이 만료된 경우 검증
        checkAvailableEntryTime(findReservation);

        // 예약 상태를 거절으로 변경
        findReservation.decline();

        return ReservationDto.fromEntity(findReservation);
    }






    private Reservation findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
    }

    private void checkAvailableEntryTime(Reservation findReservation) {
        if(!findReservation.availVisit()) {
            throw new CustomException(RESERVATION_ALREADY_EXPIRED);
        }
    }

    private void checkApproveStatus(Reservation findReservation) {
        if (findReservation.getApproveStatus() == APPROVE) {
            throw new CustomException(RESERVATION_ALREADY_APPROVED);
        }
    }

    private void checkDeclineStatus(Reservation findReservation) {
        if (findReservation.getApproveStatus() == DECLINE) {
            throw new CustomException(RESERVATION_ALREADY_DECLINED);
        }
    }

    private void checkStoreOwner(Long userId, Long reservationId) {
        Long storeOwnerId = reservationRepository.findStoreOwnerId(reservationId);

        if (!userId.equals(storeOwnerId)) {
            throw new CustomException(RESERVATION_OWNER_NOT_MATCH);
        }
    }



}

package com.project.reservation.kiosk.service.impl;

import com.project.reservation.common.exception.CustomException;
import com.project.reservation.kiosk.model.VisitForm;
import com.project.reservation.kiosk.service.KioskService;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.reservation.common.exception.ErrorCode.*;
import static com.project.reservation.reservation.entity.ReservationApproveStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class KioskServiceImpl implements KioskService {

    private final ReservationRepository reservationRepository;


    /**
     * 고객 방문 체크인
     * @return 고객 이름 반환
     */
    @Override
    public String visitStore(VisitForm form) {

        Reservation findReservation =
                reservationRepository.findById(form.getReservationId())
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        validateVisitAvail(findReservation, form.getContact(), form.getCode());

        findReservation.visit();

        return findReservation.getCustomer().getUsername();
    }

    /**
     * 해당 방문이 유효한 방문인지 확인
     */
    private void validateVisitAvail(Reservation reservation, String email, String code) {

        // 해당 고객이 예약한 예약인지 확인
        if (!reservation.getContactNumber().equals(email)) {
            throw new CustomException(RESERVATION_CUSTOMER_NOT_MATCH);
        }
        // 예약 인증 코드가 일치한지 확인
        if (!reservation.getCode().equals(code)) {
            throw new CustomException(RESERVATION_CODE_NOT_MATCH);
        }
        // 승인된 예약인지 확인
        if (reservation.getApproveStatus() != APPROVE) {
            throw new CustomException(RESERVATION_NOT_APPROVE);
        }
        // 입장 가능 시간인지 확인
        if (!reservation.availVisit()) {
            throw new CustomException(RESERVATION_ALREADY_EXPIRED);
        }
        // 이미 체크인한 예약인지 확인
        if (reservation.isVisitYn()) {
            throw new CustomException(RESERVATION_ALREADY_VISIT);
        }

    }
}

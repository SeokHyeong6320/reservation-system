package com.project.reservationservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.VisitDto;
import com.project.domain.entity.Kiosk;
import com.project.domain.entity.Reservation;
import com.project.domain.model.VisitDomainForm;
import com.project.domain.repository.ReservationRepository;
import com.project.domain.response.VisitResponse;
import com.project.reservationservice.service.ReservationVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.common.exception.ErrorCode.*;
import static com.project.common.exception.ErrorCode.RESERVATION_ALREADY_VISIT;
import static com.project.domain.type.ReservationApproveStatus.APPROVE;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationVisitServiceImpl implements ReservationVisitService {

    private final ReservationRepository reservationRepository;


    @Override
    public VisitDto visit(VisitDomainForm form) {

        Reservation findReservation =
                reservationRepository.findById(form.getReservationId())
                        .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        // 해당 방문이 유효한 방문인지 확인
        validateVisitAvail(findReservation, form);

        // 해당 예약 방문으로 변경
        findReservation.visit();

        return VisitDto.fromReservation(findReservation);
    }

    /**
     * 해당 방문이 유효한 방문인지 확인
     */
    private void validateVisitAvail
            (Reservation reservation, VisitDomainForm form) {

        // 해당 고객이 예약한 예약인지 확인
        if (!reservation.getContactNumber().equals(form.getContact())) {
            throw new CustomException(RESERVATION_CUSTOMER_NOT_MATCH);
        }
        // 예약 인증 코드가 일치한지 확인
        if (!reservation.getCode().equals(form.getCode())) {
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

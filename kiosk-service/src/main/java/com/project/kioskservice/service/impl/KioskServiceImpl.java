package com.project.kioskservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.KioskAddDto;
import com.project.domain.entity.Kiosk;
import com.project.domain.entity.Reservation;
import com.project.domain.repository.KioskRepository;
import com.project.domain.repository.ReservationRepository;
import com.project.kioskservice.model.VisitForm;
import com.project.kioskservice.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.common.exception.ErrorCode.*;
import static com.project.domain.type.ReservationApproveStatus.APPROVE;


@Service
@Transactional
@RequiredArgsConstructor
public class KioskServiceImpl implements KioskService {

    private final ReservationRepository reservationRepository;
    private final KioskRepository kioskRepository;

    /**
     * 키오스크 등록
     */
    @Override
    public Long addKiosk(KioskAddDto kioskAddDto) {

        Kiosk newKiosk = Kiosk.builder()
                .storeId(kioskAddDto.getStoreId())
                .build();

        kioskRepository.save(newKiosk);

        return newKiosk.getStoreId();
    }

    /**
     * 고객 방문 체크인
     * @return 고객 이름 반환
     */
    @Override
    public String visitStore(VisitForm form) {

        Kiosk findKiosk = kioskRepository.findById(form.getKioskId())
                .orElseThrow(() -> new CustomException(KIOSK_NOT_FOUND));

        Reservation findReservation =
                reservationRepository.findById(form.getReservationId())
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        validateVisitAvail(findKiosk, form);

        findReservation.visit();

        return findReservation.getCustomer().getUsername();
    }

    /**
     * 해당 방문이 유효한 방문인지 확인
     */
    private void validateVisitAvail(Kiosk kiosk, VisitForm form) {

        // 해당 방문이 유효한 키오스에서 이루어졌는지 확인
        if (!Objects.equals(kiosk.getId(), form.getKioskId())) {
            throw new CustomException(VISIT_INVALID);
        }
//        // 해당 고객이 예약한 예약인지 확인
//        if (!reservation.getContactNumber().equals(form.getContact())) {
//            throw new CustomException(RESERVATION_CUSTOMER_NOT_MATCH);
//        }
//        // 예약 인증 코드가 일치한지 확인
//        if (!reservation.getCode().equals(form.getCode())) {
//            throw new CustomException(RESERVATION_CODE_NOT_MATCH);
//        }
//        // 승인된 예약인지 확인
//        if (reservation.getApproveStatus() != APPROVE) {
//            throw new CustomException(RESERVATION_NOT_APPROVE);
//        }
//        // 입장 가능 시간인지 확인
//        if (!reservation.availVisit()) {
//            throw new CustomException(RESERVATION_ALREADY_EXPIRED);
//        }
//        // 이미 체크인한 예약인지 확인
//        if (reservation.isVisitYn()) {
//            throw new CustomException(RESERVATION_ALREADY_VISIT);
//        }
    }
}

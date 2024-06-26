package com.project.kioskservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.KioskAddDto;
import com.project.domain.entity.Kiosk;
import com.project.domain.repository.KioskRepository;
import com.project.domain.repository.ReservationRepository;
import com.project.domain.response.VisitResponse;
import com.project.kioskservice.client.VisitClient;
import com.project.kioskservice.model.VisitForm;
import com.project.kioskservice.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.common.exception.ErrorCode.*;


@Service
@Transactional
@RequiredArgsConstructor
public class KioskServiceImpl implements KioskService {

    private final KioskRepository kioskRepository;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final VisitClient visitClient;

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
    public VisitResponse visitStore(VisitForm form) {

        CircuitBreaker circuitbreaker =
                circuitBreakerFactory.create("circuitbreaker");

        Kiosk findKiosk = kioskRepository.findById(form.getKioskId())
                .orElseThrow(() -> new CustomException(KIOSK_NOT_FOUND));

        // 올바른 요청인지 검증
        validateKioskAvail(findKiosk, form);

        // openFeign 통해 reservation-service로 방문 요청 보내 처리
        // 올바르지 않은 방문일 경우 실패 response 반환
        return circuitbreaker.run(
                () -> visitClient.visitReservation(form.toDomainForm()).getBody(),
                throwable -> VisitResponse.fail(form.getContact())
        );
    }

    /**
     * 해당 방문이 유효한 방문인지 확인
     */
    private void validateKioskAvail(Kiosk kiosk, VisitForm form) {

        // 해당 방문이 유효한 키오스에서 이루어졌는지 확인
        if (!Objects.equals(kiosk.getId(), form.getKioskId())) {
            throw new CustomException(VISIT_INVALID);
        }
    }
}

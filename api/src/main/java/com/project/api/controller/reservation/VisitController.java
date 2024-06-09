package com.project.api.controller.reservation;

import com.project.domain.dto.VisitDto;
import com.project.domain.model.VisitDomainForm;
import com.project.domain.response.VisitResponse;
import com.project.reservationservice.service.ReservationVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VisitController {

    private final ReservationVisitService reservationVisitService;

    /**
     * 키오스크에서 고객 방문 요청 받아 처리하는 엔드포인트
     */
    @PostMapping("/reservation/visit")
    public ResponseEntity<VisitResponse> visitReservation(@RequestBody VisitDomainForm form) {

        VisitDto visitDto = reservationVisitService.visit(form);

        return ResponseEntity.ok(VisitResponse.success(visitDto));
    }

}

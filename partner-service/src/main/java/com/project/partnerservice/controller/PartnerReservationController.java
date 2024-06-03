package com.project.partnerservice.controller;

import com.project.reservation.common.model.SuccessResponse;
import com.project.reservation.partner.model.ReservationTimeTable;
import com.project.reservation.partner.service.PartnerReservationService;
import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.model.ReservationResponse;
import com.project.reservation.security.util.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.project.reservation.security.constant.SecurityConst.TOKEN_HEADER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/{id}")
public class PartnerReservationController {

    private final TokenValidator tokenValidator;
    private final PartnerReservationService partnerReservationService;



    /**
     * 예약 시간별 타임테이블 조회하는 엔드포인트
     */
    @PreAuthorize("hasAuthority('PARTNER')")
    @GetMapping("/reservation")
    public ResponseEntity<?> getReservationTimeTable(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestHeader(name = TOKEN_HEADER) String header
    ) {

        tokenValidator.validateUser(id, header);

        List<ReservationDto> list =
                partnerReservationService.getReservationTimeTable(id, date);


        return ResponseEntity.ok(
                SuccessResponse.of(ReservationTimeTable.Response.fromDto(date, list))
        );
    }

    /**
     * 예약 승인하는 엔드포인트
     */
    @PreAuthorize("hasAuthority('PARTNER')")
    @PostMapping("/reservation/{reservationId}/confirm")
    public ResponseEntity<?> confirmReservation(
            @PathVariable Long id,
            @PathVariable Long reservationId,
            @RequestHeader(TOKEN_HEADER) String header
    ) {

        tokenValidator.validateUser(id, header);

        ReservationDto reservationDto =
                partnerReservationService.confirmReservation(id, reservationId);

        return ResponseEntity.ok(
                SuccessResponse.of(ReservationResponse.fromDto(reservationDto))
        );
    }


    /**
     * 예약 거절하는 엔드포인트
     */
    @PreAuthorize("hasAuthority('PARTNER')")
    @PostMapping("/reservation/{reservationId}/decline")
    public ResponseEntity<?> declineReservation(
            @PathVariable Long id,
            @PathVariable Long reservationId,
            @RequestHeader(TOKEN_HEADER) String header
    ) {

        tokenValidator.validateUser(id, header);

        ReservationDto reservationDto =
                partnerReservationService.declineReservation(id, reservationId);

        return ResponseEntity.ok(
                SuccessResponse.of(ReservationResponse.fromDto(reservationDto))
        );
    }


}

package com.project.api.controller.partner;

import com.project.common.model.SuccessResponse;
import com.project.domain.dto.ReservationDto;
import com.project.domain.response.ReservationResponse;
import com.project.partnerservice.model.ReservationTimeTable;
import com.project.partnerservice.service.PartnerReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/{partnerEmail}/reservation")
public class PartnerReservationController {

//    private final TokenValidator tokenValidator;
    private final PartnerReservationService partnerReservationService;



    /**
     * 예약 시간별 타임테이블 조회하는 엔드포인트
     */
//    @PreAuthorize("hasAuthority('PARTNER')")
    @GetMapping
    public ResponseEntity<?> getReservationTimeTable(
            @PathVariable String partnerEmail,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
//            @RequestHeader(name = TOKEN_HEADER) String header
    ) {

//        tokenValidator.validateUser(id, header);

        List<ReservationDto> list =
                partnerReservationService.getReservationTimeTable(partnerEmail, date);


        return ResponseEntity.ok(
                SuccessResponse.of(ReservationTimeTable.Response.fromDto(date, list))
        );
    }

    /**
     * 예약 승인하는 엔드포인트
     */
//    @PreAuthorize("hasAuthority('PARTNER')")
    @PostMapping("/{reservationId}/confirm")
    public ResponseEntity<?> confirmReservation(
            @PathVariable String partnerEmail, @PathVariable Long reservationId
//            @RequestHeader(TOKEN_HEADER) String header
    ) {

//        tokenValidator.validateUser(id, header);

        ReservationDto reservationDto =
                partnerReservationService.confirmReservation(partnerEmail, reservationId);

        return ResponseEntity.ok(
                SuccessResponse.of(ReservationResponse.fromDto(reservationDto))
        );
    }


    /**
     * 예약 거절하는 엔드포인트
     */
//    @PreAuthorize("hasAuthority('PARTNER')")
    @PostMapping("/{reservationId}/decline")
    public ResponseEntity<?> declineReservation(
            @PathVariable String partnerEmail, @PathVariable Long reservationId
//            @RequestHeader(TOKEN_HEADER) String header
    ) {

//        tokenValidator.validateUser(id, header);

        ReservationDto reservationDto =
                partnerReservationService.declineReservation(partnerEmail, reservationId);

        return ResponseEntity.ok(
                SuccessResponse.of(ReservationResponse.fromDto(reservationDto))
        );
    }


}

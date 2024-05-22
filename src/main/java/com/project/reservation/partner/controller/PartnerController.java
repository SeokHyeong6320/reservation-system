package com.project.reservation.partner.controller;

import com.project.reservation.auth.dto.UserDto;
import com.project.reservation.common.model.SuccessResponse;
import com.project.reservation.partner.model.ReservationTimeTable;
import com.project.reservation.partner.service.PartnerService;
import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.model.ReservationResponse;
import com.project.reservation.security.constant.SecurityConst;
import com.project.reservation.security.util.TokenValidator;
import com.project.reservation.store.dto.StoreDto;
import com.project.reservation.store.model.StoreForm;
import com.project.reservation.store.model.StoreResponse;
import com.project.reservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.project.reservation.partner.model.PartnerResponse.PartnerEnrollResponse;
import static com.project.reservation.security.constant.SecurityConst.*;
import static com.project.reservation.store.model.StoreResponse.*;
import static org.springframework.format.annotation.DateTimeFormat.*;

@RestController
@RequestMapping("/partner/{id}")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;
    private final StoreService storeService;
    private final TokenValidator tokenValidator;


    /**
     * 파트너 가입 엔드포인트
     * @param id : 유저 아이디
     * @return : 유저 이메일과 유저타입 반환
     */
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/enroll")
    public ResponseEntity<?> enrollPartner(
            @PathVariable Long id,
            @RequestHeader(name = TOKEN_HEADER) String header
    ) {

        tokenValidator.validateUser(id, header);

        UserDto userDto = partnerService.enrollPartner(id);

        return ResponseEntity.ok(PartnerEnrollResponse.of(userDto));
    }



    /**
     * 상점 추가하는 엔드포인트
     * @param id : 파트너 아이디
     * @param form : 상점 등록 폼
     * @return : 등록한 상점 정보 반환
     */
    @PreAuthorize("hasAuthority('PARTNER')")
    @PostMapping("/store")
    public ResponseEntity<?> addStore(
            @PathVariable Long id, @RequestBody StoreForm form,
            @RequestHeader(name = TOKEN_HEADER) String header
    ) {

        tokenValidator.validateUser(id, header);

        StoreDto storeDto = storeService.addStore(id, form);

        return ResponseEntity.ok(
                SuccessResponse.of(AddStoreResponse.fromDto(storeDto))
        );
    }

    /**
     * 상점 정보 업데이트하는 엔드포인트
     */
    @PreAuthorize("hasAuthority('PARTNER')")
    @PatchMapping("/store/{storeId}")
    public ResponseEntity<?> updateStore(
            @PathVariable Long id, @PathVariable Long storeId,
            @RequestHeader(name = TOKEN_HEADER) String header,
            @RequestBody @Validated StoreForm form
            ) {

        tokenValidator.validateUser(id, header);

        storeService.validateStoreOwner(id, storeId);
        StoreDto storeDto = storeService.updateStore(storeId, form);

        return ResponseEntity.ok(
                SuccessResponse.of(StoreInfoResponse.fromDto(storeDto))
        );

    }


    /**
     * 예약 시간별 타임테이블 조회하는 엔드포인트
     */
    @PreAuthorize("hasAuthority('PARTNER')")
    @GetMapping("/reservation")
    public ResponseEntity<?> getReservationTimeTable(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
            @RequestHeader(name = TOKEN_HEADER) String header
    ) {

        tokenValidator.validateUser(id, header);

        List<ReservationDto> list =
                partnerService.getReservationTimeTable(id, date);


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
                partnerService.confirmReservation(id, reservationId);

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
                partnerService.declineReservation(id, reservationId);

        return ResponseEntity.ok(
                SuccessResponse.of(ReservationResponse.fromDto(reservationDto))
        );
    }


}

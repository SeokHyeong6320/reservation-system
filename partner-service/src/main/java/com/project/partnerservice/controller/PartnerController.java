package com.project.partnerservice.controller;

import com.project.reservation.auth.dto.UserDto;
import com.project.reservation.partner.service.PartnerReservationService;
import com.project.reservation.partner.service.PartnerService;
import com.project.reservation.security.util.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.project.reservation.partner.model.PartnerResponse.PartnerEnrollResponse;
import static com.project.reservation.security.constant.SecurityConst.TOKEN_HEADER;

@RestController
@RequestMapping("/partner/{id}")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;
    private final PartnerReservationService reservationService;
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


}

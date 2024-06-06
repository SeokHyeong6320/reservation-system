package com.project.partnerservice.controller;

import com.project.domain.dto.UserDto;
import com.project.domain.response.PartnerResponse;
import com.project.reservationservice.service.ReservationManagementService;
import com.project.partnerservice.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/{id}")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;
    private final ReservationManagementService reservationService;
//    private final TokenValidator tokenValidator;


    /**
     * 파트너 가입 엔드포인트
     * @param id : 유저 아이디
     * @return : 유저 이메일과 유저타입 반환
     */
//    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/enroll")
    public ResponseEntity<?> enrollPartner(
            @PathVariable Long id
//            @RequestHeader(name = TOKEN_HEADER) String header
    ) {

//        tokenValidator.validateUser(id, header);

        UserDto userDto = partnerService.enrollPartner(id);

        return ResponseEntity.ok(PartnerResponse
                .PartnerEnrollResponse.of(userDto));
    }


}

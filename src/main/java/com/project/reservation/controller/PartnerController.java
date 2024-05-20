package com.project.reservation.controller;

import com.project.reservation.dto.UserDto;
import com.project.reservation.model.response.PartnerResponse;
import com.project.reservation.model.response.SuccessResponse;
import com.project.reservation.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;


    // 파트너 가입 엔드포인트
    @PostMapping("/enroll/{id}")
    public ResponseEntity<?> enrollPartner(@PathVariable Long id) {

        UserDto userDto = partnerService.enrollPartner(id);

        return ResponseEntity.ok(PartnerResponse.Enroll.of(userDto));
    }

}

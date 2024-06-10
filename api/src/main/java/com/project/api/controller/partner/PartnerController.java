package com.project.api.controller.partner;

import com.project.domain.dto.UserDto;
import com.project.domain.response.PartnerResponse;
import com.project.partnerservice.service.PartnerService;
import com.project.securityservice.util.AuthVerityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/partner/{partnerEmail}")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;
    private final AuthVerityUtil authVerityUtil;

    /**
     * 파트너 가입 엔드포인트
     * @param partnerEmail : 유저 이메일
     * @return : 유저 이메일과 유저타입 반환
     */
    @PostMapping("/enroll")
    public ResponseEntity<?> enrollPartner(
            @PathVariable String partnerEmail
    ) {

        // 올바른 고객의 접근인지 검증
        authVerityUtil.verifyUser(partnerEmail);

        UserDto userDto = partnerService.enrollPartner(partnerEmail);

        return ResponseEntity.ok(PartnerResponse
                .PartnerEnrollResponse.of(userDto));
    }


}

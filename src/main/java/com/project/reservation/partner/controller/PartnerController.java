package com.project.reservation.partner.controller;

import com.project.reservation.auth.dto.UserDto;
import com.project.reservation.common.model.SuccessResponse;
import com.project.reservation.partner.service.PartnerService;
import com.project.reservation.security.constant.SecurityConst;
import com.project.reservation.security.util.TokenValidator;
import com.project.reservation.store.dto.StoreDto;
import com.project.reservation.store.model.StoreForm;
import com.project.reservation.store.model.StoreResponse;
import com.project.reservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.project.reservation.partner.model.PartnerResponse.PartnerEnrollResponse;
import static com.project.reservation.security.constant.SecurityConst.*;

@RestController
@RequestMapping("/partner")
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
    @PostMapping("/{id}/enroll")
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
    @PostMapping("/{id}/store")
    public ResponseEntity<?> addStore(
            @PathVariable Long id, @RequestBody StoreForm.AddStoreForm form,
            @RequestHeader(name = TOKEN_PREFIX) String header
    ) {

        tokenValidator.validateUser(id, header);

        StoreDto storeDto = storeService.addStore(id, form);

        return ResponseEntity.ok(
                SuccessResponse.of(StoreResponse.AddStoreResponse.fromDto(storeDto))
        );
    }

}

package com.project.reservation.partner.controller;

import com.project.reservation.common.model.SuccessResponse;
import com.project.reservation.partner.service.PartnerStoreService;
import com.project.reservation.security.util.TokenValidator;
import com.project.reservation.store.dto.StoreDto;
import com.project.reservation.store.model.StoreForm;
import com.project.reservation.store.model.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.project.reservation.security.constant.SecurityConst.TOKEN_HEADER;

@RestController
@RequestMapping("/partner/{id}")
@RequiredArgsConstructor
public class PartnerStoreController {

    private final TokenValidator tokenValidator;
    private final PartnerStoreService partnerStoreService;



    /**
     * 상점 추가하는 엔드포인트
     *
     * @param id   : 파트너 아이디
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

        StoreDto storeDto = partnerStoreService.addStore(id, form);

        return ResponseEntity.ok(
                SuccessResponse.of(StoreResponse.AddStoreResponse.fromDto(storeDto))
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

        StoreDto storeDto = partnerStoreService.updateStore(id, storeId, form);

        return ResponseEntity.ok(
                SuccessResponse.of(StoreResponse.StoreInfoResponse.fromDto(storeDto))
        );

    }

    /**
     * 상점 삭제 엔드포인트
     */
    @PreAuthorize("hasAuthority('PARTNER')")
    @DeleteMapping("/store/{storeId}")
    public ResponseEntity<?> deleteStore(
            @PathVariable Long id, @PathVariable Long storeId,
            @RequestHeader(TOKEN_HEADER) String header
    ) {

        tokenValidator.validateUser(id, header);

        partnerStoreService.deleteStore(id, storeId);

        return ResponseEntity.ok(
                SuccessResponse.of("delete complete")
        );
    }


}

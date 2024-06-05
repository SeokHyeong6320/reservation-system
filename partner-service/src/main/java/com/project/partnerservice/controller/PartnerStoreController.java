package com.project.partnerservice.controller;

import com.project.common.model.SuccessResponse;
import com.project.partnerservice.service.PartnerStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;


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

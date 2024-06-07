package com.project.api.controller.partner;

import com.project.common.model.SuccessResponse;
import com.project.domain.dto.StoreDto;
import com.project.domain.response.StoreResponse;
import com.project.partnerservice.model.StoreInfoForm;
import com.project.partnerservice.service.PartnerStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/partner/{partnerEmail}/store")
@RequiredArgsConstructor
public class PartnerStoreController {

//    private final TokenValidator tokenValidator;
    private final PartnerStoreService partnerStoreService;



    /**
     * 상점 추가하는 엔드포인트
     *
     * @param partnerEmail   : 파트너 이메일
     * @param form : 상점 등록 폼
     * @return : 등록한 상점 정보 반환
     */
//    @PreAuthorize("hasAuthority('PARTNER')")
    @PostMapping
    public ResponseEntity<?> addStore(
            @PathVariable String partnerEmail, @RequestBody StoreInfoForm form
//            @RequestHeader(name = TOKEN_HEADER) String header
    ) {

//        tokenValidator.validateUser(id, header);

        StoreDto storeDto = partnerStoreService.addStore(partnerEmail, form);

        return ResponseEntity.status(CREATED).body(
                SuccessResponse.of(StoreResponse.AddStoreResponse.fromDto(storeDto))
        );
    }

    /**
     * 상점 정보 업데이트하는 엔드포인트
     */
//    @PreAuthorize("hasAuthority('PARTNER')")
    @PatchMapping("/{storeId}")
    public ResponseEntity<?> updateStore(
            @PathVariable String partnerEmail, @PathVariable Long storeId,
//            @RequestHeader(name = TOKEN_HEADER) String header,
            @RequestBody @Validated StoreInfoForm form
    ) {

//        tokenValidator.validateUser(id, header);

        StoreDto storeDto = partnerStoreService.updateStore(partnerEmail, storeId, form);

        return ResponseEntity.ok(
                SuccessResponse.of(StoreResponse.StoreInfoResponse.fromDto(storeDto))
        );

    }

    /**
     * 상점 삭제 엔드포인트
     */
//    @PreAuthorize("hasAuthority('PARTNER')")
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStore(
            @PathVariable String partnerEmail, @PathVariable Long storeId
//            @RequestHeader(TOKEN_HEADER) String header
    ) {

//        tokenValidator.validateUser(id, header);

        partnerStoreService.deleteStore(partnerEmail, storeId);

        return ResponseEntity.ok(
                SuccessResponse.of("delete complete")
        );
    }


}

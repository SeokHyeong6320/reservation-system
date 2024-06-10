package com.project.api.controller.partner;

import com.project.common.model.SuccessResponse;
import com.project.domain.dto.StoreDto;
import com.project.domain.response.StoreResponse;
import com.project.partnerservice.model.StoreInfoForm;
import com.project.partnerservice.service.PartnerStoreService;
import com.project.securityservice.util.AuthVerityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/partner/{partnerEmail}/store")
@RequiredArgsConstructor
public class PartnerStoreController {

    private final PartnerStoreService partnerStoreService;
    private final AuthVerityUtil authVerityUtil;


    /**
     * 상점 추가하는 엔드포인트
     * @param partnerEmail   : 파트너 이메일
     * @param form : 상점 등록 폼
     * @return : 등록한 상점 정보 반환
     */
    @PostMapping
    public ResponseEntity<?> addStore(
            @PathVariable String partnerEmail, @RequestBody StoreInfoForm form
    ) {

        // 올바른 유저의 접근인지 검증
        authVerityUtil.verifyUser(partnerEmail);

        StoreDto storeDto = partnerStoreService.addStore(partnerEmail, form);

        return ResponseEntity.status(CREATED).body(
                SuccessResponse.of(StoreResponse.AddStoreResponse.fromDto(storeDto))
        );
    }

    /**
     * 상점 정보 업데이트하는 엔드포인트
     */
    @PatchMapping("/{storeId}")
    public ResponseEntity<?> updateStore(
            @PathVariable String partnerEmail, @PathVariable Long storeId,
            @RequestBody @Validated StoreInfoForm form
    ) {

        // 올바른 유저의 접근인지 검증
        authVerityUtil.verifyUser(partnerEmail);

        StoreDto storeDto = partnerStoreService.updateStore(partnerEmail, storeId, form);

        return ResponseEntity.ok(
                SuccessResponse.of(StoreResponse.StoreInfoResponse.fromDto(storeDto))
        );

    }

    /**
     * 상점 삭제 엔드포인트
     */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStore(
            @PathVariable String partnerEmail, @PathVariable Long storeId
    ) {

        // 올바른 유저의 접근인지 검증
        authVerityUtil.verifyUser(partnerEmail);

        partnerStoreService.deleteStore(partnerEmail, storeId);

        return ResponseEntity.ok(
                SuccessResponse.of("delete complete")
        );
    }


}

package com.project.reservation.controller;

import com.project.reservation.dto.StoreDto;
import com.project.reservation.dto.UserDto;
import com.project.reservation.model.input.StoreForm;
import com.project.reservation.model.response.PartnerResponse;
import com.project.reservation.model.response.StoreResponse;
import com.project.reservation.model.response.SuccessResponse;
import com.project.reservation.service.PartnerService;
import com.project.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.reservation.model.response.PartnerResponse.*;

@RestController
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;
    private final StoreService storeService;


    /**
     * 파트너 가입 엔드포인트
     * @param id : 유저 아이디
     * @return : 유저 이메일과 유저타입 반환
     */
    @PostMapping("/{id}/enroll")
    public ResponseEntity<?> enrollPartner(@PathVariable Long id) {

        UserDto userDto = partnerService.enrollPartner(id);

        return ResponseEntity.ok(PartnerEnrollResponse.of(userDto));
    }



    /**
     * 상점 추가하는 엔드포인트
     * @param id : 파트너 아이디
     * @param form : 상점 등록 폼
     * @return : 등록한 상점 정보 반환
     */
    @PostMapping("/{id}/store")
    public ResponseEntity<?> addStore(
            @PathVariable Long id, @RequestBody StoreForm.AddStoreForm form
    ) {

        StoreDto storeDto = storeService.addStore(id, form);

        return ResponseEntity.ok(
                SuccessResponse.of(StoreResponse.AddStoreResponse.fromDto(storeDto))
        );
    }

}

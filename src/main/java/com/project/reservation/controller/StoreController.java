package com.project.reservation.controller;

import com.project.reservation.dto.StoreDto;
import com.project.reservation.model.input.StoreForm;
import com.project.reservation.model.response.StoreResponse;
import com.project.reservation.model.response.SuccessResponse;
import com.project.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.reservation.model.input.StoreForm.*;
import static com.project.reservation.model.response.StoreResponse.*;

@RestController
@RequestMapping("/partner/{id}/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 상점 추가하는 엔드포인트
     * @param id : 파트너 아이디
     * @param form : 상점 등록 폼
     * @return : 등록한 상점 정보 반환
     */
    @PostMapping
    public ResponseEntity<?> addStore(
            @PathVariable Long id, @RequestBody AddStoreForm form
    ) {

        StoreDto storeDto = storeService.addStore(id, form);

        return ResponseEntity.ok(
                SuccessResponse.of(AddStoreResponse.fromDto(storeDto))
        );
    }

}

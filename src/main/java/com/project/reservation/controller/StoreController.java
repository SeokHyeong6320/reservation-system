package com.project.reservation.controller;

import com.project.reservation.dto.StoreDto;
import com.project.reservation.model.input.StoreForm;
import com.project.reservation.model.response.PageResponse;
import com.project.reservation.model.response.StoreResponse;
import com.project.reservation.model.response.SuccessResponse;
import com.project.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.reservation.model.input.StoreForm.*;
import static com.project.reservation.model.response.StoreResponse.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 이름 순 상점 리스트 조회
     * @param pageable
     * @return PageResponse 형태로 반환
     */
    @GetMapping(params = "sort=name")
    public ResponseEntity<?> searchStoreSortByName(Pageable pageable) {

        Page<StoreInfoResponse> list =   // 순환참조 방지 위해 PageResponse 형태로 반환
                storeService.sortByName(pageable).map(StoreInfoResponse::fromDto);

        return ResponseEntity.ok(
                SuccessResponse.of(PageResponse.of(list))
        );
    }

    /**
     * 별점 순 상점 리스트 조회
     * @param pageable
     * @return PageResponse 형태로 반환
     */
    @GetMapping(params = "sort=star")
    public ResponseEntity<?> searchStoreSortByStar(Pageable pageable) {

        Page<StoreInfoResponse> list = storeService.sortByStar(pageable).map(StoreInfoResponse::fromDto);

        return ResponseEntity.ok(
                SuccessResponse.of(PageResponse.of(list))
        );
    }


}

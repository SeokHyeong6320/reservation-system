package com.project.api.controller.customer;

import com.project.common.model.PageResponse;
import com.project.common.model.SuccessResponse;
import com.project.customerservice.service.CustomerSearchService;
import com.project.domain.model.UserLocation;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.domain.response.StoreResponse.StoreInfoResponse;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class CustomerSearchController {

    private final CustomerSearchService customerSearchService;

    // 상점 검색 기능은 로그인 하지 않아도 가능
    // 검색 결과는 페이징 처리

    /**
     * 이름 순 상점 리스트 조회
     * @param pageable
     * @return PageResponse 형태로 반환
     */
    @GetMapping(params = "sort=name") // sort 파라미터가 name일 경우에 실행
    public ResponseEntity<?> searchStoreSortByName(Pageable pageable) {

        Page<StoreInfoResponse> list =   // 순환참조 방지 위해 PageResponse 형태로 반환
                customerSearchService.sortByName(pageable).map(StoreInfoResponse::fromDto);

        return ResponseEntity.ok(
                SuccessResponse.of(PageResponse.of(list))
        );
    }

    /**
     * 별점 순 상점 리스트 조회
     * @param pageable
     * @return PageResponse 형태로 반환
     */
    @PermitAll
    @GetMapping(params = "sort=star") // sort 파라미터가 star일 경우에 실행
    public ResponseEntity<?> searchStoreSortByStar(Pageable pageable) {

        Page<StoreInfoResponse> list = customerSearchService.sortByStar(pageable).map(StoreInfoResponse::fromDto);

        return ResponseEntity.ok(
                SuccessResponse.of(PageResponse.of(list))
        );
    }

    /**
     *
     * @param lat,lon  위도,경도
     * @param pageable
     * @return PageResponse 형태로 반환
     */
    @PermitAll
    @GetMapping(params = "sort=dist") // sort 파라미터가 dist일 경우에 실행
    public ResponseEntity<?> searchStoreSortByDistance(
            @ModelAttribute UserLocation userLocation, // 파라미터로 현재 유저의 위치 정보 받음
            Pageable pageable) {

        Page<StoreInfoResponse> list = customerSearchService.sortByDistance(userLocation, pageable).map(StoreInfoResponse::fromDto);

        return ResponseEntity.ok(
                SuccessResponse.of(PageResponse.of(list))
        );
    }


}

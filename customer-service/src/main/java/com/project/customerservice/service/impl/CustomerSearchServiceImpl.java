package com.project.customerservice.service.impl;

import com.project.customerservice.service.CustomerSearchService;
import com.project.domain.dto.StoreDto;
import com.project.domain.model.UserLocation;
import com.project.storeservice.service.StoreSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerSearchServiceImpl implements CustomerSearchService {

    private final StoreSearchService storeSearchService;

    /**
     * Store-service 모듈로 넘겨서 상점 검색 처리
     */

    @Override
    public Page<StoreDto> sortByName(Pageable pageable) {
        return storeSearchService.sortByName(pageable);
    }

    @Override
    public Page<StoreDto> sortByStar(Pageable pageable) {
        return storeSearchService.sortByStar(pageable);
    }

    @Override
    public Page<StoreDto> sortByDistance(UserLocation userLocation, Pageable pageable) {
        return storeSearchService.sortByDistance(userLocation, pageable);
    }
}

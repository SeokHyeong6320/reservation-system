package com.project.storeservice.service;

import com.project.domain.dto.StoreDto;

public interface StoreSearchService {

    Page<StoreDto> sortByName(Pageable pageable);

    Page<StoreDto> sortByStar(Pageable pageable);
    Page<StoreDto> sortByDistance(Double lat, Double lon, Pageable pageable);


}

package com.project.storeservice.service;

import com.project.reservation.store.dto.StoreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreService {

    Page<StoreDto> sortByName(Pageable pageable);

    Page<StoreDto> sortByStar(Pageable pageable);
    Page<StoreDto> sortByDistance(Double lat, Double lon, Pageable pageable);


}

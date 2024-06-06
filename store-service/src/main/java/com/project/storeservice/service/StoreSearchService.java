package com.project.storeservice.service;

import com.project.domain.dto.StoreDto;
import com.project.domain.model.UserLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreSearchService {

    Page<StoreDto> sortByName(Pageable pageable);

    Page<StoreDto> sortByStar(Pageable pageable);
    Page<StoreDto> sortByDistance(UserLocation userLocation, Pageable pageable);


}

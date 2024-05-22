package com.project.reservation.store.service;

import com.project.reservation.store.dto.StoreDto;
import com.project.reservation.store.model.StoreForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreService {

    StoreDto addStore(Long id, StoreForm form);

    Page<StoreDto> sortByName(Pageable pageable);
    Page<StoreDto> sortByStar(Pageable pageable);
    Page<StoreDto> sortByDistance(Double lat, Double lon, Pageable pageable);

    StoreDto updateStore(Long storeId, StoreForm form);
    void validateStoreOwner(Long ownerId, Long storeId);
}

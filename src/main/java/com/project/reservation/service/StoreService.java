package com.project.reservation.service;

import com.project.reservation.dto.StoreDto;
import com.project.reservation.model.input.StoreForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {

    StoreDto addStore(Long id, StoreForm.AddStoreForm form);

    Page<StoreDto> sortByName(Pageable pageable);
    Page<StoreDto> sortByStar(Pageable pageable);
    Page<StoreDto> sortByDistance(Double lat, Double lon, Pageable pageable);
}

package com.project.reservation.repository;

import com.project.reservation.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreQueryRepository {

    Page<Store> findSortByDistance(Pageable pageable, Double lat, Double lon);
}

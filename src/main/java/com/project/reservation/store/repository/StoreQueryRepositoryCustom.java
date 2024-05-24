package com.project.reservation.store.repository;

import com.project.reservation.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreQueryRepositoryCustom {

    Page<Store> findSortByDistance(Pageable pageable, Double lat, Double lon);
}

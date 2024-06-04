package com.project.domain.repository;

import com.project.domain.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreQueryRepository {

    Page<Store> findSortByDistance(Pageable pageable, Double lat, Double lon);
}

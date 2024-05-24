package com.project.reservation.store.repository;

import com.project.reservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreQueryRepositoryCustom {
}

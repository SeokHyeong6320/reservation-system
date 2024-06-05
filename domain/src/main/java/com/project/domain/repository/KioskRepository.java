package com.project.domain.repository;


import com.project.domain.entity.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KioskRepository extends JpaRepository<Kiosk, Long> {
}

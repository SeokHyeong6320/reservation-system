package com.project.reservation.kiosk.repository;

import com.project.reservation.kiosk.entity.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KioskRepository extends JpaRepository<Kiosk, Long> {
}

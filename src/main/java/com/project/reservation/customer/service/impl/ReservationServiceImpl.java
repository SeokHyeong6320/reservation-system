package com.project.reservation.customer.service.impl;

import com.project.reservation.customer.repository.ReservationRepository;
import com.project.reservation.customer.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
}

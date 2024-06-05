package com.project.customerservice.service.impl;

import com.project.customerservice.model.CustomerReservationForm;
import com.project.customerservice.service.CustomerReservationService;
import com.project.domain.dto.ReservationDto;
import com.project.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerReservationServiceImpl implements CustomerReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public ReservationDto makeReservation(Long userId, CustomerReservationForm form) {



        return null;
    }
}

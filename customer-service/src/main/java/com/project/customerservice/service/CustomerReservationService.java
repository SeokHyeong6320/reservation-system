package com.project.customerservice.service;

import com.project.customerservice.model.CustomerReservationForm;
import com.project.domain.dto.ReservationDto;

public interface CustomerReservationService {

    ReservationDto makeReservation(Long userId, CustomerReservationForm form);

}

package com.project.reservation.customer.service;

import com.project.reservation.customer.dto.ReservationDto;
import com.project.reservation.customer.model.ReservationForm;

public interface ReservationService {
    ReservationDto reservation(Long id, ReservationForm form);

}

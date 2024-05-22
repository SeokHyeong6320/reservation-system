package com.project.reservation.customer.service;

import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.model.ReservationForm;

public interface CustomerReservationService {
    ReservationDto reservation(Long id, ReservationForm form);

}

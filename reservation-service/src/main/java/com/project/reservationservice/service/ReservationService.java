package com.project.reservationservice.service;


import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.Reservation;
import com.project.reservationservice.model.ReservationForm;

public interface ReservationService {
    ReservationDto reservation(Long id, ReservationForm form);

}

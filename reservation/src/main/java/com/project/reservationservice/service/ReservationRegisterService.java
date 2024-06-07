package com.project.reservationservice.service;


import com.project.domain.dto.InitReservationDto;
import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.Reservation;

public interface ReservationRegisterService {

    ReservationDto makeReservation(InitReservationDto initReservationDto);

    void checkAvailWriteReview(Long userId, Reservation reservation);
}

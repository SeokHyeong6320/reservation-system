package com.project.reservationservice.service;


import com.project.domain.dto.InitReservationDto;
import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.Reservation;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.model.ReservationDomainForm;

public interface ReservationRegisterService {

    ReservationDto makeReservation(InitReservationDto initReservationDto);

    void checkAvailWriteReview(Long userId, Reservation reservation);
}

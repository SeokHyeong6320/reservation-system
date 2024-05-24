package com.project.reservation.partner.service;

import com.project.reservation.reservation.dto.ReservationDto;

import java.time.LocalDate;
import java.util.List;

public interface PartnerReservationService {

    List<ReservationDto> getReservationTimeTable(Long id, LocalDate date);

    ReservationDto confirmReservation(Long id, Long reservationId);

    ReservationDto declineReservation(Long id, Long reservationId);

}

package com.project.reservation.partner.service;

import com.project.reservation.auth.dto.UserDto;
import com.project.reservation.reservation.dto.ReservationDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PartnerService {
    UserDto enrollPartner(Long id);

    List<ReservationDto> getReservationTimeTable(Long id, LocalDate date);

    ReservationDto confirmReservation(Long id, Long reservationId);

    ReservationDto declineReservation(Long id, Long reservationId);
}

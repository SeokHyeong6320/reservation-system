package com.project.reservationservice.service;


import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface ReservationManagementService {

    List<ReservationDto> getReservationTimeTable(User partner, LocalDate date);

    ReservationDto confirmReservation(Long userId, Long reservationId);

    ReservationDto declineReservation(Long userId, Long reservationId);

}

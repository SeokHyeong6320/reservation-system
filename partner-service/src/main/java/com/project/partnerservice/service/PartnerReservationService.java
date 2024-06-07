package com.project.partnerservice.service;


import com.project.domain.dto.ReservationDto;

import java.time.LocalDate;
import java.util.List;

public interface PartnerReservationService {

    List<ReservationDto> getReservationTimeTable(String partnerEmail, LocalDate date);

    ReservationDto confirmReservation(String partnerEmail, Long reservationId);

    ReservationDto declineReservation(String partnerEmail, Long reservationId);

}

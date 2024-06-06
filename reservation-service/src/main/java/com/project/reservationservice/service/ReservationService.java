package com.project.reservationservice.service;


import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.Reservation;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.model.ReservationDomainForm;

public interface ReservationService {
    ReservationDto reservation(User customer, Store store, ReservationDomainForm form);

    void checkAvailWriteReview(Long userId, Reservation reservation);
}

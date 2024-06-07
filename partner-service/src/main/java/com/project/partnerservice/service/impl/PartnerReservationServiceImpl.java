package com.project.partnerservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.User;
import com.project.domain.repository.UserRepository;
import com.project.partnerservice.service.PartnerReservationService;
import com.project.reservationservice.service.ReservationManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.project.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class PartnerReservationServiceImpl implements PartnerReservationService {

    private final UserRepository userRepository;
    private final ReservationManagementService reservationManagementService;

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDto> getReservationTimeTable(String partnerEmail, LocalDate date) {

        User findUser = userRepository
                .findByEmail(partnerEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return reservationManagementService
                .getReservationTimeTable(findUser, date);

    }

    @Override
    public ReservationDto confirmReservation(String partnerEmail, Long reservationId) {

        User findUser = userRepository
                .findByEmail(partnerEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return reservationManagementService
                .confirmReservation(findUser.getId(), reservationId);
    }

    @Override
    public ReservationDto declineReservation(String partnerEmail, Long reservationId) {

        User findUser = userRepository
                .findByEmail(partnerEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return reservationManagementService
                .declineReservation(findUser.getId(), reservationId);
    }
}

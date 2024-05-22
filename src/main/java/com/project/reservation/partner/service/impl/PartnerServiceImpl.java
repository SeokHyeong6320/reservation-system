package com.project.reservation.partner.service.impl;

import com.project.reservation.auth.dto.UserDto;
import com.project.reservation.auth.entity.User;
import com.project.reservation.auth.entity.UserType;
import com.project.reservation.auth.repository.UserRepository;
import com.project.reservation.common.exception.CustomException;
import com.project.reservation.partner.service.PartnerService;
import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.project.reservation.common.exception.ErrorCode.PARTNER_ALREADY_ENROLLED;
import static com.project.reservation.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;


    // 파트너 가입 서비스
    @Override
    public UserDto enrollPartner(Long id) {

        User findUser = userRepository
                .findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 이미 파트너인 경우 에러 발생
        if (findUser.getUserType() == UserType.PARTNER) {
            throw new CustomException(PARTNER_ALREADY_ENROLLED);
        }

        // userType을 PARTNER로 변경
        findUser.enrollPartner();

        // dto로 변환해서 반환
        return UserDto.fromEntity(findUser);
    }

    @Override
    public List<ReservationDto> getReservationTimeTable(Long id, LocalDate date) {

        User findUser = userRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return reservationRepository.findAllByTime(findUser, year, month, day)
                .stream()
                .map(ReservationDto::fromEntity)
                .toList();
    }
}

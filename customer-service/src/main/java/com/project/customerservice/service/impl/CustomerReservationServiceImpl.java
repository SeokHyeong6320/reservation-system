package com.project.customerservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.customerservice.model.CustomerReservationForm;
import com.project.customerservice.service.CustomerReservationService;
import com.project.domain.dto.ReservationDto;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.repository.StoreRepository;
import com.project.domain.repository.UserRepository;
import com.project.reservationservice.service.ReservationRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerReservationServiceImpl implements CustomerReservationService {


    private final ReservationRegisterService reservationRegisterService;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    public ReservationDto makeReservation(Long userId, CustomerReservationForm form) {

        // 고객정보, 상점정보 검증 후 예약서비스 모듈로 넘겨줌
        User findUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Store findStore = storeRepository
                .findById(form.getStoreId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        // 현재 예약 가능한 상태인지 확인
        if (!findStore.isAvail()) {
            throw new CustomException(STORE_UNAVAILABLE);
        }

        return reservationRegisterService
                .makeReservation(findUser, findStore, form.toDomainForm());

    }
}

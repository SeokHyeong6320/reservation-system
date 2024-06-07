package com.project.customerservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.customerservice.model.CustomerReservationForm;
import com.project.customerservice.service.CustomerReservationService;
import com.project.domain.dto.InitReservationDto;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.repository.StoreRepository;
import com.project.domain.repository.UserRepository;
import com.project.domain.response.ReservationResponse;
import com.project.kafkaservice.service.KafkaProducer;
import com.project.kafkaservice.topic.KafkaTopic;
import com.project.reservationservice.service.ReservationRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.project.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerReservationServiceImpl implements CustomerReservationService {


    private final ReservationRegisterService reservationRegisterService;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public ReservationResponse makeReservation(String customerEmail, CustomerReservationForm form) {

        // 고객정보, 상점정보 검증 후 예약서비스 모듈로 넘겨줌
        User findUser = userRepository
                .findByEmail(customerEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Store findStore = storeRepository
                .findById(form.getStoreId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        // 현재 예약 가능한 상태인지 확인
        if (!findStore.isAvail()) {
            throw new CustomException(STORE_UNAVAILABLE);
        }

        // 예약 가능 시간인지 확인 (키오스크 방문 확인이 예약 10분 전까지만 가능 하므로 10분 더해 주었음)
        if (form.getReserveDt().minusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new CustomException(RESERVATION_DATE_INVALID);
        }

        InitReservationDto initReservationDto = InitReservationDto.builder()
                .customerId(findUser.getId())
                .storeId(findStore.getId())
                .reserveDt(form.getReserveDt())
                .contact(form.getContact())
                .build();

        // kafka를 통해 예약 내역 전송
        kafkaProducer.produce(KafkaTopic.DO_RESERVATION, initReservationDto);

        return ReservationResponse.fromDto(initReservationDto, findStore);
    }
}

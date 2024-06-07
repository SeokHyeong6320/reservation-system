package com.project.kafkaservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.exception.CustomException;
import com.project.common.exception.ErrorCode;
import com.project.domain.dto.InitReservationDto;
import com.project.domain.dto.ReservationDto;
import com.project.domain.dto.VisitDto;
import com.project.kafkaservice.topic.KafkaTopic;
import com.project.reservationservice.service.ReservationManagementService;
import com.project.reservationservice.service.ReservationRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.project.common.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper mapper;
    private final ReservationRegisterService reservationRegisterService;

    @KafkaListener(topics = KafkaTopic.DO_RESERVATION)
    public void receiptVisitCustomer(String kafkaMessage) {

        log.info("Consume init reservation = {}", kafkaMessage);

        try {
            InitReservationDto initReservationDto =
                    mapper.readValue(kafkaMessage, InitReservationDto.class);

            ReservationDto reservationDto =
                    reservationRegisterService.makeReservation(initReservationDto);

            log.info(
                    "Init Reservation. id={}, customerId={}",
                    reservationDto.getId(), reservationDto.getCustomerId()
                    );


        } catch (JsonProcessingException e) {
            throw new CustomException(KAFKA_CONSUMING_INVALID);
        }
    }

}

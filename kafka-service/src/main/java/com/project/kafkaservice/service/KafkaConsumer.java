package com.project.kafkaservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.exception.CustomException;
import com.project.common.exception.ErrorCode;
import com.project.domain.dto.VisitDto;
import com.project.kafkaservice.topic.KafkaTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.project.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper mapper;

    @KafkaListener(topics = KafkaTopic.VISIT_CUSTOMER)
    public VisitDto receiptVisitCustomer(String kafkaMessage) {

        try {
            return mapper.readValue(kafkaMessage, VisitDto.class);

        } catch (JsonProcessingException e) {
            throw new CustomException(KAFKA_CONSUMING_INVALID);
        }
    }

}

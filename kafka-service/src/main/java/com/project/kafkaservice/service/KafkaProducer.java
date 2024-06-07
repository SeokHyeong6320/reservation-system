package com.project.kafkaservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.exception.CustomException;
import com.project.domain.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.project.common.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public VisitDto announceVisitCustomer(String topic, VisitDto visitDto) {

        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(visitDto);

        } catch (JsonProcessingException e) {
            throw new CustomException(KAFKA_PRODUCING_INVALID);
        }

        kafkaTemplate.send(topic, jsonString);

        return visitDto;
    }

}

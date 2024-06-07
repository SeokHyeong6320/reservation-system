package com.project.kafkaservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.project.common.exception.ErrorCode.KAFKA_PRODUCING_INVALID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;


    public <T> void produce(String topic, T producingDto) {

        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(producingDto);

        } catch (JsonProcessingException e) {
            throw new CustomException(KAFKA_PRODUCING_INVALID);
        }

        kafkaTemplate.send(topic, jsonString);

    }

}

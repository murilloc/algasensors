package com.murilloc.algasensors.temperature.processing.api.controller;


import com.murilloc.algasensors.temperature.processing.api.model.TemperatureLogOutput;
import com.murilloc.algasensors.temperature.processing.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

import static com.murilloc.algasensors.temperature.processing.infrastructure.rabbitmq.RabbitMQConfig.FANOUT_EXCHANGE_NAME;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures/data")
@Slf4j
@RequiredArgsConstructor
public class TemperatureProcessingConstroller {

    private final RabbitTemplate rabbitMQTemplate;


    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public void processTemperatureData(@PathVariable TSID sensorId, @RequestBody String temperatureData) {

        if (temperatureData == null || temperatureData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Temperature data cannot be null or empty");
        }

        Double temperature;

        try {
            temperature = Double.parseDouble(temperatureData);

        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid temperature data format");
        }

        TemperatureLogOutput logOutput = TemperatureLogOutput.builder()
                .id(IdGenerator.generateTimeBasedUUID())
                .sensorId(sensorId)
                .value(temperature)
                .registeredAt(OffsetDateTime.now())
                .build();

        log.info("Received temperature data: {}", logOutput.toString());

        String exchange = FANOUT_EXCHANGE_NAME;
        String routingKey = "";
        Object payload = logOutput;

        MessagePostProcessor messagePostProcessor =  message -> {
            message.getMessageProperties().setHeader("sensorId", logOutput.getSensorId().toString());
            return message;

        };
        rabbitMQTemplate.convertAndSend(exchange, routingKey, payload, messagePostProcessor);

    }
}

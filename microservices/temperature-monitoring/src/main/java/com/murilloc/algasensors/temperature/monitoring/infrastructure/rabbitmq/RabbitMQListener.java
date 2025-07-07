package com.murilloc.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.murilloc.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.murilloc.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQListener {

    private  final TemperatureMonitoringService temperatureMonitoringService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, concurrency = "2-3")
    @SneakyThrows
    public void processTemperature(@Payload TemperatureLogData temperatureLogData, @Headers Map<String, Object> headers) {

        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));

    }
}

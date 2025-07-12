package com.murilloc.algasensors.temperature.monitoring.domain.service;


import com.murilloc.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorId;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.murilloc.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.murilloc.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.murilloc.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.murilloc.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemperatureMonitoringService {

    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    @Transactional
    public void processTemperatureReading(TemperatureLogData temperatureLogData) {
        log.info("Processing temperature reading for sensor {} with value {} at {}",
                temperatureLogData.getSensorId(),
                temperatureLogData.getValue(),
                temperatureLogData.getRegisteredAt());

        if(temperatureLogData.getValue().equals(10.6)) {
            throw new RuntimeException("somente para efeito de retry-test: " + temperatureLogData.getValue());
        }

        sensorMonitoringRepository.findById(new SensorId(temperatureLogData.getSensorId()))

                .ifPresentOrElse(sensor -> handleSensorMonitoring(temperatureLogData, sensor),
                        () -> logIgnoredTemperatureReading(temperatureLogData));
    }

    private void logIgnoredTemperatureReading(TemperatureLogData temperatureLogData) {

        log.info("Temperature ignored for sensor {} with value {} at {}",
                temperatureLogData.getSensorId(),
                temperatureLogData.getValue(),
                temperatureLogData.getRegisteredAt());

    }

    private void handleSensorMonitoring(TemperatureLogData temperatureLogData, SensorMonitoring sensor) {
        if (sensor.isEnabled()) {
            sensor.setLastTemperature(temperatureLogData.getValue());
            sensor.setUpdatedAt(OffsetDateTime.now());
            sensorMonitoringRepository.save(sensor);

            TemperatureLog temperatureLog = TemperatureLog.builder()
                    .id(new TemperatureLogId(temperatureLogData.getId()))
                    .sensorId(sensor.getId())
                    .value(temperatureLogData.getValue())
                    .registeredAt(temperatureLogData.getRegisteredAt())
                    .value(temperatureLogData.getValue())
                    .sensorId(new SensorId(temperatureLogData.getSensorId()))
                    .build();

            temperatureLogRepository.save(temperatureLog);
            log.info("Temperature updated for sensor {} with value {} at {}",
                    temperatureLogData.getSensorId(),
                    temperatureLogData.getValue(),
                    temperatureLogData.getRegisteredAt());
        } else {
            logIgnoredTemperatureReading(temperatureLogData);
        }
    }
}

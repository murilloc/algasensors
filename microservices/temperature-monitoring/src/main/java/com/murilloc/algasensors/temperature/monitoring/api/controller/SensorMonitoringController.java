package com.murilloc.algasensors.temperature.monitoring.api.controller;


import com.murilloc.algasensors.temperature.monitoring.api.model.SensorMonitoringOutput;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorId;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.murilloc.algasensors.temperature.monitoring.domain.repository.SensorMonitoryRepository;
import io.hypersistence.tsid.TSID;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
public class SensorMonitoringController {

    private final SensorMonitoryRepository sensorMonitoryRepository;

    public SensorMonitoringController(SensorMonitoryRepository sensorMonitoryRepository) {
        this.sensorMonitoryRepository = sensorMonitoryRepository;
    }


    @GetMapping
    public SensorMonitoringOutput getDetail(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        return SensorMonitoringOutput.builder()
                .id(sensorMonitoring.getId().getValue())
                .lastTemperature(sensorMonitoring.getLastTemperature())
                .updatedAt(sensorMonitoring.getUpdatedAt())
                .enabled(sensorMonitoring.getEnabled())
                .build();
    }


    private SensorMonitoring findByIdOrDefault(TSID sensorId) {
        return sensorMonitoryRepository.findById(new SensorId(sensorId))
                .orElse(SensorMonitoring.builder()
                        .id(new SensorId(sensorId))
                        .enabled(false)
                        .lastTemperature(null)
                        .updatedAt(null)
                        .build());
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableSensorMonitoring(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        if (sensorMonitoring.getEnabled()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Sensor Monitoring already enabled");
        }
        sensorMonitoring.setEnabled(true);
        sensorMonitoryRepository.saveAndFlush(sensorMonitoring);
    }


    @DeleteMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SneakyThrows
    public void disableSensorMonitoring(@PathVariable TSID sensorId) {
        SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);
        if (!sensorMonitoring.getEnabled()) {
            // teste timeout
            Thread.sleep(Duration.ofSeconds(10).toMillis());
        }
        sensorMonitoring.setEnabled(false);
        sensorMonitoryRepository.saveAndFlush(sensorMonitoring);
    }


}

package com.murilloc.algasensors.temperature.monitoring.api.controller;

import com.murilloc.algasensors.temperature.monitoring.api.model.SensorAlertInput;
import com.murilloc.algasensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorAlertId;
import com.murilloc.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorAlertController {

    private final SensorAlertRepository sensorAlertRepository;



    @GetMapping("{sensorId}/alert")
    @ResponseStatus(HttpStatus.OK)
    public SensorAlertOutput getSensorAlert(@PathVariable TSID sensorId) {
        Optional<SensorAlert> sensorAlert = sensorAlertRepository.findById(new SensorAlertId(sensorId));
        if (sensorAlert.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor alert not found");
        }
        return SensorAlertOutput.builder()
                .id(sensorAlert.get().getId().getValue())
                .maxTemperature(sensorAlert.get().getMaxTemperature())
                .minTemperature(sensorAlert.get().getMinTemperature())
                .build();
    }

    @PutMapping("/{sensorId}/alert")
    public SensorAlertOutput createOrUpdateSensorAlert(@PathVariable TSID sensorId, @RequestBody SensorAlertInput input) {
        SensorAlertId alertId = new SensorAlertId(sensorId);
        Optional<SensorAlert> sensorAlert = sensorAlertRepository.findById(alertId);
        if (sensorAlert.isEmpty()) {
            sensorAlert = Optional.ofNullable(SensorAlert.builder()
                    .id(alertId)
                    .build());
        }
        sensorAlert.get().setMaxTemperature(input.getMaxTemperature());
        sensorAlert.get().setMinTemperature(input.getMinTemperature());
        sensorAlertRepository.save(sensorAlert.get());

        return SensorAlertOutput.builder()
                .id(sensorAlert.get().getId().getValue())
                .maxTemperature(sensorAlert.get().getMaxTemperature())
                .minTemperature(sensorAlert.get().getMinTemperature())
                .build();
    }


    @DeleteMapping("/{sensorId}/alert")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSensorAlert(@PathVariable TSID sensorId) {
        Optional<SensorAlert> sensorAlert = sensorAlertRepository.findById(new SensorAlertId(sensorId));
        if (sensorAlert.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor alert not found");
        }
        sensorAlertRepository.delete(sensorAlert.get());
    }
}

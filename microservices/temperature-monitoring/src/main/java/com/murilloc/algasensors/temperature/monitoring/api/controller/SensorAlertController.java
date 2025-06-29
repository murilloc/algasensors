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

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorAlertController {

    private SensorAlertRepository sensorAlertRepository;


    @GetMapping("{sensorId}/alert")
    @ResponseStatus(HttpStatus.OK)
    public SensorAlertOutput getSensorAlert(@PathVariable TSID sensorId) {
        SensorAlert sensorAlert = sensorAlertRepository.findById(new SensorAlertId(sensorId));
        if (sensorAlert == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor alert not found");
        }
        return SensorAlertOutput.builder()
                .id(sensorAlert.getId().getValue())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }

    @PutMapping("/{sensorId}/alert")
    public SensorAlertOutput createOrUpdateSensorAlert(@PathVariable TSID sensorId, @RequestBody SensorAlertInput input) {
        SensorAlertId alertId = new SensorAlertId(sensorId);
        SensorAlert sensorAlert = sensorAlertRepository.findById(alertId);
        if (sensorAlert == null) {
            sensorAlert = SensorAlert.builder()
                    .id(alertId)
                    .build();
        }
        sensorAlert.setMaxTemperature(input.getMaxTemperature());
        sensorAlert.setMinTemperature(input.getMinTemperature());
        sensorAlertRepository.save(sensorAlert);

        return SensorAlertOutput.builder()
                .id(sensorAlert.getId().getValue())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }


    @DeleteMapping("/{sensorId}/alert")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSensorAlert(@PathVariable TSID sensorId) {
        SensorAlert sensorAlert = sensorAlertRepository.findById(new SensorAlertId(sensorId));
        if (sensorAlert == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor alert not found");
        }
        sensorAlertRepository.delete(sensorAlert);
    }
}

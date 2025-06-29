package com.murilloc.algasensors.temperature.monitoring.api.controller;


import com.murilloc.algasensors.temperature.monitoring.api.model.TemperatureLogOutput;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorId;
import com.murilloc.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.murilloc.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures")
@RequiredArgsConstructor
public class TemperatureLogController {

    private final TemperatureLogRepository temperatureLogRepository;

    @GetMapping
    public Page<TemperatureLogOutput> getTemperatureLogs(@PathVariable TSID sensorId, @PageableDefault Pageable pageable) {
        Page<TemperatureLog> temeratureLogs = temperatureLogRepository.findAllBySensorId(new SensorId(sensorId), pageable);
        return temeratureLogs.map(temperatureLog -> TemperatureLogOutput.builder()
                .id(temperatureLog.getId().getValue())
                .value(temperatureLog.getValue())
                .registeredAt(temperatureLog.getRegisteredAt())
                .sensorId(temperatureLog.getSensorId().getValue())
                .build());
    }

}

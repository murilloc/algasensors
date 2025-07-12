package com.murilloc.algasensors.temperature.monitoring.domain.service;


import com.murilloc.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorAlertId;
import com.murilloc.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorAlertService {

    private final SensorAlertRepository sensorAlertRepository;

    @Transactional
    public void handleAlerting(TemperatureLogData temperatureLogData) {

        sensorAlertRepository.findById(new SensorAlertId(temperatureLogData.getSensorId()))
                .ifPresentOrElse(sensorAlert -> {
                            if (sensorAlert.getMaxTemperature() != null && temperatureLogData.getValue().compareTo(sensorAlert.getMaxTemperature()) >= 0) {
                                logIgnoredAlert("alert MAX Temperature for sensor : sensorID {}, temperature: {}", temperatureLogData);
                            } else if (sensorAlert.getMinTemperature() != null && temperatureLogData.getValue().compareTo(sensorAlert.getMinTemperature()) <= 0) {
                                logIgnoredAlert("alert MIN temperature for sensor : sensorID {}, temperature: {}", temperatureLogData);
                            } else{
                                logIgnoredAlert("Ignoring alert for sensor as it is not registered in the system: sensorID {}, temperature: {}", temperatureLogData);
                            }
                        }, () -> {
                            logIgnoredAlert("Ignoring alert for sensor as it is not registered in the system: sensorID {}, temperature: {}", temperatureLogData);
                        }
                );
    }

    private static void logIgnoredAlert(String format, TemperatureLogData temperatureLogData) {
        log.info(format,
                temperatureLogData.getSensorId(), temperatureLogData.getValue());
    }
}

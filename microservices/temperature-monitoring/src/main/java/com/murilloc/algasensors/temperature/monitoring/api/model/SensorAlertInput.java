package com.murilloc.algasensors.temperature.monitoring.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorAlertInput {
    private Double maxTemperature;
    private Double minTemperature;

    public SensorAlertInput(Double maxTemperature, Double minTemperature) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }
}

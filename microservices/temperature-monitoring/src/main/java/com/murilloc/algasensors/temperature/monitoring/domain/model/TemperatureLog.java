package com.murilloc.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@Data
public class TemperatureLog {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "UUID"))
    private TemperatureLogId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "sensor_id", columnDefinition = "BIGINT"))
    private SensorId sensorId;

    @Column(name = "\"value\"")
    private Double value;

    private OffsetDateTime registeredAt;

    public TemperatureLog(TemperatureLogId id, SensorId sensorId, Double value, OffsetDateTime registeredAt) {
        this.id = id;
        this.sensorId = sensorId;
        this.value = value;
        this.registeredAt = registeredAt;
    }


}

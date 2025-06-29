package com.murilloc.algasensors.temperature.monitoring.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SensorAlertId implements Serializable {
    private TSID value;

    public SensorAlertId(TSID value) {
        Objects.requireNonNull(value, "SensorId cannot be null");
        this.value = value;
    }

    public SensorAlertId(Long value) {
        Objects.requireNonNull(value, "SensorId cannot be null");
        this.value = TSID.from(value);
    }

    public SensorAlertId(String value) {
        Objects.requireNonNull(value, "SensorId cannot be null");
        this.value = TSID.from(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

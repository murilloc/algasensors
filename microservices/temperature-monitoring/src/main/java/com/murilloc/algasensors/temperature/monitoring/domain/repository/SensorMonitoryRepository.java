package com.murilloc.algasensors.temperature.monitoring.domain.repository;

import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorId;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorMonitoryRepository extends JpaRepository<SensorMonitoring, SensorId> {
}

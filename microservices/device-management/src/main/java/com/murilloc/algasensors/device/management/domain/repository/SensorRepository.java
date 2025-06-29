package com.murilloc.algasensors.device.management.domain.repository;

import com.murilloc.algasensors.device.management.domain.model.SensorId;
import com.murilloc.algasensors.device.management.domain.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, SensorId> {

}

package com.murilloc.algasensors.temperature.monitoring.domain.repository;

import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorAlertId;
import org.springframework.data.repository.Repository;


import java.util.Optional;


public interface SensorAlertRepository extends Repository<SensorAlert, SensorAlertId> {

   Optional<SensorAlert> findById(SensorAlertId sensorAlertId);

   SensorAlert save(SensorAlert sensorAlert);

   void delete(SensorAlert sensorAlert);
}
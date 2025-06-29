package com.murilloc.algasensors.temperature.monitoring.domain.repository;

import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.murilloc.algasensors.temperature.monitoring.domain.model.SensorAlertId;
import org.springframework.data.repository.Repository;

public interface SensorAlertRepository extends Repository<SensorAlert, SensorAlertId> {

   SensorAlert findById(SensorAlertId sensorAlertId);

   void save(SensorAlert sensorAlert);

   void delete(SensorAlert sensorAlert);
}
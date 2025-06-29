package com.murilloc.algasensors.temperature.monitoring.api.config.jpa;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class TSIDToLongJPAAttributeConverter implements AttributeConverter<TSID, Long> {
    @Override
    public Long convertToDatabaseColumn(TSID attribute) {
        return attribute != null ? attribute.toLong() : null;
    }

    @Override
    public TSID convertToEntityAttribute(Long dbData) {
        return dbData != null ? TSID.from(dbData) : null;
    }
}

package com.murilloc.algasensors.temperature.processing.common;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class UUIDV7Utils {

    private UUIDV7Utils() {}



    public static OffsetDateTime extractOffsetDateTime(UUID uuid) {

        if(uuid == null){
            throw new IllegalArgumentException("UUID cannot be null");
        }

        long timestamp = uuid.getMostSignificantBits() >>> 16;

        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp),ZoneId.systemDefault());
    }

}

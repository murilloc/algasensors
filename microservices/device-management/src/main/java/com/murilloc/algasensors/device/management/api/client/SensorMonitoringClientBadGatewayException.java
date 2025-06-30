package com.murilloc.algasensors.device.management.api.client;

public class SensorMonitoringClientBadGatewayException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public SensorMonitoringClientBadGatewayException() {
        super();
    }
    
    public SensorMonitoringClientBadGatewayException(String message) {
        super(message);
    }
    
    public SensorMonitoringClientBadGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SensorMonitoringClientBadGatewayException(Throwable cause) {
        super(cause);
    }
}
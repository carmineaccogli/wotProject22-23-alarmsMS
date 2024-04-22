package it.safesiteguard.ms.alarms_ssguard.service;

import org.springframework.messaging.handler.annotation.Payload;

public interface MQTT_AlertsListener {

    void consume_MachineriesAlarms(@Payload String message);
}

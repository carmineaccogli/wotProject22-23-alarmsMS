package it.safesiteguard.ms.alarms_ssguard.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

public class MqttMessageException extends Exception {

    public MqttMessageException(String message) {
        super(message);
    }
}

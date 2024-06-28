package it.safesiteguard.ms.alarms_ssguard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.domain.DistanceAlert;
import it.safesiteguard.ms.alarms_ssguard.exceptions.MqttMessageException;
import it.safesiteguard.ms.alarms_ssguard.messages.AlertMessage;
import it.safesiteguard.ms.alarms_ssguard.messages.DistanceAlertMessage;
import it.safesiteguard.ms.alarms_ssguard.messages.GeneralAlertMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
public class MQTT_AlertsListener_Impl implements MQTT_AlertsListener {

    @Autowired
    private Validator validator;

    @Autowired
    private ManageAlertsService manageAlertsService;

    private static final Logger logger = LoggerFactory.getLogger(MQTT_AlertsListener_Impl.class);

    // Cache per la deduplicazione dei messaggi tramite MQTT
    private final Cache<LocalDateTime, AlertMessage> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    /** Funzione adibita all'ascolto dei topic: "zone/+/machineries/+/alarms"
     *                                          (tutti i macchinari di tutte le zone)
     *  1) Conversione da stringa al formato di messaggio Alert
     *  2) Controllo dei duplicati visto l'uso di una QoS=1:
     *      check se nella cache è presente un messaggio con lo stesso timestamp (stesso messaggio)
     *  3) Validazione del messaggio ricevuto
     *  4) Chiamata all'apposita funzione del service che gestisce quel tipo di alert e inserimento nella cache
     *
     * @param message
     */

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void consume_MachineriesAlarms(@Payload String message) {


        // 1
        AlertMessage genericAlert = convertMessage(message);
        if(genericAlert == null) {
            logger.error("Alert message not valid");
            return;
        }

        // 2
        if(cache.getIfPresent(genericAlert.getTimestamp()) != null) {
            logger.error("Duplicated message detected");
            return;
        }


        // 3
        try {
            validateMessage(genericAlert);
        }catch(MqttMessageException ex) {
            ex.printStackTrace();
            logger.error("Failed validation of alert message");
            return;
        }

        // 4
        Alert.Type alertType = genericAlert.getType();
        switch (alertType) {
            case DISTANCE:
                manageAlertsService.manageDistanceAlerts((DistanceAlertMessage) genericAlert);
                cache.put(genericAlert.getTimestamp(), genericAlert);
                break;
            case GENERAL:
                manageAlertsService.manageGeneralAlerts((GeneralAlertMessage) genericAlert);
                cache.put(genericAlert.getTimestamp(), genericAlert);
                break;
            case DRIVER_AWAY:
                manageAlertsService.manageDriverAwayAlerts((DistanceAlertMessage) genericAlert);
                break;
            default:
                logger.error("Alert type {} not available", alertType);
        }
    }




    private AlertMessage convertMessage(String message) {
        AlertMessage genericAlert = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            genericAlert = objectMapper.readValue(message, AlertMessage.class);
        }catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }

        System.out.println(genericAlert.getType());
        System.out.println(genericAlert.getTimestamp());
        System.out.println(genericAlert.getPriority());
        System.out.println(genericAlert.getTechnologyID());

        return genericAlert;
    }



    private void validateMessage(AlertMessage message) throws MqttMessageException {

        String validationError = isMessageValid(message);
        if(validationError != null) {
            throw new MqttMessageException(validationError);
        }
    }


    private String isMessageValid(AlertMessage genericAlert) {

        Set<ConstraintViolation<AlertMessage>> violations = validator.validate(genericAlert);

        StringBuilder errorMessage = new StringBuilder("Errori di validazione: ");
        for (ConstraintViolation<AlertMessage> violation : violations) {
            errorMessage.append(violation.getMessage()).append(", ");
        }

        errorMessage.setLength(errorMessage.length() - 2);
        String errors = errorMessage.toString();

        if (!violations.isEmpty()) {
            return errors;
        }

        return null;
    }
}

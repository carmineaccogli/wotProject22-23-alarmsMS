package it.safesiteguard.ms.alarms_ssguard.mappers;

import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.domain.DistanceAlert;
import it.safesiteguard.ms.alarms_ssguard.domain.GeneralAlert;
import it.safesiteguard.ms.alarms_ssguard.messages.DistanceAlertMessage;
import it.safesiteguard.ms.alarms_ssguard.messages.GeneralAlertMessage;
import org.springframework.stereotype.Component;

@Component
public class AlertMessageMapper {

    public DistanceAlert fromDistanceMessageToEntity(DistanceAlertMessage message) {
        DistanceAlert distanceAlert = new DistanceAlert();

        distanceAlert.setType(message.getType());
        distanceAlert.setPriority(message.getPriority());
        distanceAlert.setTechnologyID(message.getTechnologyID());
        distanceAlert.setWorkerID(message.getWorkerID());
        distanceAlert.setMachineryID(message.getMachineryID());
        distanceAlert.setTimestamp(message.getTimestamp());

        return distanceAlert;
    }


    public GeneralAlert fromGeneralMessageToEntity(GeneralAlertMessage message) {
        GeneralAlert generalAlert = new GeneralAlert();

        generalAlert.setType(Alert.Type.GENERAL);
        generalAlert.setPriority(message.getPriority());
        generalAlert.setTimestamp(message.getTimestamp());
        generalAlert.setTechnologyID(message.getTechnologyID());
        generalAlert.setDescription(message.getDescription());

        return generalAlert;
    }
}

package it.safesiteguard.ms.alarms_ssguard.mappers;

import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.domain.DistanceAlert;
import it.safesiteguard.ms.alarms_ssguard.domain.GeneralAlert;
import it.safesiteguard.ms.alarms_ssguard.dto.AlertViewDTO;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {

    public AlertViewDTO fromAlertTypeToViewDTO(Alert alert) {

        AlertViewDTO alertViewDTO = new AlertViewDTO();

        alertViewDTO.setId(alert.getId());
        alertViewDTO.setType(alert.getType());
        alertViewDTO.setPriority(alert.getPriority());
        alertViewDTO.setTimestamp(alert.getTimestamp().toString());
        alertViewDTO.setTechnologyID(alert.getTechnologyID());

        if(alert instanceof GeneralAlert)
            alertViewDTO.setDescription(((GeneralAlert) alert).getDescription());
        else if(alert instanceof DistanceAlert distanceAlert) {
            alertViewDTO.setMachineryID(distanceAlert.getMachineryID());
            alertViewDTO.setWorkerID(distanceAlert.getWorkerID());

            if(distanceAlert.getType().equals(Alert.Type.DISTANCE) && distanceAlert.getDuration() != null)
                alertViewDTO.setSecondsDuration(distanceAlert.getDuration().getSeconds());
        }

        return alertViewDTO;
    }
}

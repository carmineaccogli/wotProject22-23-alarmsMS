package it.safesiteguard.ms.alarms_ssguard.service;

import it.safesiteguard.ms.alarms_ssguard.messages.DistanceAlertMessage;
import it.safesiteguard.ms.alarms_ssguard.messages.GeneralAlertMessage;

public interface ManageAlertsService {


    void manageDistanceAlerts(DistanceAlertMessage message);

    void manageGeneralAlerts(GeneralAlertMessage message);

    void manageDriverAwayAlerts(DistanceAlertMessage message);

}

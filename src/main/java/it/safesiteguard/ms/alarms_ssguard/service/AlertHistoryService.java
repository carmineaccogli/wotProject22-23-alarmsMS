package it.safesiteguard.ms.alarms_ssguard.service;

import it.safesiteguard.ms.alarms_ssguard.domain.Alert;

import java.time.LocalDate;
import java.util.List;

public interface AlertHistoryService {

    List<Alert> getAll();

    List<Alert> filterAlertsByType(String type);

    List<Alert> filterAlertsByPriority(String priority);

    List<Alert> filterAlertsByDate(LocalDate date);
}

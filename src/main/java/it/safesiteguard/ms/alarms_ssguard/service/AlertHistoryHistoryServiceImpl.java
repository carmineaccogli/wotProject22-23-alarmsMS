package it.safesiteguard.ms.alarms_ssguard.service;

import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.repositories.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertHistoryHistoryServiceImpl implements AlertHistoryService {

    @Autowired
    private AlertRepository alertRepository;

    public List<Alert> getAll() {
        return alertRepository.findAllByOrderByTimestampDesc();
    }

    public List<Alert> filterAlertsByType(String type) {
        return alertRepository.findAlertsByTypeOrderByTimestampDesc(type);
    }

    public List<Alert> filterAlertsByPriority(String priority) {
        return alertRepository.findAlertsByPriorityOrderByTimestampDesc(priority);
    }

    public List<Alert> filterAlertsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atStartOfDay().plusDays(1).minusSeconds(1);
        return alertRepository.findAlertsByTimestampBetween(startOfDay, endOfDay);
    }


    public List<Alert> filterAlertByWorker(String workerID) {
        Sort sort = Sort.by(Sort.Direction.DESC, "timestamp");
        return alertRepository.findAlertsByWorkerIDOrderByTimestampDesc(workerID, sort);
    }

}

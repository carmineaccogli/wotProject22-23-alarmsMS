package it.safesiteguard.ms.alarms_ssguard.dto;

import java.util.List;
import java.util.Map;

public class StatisticsDTO {

    private Integer totalAlerts;

    private Map<String, Integer> numberOfAlertsByType;

    private Double averageDurationDistanceAlarms;

    private Map<String, Integer> top3WorkersByAlarms;

    private Map<String, Integer> top3MachineriesByAlarms;


    public Integer getTotalAlerts() {
        return totalAlerts;
    }

    public void setTotalAlerts(Integer totalAlerts) {
        this.totalAlerts = totalAlerts;
    }

    public Map<String, Integer> getNumberOfAlertsByType() {
        return numberOfAlertsByType;
    }

    public void setNumberOfAlertsByType(Map<String, Integer> numberOfAlertsByType) {
        this.numberOfAlertsByType = numberOfAlertsByType;
    }

    public Double getAverageDurationDistanceAlarms() {
        return averageDurationDistanceAlarms;
    }

    public void setAverageDurationDistanceAlarms(Double averageDurationDistanceAlarms) {
        this.averageDurationDistanceAlarms = averageDurationDistanceAlarms;
    }

    public Map<String, Integer> getTop3WorkersByAlarms() {
        return top3WorkersByAlarms;
    }

    public void setTop3WorkersByAlarms(Map<String, Integer> top3WorkersByAlarms) {
        this.top3WorkersByAlarms = top3WorkersByAlarms;
    }

    public Map<String, Integer> getTop3MachineriesByAlarms() {
        return top3MachineriesByAlarms;
    }

    public void setTop3MachineriesByAlarms(Map<String, Integer> top3MachineriesByAlarms) {
        this.top3MachineriesByAlarms = top3MachineriesByAlarms;
    }
}

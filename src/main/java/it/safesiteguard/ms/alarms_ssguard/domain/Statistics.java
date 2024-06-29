package it.safesiteguard.ms.alarms_ssguard.domain;

import java.util.Map;

public class Statistics {

    private int totalAlarms;

    private Map<String, Integer> numberOfAlarmsByType;

    private double totalDistanceDuration;

    private Map<String, Integer> top3WorkersByAlarms;

    private Map<String, Integer> top3MachineriesByAlarms;



    public int getTotalAlarms() {
        return totalAlarms;
    }

    public void setTotalAlarms(int totalAlarms) {
        this.totalAlarms = totalAlarms;
    }

    public Map<String, Integer> getNumberOfAlarmsByType() {
        return numberOfAlarmsByType;
    }

    public void setNumberOfAlarmsByType(Map<String, Integer> numberOfAlarmsByType) {
        this.numberOfAlarmsByType = numberOfAlarmsByType;
    }

    public double getTotalDistanceDuration() {
        return totalDistanceDuration;
    }

    public void setTotalDistanceDuration(double totalDistanceDuration) {
        this.totalDistanceDuration = totalDistanceDuration;
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

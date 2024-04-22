package it.safesiteguard.ms.alarms_ssguard.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceAlertMessage extends AlertMessage {

    private String workerID;

    private String machineryID;

    private boolean isEntryAlarm;


    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getMachineryID() {
        return machineryID;
    }

    public void setMachineryID(String machineryID) {
        this.machineryID = machineryID;
    }


    public boolean isEntryAlarm() {
        return isEntryAlarm;
    }

    public void setEntryAlarm(boolean entryAlarm) {
        isEntryAlarm = entryAlarm;
    }
}

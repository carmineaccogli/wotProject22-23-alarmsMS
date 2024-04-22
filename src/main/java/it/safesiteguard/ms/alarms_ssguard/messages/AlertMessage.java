package it.safesiteguard.ms.alarms_ssguard.messages;

import it.safesiteguard.ms.alarms_ssguard.domain.Alert;

import java.time.LocalDateTime;

public abstract class AlertMessage {


    private LocalDateTime timestamp;

    private Alert.Type type;

    private String technologyID;

    private Alert.Priority priority;



    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Alert.Type getType() {
        return type;
    }

    public void setType(Alert.Type type) {
        this.type = type;
    }

    public String getTechnologyID() {
        return technologyID;
    }

    public void setTechnologyID(String technologyID) {
        this.technologyID = technologyID;
    }

    public Alert.Priority getPriority() {
        return priority;
    }

    public void setPriority(Alert.Priority priority) {
        this.priority = priority;
    }
}

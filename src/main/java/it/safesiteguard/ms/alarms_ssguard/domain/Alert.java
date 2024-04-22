package it.safesiteguard.ms.alarms_ssguard.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="alerts")
public abstract class Alert {

    /* Definizione del formato di base per un alert.
        Queste proprietà si ritroveranno in ogni tipologia di allarme
     */

    @Id
    private String id;

    private LocalDateTime timestamp;

    private Type type;

    private String technologyID;

    private Priority priority;


    public enum Type {
        DISTANCE, GENERAL, DRIVER_AWAY
    }

    public enum Priority {
        LOW, MID, HIGH
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTechnologyID() {
        return technologyID;
    }

    public void setTechnologyID(String technologyID) {
        this.technologyID = technologyID;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}

package it.safesiteguard.ms.alarms_ssguard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.safesiteguard.ms.alarms_ssguard.domain.Alert;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlertViewDTO {


    private String id;

    private LocalDateTime timestamp;

    private Alert.Type type;

    private String technologyID;

    private Alert.Priority priority;


    // FOR TYPE: DISTANCE
    private String workerID;

    private String machineryID;

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = ZeroLongValueFilter.class)
    private long secondsDuration;
    //


    // FOR TYPE: GENERAL
    private String description;
    //


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

    public long getSecondsDuration() {
        return secondsDuration;
    }

    public void setSecondsDuration(long secondsDuration) {
        this.secondsDuration = secondsDuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class ZeroLongValueFilter {
        @Override
        public boolean equals(Object obj) {
            return (obj instanceof Long) && ((Long) obj) == 0L;
        }
    }
}

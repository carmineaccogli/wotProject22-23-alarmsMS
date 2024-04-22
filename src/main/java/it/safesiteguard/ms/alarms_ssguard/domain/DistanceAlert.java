package it.safesiteguard.ms.alarms_ssguard.domain;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;

@Document(collection="alerts")
@TypeAlias("distanceAlert")
public class DistanceAlert extends Alert {

    private String workerID;

    private String machineryID;

    private Duration duration;



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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}

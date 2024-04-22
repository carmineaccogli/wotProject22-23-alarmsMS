package it.safesiteguard.ms.alarms_ssguard.domain;


import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="alerts")
@TypeAlias("generalAlert")
public class GeneralAlert extends Alert {

    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

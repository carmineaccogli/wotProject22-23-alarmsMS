package it.safesiteguard.ms.alarms_ssguard.service;


import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.domain.DistanceAlert;
import it.safesiteguard.ms.alarms_ssguard.domain.GeneralAlert;
import it.safesiteguard.ms.alarms_ssguard.mappers.AlertMessageMapper;
import it.safesiteguard.ms.alarms_ssguard.messages.DistanceAlertMessage;
import it.safesiteguard.ms.alarms_ssguard.messages.GeneralAlertMessage;
import it.safesiteguard.ms.alarms_ssguard.repositories.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class ManageAlertsServiceImpl implements ManageAlertsService {


    @Autowired
    private AlertMessageMapper alertMessageMapper;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private StatisticsService statisticsService;


    @Override
    public void manageDistanceAlerts(DistanceAlertMessage message) {

        // conversione da messaggio a oggetto di dominio
        DistanceAlert distanceAlert = alertMessageMapper.fromDistanceMessageToEntity(message);

        // controllare sul db se esiste un alert con stesso worker e machinery con durata=null ordinati per timestamp dec
        Optional<Alert> optPastEntryAlert = alertRepository.findFirstByWorkerIDAndMachineryIDAndDurationIsNullOrderByTimestampDesc(distanceAlert.getWorkerID(), distanceAlert.getMachineryID());

        // se esiste significa che quello arrivato è un messaggio di exit
        // aggiornato il campo durata come differenza dei timestamp
        // memorizzazione nel db
        // aggiornamento statistiche
        if(optPastEntryAlert.isPresent()) {

            /* Se arriva uno stesso allarme con priorità Warning 2 volte di seguito:
               1. memorizzare il nuovo allarme con duration null
               2. eliminare l'allarme precedente in quanto non ha visto una risoluzione
               3. return
             */
            DistanceAlert pastEntryAlert = (DistanceAlert) optPastEntryAlert.get();

            if(!distanceAlert.getPriority().equals(Alert.Priority.COMMUNICATION)) {

                // 1
                distanceAlert.setDuration(null);
                alertRepository.save(distanceAlert);

                // 2
                alertRepository.delete(pastEntryAlert);

                return;
            }


            Duration duration = Duration.between(pastEntryAlert.getTimestamp(), distanceAlert.getTimestamp());

            pastEntryAlert.setDuration(duration);
            alertRepository.save(pastEntryAlert);

            // gestione statistiche
            statisticsService.updateStatistics(pastEntryAlert);
        }
        // se non esiste significa che quello arrivato è un messaggio di entry (tramite apposito controllo aggiuntivo
        // per evitare la presenza di exit alarms privi del corrispettivo entry)
        // memorizzazione nel db con campo durata=null
        else if(!distanceAlert.getPriority().equals(Alert.Priority.COMMUNICATION)) {
            distanceAlert.setDuration(null);
            alertRepository.save(distanceAlert);
        }

    }


    @Override
    public void manageGeneralAlerts(GeneralAlertMessage message) {

        // conversione da messaggio a oggetto di dominio
        GeneralAlert generalAlert = alertMessageMapper.fromGeneralMessageToEntity(message);

        // memorizzazione nel db
        alertRepository.save(generalAlert);

        // gestione statistiche
        statisticsService.updateStatistics(generalAlert);
    }

    @Override
    public void manageDriverAwayAlerts(DistanceAlertMessage message) {

        DistanceAlert driverAwayAlert = alertMessageMapper.fromDistanceMessageToEntity(message);
        alertRepository.save(driverAwayAlert);

        // gestione statistiche
        statisticsService.updateStatistics(driverAwayAlert);
    }




}

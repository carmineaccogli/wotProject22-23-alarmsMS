package it.safesiteguard.ms.alarms_ssguard.service;


import it.safesiteguard.ms.alarms_ssguard.domain.*;
import it.safesiteguard.ms.alarms_ssguard.repositories.AnnualStatisticsRepository;
import it.safesiteguard.ms.alarms_ssguard.repositories.MonthlyStatisticsRepository;
import it.safesiteguard.ms.alarms_ssguard.repositories.WeeklyStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService{

    @Autowired
    private AnnualStatisticsRepository annualStatisticsRepository;

    @Autowired
    private MonthlyStatisticsRepository monthlyStatisticsRepository;

    @Autowired
    private WeeklyStatisticsRepository weeklyStatisticsRepository;

    @Autowired
    private AlertHistoryService alertHistoryService;



    /**
     *  1. Verificare l'esistenza di un documento con anno pari all'anno richiesto
     *  2. Se è presente:
     *      2.1 Calcolare il valore medio di duration come somma delle duration / numero di allarmi distanza
     *      2.2 Impostare il campo totalDuration a questo valore
     * @param year
     * @return
     */
    public AnnualStatistics deriveAnnualStatistics(int year) {

        // 1
        Optional<AnnualStatistics> optAnnualStatistics = annualStatisticsRepository.findByYear(year);

        if(optAnnualStatistics.isEmpty())
            return null;

        // 2.1
        AnnualStatistics annualStatistics = optAnnualStatistics.get();
        double avgDurationDistanceAlert = calculateAverageDistanceDuration(annualStatistics.getNumberOfAlarmsByType(), annualStatistics.getTotalDistanceDuration());

        // 2.2
        annualStatistics.setTotalDistanceDuration(avgDurationDistanceAlert);

        return annualStatistics;
    }

    public MonthlyStatistics deriveMonthlyStatistics(int year, int month) {

        Optional<MonthlyStatistics> optMonthlyStatistics = monthlyStatisticsRepository.findByYearAndMonth(year, month);

        if(optMonthlyStatistics.isEmpty())
            return null;

        // 2.1
        MonthlyStatistics monthlyStatistics = optMonthlyStatistics.get();
        double avgDurationDistanceAlert = calculateAverageDistanceDuration(monthlyStatistics.getNumberOfAlarmsByType(), monthlyStatistics.getTotalDistanceDuration());

        // 2.2
        monthlyStatistics.setTotalDistanceDuration(avgDurationDistanceAlert);

        return monthlyStatistics;

    }


    public WeeklyStatistics deriveWeeklyStatistics(int year, int month, int day) {

        // Derivazione della settimana dell'anno a partire dalla data
        int week = getWeekOfYear(year, month, day);

        Optional<WeeklyStatistics> optWeeklyStatistics = weeklyStatisticsRepository.findByYearAndWeek(year, week);
        if(optWeeklyStatistics.isEmpty())
            return null;

        // 2.1
        WeeklyStatistics weeklyStatistics = optWeeklyStatistics.get();
        double avgDurationDistanceAlert = calculateAverageDistanceDuration(weeklyStatistics.getNumberOfAlarmsByType(), weeklyStatistics.getTotalDistanceDuration());

        // 2.2
        weeklyStatistics.setTotalDistanceDuration(avgDurationDistanceAlert);

        return weeklyStatistics;
    }

    /** FUNZIONE PER IL CALCOLO DELLE STATISTICHE GIORNALIERE A PARTIRE DALLA LISTA DI ALLARMI NEL DB
     *
     *  1. Ottenimento lista degli allarmi per la data indicata
     *  2. Creazione dell'oggetto statistica, hashMap per il numero di allarmi per tipologia e variabile per il calcolo
     *      della media della durata degli allarmi di tipo DISTANCE
     *  3. Setting del numero di allarmi totali pari alla dimensione della lista ottenuta
     *  4. Iterazione su ogni allarme
     *      4.1 Aggiornamento della somma totale delle duration se l'allarme è di tipo DISTANCE
     *      4.2 Creazione del mapping Tipo Allarme - Numero allarmi
     *  5. Setting mapping ottenuto nell'apposito campo
     *  6. Creazione del mapping che contiene le 3 top entità (workers and machineries) per allarmi
     *  7. Calcolo della durata media degli allarmi di distanza e setting dell'apposito campo
     *
     * @param year
     * @param month
     * @param day
     * @return
     */

    public Statistics calculateDailyStatistics(int year, int month, int day) {

        // 1
        LocalDate dateToSearch = LocalDate.of(year, month, day);
        List<Alert> dayAlertsList = alertHistoryService.filterAlertsByDate(dateToSearch);

        if(dayAlertsList.isEmpty())
            return null;

        // 2
        Statistics dailyStats = new Statistics();
        Map<String, Integer> mappingTypeCounter = new HashMap<>();
        double sumDurationDistanceAlerts = 0.0;

        // 3
        dailyStats.setTotalAlarms(dayAlertsList.size());

        // 4
        for(Alert alert : dayAlertsList) {

            // 4.1
            if(alert.getType().equals(Alert.Type.DISTANCE)) {
                DistanceAlert distanceAlert = (DistanceAlert) alert;
                sumDurationDistanceAlerts += distanceAlert.getDuration().getSeconds();
            }

            // 4.2
            String typeName = alert.getType().toString();
            if(!mappingTypeCounter.containsKey(typeName))
                mappingTypeCounter.put(typeName, 1);
            else
                mappingTypeCounter.put(typeName, mappingTypeCounter.get(typeName) + 1);
        }

        // 5
        dailyStats.setNumberOfAlarmsByType(mappingTypeCounter);

        // 6
        Map<String,Integer> top3Workers = updateTopEntityList("workerID", dayAlertsList);
        dailyStats.setTop3WorkersByAlarms(top3Workers);

        Map<String,Integer> top3Machineries = updateTopEntityList("machineryID", dayAlertsList);
        dailyStats.setTop3MachineriesByAlarms(top3Machineries);

        // 7
        double avgDurationDistanceAlert = calculateAverageDistanceDuration(dailyStats.getNumberOfAlarmsByType(), sumDurationDistanceAlerts);
        dailyStats.setTotalDistanceDuration(avgDurationDistanceAlert);

        return dailyStats;
    }

    /** FUNZIONE PER L'INIZIALIZZAZIONE DI UN DOCUMENTO VUOTO DI STATISTICHE (per i campi comuni)
     *
     *  1. Setting del totale Allarmi a 1
     *  2. Inserimento della coppia TIPO-1 nel mapping di gestione dei tipi
     *  3. Controllo se alert è di tipo DISTANCE o DRIVER_AWAY
     *      3.1 Inserimento delle coppie WORKERID-1 e MACHINERYID-1 nei mapping dei top3 by alarms
     *      3.2 Controllo se alert è di tipo DISTANCE per poter inizializzare la total duration alla durata di tale allarme
     *
     * @param statistics
     * @param alert
     */
    private void createStatsDocument(Statistics statistics, Alert alert) {

        // 1
        statistics.setTotalAlarms(1);

        // 2
        Map<String, Integer> numberOfAlarmsByType = new HashMap<>();
        numberOfAlarmsByType.put(alert.getType().toString(), 1);
        statistics.setNumberOfAlarmsByType(numberOfAlarmsByType);

        // 3
        if (alert instanceof DistanceAlert distanceAlert) {

            // 3.1
            Map<String, Integer> mapWorkersCounter = new HashMap<>();
            Map<String, Integer> mapMachineriesCounter = new HashMap<>();

            mapWorkersCounter.put(distanceAlert.getWorkerID(), 1);
            mapMachineriesCounter.put(distanceAlert.getMachineryID(), 1);

            statistics.setTop3WorkersByAlarms(mapWorkersCounter);
            statistics.setTop3MachineriesByAlarms(mapMachineriesCounter);

            // 3.2
            if (alert.getType().equals(Alert.Type.DISTANCE))
                statistics.setTotalDistanceDuration(distanceAlert.getDuration().getSeconds());
            else
                statistics.setTotalDistanceDuration(0.0);
        }
    }

    /** FUNZIONE PER L'AGGIORNAMENTO DEI VALORI DI UNO STATS DOCUMENT GENERICO (campi comuni)
     *
     *  1. Incremento di 1 il numero totale di allarmi
     *  2. Aggiornamento del map tipo alert-counter: se il tipo è già presente incremento di 1 il contatore, altrimenti
     *      inserisco la nuova coppia
     *  3. Controllo se alert è di tipo DISTANCE
     *      3.1 Aggiorno i mapping WORKER-COUNTER e MACHINERY-COUNTER mantenendo solo le prime 3 entità per entrambi
     *      3.2 Se il tipo è DISTANCE, aggiorno la total duration sommando il valore di duration di tale alert alla somma precedente
     *
     * @param statistics
     * @param alert
     */
    private void updateStatsDocument(Statistics statistics, Alert alert) {

        // 1
        statistics.setTotalAlarms(statistics.getTotalAlarms() + 1);

        // 2
        String typeName = alert.getType().toString();
        Map<String, Integer> numOfAlarmsByType = statistics.getNumberOfAlarmsByType();
        if(!numOfAlarmsByType.containsKey(typeName))
            numOfAlarmsByType.put(typeName, 1);
        else
            numOfAlarmsByType.put(typeName, numOfAlarmsByType.get(typeName) + 1);

        statistics.setNumberOfAlarmsByType(numOfAlarmsByType);


        // 3
        if(alert instanceof DistanceAlert distanceAlert) {

            // 3.1
            Map<String, Integer> newTop3WorkersByAlarms = updateAndLimitMapping("workerID", distanceAlert, statistics.getTop3WorkersByAlarms());
            Map<String, Integer> newTop3MachineriesByAlarms = updateAndLimitMapping("machineryID", distanceAlert, statistics.getTop3MachineriesByAlarms());

            statistics.setTop3WorkersByAlarms(newTop3WorkersByAlarms);
            statistics.setTop3MachineriesByAlarms(newTop3MachineriesByAlarms);

            // 3.2
            if (alert.getType().equals(Alert.Type.DISTANCE))
                statistics.setTotalDistanceDuration(statistics.getTotalDistanceDuration() + distanceAlert.getDuration().getSeconds());
        }
    }





    public void updateStatistics(Alert alert) {
        updateYearlyStats(alert);
        updateMonthlyStats(alert);
        updateWeeklyStats(alert);
    }


    /**
     *  1. Individuazione del documento nell'apposita repository
     *  2. Se il documento non è presente, è necessario crearlo inizializzando i campi e salvarlo
     *  3. Se il documento è presente, è necessario solo aggiornare i campi in modo opportuno e salvarlo aggiornato
     * @param alert
     */
    private void updateYearlyStats(Alert alert) {
        // 1
        Optional<AnnualStatistics> optAnnualStats = annualStatisticsRepository.findByYear(alert.getTimestamp().getYear());

        // 2
        if(optAnnualStats.isEmpty()) {
            AnnualStatistics annualStatistics = new AnnualStatistics();

            annualStatistics.setYear(alert.getTimestamp().getYear());
            createStatsDocument(annualStatistics, alert);

            annualStatisticsRepository.save(annualStatistics);
            return;

        }
        // 3
        AnnualStatistics annualStatistics = optAnnualStats.get();
        updateStatsDocument(annualStatistics, alert);

        annualStatisticsRepository.save(annualStatistics);
    }



    private void updateMonthlyStats(Alert alert) {
        // 1
        Optional<MonthlyStatistics> optMonthlyStats = monthlyStatisticsRepository.findByYearAndMonth(alert.getTimestamp().getYear(), alert.getTimestamp().getMonthValue());

        // 2
        if(optMonthlyStats.isEmpty()) {
            MonthlyStatistics monthlyStatistics = new MonthlyStatistics();

            monthlyStatistics.setYear(alert.getTimestamp().getYear());
            monthlyStatistics.setMonth(alert.getTimestamp().getMonthValue());
            createStatsDocument(monthlyStatistics, alert);

            monthlyStatisticsRepository.save(monthlyStatistics);
            return;

        }
        // 3
        MonthlyStatistics monthlyStatistics = optMonthlyStats.get();
        updateStatsDocument(monthlyStatistics, alert);

        monthlyStatisticsRepository.save(monthlyStatistics);
    }


    private void updateWeeklyStats(Alert alert) {
        // 1
        int week = getWeekOfYear(alert.getTimestamp().getYear(), alert.getTimestamp().getMonthValue(), alert.getTimestamp().getDayOfMonth());
        Optional<WeeklyStatistics> optWeeklyStats = weeklyStatisticsRepository.findByYearAndWeek(alert.getTimestamp().getYear(), week);

        // 2
        if(optWeeklyStats.isEmpty()) {
            WeeklyStatistics weeklyStatistics = new WeeklyStatistics();

            weeklyStatistics.setYear(alert.getTimestamp().getYear());
            weeklyStatistics.setWeek(week);

            createStatsDocument(weeklyStatistics, alert);

            weeklyStatisticsRepository.save(weeklyStatistics);
            return;

        }
        // 3
        WeeklyStatistics weeklyStatistics = optWeeklyStats.get();
        updateStatsDocument(weeklyStatistics, alert);

        weeklyStatisticsRepository.save(weeklyStatistics);

    }






    private double calculateAverageDistanceDuration(Map<String, Integer> numberOfAlarmsByType, double totalDuration) {
        Integer distanceAlarmCount = numberOfAlarmsByType.get("DISTANCE");
        if (distanceAlarmCount != null && distanceAlarmCount > 0) {
            return totalDuration/ distanceAlarmCount;
        }
        return 0;
    }

    private int getWeekOfYear(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        WeekFields weekFields = WeekFields.of(Locale.ITALY);
        return date.get(weekFields.weekOfWeekBasedYear());
    }

    private Map<String, Integer> updateAndLimitMapping(String field, DistanceAlert distanceAlert, Map<String, Integer> entityCountMap) {
        updateMapping(field, distanceAlert, entityCountMap);
        return limitMapping(entityCountMap);
    }


    private void updateMapping(String field, DistanceAlert distanceAlert, Map<String, Integer> entityCountMap) {
        String key = "";
        if (field.equals("workerID")) {
            key = distanceAlert.getWorkerID();
        } else if (field.equals("machineryID")) {
            key = distanceAlert.getMachineryID();
        }

        if (!key.isEmpty()) {
            entityCountMap.put(key, entityCountMap.getOrDefault(key, 0) + 1);
        }
    }

    private Map<String, Integer> limitMapping(Map<String, Integer> entityCountMap) {
        return entityCountMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


    private Map<String, Integer> updateTopEntityList(String field, List<Alert> dayAlertsList) {


        // Mapping dinamico in base a field: String può essere workerID o machineryID
        Map<String, Integer> entityCountMap = new HashMap<>();

        // Avvaloramento di un mapping con tutti i lavoratori o macchinari con corrispondente alarms count
        for (Alert alert : dayAlertsList) {
            if(alert.getType().equals(Alert.Type.DISTANCE) || alert.getType().equals(Alert.Type.DRIVER_AWAY)) {
                DistanceAlert distanceAlert = (DistanceAlert) alert;

                updateMapping(field, distanceAlert, entityCountMap);
            }
        }

        // Creazione di un mapping che contiene solo i top 3 elementi per numero di allarmi
        return limitMapping(entityCountMap);
    }

}

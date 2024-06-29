package it.safesiteguard.ms.alarms_ssguard.mappers;

import it.safesiteguard.ms.alarms_ssguard.domain.AnnualStatistics;
import it.safesiteguard.ms.alarms_ssguard.domain.MonthlyStatistics;
import it.safesiteguard.ms.alarms_ssguard.domain.Statistics;
import it.safesiteguard.ms.alarms_ssguard.domain.WeeklyStatistics;
import it.safesiteguard.ms.alarms_ssguard.dto.StatisticsDTO;
import org.springframework.stereotype.Component;

@Component
public class StatisticsMapper {

    public StatisticsDTO fromAnnualStatisticsToDTO(AnnualStatistics annualStatistics) {
        StatisticsDTO statisticsDTO = new StatisticsDTO();

        statisticsDTO.setTotalAlerts(annualStatistics.getTotalAlarms());
        statisticsDTO.setNumberOfAlertsByType(annualStatistics.getNumberOfAlarmsByType());
        statisticsDTO.setAverageDurationDistanceAlarms(annualStatistics.getTotalDistanceDuration()); // in realtà il campo è già avvalorato con il valore di media
        statisticsDTO.setTop3WorkersByAlarms(annualStatistics.getTop3WorkersByAlarms());
        statisticsDTO.setTop3MachineriesByAlarms(annualStatistics.getTop3MachineriesByAlarms());

        return statisticsDTO;
    }

    public StatisticsDTO fromMonthlyStatisticsToDTO(MonthlyStatistics monthlyStatistics) {
        StatisticsDTO statisticsDTO = new StatisticsDTO();

        statisticsDTO.setTotalAlerts(monthlyStatistics.getTotalAlarms());
        statisticsDTO.setNumberOfAlertsByType(monthlyStatistics.getNumberOfAlarmsByType());
        statisticsDTO.setAverageDurationDistanceAlarms(monthlyStatistics.getTotalDistanceDuration()); // stessa cosa
        statisticsDTO.setTop3WorkersByAlarms(monthlyStatistics.getTop3WorkersByAlarms());
        statisticsDTO.setTop3MachineriesByAlarms(monthlyStatistics.getTop3MachineriesByAlarms());

        return statisticsDTO;
    }

    public StatisticsDTO fromWeeklyStatisticsToDTO(WeeklyStatistics weeklyStatistics) {
        StatisticsDTO statisticsDTO = new StatisticsDTO();

        statisticsDTO.setTotalAlerts(weeklyStatistics.getTotalAlarms());
        statisticsDTO.setNumberOfAlertsByType(weeklyStatistics.getNumberOfAlarmsByType());
        statisticsDTO.setAverageDurationDistanceAlarms(weeklyStatistics.getTotalDistanceDuration()); // stessa cosa
        statisticsDTO.setTop3WorkersByAlarms(weeklyStatistics.getTop3WorkersByAlarms());
        statisticsDTO.setTop3MachineriesByAlarms(weeklyStatistics.getTop3MachineriesByAlarms());

        return statisticsDTO;
    }

    public StatisticsDTO fromDailyStatisticsToDTO(Statistics statistics) {
        StatisticsDTO statisticsDTO = new StatisticsDTO();

        statisticsDTO.setTotalAlerts(statistics.getTotalAlarms());
        statisticsDTO.setNumberOfAlertsByType(statistics.getNumberOfAlarmsByType());
        statisticsDTO.setAverageDurationDistanceAlarms(statistics.getTotalDistanceDuration()); // stessa cosa
        statisticsDTO.setTop3WorkersByAlarms(statistics.getTop3WorkersByAlarms());
        statisticsDTO.setTop3MachineriesByAlarms(statistics.getTop3MachineriesByAlarms());

        return statisticsDTO;
    }
}

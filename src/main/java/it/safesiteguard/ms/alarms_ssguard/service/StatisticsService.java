package it.safesiteguard.ms.alarms_ssguard.service;

import it.safesiteguard.ms.alarms_ssguard.domain.*;

public interface StatisticsService {

    AnnualStatistics deriveAnnualStatistics(int year);

    MonthlyStatistics deriveMonthlyStatistics(int year, int month);

    WeeklyStatistics deriveWeeklyStatistics(int year, int month, int day);

    Statistics calculateDailyStatistics(int year, int month, int day);

    void updateStatistics(Alert alert);

}

package it.safesiteguard.ms.alarms_ssguard.service;

import it.safesiteguard.ms.alarms_ssguard.domain.AnnualStatistics;
import it.safesiteguard.ms.alarms_ssguard.domain.MonthlyStatistics;
import it.safesiteguard.ms.alarms_ssguard.domain.Statistics;
import it.safesiteguard.ms.alarms_ssguard.domain.WeeklyStatistics;

public interface StatisticsService {

    AnnualStatistics deriveAnnualStatistics(int year);

    MonthlyStatistics deriveMonthlyStatistics(int year, int month);

    WeeklyStatistics deriveWeeklyStatistics(int year, int month, int day);

    Statistics calculateDailyStatistics(int year, int month, int day);
}

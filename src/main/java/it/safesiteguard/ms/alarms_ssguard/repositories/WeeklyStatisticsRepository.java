package it.safesiteguard.ms.alarms_ssguard.repositories;

import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.domain.MonthlyStatistics;
import it.safesiteguard.ms.alarms_ssguard.domain.WeeklyStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WeeklyStatisticsRepository extends MongoRepository<WeeklyStatistics, String> {

    Optional<WeeklyStatistics> findByYearAndWeek(int year, int week);
}

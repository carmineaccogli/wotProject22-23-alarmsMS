package it.safesiteguard.ms.alarms_ssguard.repositories;

import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.domain.AnnualStatistics;
import it.safesiteguard.ms.alarms_ssguard.domain.MonthlyStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MonthlyStatisticsRepository extends MongoRepository<MonthlyStatistics, String> {

    Optional<MonthlyStatistics> findByYearAndMonth(int year, int month);
}

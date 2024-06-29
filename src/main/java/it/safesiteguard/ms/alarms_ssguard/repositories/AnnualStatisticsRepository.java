package it.safesiteguard.ms.alarms_ssguard.repositories;

import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.domain.AnnualStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AnnualStatisticsRepository extends MongoRepository<AnnualStatistics, String> {

    Optional<AnnualStatistics> findByYear(int year);
}

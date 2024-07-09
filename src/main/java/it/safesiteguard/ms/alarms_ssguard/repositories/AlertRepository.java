package it.safesiteguard.ms.alarms_ssguard.repositories;

import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlertRepository extends MongoRepository<Alert, String> {

    List<Alert> findAllByOrderByTimestampDesc();

    @Query("{ 'type' : { '$regex' : ?0, '$options' : 'i' } }")
    List<Alert> findAlertsByTypeOrderByTimestampDesc(String type);

    @Query("{ 'priority' : { '$regex' : ?0, '$options' : 'i' } }")
    List<Alert> findAlertsByPriorityOrderByTimestampDesc(String priority);

    List<Alert> findAlertsByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query("{'workerID': ?0, 'machineryID': ?1, 'duration': null, '_class': 'distanceAlert'}")
    Optional<Alert> findFirstByWorkerIDAndMachineryIDAndDurationIsNullOrderByTimestampDesc(String workerID, String machineryID);

    @Query("{ '_class' : 'distanceAlert', 'workerID' : ?0 }")
    List<Alert> findAlertsByWorkerIDOrderByTimestampDesc(String workerID, Sort sort);
}

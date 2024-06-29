package it.safesiteguard.ms.alarms_ssguard.restcontrollers;


import it.safesiteguard.ms.alarms_ssguard.domain.*;
import it.safesiteguard.ms.alarms_ssguard.dto.AlertViewDTO;
import it.safesiteguard.ms.alarms_ssguard.dto.StatisticsDTO;
import it.safesiteguard.ms.alarms_ssguard.mappers.StatisticsMapper;
import it.safesiteguard.ms.alarms_ssguard.service.StatisticsService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/api/statistics")
@Validated
public class StatisticsRestController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private StatisticsMapper statisticsMapper;


    /*
        GET per ottenere le statistiche annuali
     */
    @RequestMapping(value="/{year}", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsDTO> getAnnualStatistics(@Min(2000) @Max(2025) @PathVariable("year") int year) {

        AnnualStatistics annualStatistics = statisticsService.deriveAnnualStatistics(year);

        if(annualStatistics == null)
            return ResponseEntity.noContent().build();

        StatisticsDTO statisticsDTO = statisticsMapper.fromAnnualStatisticsToDTO(annualStatistics);
        return ResponseEntity.ok(statisticsDTO);
    }


    /*
        GET per ottenere le statistiche mensili
     */
    @RequestMapping(value="/{year}/{month}", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsDTO> getMonthlyStatistics(@Min(2000) @Max(2025) @PathVariable("year") int year,
                                                              @Min(1) @Max(12) @PathVariable("month") int month) {

        MonthlyStatistics monthlyStatistics = statisticsService.deriveMonthlyStatistics(year, month);

        if(monthlyStatistics == null)
            return ResponseEntity.noContent().build();

        StatisticsDTO statisticsDTO = statisticsMapper.fromMonthlyStatisticsToDTO(monthlyStatistics);
        return ResponseEntity.ok(statisticsDTO);
    }


    /*
        GET per ottenere le statistiche giornaliere
     */
    @RequestMapping(value="/{year}/{month}/{day}", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsDTO> getDailyStatistics(@Min(2000) @Max(2025) @PathVariable("year") int year,
                                                                 @Min(1) @Max(12) @PathVariable("month") int month,
                                                                 @Min(1) @Max(31) @PathVariable("day") int day) {

        Statistics dailyStats = statisticsService.calculateDailyStatistics(year, month, day);

        if(dailyStats == null)
            return ResponseEntity.noContent().build();

        StatisticsDTO statisticsDTO = statisticsMapper.fromDailyStatisticsToDTO(dailyStats);
        return ResponseEntity.ok(statisticsDTO);
    }


    /*
        GET per ottenere le statistiche settimanali
     */
    @RequestMapping(value="/{year}/{month}/{day}/week", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsDTO> getWeeklyStatistics(@PathVariable("year") int year,
                                                                  @PathVariable("month") int month,
                                                                  @PathVariable("day") int day) {

        WeeklyStatistics weeklyStatistics = statisticsService.deriveWeeklyStatistics(year, month, day);

        if(weeklyStatistics == null)
            return ResponseEntity.noContent().build();

        StatisticsDTO statisticsDTO = statisticsMapper.fromWeeklyStatisticsToDTO(weeklyStatistics);
        return ResponseEntity.ok(statisticsDTO);
    }
}

package it.safesiteguard.ms.alarms_ssguard.restcontrollers;


import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.dto.AlertViewDTO;
import it.safesiteguard.ms.alarms_ssguard.mappers.AlertMapper;
import it.safesiteguard.ms.alarms_ssguard.service.AlertHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/api/alerts")
public class AlertsRestController {

    @Autowired
    private AlertHistoryService alertHistoryService;

    @Autowired
    private AlertMapper alertMapper;



    /*
        GET ALL: ritorna la lista di tutti gli alerts ordinati per timestamp decrescente
     */
    @RequestMapping(value="/", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlertViewDTO>> getAllAlerts() {

        List<Alert> allAlerts = alertHistoryService.getAll();

        if(allAlerts.isEmpty())
            return ResponseEntity.noContent().build();

        List<AlertViewDTO> allAlertsDTO = fromAlertToDTOArray(allAlerts);
        return ResponseEntity.ok(allAlertsDTO);
    }


    /*
        GET filterByType
     */
    @RequestMapping(value="", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlertViewDTO>> getAlertsByType(@RequestParam(name="type", required = true) String type)  {

        List<Alert> allAlerts = alertHistoryService.filterAlertsByType(type);
        if(allAlerts.isEmpty())
            return ResponseEntity.noContent().build();

        List<AlertViewDTO> allAlertsDTO = fromAlertToDTOArray(allAlerts);
        return ResponseEntity.ok(allAlertsDTO);
    }

    /*
        GET filterByPriority
     */
    @RequestMapping(value="", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlertViewDTO>> getAlertsByPriority(@RequestParam(name="priority", required = true) String priority)  {

        List<Alert> allAlerts = alertHistoryService.filterAlertsByPriority(priority);
        if(allAlerts.isEmpty())
            return ResponseEntity.noContent().build();

        List<AlertViewDTO> allAlertsDTO = fromAlertToDTOArray(allAlerts);
        return ResponseEntity.ok(allAlertsDTO);
    }

    /*
        GET filterByDate
     */
    @RequestMapping(value="", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlertViewDTO>> getAlertsByDate(@RequestParam(name="date", required = true) LocalDate date)  {

        List<Alert> allAlerts = alertHistoryService.filterAlertsByDate(date);
        if(allAlerts.isEmpty())
            return ResponseEntity.noContent().build();

        List<AlertViewDTO> allAlertsDTO = fromAlertToDTOArray(allAlerts);
        return ResponseEntity.ok(allAlertsDTO);
    }






    private List<AlertViewDTO> fromAlertToDTOArray(List<Alert> entityAlerts) {
        List<AlertViewDTO> result = new ArrayList<>();

        for(Alert alert: entityAlerts) {
            AlertViewDTO alertViewDTO = alertMapper.fromAlertTypeToViewDTO(alert);
            result.add(alertViewDTO);
        }
        return result;
    }

}

package it.safesiteguard.ms.alarms_ssguard.restcontrollers;


import it.safesiteguard.ms.alarms_ssguard.domain.Alert;
import it.safesiteguard.ms.alarms_ssguard.dto.AlertViewDTO;
import it.safesiteguard.ms.alarms_ssguard.mappers.AlertMapper;
import it.safesiteguard.ms.alarms_ssguard.service.AlertHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SAFETY_MANAGER')")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SAFETY_MANAGER')")
    @RequestMapping(value="/type/{typeName}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlertViewDTO>> getAlertsByType(@PathVariable("typeName") String typeName)  {

        List<Alert> allAlerts = alertHistoryService.filterAlertsByType(typeName);
        if(allAlerts.isEmpty())
            return ResponseEntity.noContent().build();

        List<AlertViewDTO> allAlertsDTO = fromAlertToDTOArray(allAlerts);
        return ResponseEntity.ok(allAlertsDTO);
    }

    /*
        GET filterByPriority
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SAFETY_MANAGER')")
    @RequestMapping(value="/priority/{priorityName}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlertViewDTO>> getAlertsByPriority(@PathVariable("priorityName") String priorityName)  {

        List<Alert> allAlerts = alertHistoryService.filterAlertsByPriority(priorityName);
        if(allAlerts.isEmpty())
            return ResponseEntity.noContent().build();

        List<AlertViewDTO> allAlertsDTO = fromAlertToDTOArray(allAlerts);
        return ResponseEntity.ok(allAlertsDTO);
    }

    /*
        GET filterByDate
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SAFETY_MANAGER')")
    @RequestMapping(value="/date/{dateToSearch}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlertViewDTO>> getAlertsByDate(@PathVariable("dateToSearch") LocalDate dateToSearch)  {

        List<Alert> allAlerts = alertHistoryService.filterAlertsByDate(dateToSearch);
        if(allAlerts.isEmpty())
            return ResponseEntity.noContent().build();

        List<AlertViewDTO> allAlertsDTO = fromAlertToDTOArray(allAlerts);
        return ResponseEntity.ok(allAlertsDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SAFETY_MANAGER')")
    @RequestMapping(value="/worker/{workerID}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlertViewDTO>> getAlertsByWorker(@PathVariable("workerID") String workerID)  {

        List<Alert> allAlerts = alertHistoryService.filterAlertByWorker(workerID);
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

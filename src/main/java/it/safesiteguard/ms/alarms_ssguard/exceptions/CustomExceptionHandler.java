package it.safesiteguard.ms.alarms_ssguard.exceptions;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *  Quando una eccezione viene sollevata, questa classe si occupa di restituire nel body
 *  un json contenente le informazioni sull'eccezione.
 *  Informazioni eccezione:
 *      - codice eccezione (fa riferimento ad un elenco di eccezioni già stilato su un documento excel)
 *      - nome eccezione (nome della Classe Java relativa all'eccezione)
 *      - descrizione eccezione
 */
@ControllerAdvice
public class CustomExceptionHandler {


    /*@ExceptionHandler(WorkerTypeNotValidException.class)
    public ResponseEntity<Object> workerTypeNotValidHandler(WorkerTypeNotValidException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDTO(
                        4,
                        WorkerTypeNotValidException.class.getSimpleName(),
                        "Worker type not valid"
                ));
    }

    @ExceptionHandler(MachineryNotFoundException.class)
    public ResponseEntity<Object> machineryNotFoundHandler(MachineryNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDTO(
                        3,
                        MachineryNotFoundException.class.getSimpleName(),
                        "Machinery not found"
                ));
    }

    @ExceptionHandler(MachineryTypeNotFoundException.class)
    public ResponseEntity<Object> machineryTypeNotFoundHandler(MachineryTypeNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDTO(
                        3,
                        MachineryTypeNotFoundException.class.getSimpleName(),
                        "Machinery type not found"
                ));
    }

    @ExceptionHandler(InvalidDailyMappingException.class)
    public ResponseEntity<Object> invaliDailyMappingHandler(InvalidDailyMappingException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDTO(
                        3,
                        InvalidDailyMappingException.class.getSimpleName(),
                        "Data in site configuration are not valid"
                ));
    }

    @ExceptionHandler(DailyMappingDateNotValidException.class)
    public ResponseEntity<Object> invalidDailyMappingHandler(DailyMappingDateNotValidException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDTO(
                        3,
                        DailyMappingDateNotValidException.class.getSimpleName(),
                        "Date must be the same as today or tomorrow"
                ));
    }

    @ExceptionHandler(MappingAlreadyExistsException.class)
    public ResponseEntity<Object> mappingAlreadyExistsHandler(MappingAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDTO(
                        3,
                        MappingAlreadyExistsException.class.getSimpleName(),
                        ex.getMessage()
                ));
    }*/
    /*
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> duplicateKeyHandler(DuplicateKeyException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ExceptionDTO(
                        5,
                        DuplicateKeyException.class.getSimpleName(),
                        "An entity with the same key already exists"
                ));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidHandler (
            MethodArgumentNotValidException ex) {

        StringBuilder errorString = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorString.append(errorMessage).append(".");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDTO(
                        6,
                        MethodArgumentNotValidException.class.getSimpleName(),
                        errorString.deleteCharAt(errorString.length() - 1).toString()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidTypeIdException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ExceptionDTO(
                            7,
                            HttpMessageNotReadableException.class.getSimpleName(),"Missing type property"
                    ));
        }
        else
            return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> webClientResponseHandler(WebClientResponseException ex) {

        HttpStatusCode httpStatus = ex.getStatusCode();
        String responseBody = ex.getResponseBodyAsString();

        return ResponseEntity.status(httpStatus)
                .body(new ExceptionDTO(
                        8,
                        WebClientResponseException.class.getSimpleName(),
                        responseBody
                ));
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<Object> webClientRequestHandler(WebClientRequestException ex) {

        String responseBody = ex.getMessage();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDTO(
                        9,
                        WebClientRequestException.class.getSimpleName(),
                        responseBody
                ));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> noHandlerFoundHandler(NoHandlerFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getBody());
    }*/

}


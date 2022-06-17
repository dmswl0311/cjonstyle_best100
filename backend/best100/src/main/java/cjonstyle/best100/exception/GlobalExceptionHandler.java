package cjonstyle.best100.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OpinionNotFoundException.class)
    protected ResponseEntity<?> handleOpinionNotFoundException(OpinionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(OpinionPasswordNotMatchException.class)
    protected ResponseEntity<?> handleOpinionPaswordNotMatchException(OpinionPasswordNotMatchException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}

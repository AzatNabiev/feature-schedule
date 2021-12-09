package ru.waveaccess.features.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.waveaccess.features.dto.ExceptionDto;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleError(Exception ex) {
        return ResponseEntity.badRequest().body(ExceptionDto.builder().message(ex.getMessage()).build());
    }
}

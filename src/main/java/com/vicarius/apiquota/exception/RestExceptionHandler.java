package com.vicarius.apiquota.exception;

import com.vicarius.apiquota.model.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({TemplateException.class, Exception.class})
    public ResponseEntity<ResponseErrorDto> handleEventException(Exception ex) {
        return new ResponseEntity<>(
                new ResponseErrorDto(ex.getLocalizedMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

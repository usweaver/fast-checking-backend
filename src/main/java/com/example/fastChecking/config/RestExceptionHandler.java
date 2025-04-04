package com.example.fastChecking.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.fastChecking.dto.ErrorDto;
import com.example.fastChecking.exceptions.AppException;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(value = {AppException.class})
  @ResponseBody
  public ResponseEntity<ErrorDto> handleException(AppException ex) {

    return ResponseEntity.status(ex.getStatus()).body(new ErrorDto(ex.getMessage()));

  }

}

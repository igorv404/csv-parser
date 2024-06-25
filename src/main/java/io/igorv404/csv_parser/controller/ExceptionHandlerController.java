package io.igorv404.csv_parser.controller;

import io.igorv404.csv_parser.exception.BadCsvDataException;
import io.igorv404.csv_parser.exception.EmptyCsvException;
import io.igorv404.csv_parser.exception.FileIsNotCsvException;
import io.igorv404.csv_parser.model.RequestError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
  @ExceptionHandler(value = {EntityNotFoundException.class})
  private ResponseEntity<RequestError> handleEntityNotFoundException() {
    return new ResponseEntity<>(new RequestError("The entity with this id is absent", HttpStatus.NOT_FOUND.value()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {EmptyCsvException.class})
  private ResponseEntity<RequestError> handleEmptyCsvException(EmptyCsvException e) {
    return new ResponseEntity<>(new RequestError(e.getMessage(), e.getHttpStatus()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {FileIsNotCsvException.class})
  private ResponseEntity<RequestError> handleFileIsNotCsvException(FileIsNotCsvException e) {
    return new ResponseEntity<>(new RequestError(e.getMessage(), e.getHttpStatus()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {BadCsvDataException.class})
  private ResponseEntity<RequestError> handleBadCsvDataException(BadCsvDataException e) {
    return new ResponseEntity<>(new RequestError(e.getMessage(), e.getHttpStatus()),
        HttpStatus.BAD_REQUEST);
  }
}

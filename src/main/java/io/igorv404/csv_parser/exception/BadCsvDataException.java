package io.igorv404.csv_parser.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BadCsvDataException extends RuntimeException {
  private final String message = "A provided CSV has a bad data for parsing";
  private final Integer httpStatus = HttpStatus.BAD_REQUEST.value();
}

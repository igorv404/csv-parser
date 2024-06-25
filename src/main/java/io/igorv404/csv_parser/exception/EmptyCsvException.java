package io.igorv404.csv_parser.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class EmptyCsvException extends RuntimeException {
  private final String message = "An empty CSV file was provided";
  private final Integer httpStatus = HttpStatus.BAD_REQUEST.value();
}

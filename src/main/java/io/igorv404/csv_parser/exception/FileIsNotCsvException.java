package io.igorv404.csv_parser.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class FileIsNotCsvException extends RuntimeException {
  private final String message = "A provided file is not a CSV";
  private final Integer httpStatus = HttpStatus.BAD_REQUEST.value();
}

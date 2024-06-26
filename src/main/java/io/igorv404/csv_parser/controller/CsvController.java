package io.igorv404.csv_parser.controller;

import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.service.CsvService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/csv")
public class CsvController {
  private final CsvService csvService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<List<Movie>> uploadCsvData(@RequestParam("csv") MultipartFile csv) {
    return new ResponseEntity<>(this.csvService.uploadCsvData(csv), HttpStatus.CREATED);
  }
}

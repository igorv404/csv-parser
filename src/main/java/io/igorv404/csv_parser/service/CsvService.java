package io.igorv404.csv_parser.service;

import com.opencsv.bean.CsvToBeanBuilder;
import io.igorv404.csv_parser.exception.BadCsvDataException;
import io.igorv404.csv_parser.exception.EmptyCsvException;
import io.igorv404.csv_parser.exception.FileIsNotCsvException;
import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.repository.MovieRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CsvService {
  private final MovieService movieService;
  private final MovieRepository movieRepository;

  public List<Movie> uploadCsvData(MultipartFile csv) {
    if (this.checkIfFileIsCsv(csv)) {
      if (csv.isEmpty()) {
        throw new EmptyCsvException();
      } else {
        this.parseCsvFile(csv);
      }
    } else {
      throw new FileIsNotCsvException();
    }
    return this.movieService.getAllEntities();
  }

  private boolean checkIfFileIsCsv(MultipartFile csv) {
    return Objects.requireNonNull(csv.getOriginalFilename()).endsWith(".csv");
  }

  private void parseCsvFile(MultipartFile csv) {
    try(BufferedReader reader = new BufferedReader(new InputStreamReader(csv.getInputStream()))) {
      List<Movie> parsedData = new CsvToBeanBuilder<Movie>(reader)
          .withSkipLines(1)
          .withType(Movie.class)
          .build()
          .parse();
      this.movieRepository.saveAll(parsedData);
    } catch (Exception e) {
      throw new BadCsvDataException();
    }
  }
}

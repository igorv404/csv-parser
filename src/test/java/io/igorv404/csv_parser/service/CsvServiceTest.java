package io.igorv404.csv_parser.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.igorv404.csv_parser.exception.EmptyCsvException;
import io.igorv404.csv_parser.exception.FileIsNotCsvException;
import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.repository.MovieRepository;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class CsvServiceTest {
  @Mock
  private MovieRepository movieRepository;

  @Mock
  private MovieService movieService;

  @InjectMocks
  private CsvService csvService;

  private final Movie entity1 = new Movie(1, " The Shawshank Redemption",
      "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.",
      82, 1, 1994, "2h 22m", "15", 9.3f, "(2.9M)");

  private final Movie entity2 = new Movie(2, " The Godfather",
      "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
      100, 2, 1972, "2h 55m", "15",
      9.2f, "(2M)");

  private final Movie entity3 = new Movie(3, " The Dark Knight",
      "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
      84, 3, 2008, "2h 32m", "12A",
      9f, "(2.9M)");

  private final List<Movie> data = List.of(this.entity1, this.entity2, this.entity3);

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldUploadCsvData() {
    try {
      when(this.movieRepository.findAll()).thenReturn(this.data);
      when(this.movieService.getAllEntities()).thenReturn(this.data);
      when(this.movieRepository.saveAll(this.data)).thenReturn(this.data);
      File csv = new File("src/test/resources/data.csv");
      FileInputStream inputStream = new FileInputStream(csv);
      MultipartFile multipartFile = new MockMultipartFile("file", "data.csv", "text/csv",
          inputStream);
      List<Movie> savedData = this.csvService.uploadCsvData(multipartFile);
      assertNotEquals(0, savedData.size());
      assertEquals(3, savedData.size());
      assertEquals(this.data, savedData);
      verify(this.movieService, times(1)).getAllEntities();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void shouldThrowEmptyCsvExceptionWhenCsvFileIsEmpty() {
    try {
      File csv = new File("src/test/resources/empty.csv");
      FileInputStream inputStream = new FileInputStream(csv);
      MultipartFile multipartFile = new MockMultipartFile("file", "empty.csv", "text/csv",
          inputStream);
      assertThrows(EmptyCsvException.class, () -> this.csvService.uploadCsvData(multipartFile));
      verify(this.movieRepository, times(0)).findAll();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void shouldThrowFileIsNotCsvExceptionWhenFileIsNotCsv() {
    try {
      File csv = new File("src/test/resources/badExtension.txt");
      FileInputStream inputStream = new FileInputStream(csv);
      MultipartFile multipartFile = new MockMultipartFile("file", "badExtension.txt", "text/plain",
          inputStream);
      assertThrows(FileIsNotCsvException.class, () -> this.csvService.uploadCsvData(multipartFile));
      verify(this.movieRepository, times(0)).findAll();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}

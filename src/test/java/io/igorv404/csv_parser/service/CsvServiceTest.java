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
import java.io.FileInputStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
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

  private final Movie entity1 = new Movie(1, "Movie 1", "Description", 80, 4, 2004, "2h 22m", "15", 8.7f, "(2.9M)");

  private final Movie entity2 = new Movie(2, "Movie 2", "Description", 87, 1, 2012, "1h 50m", "12", 9.1f, "(10.3M)");

  private final Movie entity3 = new Movie(3, "Movie 3", "Description", 55, 45, 2011, "1h 12m", "7", 6.6f, "(1.2M)");

  private final List<Movie> data = List.of(this.entity1, this.entity2, this.entity3);

  private AutoCloseable mocks;

  @BeforeEach
  public void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void tearDown() {
    try {
      mocks.close();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void shouldUploadCsvData() {
    try (FileInputStream inputStream = new FileInputStream("src/test/resources/data.csv")) {
      when(this.movieRepository.findAll()).thenReturn(this.data);
      when(this.movieService.getAllEntities()).thenReturn(this.data);
      when(this.movieRepository.saveAll(this.data)).thenReturn(this.data);
      MultipartFile multipartFile = new MockMultipartFile("file", "data.csv", "text/csv", inputStream);
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
    try (FileInputStream inputStream = new FileInputStream("src/test/resources/empty.csv")) {
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
    try (FileInputStream inputStream = new FileInputStream("src/test/resources/badExtension.txt")) {
      MultipartFile multipartFile = new MockMultipartFile("file", "badExtension.txt", "text/plain",
          inputStream);
      assertThrows(FileIsNotCsvException.class, () -> this.csvService.uploadCsvData(multipartFile));
      verify(this.movieRepository, times(0)).findAll();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}

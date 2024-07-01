package io.igorv404.csv_parser.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MovieServiceTest {
  @Mock
  private MovieRepository movieRepository;

  @InjectMocks
  private MovieService movieService;

  private final Movie entity1 = new Movie(1, "Movie 1", "Description", 80, 4, 2004, "2h 22m", "15", 8.7f, "(2.9M)");

  private final Movie entity2 = new Movie(2, "Movie 2", "Description", 87, 1, 2012, "1h 50m", "12", 9.1f, "(10.3M)");

  private final List<Movie> data = List.of(this.entity1, this.entity2);

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
  public void shouldReturnAllMovies() {
    when(this.movieRepository.findAll()).thenReturn(data);
    List<Movie> responseData = this.movieService.getAllEntities();
    assertNotNull(responseData);
    assertEquals(data.size(), responseData.size());
    assertTrue(responseData.containsAll(data));
    verify(this.movieRepository, times(1)).findAll();
  }

  @Test
  public void shouldReturnMovieWithId2() {
    final Integer id = 2;
    when(this.movieRepository.findById(id)).thenReturn(Optional.of(this.entity2));
    Movie responseData = this.movieService.getEntity(id);
    assertNotNull(responseData);
    assertEquals(this.entity2, responseData);
    verify(this.movieRepository, times(1)).findById(id);
  }

  @Test
  public void shouldThrowEntityNotFoundExceptionWhenCallMovieWithId0() {
    final Integer id = 0;
    when(this.movieRepository.findById(id)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> this.movieService.getEntity(id));
    verify(this.movieRepository, times(1)).findById(id);
  }

  @Test
  public void shouldCreateNewMovie() {
    final Movie entity = new Movie(3, "Movie 3", "Description", 69, 15, 2000, "2h 24m", "18", 6.9f, "(900K)");
    when(this.movieRepository.save(entity)).thenReturn(entity);
    Movie responseData = this.movieService.createEntity(entity);
    assertNotNull(responseData);
    assertEquals(entity, responseData);
    verify(this.movieRepository, times(1)).save(entity);
  }

  @Test
  public void shouldUpdateMovie() {
    final Integer id = 1;
    final Movie updatedEntity = new Movie(1, "New movie 1", "Description", 80, 4, 2004, "2h 50m", "13", 8.9f, "(2.9M)");
    when(this.movieRepository.findById(id)).thenReturn(Optional.of(this.entity1));
    when(this.movieRepository.save(any(Movie.class))).thenReturn(updatedEntity);
    Movie responseData = this.movieService.updateEntity(updatedEntity);
    assertNotNull(responseData);
    assertEquals(updatedEntity, responseData);
    assertNotEquals(this.entity1, responseData);
    verify(this.movieRepository, times(1)).findById(id);
    verify(this.movieRepository, times(1)).save(any(Movie.class));
  }

  @Test
  public void shouldThrowEntityNotFoundExceptionWhenTryToUpdateMovieWithAbsentId() {
    final Integer id = 0;
    final Movie badEntity = new Movie(0, "Bad movie", "Description", 0, 1000, 1999, "2h 50m", "16", 0.0f, "(2M)");
    when(this.movieRepository.findById(id)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> this.movieService.updateEntity(badEntity));
    verify(this.movieRepository, times(1)).findById(id);
    verify(this.movieRepository, times(0)).save(badEntity);
  }

  @Test
  public void shouldDeleteMovie() {
    final Integer id = 1;
    when(this.movieRepository.findById(id)).thenReturn(Optional.of(this.entity1));
    String responseMessage = this.movieService.deleteEntity(id);
    assertNotNull(responseMessage);
    assertEquals(String.format("Movie \"%s\" was deleted", entity1.getName()), responseMessage);
    verify(this.movieRepository, times(1)).findById(id);
    verify(this.movieRepository, times(1)).deleteById(id);
  }

  @Test
  public void shouldThrowEntityNotFoundExceptionWhenTryToDeleteMovieWithAbsentId() {
    final Integer id = 0;
    when(this.movieRepository.findById(id)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> this.movieService.deleteEntity(id));
    verify(this.movieRepository, times(1)).findById(id);
    verify(this.movieRepository, times(0)).deleteById(id);
  }
}

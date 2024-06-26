package io.igorv404.csv_parser.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.repository.MovieRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SearchServiceTest {
  @Mock
  private MovieRepository movieRepository;

  @InjectMocks
  private SearchService searchService;

  private final Movie entity1 = new Movie(1, "Movie 1", "Description", 80, 4, 2004, "2h 22m", "15", 8.7f, "(2.9M)");

  private final Movie entity2 = new Movie(2, "Movie 2", "Description", 87, 1, 2012, "1h 50m", "12", 9.1f, "(10.3M)");

  private final Movie entity3 = new Movie(3, "Movie 3", "Description", 55, 45, 2011, "1h 12m", "7", 6.6f, "(1.2M)");

  private final List<Movie> data = List.of(this.entity1, this.entity2, this.entity3);

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldReturnOnlyMatchedMovies() {
    final String query = "1h";
    when(this.movieRepository.findAll()).thenReturn(data);
    List<Movie> result = this.searchService.search(query);
    assertNotEquals(0, result.size());
    assertEquals(2, result.size());
    assertEquals(List.of(this.entity2, this.entity3), result);
    verify(this.movieRepository, times(1)).findAll();
  }

  @Test
  public void shouldReturnAllMoviesWhenQueryIsEmpty() {
    final String query = "";
    when(this.movieRepository.findAll()).thenReturn(data);
    List<Movie> result = this.searchService.search(query);
    assertEquals(3, result.size());
    assertEquals(data, result);
    verify(this.movieRepository, times(1)).findAll();
  }

  @Test
  public void shouldReturnAllMoviesWhenQueryContainsOnlySpaces() {
    final String query = "    ";
    when(this.movieRepository.findAll()).thenReturn(data);
    List<Movie> result = this.searchService.search(query);
    assertEquals(3, result.size());
    assertEquals(data, result);
    verify(this.movieRepository, times(1)).findAll();
  }
}

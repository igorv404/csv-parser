package io.igorv404.csv_parser.service;

import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.repository.MovieRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
  private final MovieRepository movieRepository;

  public List<Movie> search(String query) {
    if (query == null || query.isBlank()) {
      return this.movieRepository.findAll();
    } else {
      String lowerCaseQuery = query.toLowerCase();
      boolean isNumeric = query.matches("\\d");
      return this.movieRepository.findAll().stream()
          .filter(entity -> hasMatch(entity, lowerCaseQuery, isNumeric))
          .toList();
    }
  }

  private boolean hasMatch(Movie entity, String query, boolean isNumeric) {
    if (isNumeric) {
      return String.valueOf(entity.getId()).matches(query) ||
          String.valueOf(entity.getMetascore()).matches(query) ||
          String.valueOf(entity.getRank()).matches(query) ||
          String.valueOf(entity.getYear()).matches(query) ||
          entity.getName().toLowerCase().contains(query) ||
          entity.getDescription().toLowerCase().contains(query) ||
          entity.getDuration().toLowerCase().contains(query) ||
          entity.getAgeLimit().toLowerCase().contains(query) ||
          entity.getNumberOfRatings().toLowerCase().contains(query);
    } else {
      return entity.getName().toLowerCase().contains(query) ||
          entity.getDescription().toLowerCase().contains(query) ||
          entity.getDuration().toLowerCase().contains(query) ||
          entity.getAgeLimit().toLowerCase().contains(query) ||
          entity.getNumberOfRatings().toLowerCase().contains(query);
    }
  }
}

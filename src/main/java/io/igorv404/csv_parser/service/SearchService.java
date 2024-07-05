package io.igorv404.csv_parser.service;

import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.repository.MovieRepository;
import java.util.LinkedList;
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
    List<String> properties = new LinkedList<>(List.of(
        entity.getName().toLowerCase(),
        entity.getDescription().toLowerCase(),
        entity.getDuration().toLowerCase(),
        entity.getAgeLimit().toLowerCase(),
        entity.getNumberOfRatings().toLowerCase()
    ));
    if (isNumeric) {
      List<String> numericProperties = List.of(
          String.valueOf(entity.getId()),
          String.valueOf(entity.getMetascore()),
          String.valueOf(entity.getRank()),
          String.valueOf(entity.getYear())
      );
      properties.addAll(numericProperties);
    }
    return properties.stream()
        .anyMatch(property -> property.contains(query));
  }
}

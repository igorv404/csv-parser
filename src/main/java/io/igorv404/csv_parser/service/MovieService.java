package io.igorv404.csv_parser.service;

import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.repository.MovieRepository;
import io.igorv404.csv_parser.util.RestCrudService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService implements RestCrudService<Movie, Integer> {
  private final MovieRepository movieRepository;

  @Override
  public List<Movie> getAllEntities() {
    return this.movieRepository.findAll();
  }

  @Override
  public Movie getEntity(Integer id) {
    return this.movieRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Movie createEntity(Movie entity) {
    return this.movieRepository.save(entity);
  }

  @Override
  public Movie updateEntity(Movie entity) {
    this.getEntity(entity.getId());
    return this.movieRepository.save(entity);
  }

  @Override
  public String deleteEntity(Integer id) {
    Movie deletedEntity = this.getEntity(id);
    this.movieRepository.deleteById(id);
    return String.format("Movie \"%s\" was deleted", deletedEntity.getName());
  }
}

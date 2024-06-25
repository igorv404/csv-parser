package io.igorv404.csv_parser.service;

import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.repository.MovieRepository;
import io.igorv404.csv_parser.util.ServiceTemplate;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService implements ServiceTemplate<Movie, Integer> {
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
    Movie updatedEntity = this.getEntity(entity.getId());
    updatedEntity.setName(entity.getName());
    updatedEntity.setDescription(entity.getDescription());
    updatedEntity.setMetascore(entity.getMetascore());
    updatedEntity.setRank(entity.getRank());
    updatedEntity.setYear(entity.getYear());
    updatedEntity.setDuration(entity.getDuration());
    updatedEntity.setAgeLimit(entity.getAgeLimit());
    updatedEntity.setRating(entity.getRating());
    updatedEntity.setNumberOfRatings(entity.getNumberOfRatings());
    return this.movieRepository.save(updatedEntity);
  }

  @Override
  public String deleteEntity(Integer id) {
    Movie deletedEntity = this.getEntity(id);
    this.movieRepository.deleteById(id);
    return String.format("Movie \"%s\" was deleted", deletedEntity.getName());
  }
}

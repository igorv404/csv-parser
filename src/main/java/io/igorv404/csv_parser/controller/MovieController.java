package io.igorv404.csv_parser.controller;

import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.service.MovieService;
import io.igorv404.csv_parser.util.ControllerTemplate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController implements ControllerTemplate<Movie, Integer> {
  private final MovieService movieService;

  @Override
  @GetMapping
  public ResponseEntity<List<Movie>> getAllEntities() {
    return ResponseEntity.ok(this.movieService.getAllEntities());
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<Movie> getEntity(@PathVariable Integer id) {
    return ResponseEntity.ok(this.movieService.getEntity(id));
  }

  @Override
  @PostMapping
  public ResponseEntity<Movie> createEntity(@RequestBody Movie entity) {
    return new ResponseEntity<>(this.movieService.createEntity(entity), HttpStatus.CREATED);
  }

  @Override
  @PutMapping
  public ResponseEntity<Movie> updateEntity(@RequestBody Movie entity) {
    return new ResponseEntity<>(this.movieService.updateEntity(entity), HttpStatus.ACCEPTED);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEntity(@PathVariable Integer id) {
    return new ResponseEntity<>(this.movieService.deleteEntity(id), HttpStatus.ACCEPTED);
  }
}

package io.igorv404.csv_parser.controller;

import io.igorv404.csv_parser.model.Movie;
import io.igorv404.csv_parser.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
  private final SearchService searchService;

  @GetMapping
  public ResponseEntity<List<Movie>> search(@RequestParam("query") String query) {
    return ResponseEntity.ok(searchService.search(query));
  }
}

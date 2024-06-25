package io.igorv404.csv_parser.util;

import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ControllerTemplate<T, ID> {
  ResponseEntity<List<T>> getAllEntities();

  ResponseEntity<T> getEntity(ID id);

  ResponseEntity<T> createEntity(T entity);

  ResponseEntity<T> updateEntity(T entity);

  ResponseEntity<String> deleteEntity(ID id);
}

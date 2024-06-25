package io.igorv404.csv_parser.util;

import java.util.List;

public interface ServiceTemplate<T, ID> {
  List<T> getAllEntities();
  
  T getEntity(ID id);
  
  T createEntity(T entity);
  
  T updateEntity(T entity);
  
  String deleteEntity(ID id);
}

package io.igorv404.csv_parser.model;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @CsvBindByPosition(position = 8)
  private String name;

  @CsvBindByPosition(position = 7)
  @Column(length = 500)
  private String description;

  @CsvBindByPosition(position = 6)
  private Integer metascore;

  @CsvBindByPosition(position = 0)
  private Integer rank;

  @CsvBindByPosition(position = 1)
  private Integer year;

  @CsvBindByPosition(position = 2)
  private String duration;

  @CsvBindByPosition(position = 3)
  private String ageLimit;

  @CsvBindByPosition(position = 4)
  private Float rating;

  @CsvBindByPosition(position = 5)
  private String numberOfRatings;
}

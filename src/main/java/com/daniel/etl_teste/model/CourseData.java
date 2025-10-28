package com.daniel.etl_teste.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("courses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseData {

  @Field("level.name")
  private String levelName;

  @Field("category.name")
  private String categoryName;

  @Field("lessonsCount")
  private Integer lessonsCount;

  @Field("isActive")
  private Boolean isActive;
}

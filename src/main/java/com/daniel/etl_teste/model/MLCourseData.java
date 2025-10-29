package com.daniel.etl_teste.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.Id;

@Document("ml_course_data")
@Data
@Getter
@NoArgsConstructor
public class MLCourseData{
  
  @Id
  private ObjectId id;
  private String level;
  private Integer lessonsCount;
  private String category;
  private Double finishPredictCourse;

  public MLCourseData(String level, Integer lessonsCount,
                      String category,
                      Double finishPredictCourse) {
    this.level = level;
    this.lessonsCount = lessonsCount;
    this.category = category;
    this.finishPredictCourse = finishPredictCourse;
  }

}

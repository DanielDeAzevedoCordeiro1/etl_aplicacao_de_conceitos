package com.daniel.etl_teste.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ml_course_data")
@Data
@Getter
@NoArgsConstructor
public class MLCourseData{

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

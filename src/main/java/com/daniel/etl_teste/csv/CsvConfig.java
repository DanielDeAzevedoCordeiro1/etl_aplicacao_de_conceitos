package com.daniel.etl_teste.csv;

import java.util.List;
import org.springframework.stereotype.Component;
import com.daniel.etl_teste.model.MLCourseDataDTO;

@Component
public class CsvConfig {
    private static final String CSV_HEADER = "level, lessonsCount, category, finishPredictCourse";

    public String generateCSV(List<MLCourseDataDTO> mlCourseDatas){
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER).append("\n");

        for (MLCourseDataDTO mlCourseData : mlCourseDatas) {
            csvContent.append(mlCourseData.level()).append(",");
            csvContent.append(mlCourseData.lessonsCount()).append(",");
            csvContent.append(mlCourseData.category()).append(",");
            csvContent.append(mlCourseData.finishPredictCourse()).append("\n");
        }

        return csvContent.toString();
    }
}

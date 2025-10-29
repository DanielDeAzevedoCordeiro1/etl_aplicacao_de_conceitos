package com.daniel.etl_teste.csv.service;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;
import com.daniel.etl_teste.csv.CsvConfig;
import com.daniel.etl_teste.model.MLCourseDataDTO;
import com.daniel.etl_teste.model.repository.MLCourseDataRepository;

@Service
public class CreateCsvService {
    
    private final CsvConfig csvConfig;
    private final MLCourseDataRepository mlCourseDataRepository;

    public CreateCsvService(MLCourseDataRepository mlCourseDataRepository,
                            CsvConfig csvConfig){
        this.mlCourseDataRepository = mlCourseDataRepository;
        this.csvConfig = csvConfig;
    }

    public byte[] generateCsv() throws UnsupportedEncodingException{
        var data = mlCourseDataRepository.findAll()
                    .stream()
                    .map(d -> new MLCourseDataDTO(
                        d.getLevel(),
                        d.getLessonsCount(),
                        d.getCategory(),
                        d.getFinishPredictCourse()
                    )).toList();
       
        var csvStream = csvConfig.generateCSV(data);
        byte[] csvBytes = csvStream.getBytes("UTF-8");
        return csvBytes;
    }
}

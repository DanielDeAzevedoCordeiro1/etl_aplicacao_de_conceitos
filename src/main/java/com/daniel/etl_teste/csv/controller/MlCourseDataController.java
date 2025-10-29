package com.daniel.etl_teste.csv.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daniel.etl_teste.csv.service.CreateCsvService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/v1/analytics/")
public class MlCourseDataController {
    
    private final CreateCsvService createCsvService;

    public MlCourseDataController(CreateCsvService createCsvService){
        this.createCsvService = createCsvService;
    }

    @GetMapping("/csv")
    public ResponseEntity<byte[]> exportCsv() throws Exception {
        
        byte[] csvBytes = createCsvService.generateCsv();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv;charset=UTF-8"));
        headers.setContentDispositionFormData("attachment", "courses_analytics.csv");
        headers.setContentLength(csvBytes.length);
        
        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}

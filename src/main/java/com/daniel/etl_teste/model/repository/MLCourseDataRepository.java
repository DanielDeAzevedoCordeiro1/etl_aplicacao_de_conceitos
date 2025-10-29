package com.daniel.etl_teste.model.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.daniel.etl_teste.model.MLCourseData;

@Repository
public interface MLCourseDataRepository extends MongoRepository<MLCourseData, ObjectId>{
} 
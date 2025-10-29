package com.daniel.etl_teste.config;

import com.daniel.etl_teste.model.CourseData;
import com.daniel.etl_teste.model.MLCourseData;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoCursorItemReaderBuilder;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class MongoBatchConfig {


  @Bean(name = "sourceMongoClient")
  public MongoClient sourceMongoClient(@Value("${source.mongodb.uri}") String sourceMongoDbUri) {
      return MongoClients.create(sourceMongoDbUri);
  }

  @Bean(name = "sourceMongoTemplate")
  public MongoTemplate sourceMongoTemplate(
      @Qualifier("sourceMongoClient") MongoClient client){

    return new MongoTemplate(client, "imo_test");
  }

  @Bean(name = "mainMongoClient")
  @Primary
  public MongoClient mainMongoClient(@Value("${spring.data.mongodb.uri}") String mainMongoDbUri){
    return MongoClients.create(mainMongoDbUri);
  }

  @Bean(name = "mainMongoTemplate")
  @Primary
  public MongoTemplate mainMongoTemplate(
      @Qualifier("mainMongoClient") MongoClient mainMongoClient){
        return new MongoTemplate(mainMongoClient, "ml_imo_db");
  }

  @Bean
  public ItemReader<CourseData> sourceMongoItemReader(
      @Qualifier("sourceMongoTemplate") MongoTemplate mongoTemplate
  ){
    String query = "{'isActive': true}";
    String fields = "{level: 1, lessonsCount: 1, category: 1}";

    Map<String, Sort.Direction> sorts = new HashMap<>();

    return new MongoCursorItemReaderBuilder<CourseData>()
        .name("courseReader")
        .template(mongoTemplate)
        .collection("courses")
        .jsonQuery(query)
        .fields(fields)
        .targetType(CourseData.class)
        .sorts(sorts)
        .build();

  }

  @Bean
  public ItemProcessor<CourseData, MLCourseData> processor(
      @Qualifier("sourceMongoTemplate") MongoTemplate mongoTemplate
  ) {
    return data -> {
      if (data == null) return null;

      return new MLCourseData(
          data.getLevelName(),
          data.getLessonsCount(),
          data.getCategoryName(),
          0.0
      );
    };
  }

  @Bean
  public ItemWriter<MLCourseData> itemWriter(
      @Qualifier("mainMongoTemplate") MongoTemplate mongoTemplate
  ){
    return new MongoItemWriterBuilder<MLCourseData>()
        .template(mongoTemplate)
        .collection("ml_course_data")
        .build();
  }

  @Bean
  public Step etlStep(
      JobRepository jobRepository,
      PlatformTransactionManager platformTransactionManager,
      ItemReader<CourseData> itemReader,
      ItemProcessor<CourseData, MLCourseData> itemProcessor,
      ItemWriter<MLCourseData> itemWriter
  ){
    return new StepBuilder("etlStep", jobRepository)
        .<CourseData, MLCourseData> chunk(10, platformTransactionManager)
        .reader(itemReader)
        .processor(itemProcessor)
        .writer(itemWriter)
        .build();
  }

  @Bean
  public Job etlJob(
      JobRepository jobRepository,
      Step etlStep
  ){
    return new JobBuilder("etlJob", jobRepository)
        .start(etlStep)
        .build();
  }
}

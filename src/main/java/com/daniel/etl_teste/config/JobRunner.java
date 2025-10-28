package com.daniel.etl_teste.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JobRunner implements CommandLineRunner {

  private final JobLauncher jobLauncher;
  private final Job job;

  public JobRunner(JobLauncher jobLauncher, Job job) {
    this.jobLauncher = jobLauncher;
    this.job = job;
  }

  @Override
  public void run(String... args) throws Exception {


    var params = new JobParametersBuilder()
        .addLong("time", System.currentTimeMillis())
        .toJobParameters();
 
    JobExecution execution = jobLauncher.run(job, params);
    
    log.info("Nome do JOB: {}", execution.getJobInstance().getJobName());
    log.info("Status do JOB: {}", execution.getStatus());

    for (StepExecution stepExecution : execution.getStepExecutions()) {
      log.info("Registros Lidos: {}", stepExecution.getReadCount());
      log.info("Registros Processados: {}", stepExecution.getWriteCount());
    }
  }
}
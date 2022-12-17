package com.example.assignment.job;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.List;

@Configuration
public class JobConfig {

    /*
    //Testing purposes, initialized table with data
    @Bean
    CommandLineRunner commandLineRunner(JobRepository repository){
        return args -> {
            Job calculatepi = new Job(1L, "Calculate Pi", "new");
            Job handlethiserror = new Job("Handle This Error", "new");
            Job writeaprogram = new Job("Write A Program", "new");

            repository.saveAll(List.of(calculatepi, handlethiserror, writeaprogram));
        };
    }
    */

}

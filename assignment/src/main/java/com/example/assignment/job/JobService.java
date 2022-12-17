package com.example.assignment.job;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.lang.Long.parseLong;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private static final Logger logger = LoggerFactory.getLogger(JobService.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    public static final String topic = "jobs";

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    //returns job(id, description, status) by id
    public Optional<Job> getJobById(Long jobId){
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new IllegalStateException(
                "job Id: " + jobId   + " could not be found!"));

        return jobRepository.findById(jobId);
    }

    //saves job with status new
    //get the job's id from table, send job id to kafka broker and return id
    public Long addNewJob(String description) {
        //save job to database
        Job addJob = new Job(description, "new");
        jobRepository.save(addJob);

        //get id of job from database
        Optional<Job> jobOptional = jobRepository
                .findJobByDescription(description);
        String jobId = (jobOptional.get().getId().toString());

        //send kafka message to broker
        logger.info("*** message sent to broker, job id = " + jobId + ", status = \'new\'");
        this.kafkaTemplate.send(topic, jobId);

        return jobOptional.get().getId();
    }

    //kafka consumer - listens for kafka messages
    @KafkaListener(topics = topic, groupId = "group-id")
    public void consume(String message) throws InterruptedException {
        logger.info("*** message received from broker, job id = " + message);
        if (!message.isEmpty()){
            doWork(message);
        }
    }

    //puts computer to sleep for a random time between 1 and 5 seconds
    public void doWork(String jobId) throws InterruptedException {
        //place job status to in progress
        updateJob(parseLong(jobId));

        //create random number
        Random random = new Random();
        Integer workTime = (random.nextInt(5 - 1) + 1) + 1;

        //sleep between 1 and 5 seconds and return message
        Thread.sleep(workTime*1000);
        logger.info("*** job: " + jobId + " processed in " + workTime + " seconds");

        //place job status to done
        updateJob(parseLong(jobId));
    }

    //find the job by id and check its current status
    //cycle up the job's status: new > in progress > done
    @Transactional
    public void updateJob(Long jobId){
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalStateException(
                        "job Id: " + jobId   + " could not be found!"));

        //updates status: new -> in progress -> done
        if (job.getStatus().equals("new")){
            job.setStatus("in progress");
        }
        else {
            job.setStatus("done");
        }
        logger.info("*** job: " + jobId + " updated status to \'" + job.getStatus() + "\'");
        jobRepository.save((job));
    }

    /*
    //methods below were for testing purposes only
    public void sendMessage(String message) {
        logger.info(String.format("Message sent -> %s", message));
        this.kafkaTemplate.send("test", message);
    }

    public String getJobStatusById(Long jobId){
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new IllegalStateException(
                "Job Id: " + jobId   + " could not be found!"));

        return "Job: " + job.getDescription() + "; Status: " + job.getStatus();
    }

     public List<Job> getJobAll() {
        return jobRepository.findAll();
    }

    public void deleteJob(Long jobId) {
        boolean exists = jobRepository.existsById(jobId);
        if (!exists){
            throw new IllegalStateException(
                    "JobId: " + jobId + " could not be found!"
            );
        }
        jobRepository.deleteById(jobId);
    }
    */

}

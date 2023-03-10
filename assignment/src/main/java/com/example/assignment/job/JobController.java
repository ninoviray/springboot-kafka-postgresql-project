package com.example.assignment.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//uses localhost:8080
@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/api/job")
public class JobController {

    //references the JobService class
    private final JobService jobService;


    //initiates an instance of the JobServiceClass
    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    //add a new job to table and return the id from the table - tested
    @PostMapping("/addNewJob/{jobDescription}")
    public Long addNewJob(@PathVariable("jobDescription") String jobDescription){
        return jobService.addNewJob(jobDescription);
    }

    //this is a work around for POST requests
    //having issues with http 404 error when requesting post so instead I am using a get
    //request and will call the service method that handles post commands
    @GetMapping(path = "/addNewJobUsingGet/{jobDescription}")
    public List<Job> addNewJobUsingGet(@PathVariable("jobDescription") String jobDescription){
        return jobService.addNewJobUsingGet(jobDescription);
    }

    //returns a single job by id as optional - tested
    //optional is like a list but can only contain zero or a single element
    @GetMapping(path = "/getJobByIdOptional/{jobId}")
    public List<Job> getJobByIdOptional(@PathVariable("jobId") Long jobId){
        return jobService.getJobById(jobId);
    }

    //returns all jobs - tested
    @GetMapping("/getAllJobs")
    public List<Job> getAllJobs(){
        return jobService.getJobAll();
    }

    //updates the status from New > In Progress > Done - tested
    @PutMapping(path = "/updateJobById/{jobId}")
    public void updateJob(@PathVariable("jobId") Long jobId){
        jobService.updateJob(jobId);
    }

    /*
    //methods below are for testing purposes only
    //sends message to kafka broker - tested
    //only for testing purposes, kafkaService gets called directly from JobService addNewJob
    //returns a single job by id as a string - tested
    @GetMapping(path = "/getJobStatusByIdString/{jobId}")
    public String getJobStatusByIdString(@PathVariable("jobId") Long jobId) {
        return jobService.getJobStatusById(jobId);
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message)
    {
        this.jobService.sendMessage(message);
    }

    //calls the random number sleep method - tested
    @GetMapping("/doWork")
    public void doWork() throws InterruptedException {
        jobService.doWork("1");
    }

    //returns all jobs - tested
    @GetMapping("/getAllJobs")
    public List<Job> getAllJobs(){
        return jobService.getJobAll();
    }

    //returns a single job by id as a string - tested
    @GetMapping(path = "/getJobStatusByIdString/{jobId}")
    public String getJobStatusByIdString(@PathVariable("jobId") Long jobId){
        return jobService.getJobStatusById(jobId);
    }*/

}

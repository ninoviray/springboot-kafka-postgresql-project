package com.example.assignment.job;

import jakarta.persistence.*;
import org.springframework.core.SpringVersion;

@Entity
@Table
public class Job {

    @Id
    @SequenceGenerator(
            name = "job_sequence",
            sequenceName = "job_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy =  GenerationType.SEQUENCE,
            generator = "job_sequence"
    )

    private Long id;
    private String description;
    private String status;

    public Job() {
    }

    public Job(Long id, String description, String status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public Job(String description, String status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString(){
        return "Job{" +
                "id=" + id +
                ", description=" + description + '\'' +
                ", status=" + status + '\'' +
                "}";
    }

}

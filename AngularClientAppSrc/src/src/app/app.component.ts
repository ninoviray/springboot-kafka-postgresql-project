import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, NgForm, NgModel } from '@angular/forms';
import { map } from 'rxjs';
import { Job } from './app.model';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'AngularClient';
  getJob: Job[] = [];
  inputDescription: string = '';
  inputId: string = '';

  constructor(private http: HttpClient){
  }

  onGetJobAll(){
    this.getJobAll();
  }

   private getJobAll(){
    this.http.get<{[key: string]: Job}>('http://localhost:8080/api/job/getAllJobs')
    .pipe(map((response) => {
      const job = [];
      for(const key in response){
        if(response.hasOwnProperty(key)){
          job.push({...response[key], id: key})
        }
      }
      return job;
    }))
    .subscribe((job) => {
      console.log(job);
      this.getJob = job;
    });
  }

  getJobById(inputId: string){
    console.log(inputId)
    this.http.get<{[key: string]: Job}>('http://localhost:8080/api/job/getJobByIdOptional/' + inputId)
    .pipe(map((response) => {
      const job = [];
      for(const key in response){
        if(response.hasOwnProperty(key)){
          job.push({...response[key], id: key})
        }
      }
      return job;
    }))
    .subscribe((response) => {
      this.getJob = response;
      this.inputId = '';
    });
  }

  //this method actuall calls a get request
  //the get request method calls the service that handles post requests
  //this is a necessary work around because of 404 error issues
  //a normal POST request works via browser but not through Angular
  postJob(inputDescription: string){
    console.log(inputDescription)
    this.http.get<{[key: string]: Job}>('http://localhost:8080/api/job/addNewJobUsingGet/' + inputDescription)
    .pipe(map((response) => {
      const job = [];
      for(const key in response){
        if(response.hasOwnProperty(key)){
          job.push({...response[key], id: key})
        }
      }
      return job;
    }))
    .subscribe((response) => {
      this.getJob = response;
      this.inputDescription = '';
    });
  }
 

  //404 errors for post
  // postJobIOld(job: string){
  //   console.log(job);
  //   const headers = new HttpHeaders({
  //     'Access-Control-Allow-Origin': '*', 
  //     'Access-Control-Allow-Headers': 'Origin'
  //   });
  //   this.http.post<{name: string}>('http://localhost:8080/api/job/addNewJob/', job, {headers: headers})
  //   .subscribe((response) => {
  //     console.log(response);
  //   });
  //   this.formDescription = '';
  // }


}

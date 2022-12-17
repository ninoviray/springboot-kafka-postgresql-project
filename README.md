# springboot-kafka-postgresql-project
demo project using spring boot, poastgresql and kafka

tools/kits: java sdk17 editor - intellij spring initializer - project file - maven, java8(1.8), dependencies: spring web, kafka, jpa, postgreSQL postman - testing http requests kafka 3.3.1 postgreSQL - pgAdmin4

app uses POST requests"localhost:8080/api/job/addNewJob/{job description goes here}" to retrieve jobs from client the job will be saved into a table in the database, sent to kafka broker, returns a job id

app uses kafka listener to consume kafka messages, takes each message and updates status in database to "in progress", runs sleep timer between 1 and 5 seconds, updates status in database to "done"

app uses GET requests "localhost:8080/api/job/getJobByIdOptional/{job id goes here}" for clients to retrieve the jobs information this returns the jobs id, description and status

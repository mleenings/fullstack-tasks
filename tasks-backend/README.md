# TasksBackend
This is the Java backend of the Application.
## Run Application
1 - You should build the project with gradle and download dependencies:
```bash
./gradlew clean build --refresh-dependencies
```
2 - Start the Spring Boot Application
```bash
./gradlew bootRun
```

### Information
Because I develop on Windows Home System I can not start Docker, because Docker need Hyper-V Support which Windows Home not have. So I mocked the MongoDB and use a In-Memory database.

### JsonDB
Example for the Setup of the JsonDB in Spring is: https://github.com/vince-bickers/spring-data-test.jsondb
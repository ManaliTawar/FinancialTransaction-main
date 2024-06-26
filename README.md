# Spring Boot Application for FinancialTransaction

FinancialTransaction using spring boot

## Built With

-     [Maven](https://maven.apache.org/) - Dependency Management
-     [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit
-     [Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
-     [Swagger](https://swagger.io/) - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.

## External Tools Used

- [Postman](https://www.getpostman.com/) - API Development Environment (Testing Docmentation)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `/FinancialTransaction/src/main/java/dev/codescreen/FinancialTransactionApplication.java` class from your IDE.

- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse
  - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
  - Select the project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## packages

- `entity` — to hold our entities;
- `domain` - to hold add java objects
- `repository` — repository
- `exception` — custom exception and error handling
- `presentation` — to hold our business logic;

- `resources/` - Contains all the static resources, templates and property files.
- `resources/application.properties` - It contains application-wide properties. Spring reads the properties defined in this file to configure your application. You can define server’s default port, server’s context path, database URLs etc, in this file.

- `pom.xml` - contains all the project dependencies

## Expectations

1. API accepts requests in prescribed format.
2. Responses conform to specified schema.
3. Authorization endpoint functions as documented.
4. Comprehensive unit and integration tests implemented.


## Design considerations

- I chose to build a RESTful Transaction Service API to facilitate seamless loading of funds to users' accounts and efficient authorization of transactions based on their balances.
- If we Implement using event sourcing it involves tracking each transaction as an immutable event, allowing us to maintain an accurate and auditable record of account changes over time. By replaying these events, we can derive the current balance in real-time.
- Event sourcing offers several advantages. Firstly, it ensures precise balance calculations by relying on the complete history of transactions. Additionally, it facilitates scalability by distributing processing across multiple nodes and provides a transparent audit trail for debugging and compliance purposes.
- However, it's important to acknowledge the complexities associated with event sourcing, including managing event streams, ensuring eventual consistency, and implementing appropriate infrastructure and tooling.

## Deployment considerations

These are basic step to get you started with deploying Transaction Service using Docker. Can Modify based on your specific requirements and environment.

1. **Containerize Your Application**:

   - Create a `Dockerfile` in your project directory with the following content:

   ```Dockerfile
    FROM maven:3.6.3-jdk-17-slim AS build
    RUN mkdir -p /workspace
    WORKDIR /workspace
    COPY pom.xml /workspace
    COPY src /workspace/src
    RUN mvn -B -f pom.xml clean package -DskipTests

    FROM openjdk:17-jdk-alpine
    COPY --from=build /workspace/target/*.jar app.jar
    EXPOSE 8080
    ENTRYPOINT ["java","-jar","app.jar"]
   ```

2. **Build Docker Image**:

   - In the same directory as your `Dockerfile`, run the following command to build the Docker image:

   ```bash
   docker build -t transaction-service .
   ```

3. **Push Docker Image to Registry**:

   - If you want to push the image to Docker Hub, log in to Docker Hub using `docker login`, and then tag and push the image:

   ```bash
   docker tag transaction-service username/transaction-service
   docker push username/transaction-service
   ```

4. **Setup Docker Environment**:

   - Install Docker on your target environment by following the official Docker documentation for your operating system.

5. **Deploy Docker Containers**:

   - Use Docker Compose to define your service in a `docker-compose.yml` file:

   ```yaml
   version: "3"
   services:
     transaction-service:
       image: username/transaction-service:latest
       ports:
         - "8080:8080"
   ```

   Then, run the following command to deploy:

   ```bash
   docker-compose up -d
   ```

6. **Monitor Docker Containers**:

   - Use `docker stats` to monitor resource usage of running containers:

   ```bash
   docker stats
   ```

7. **Scale and Manage Containers**:
   - Use Docker Swarm or Kubernetes to scale and manage your containers across multiple hosts. For example, with Docker Swarm:
   ```bash
   docker service scale transaction-service=3
   ```

## Implementation Screenshot

1. /ping :

  ![Ping](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/e4f749b9-09e5-488d-a3b6-54f0bb7dd291)

  ![ping2](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/484d3633-6300-44f9-8faf-71333b8dd071)




2.  /authorization/{messageId}

   Success:
     ![authorization](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/893b95d7-1b22-4cf0-810d-dbe56640bb40)

     

    
  ![authorization2](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/f7e5c77c-dc6f-45fd-8cb9-f11fa33a53ae)

  

  ![authorization3](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/320f183c-767f-466f-b49f-a7b3f8c03a37)

  Failure:

    
  ![authorization4](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/40371e7d-d870-41df-9c77-a2f9241df55b)

  ![authorization5](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/882ac704-7577-4fb8-942e-f47c1bbfbccf)




3.  /load/{messageId}
   
   ![load](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/69ed8964-baa3-4fdb-ade5-e3174277c5e1)

   ![load1](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/df9e84f9-10a1-489e-9bbd-89c7eafa9426)

   ![load2](https://github.com/ManaliTawar/FinancialTransaction-main/assets/42312685/8731f00b-d068-4b26-a80b-437aa62a4846)




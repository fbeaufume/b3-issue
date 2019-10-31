# B3 propagation issue sample

This project is a minimal working example for the B3 header propagation issue I described in https://github.com/spring-cloud/spring-cloud-sleuth/issues/1452

It contains a gateway and two business microservices. No database is required.

Requires Java 8+.

## Installation

- Clone the sources
- Edit the Spring Boot and Spring Cloud versions in the main pom.xml if needed
- Build the sources with `mvnw package`
- Install Zipkin server by downloading from https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec or use Docker, see https://zipkin.io/pages/quickstart for details

## Execution

- Start Zipkin, for example with `java -jar zipkin-server-2.18.3-exec.jar` (uses port 9411)
- Start business service 2 with `mvnw spring-boot:run -pl b3-issue-service2` or `java -jar ...` (uses port 8082)
- Start business service 1 with `mvnw spring-boot:run -pl b3-issue-service1` or `java -jar ...` (uses port 8081)
- Start gateway with `mvnw spring-boot:run -pl b3-issue-gateway` or `java -jar ...` (uses port 8080)

## Test

- Open http://localhost:8080/date or use `curl http://localhost:8080/date` to run a first business request
- Open http://localhost:9411/ to see that first request correctly displayed in Zipkin
- Open http://localhost:8080/date with the "B3" HTTP header set to (for example) "1111111111111111-1111111111111111"
  (use `curl -H "B3: 1111111111111111-1111111111111111" http://localhost:8080/date` or Postman or whatever), to run a second business request
- In http://localhost:9411/ see that the second request is incorrectly displayed, the spans order is wrong

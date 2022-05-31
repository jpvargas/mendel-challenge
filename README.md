Copyright &copy; Juan Pablo Vargas 2022. All rights reserved.

# com.mendel:transactions-challenge

[Maven](http://maven.apache.org/) base project for labels using [Spring Boot
](https://projects.spring.io/spring-boot/).

## Building and Running

### Build-Time Dependencies

The tool chain required to build this project consists of:x

- JDK 11
- Maven 3
- Docker

All other dependencies are resolved using Maven at build time.


### Configuration files
The project uses Spring application yaml to load the properties.

### Building and Deploying

To create the package:

    mvn clean package

The generated POM files implicitly invoke [JUnit](http://junit.org) based unit tests using Maven's _surefire_ plugin.
In other words, a command line like

    mvn install

implicitly invokes unit tests. The build will break if any unit tests fail.


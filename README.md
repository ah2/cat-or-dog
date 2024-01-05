#Are you a cat or a dog person?
This project used Vaadin to create a Spring Boot app that runs in a browser.

## setting up the project
rename `apikeyRENAME.properties` found under `src\main\resources to apikey.properties`
add the dog api key and the cat key values in the `apikey.properties`

#running the web app
The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different IDEs](https://vaadin.com/docs/latest/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).

#building and running the jar
To create a production build, call `mvnw clean package -Pproduction`(Windows),
or `./mvnw clean package -Pproduction`(Mac & Linux).

This will build a JAR file with all the dependencies and front-end resources, ready to be deployed. The file can be found in the target folder after the build completes.

Once the JAR file is built, you can run it using `java -jar target/catordog-0.1.jar`

## Useful links
- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorial at [vaadin.com/docs/latest/tutorial/overview](https://vaadin.com/docs/latest/tutorial/overview).

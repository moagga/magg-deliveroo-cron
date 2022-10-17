This project is the solution to the Cron parser problem for Deliveroo coding assignment.

## Pre-Requisites
1. Java 8+
2. Apache Maven

## How to Run this application
1. Clone the repository into `<project>` directory
2. Run `mvn clean install`. This step will generate a JAR file `magg-deliveroo-cron-1.0-SNAPSHOT.jar`, under `<project>/target` directory
3. Execute the program using the command  `java -jar target/magg-deliveroo-cron-1.0-SNAPSHOT.jar <expression>` where expression is the cron expression with command. Ex: `"40/5 1-2 4,5,17 */2 1/2 echo"`

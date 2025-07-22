#XYZ Digital Banking Application - Java

Welcome to the XYZ Digital Banking.

*This is the Java version of the Digital banking Application.*

##Pre-Requisites

Java 17

Maven

PostgreSQL

#Setup Instructions

Clone the repository:

git clone https://github.com/Sachingouda-Patil/digital-banking-springboot.git cd digital-banking-springboot

Configure DB credentials in src/main/resources/application.properties

Create database: CREATE DATABASE bankdb;

Run the application: mvn clean Install

mvn spring-boot:run

Test APIs using the Postman on http://localhost:8080

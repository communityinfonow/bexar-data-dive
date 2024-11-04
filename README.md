# Bexar Data Dive

## Technologies Used
- Java/Spring Boot/Liquibase
- Vue/Vuetify
- Docker/PostgreSQL

## Development Environment
For development, the following will need to be installed:
- Java JDK (version 11 or later)
- Node/NPM/Yarn
- Docker
Visual Studio Code and pgAdmin were used to build this application - feel free to use your preferred IDE and database managment application (optional).

## Setup
### Vue
Run the following code to install the Vue app dependencies:
```
yarn install
```
### Docker/PostgreSQL
Run the following command to create a PostgreSQL image with Docker. Replace any bracketed text as needed.
```
sudo docker create --name {name} -p 5432:5432 -e POSTGRES_USER={user} -e POSTGRES_PASS={password} -e 
POSTGRES_DBNAME={database name} -t kartoza/postgis:14-3.1
```

## Running Locally
To run the webapp locally:
1. Start the database: ```sudo docker start {name}```
1. Start the Vue app (webpack): ```yarn serve```
1. Start the Java API (spring boot): ```mvn spring-boot:run```
1. Access the webapp at ```http://localhost:3000```

## Building
The webapp is run locally with an independent API and UI, but combined into a single package during the build. To build the Spring Boot JAR with embedded Tomcat, run ```mvn clean package -Denvironment={env}```. The JAR will be found in ```/target/omh.jar```

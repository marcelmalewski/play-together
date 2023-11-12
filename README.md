# Play together

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Features](#features)
* [Links](#Links)
* [How to launch](#how-to-launch)

## General Info
Web application whose primary objective is to assist in effectively organizing meetings, primarily related to computer gaming. It facilitates scheduling for both individual sessions and groups, accommodating a potentially high volume of such gatherings. Users can easily coordinate timings and days, making it a valuable tool for avid gamers.

## Technologies
Not all technologies are included.
* Java 17
* Spring 3.0.1
* Spring security 6.0.1
* Springdoc openapi 2.1.0
* JUnit 5
* Vite 4.1.0
* React 18.2.0
* Redux 4.2.1
* Typescript 4.9.3
* Tailwindcss 3.2.6
* PostgreSQL

## Features
Please note that this is a summary of features, and the complete list of features and their details can be found in the application's documentation: [documentation](#Links).
Additionally, it's important to note that the project is currently in progress, and not all features may always be included in this summary.

User and Moderator Role
1. Login/Register
2. Update own profile/authentication data
3. Delete own account
4. Get private informations about own account
5. Find all gamers public informations
6. Get public informations about other gamer
   
1. Find all game sessions
2. Get game session
3. Create game session
4. Update own game session informations
5. Delete own game session

Moderator Role
...

## Links
Importont links in app.
1. localhost/api/docs - Documentaion

## Launch Instructions
### Local Development Environment
1. Create a `.env` file with the following example values:
```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=play-together
POSTGRES_PORT=5432
PORT=5000
SPRING_PROFILES_ACTIVE=dev
```
2. Initiate the services using `./local-dev/docker-compose-dev.yml` along with the `.env` file.
   
You can either set up a run configuration in IntelliJ or start it manually. 

Example of manual start command: `docker-compose -f ./local-dev/docker-compose-dev.yml --env-file .env up` 

The Docker setup will launch:
   1. PostgreSQL database
   2. Development Proxy

### Frontend
#### Before coding frontend
1. Ensure that you are using yarn as your package manager. Current yarn version: `yarn-1.22.19` and only this version is enabled.
3. Set prettier. In Intelij you can just set "Automatic Prettier configuration".
   
#### Running the Local Frontend
1. Navigate to the ./frontend directory.
2. Install the required dependencies using: yarn install
3. Launch the frontend by executing: yarn dev"

### Backend
#### Before coding backend
...
   
#### Running the Local Backend with Intelij
To start the application, execute `application bootRun` from the Gradle panel, using the variables specified in the `.env` file.

#### Running the Backend with images
1. Build the project: `./gradlew build`
2. Build the Docker image: `docker build -t play-together-api .`
3. Tag the image: `docker tag play-together-api 6745345/play-together-api:1.0`
4. Push the image to Docker Hub: `docker push 6745345/play-together-api:1.0`
5. Use the provided docker-compose-dev.yml.
In case push doesn't reflect changes, remove the local image and rerun the Docker Compose.

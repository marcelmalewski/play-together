# What will docker start:
1. database...

# Local dev environment
1. create .env file
2. start `./local-dev/docker-compose.yml` with environment variables file: `.env`

# example values for `.env`
```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=play-together
POSTGRES_PORT=5432
PORT=5000
SPRING_PROFILES_ACTIVE=dev
```
## Before coding frontend
1. set `yarn` as package manager
2. set prettier
   
## Start local frontend
1. go to `./frontend`
2. yarn install
3. yarn dev

## Before coding backend
1. set environment variables for api from `.env`
   
## Start local backend
1. clean build with gradle (poprawić) dać komende


  
# Backend with docker
1. command: `./gradlew build`
2. command: `docker build -t play-together-api .`
   optional parametr: --build-arg JAR_FILE=build/libs/api-1.0.0.jar
3. command: `docker tag play-together-api 6745345/play-together-api:1.0`
4. command: `docker push 6745345/play-together-api:1.0`
5. docker-compose.yml
Czasami po pushu nie załapie wtedy usunąć lokalny image i jeszcze raz odpalic dockercompose

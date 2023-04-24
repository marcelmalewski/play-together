# Przygotowanie środowisko
1. konfuguracja dockercompose
2. konfiguracja api
3. zbudowanie projektu zeby pojawily sie rzeczy od mapstructa

# Frontend lokalnie
1. wejść do folderu frontend
2. yarn install
3. yarn dev

# Backend lokalnie

# Postgres przez dockera konfiguracja lokalna (.env)
```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=play-together
POSTGRES_PORT=5432
```
  
# Backend przez dockera
1. komenda: `./gradlew build`
2. komenda: `docker build -t play-together-api .`
   opcjonalny parametr: --build-arg JAR_FILE=build/libs/api-1.0.0.jar
3. komenda: `docker tag play-together-api 6745345/play-together-api:1.0`
4. komenda: `docker push 6745345/play-together-api:1.0`
5. wtedy można odpalic dockercompose
Czasami po pushu nie załapie wtedy usunąć lokalny image i jeszcze raz odpalic dockercompose


package com.marcel.malewski.playtogetherapi.group;


public class Group {

}

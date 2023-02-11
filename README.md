# 1. Stawianie postgresa
# 2. Stawianie skonteryzowanego springa
1. ./gradlew build
2. docker build -t play-together-api .
   opcjonalny parametr: --build-arg JAR_FILE=build/libs/api-1.0.0.jar
3. docker tag play-together-api 6745345/play-together-api:1.0
4. docker push 6745345/play-together-api:1.0
5. wtedy można odpalic dockercompose
Czasami po pushu nie załapie wtedy usunąć lokalny image i jeszcze raz odpalic dockercompose

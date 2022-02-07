# Pokemon Example Using Helidon MP And JPA

Minimal Helidon MP project for managing Pokemons.

## Build and run

With JDK11+
```bash
mvn package
java -jar target/helidon-jpa-pokemon.jar
```


## Exercise the application


| Methods | Urls    | Actions     |
|---------|---------|-------------|
| GET    | /pokemon | retrieve all Pokemons |
| GET    | /pokemon/:id | retrieve a Pokemons by :id      |
| GET    | /pokemon/type/:type | retrieve a Pokemons by :type      |
| POST    | /pokemon | create new Pokemon |
| PUT    | /pokemon | update a Pokemon by id |
| DELETE  | /pokemon/:id | delete a Pokemon by :id |


```
curl -X GET http://localhost:8080/pokemon
[{"id":"p1","name":"Pikachu","type":"Electric","age":8},{"id":"p2","name":"Eevee","type":"Normal","age":3},{"id":"p3","name":"Charizard","type":"Flying","age":9}]
```

## Try health and metrics

```
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .

# Prometheus Format
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .
```

## Todo
|Todo|
|---------|
| Pagination |
| Security |

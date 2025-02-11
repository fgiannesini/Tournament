# Tournament

This project was created using the [Ktor Project Generator](https://start.ktor.io).

It is based on :

| Name                                               | Description                                                 |
|----------------------------------------------------|-------------------------------------------------------------|
| [Koin](https://start.ktor.io/p/koin)               | Provides dependency injection                               |
| [Routing](https://start.ktor.io/p/routing-default) | Allows to define structured routes and associated handlers. |
| [DynamoDb](https://aws.amazon.com/fr/dynamodb/)    | NoSQL database                                              |

## Features

Here's a list of features included in this project:

- Create a player with a pseudo (id returned in header `Location`)

```
curl -X POST --location "http://127.0.0.1:8080/players" \
  -H "Content-Type: application/json" \
  -d '{
  "pseudo": "second"
  }' 
 ```

- Get a player by id

```
curl -X GET --location "http://127.0.0.1:8080/players/810ebdeb-9cb0-45b4-a357-b79393e6bd40" \
    -H "Content-Type: application/json" 
 ```

- Update a player by id

```
curl -X PATCH --location "http://127.0.0.1:8080/players/810ebdeb-9cb0-45b4-a357-b79393e6bd40" \
    -H "Content-Type: application/json" \
    -d '{
          "score": 10
        }'
 ```

- Get all players ordered by score

```
  curl -X GET --location "http://127.0.0.1:8080/players" \
  -H "Content-Type: application/json"
 ```

- Delete all players

```
curl -X DELETE --location "http://127.0.0.1:8080/players" \
    -H "Content-Type: application/json"
```

## Building & Running

To build or run the project, use one of the following tasks:

| Task              | Description      |
|-------------------|------------------|
| `./gradlew test`  | Run the tests    |
| `./gradlew build` | Build everything | 
| `./gradlew run`   | Run the server   | 

You need docker to launch this application, then you can use the script [launch.bat](./launch.bat)

## TO DO

- Implements a dockerFile
- Implements a healthCheck for cloud deployment
- Generate OpenApi documentation
- Expose a swagger
- Add an authentication via token OAuth2
- Use aws-sdk-kotlin to simplify request declaration

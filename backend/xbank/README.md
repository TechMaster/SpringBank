## Development

To start your application in the dev profile, run:

```
./mvnw
```
## Building for production

### Packaging as jar

To build the final jar and optimize the xbank application for production, run:

```
./mvnw -Pprod clean verify
```

To ensure everything worked, run:

```
ws://localhost:8081/ws/events

java -jar target/*.jar


```
### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```

./mvnw -Pprod,war clean verify


```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

## Swagger 

To check swagger, go to url:

```
https://app.swaggerhub.com/apis/hoaronal/XBANK/1.0.0
```

## Paging and soft 

To check swagger, go to url:

```
http://localhost:8081/api/users?page=2&size=1
```
## run with docker

```bash
docker-compose build && docker-compose up -d
```
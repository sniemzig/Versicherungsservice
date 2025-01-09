## Versicherungsservice

---

### Setup:
#### Environment:
```shell
cp .env.tmpl .env
# edit .env file...
```

#### Build / Run:
```shell
./gradlew build && docker compose up -d
```
>The application is then live on <http://localhost>

---

### Testing:
```shell
./gradlew test
```
>results are located at: `./build/reports/index.html`

### Api Documentation:
Swagger docs at: <http://localhost/swagger-ui.html>
# Aromas de Viña — Core Backend

This backend was generated with [JHipster](https://www.jhipster.tech/). You don't need to know JHipster to run it.

---

## Prerequisites

- [Node.js](https://nodejs.org/) v24+
- [OpenJDK](https://adoptium.net/) 25+
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (includes Docker Compose)

---

## Running the app

```bash
./mvnw
```

Spring Boot will automatically start the required services (PostgreSQL, Keycloak, MinIO) via Docker Compose and launch the application. The API will be available at `http://localhost:8080`.

---

## Stopping and cleaning up

Press `Ctrl+C` in the terminal where `./mvnw` is running to stop the app.

To stop and remove all Docker containers and their data volumes:

```bash
docker compose -f src/main/docker/app.yml down -v
```

> Omit `-v` if you want to keep the database and storage data between sessions.

---

## Ports

Make sure these are free before starting:

| Port   | Service                    |
| ------ | -------------------------- |
| `8080` | Application (API)          |
| `9080` | Keycloak (auth server)     |
| `5432` | PostgreSQL                 |
| `9000` | MinIO (object storage API) |
| `9001` | MinIO Console              |

---

## The Angular frontend

JHipster ships a built-in Angular frontend at `http://localhost:8080` for browsing the API and monitoring application metrics.

The OpenAPI spec is available at `http://localhost:8080/v3/api-docs` and can be used to generate a typed client for your frontend.

---

## Connecting a frontend

The backend validates a JWT on each request. Your frontend authenticates directly with Keycloak — the backend is never involved in the login flow.

### Auth settings

| Setting                | Value                                         |
| ---------------------- | --------------------------------------------- |
| Issuer / Authority URL | `http://localhost:9080/realms/aromas-de-vina` |
| Client ID              | `web_app`                                     |
| Client secret          | none — public client                          |
| Grant type             | Authorization Code + PKCE                     |
| Redirect URI           | your app's login callback URL                 |

`web_app` is pre-configured in the realm. Its redirect URIs cover all `localhost` ports so no Keycloak changes are needed for local dev.

Include `offline_access` in your requested scopes if you want refresh tokens — without it the user will need to log in again when the access token expires.

Default test users:

| Username | Password | Role  |
| -------- | -------- | ----- |
| `admin`  | `admin`  | Admin |
| `user`   | `user`   | User  |

### Example with keycloak-js

```javascript
import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://localhost:9080',
  realm: 'aromas-de-vina',
  clientId: 'web_app',
});

await keycloak.init({
  onLoad: 'login-required', // redirect to Keycloak if not logged in
  pkceMethod: 'S256', // enables PKCE
  scope: 'openid profile email offline_access', // offline_access for refresh tokens
});

// attach the token to every API request
const response = await fetch('http://localhost:8080/api/some-endpoint', {
  headers: {
    Authorization: `Bearer ${keycloak.token}`,
  },
});

// check every 60s and refresh if the token expires in less than 30s
setInterval(async () => {
  await keycloak.updateToken(30);
}, 60000);
```

### CORS

Add your frontend's origin to `jhipster.cors.allowed-origins` in `src/main/resources/config/application-dev.yml` and restart the app.

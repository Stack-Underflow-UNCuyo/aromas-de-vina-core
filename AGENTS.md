# AGENTS.md - Development Guidelines

This is a JHipster-generated full-stack application with Spring Boot (Java 21) backend and Angular 21 frontend.

## Build Commands

### Frontend (Angular/TypeScript)

```bash
npm start              # Start dev server with HMR (port 4200)
npm run test           # Run Vitest with coverage
npm run test:watch     # Run tests in watch mode
npm run lint           # ESLint check
npm run lint:fix       # ESLint fix
npm run prettier:check # Check formatting
npm run prettier:format # Format code
```

### Backend (Java/Maven)

```bash
./mvnw -Dskip.installnodenpm -Dskip.npm    # Build backend only
./mvnw -ntp -Dskip.installnodenpm -Dskip.npm verify  # Full backend test
```

### Running Single Tests

```bash
# Single Vitest file
npx vitest run src/main/webapp/app/account/login/login.component.spec.ts

# Single test in file (Vitest)
npx vitest run src/main/webapp/app/account/login/login.component.spec.ts -t "test name"

# Single Maven test class
./mvnw test -Dtest=UserServiceTest

# Single Maven test method
./mvnw test -Dtest=UserServiceTest#testCreateUser
```

### Full Build & CI

```bash
npm run build          # Full production build (backend + frontend)
npm run ci:backend:test  # Backend: javadoc, checkstyle, unit tests
npm run ci:frontend:test # Frontend: build + test
```

## Code Style

### Formatting

- **Prettier**: 140 char line width, single quotes, 2 space indent (4 for Java)
- Run `npm run prettier:format` before committing

### TypeScript/Angular

- **Strict mode enabled**: `strict: true`, `strictNullChecks: true`, `strictTemplates: true`
- Use `import { type }` syntax for type-only imports
- Components use `jhi` prefix
- SCSS for styles, not plain CSS
- Use `OnPush` change detection where appropriate

### Java

- **Checkstyle**: Run `./mvnw checkstyle:check` to validate
- **Spotless**: Auto-applied during build (formats Java source)
- **MapStruct**: Used for DTO mapping - ensure mappers are regenerated after entity changes

### Naming Conventions

- **TypeScript**: camelCase for variables/functions, PascalCase for classes/components
- **Java**: camelCase for methods/fields, PascalCase for classes
- **Database**: Liquibase migrations in `src/main/resources/config/liquibase/changelog/`
- **SQL identifiers**: Use lowercase with underscores (snake_case)

## Project Structure

```
src/main/java/com/aromasdevina/core/    # Java backend
src/main/webapp/                        # Angular frontend
src/main/resources/                     # Backend config
src/main/docker/                        # Docker configs
src/test/                               # Backend tests
src/main/webapp/app/                   # Angular app code
```

### Backend Patterns

- **Entities**: `domain/` - JPA entities with Liquibase changelogs
- **Repositories**: `repository/` - Spring Data JPA
- **Services**: `service/` - Business logic
- **REST**: `web/rest/` - REST controllers
- **DTOs**: `service/dto/` - Data transfer objects
- **Mappers**: `service/mapper/` - MapStruct mappers

### Frontend Patterns

- **Components**: `app/*/` - Feature components
- **Services**: `app/core/` - Core services (auth, API)
- **Shared**: `app/shared/` - Shared components/pipes/directives
- **i18n**: `i18n/` - Translation files

## Database

- **Dev**: PostgreSQL at `localhost:5432/core`
- **Migrations**: Liquibase (run on startup in dev, required in prod)
- **H2 Console**: Available in dev at `/h2-console`

## Common Development Tasks

### Create new entity

1. Create JPA entity in `src/main/java/com/aromasdevina/core/domain/`
2. Add Liquibase changelog in `src/main/resources/config/liquibase/changelog/`
3. Generate repositories/services with JHipster: `npm run jhipster:entity`

### Add new REST endpoint

1. Create controller in `src/main/webapp/app/core/` or feature module
2. Add route to router module
3. Update API service if needed

### Database changes

1. Create changelog in `src/main/resources/config/liquibase/changelog/`
2. Reference from `master.xml`

## Testing

- **Frontend**: Vitest + Angular testing utilities
- **Backend**: JUnit 5 + Spring Boot Test
- **Integration tests**: Classes ending with `IT` or `IntTest`
- Run `npm run ci:backend:test` for full backend validation

## Security

- OAuth2/OIDC (Keycloak in dev)
- Spring Security on backend
- JWT tokens for API authentication
- Never commit secrets - use environment variables

## Dependencies

- **Java**: 21 (min), Spring Boot 4.0.3
- **Node**: >=24.14.0
- **Angular**: 21.2.2
- **Database**: PostgreSQL

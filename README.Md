# Prueba Técnica Java - Isaac De Ramón

### Descripción
Sistema de procesamiento de transacciones basado en microservicios (Validator + Persistence) con seguridad Basic Auth (BCrypt), validación de integridad SHA-256 y persistencia en H2.

### Requisitos
- Java 17+
- Node.js & Angular CLI
- Maven

### Instalación y Ejecución
1. **API Persistence (Puerto 8081):** `cd api-persistence && ./mvnw spring-boot:run`
2. **API Validator (Puerto 8080):** `cd api-validator && ./mvnw spring-boot:run`
3. **Frontend (Puerto 4200):** `cd api-validator-ui && npm install && ng serve`

### Credenciales de Acceso
- **Usuario:** isaacdp
- **Password:** contra123

### Características Implementadas
- Firma SHA-256 sincronizada Front/Back.
- Validación de DTOs con `@Valid` y `@Pattern`.
- Manejo de excepciones con `@RestControllerAdvice` y feedback visual con HTTP.cat.
- Actualización parcial de estatus mediante método **PATCH** y `@Query`.

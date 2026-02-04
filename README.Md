# Prueba Técnica Java - Isaac De Ramón

### Descripción
Sistema robusto de procesamiento de transacciones diseñado con una arquitectura de microservicios desacoplada. La solución asegura la integridad de los datos mediante firmas criptográficas y protege el acceso mediante autenticación segura.


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
- Integridad de Datos: Implementación de firmas SHA-256 calculadas en el Frontend y validadas en el Backend para prevenir la manipulación de información.

- Arquitectura: Comunicación entre microservicios utilizando OpenFeign.

- Seguridad: Autenticación Basic Auth con contraseñas cifradas mediante BCrypt.

- Validaciones: Uso estricto de @Valid, @Pattern (Regex) y @RestControllerAdvice para un manejo de errores limpio y consistente.

- UX/UI: Interfaz responsiva en Angular con retroalimentación visual dinámica mediante HTTP.cat para códigos de estado.

- Persistencia: Base de datos H2 en memoria con actualizaciones parciales mediante el método PATCH y @Query nativo.

### Pruebas Automatizadas (Scripts Bash)
He incluido scripts en la raíz del proyecto para facilitar la validación técnica de los endpoints desde la terminal.
1. Test de Flujo Completo

Realiza login, calcula una firma válida, registra una venta y consulta el historial.

chmod +x test_api.sh
./test_api.sh


2. Test de Vulnerabilidad (Validaciones)

Prueba la robustez del validador enviando datos corruptos e importes mal formados (espera respuestas 400 Bad Request).

chmod +x test_validations.sh
./test_validations.sh


### Decisiones de Diseño

    SHA-256: Se eligió para garantizar que los datos críticos (importe/cliente) no sean alterados durante el tránsito.

    Separación de Microservicios: El Validator actúa como un gateway de seguridad, mientras que el Persistence se encarga exclusivamente del dominio de datos.

    HTTP Cats: Implementado para proporcionar un feedback visual inmediato y humorístico sobre el estado de las peticiones durante la evaluación.
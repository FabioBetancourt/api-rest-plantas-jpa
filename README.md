# API REST de Plantas - Unidad 3

Esta versión continúa el proyecto de plantas desarrollado en las Unidades 1 y 2.

## Integrante
Fabio Esteban Betancourt Camacho

## Tecnologías
Java 17, Spring Boot, Spring Web, Spring Data JPA, Hibernate, MySQL, Bean Validation, RestClient, Spring Boot Actuator, Micrometer y Prometheus Registry.

## Entidades y relación
- `Categoria`: id, nombre, descripción.
- `Planta`: id, nombre, ubicación, necesitaSolDirecto y categoría.

Relación:

```text
Categoria 1 ----- N Planta
```

Se implementa con `@OneToMany` y `@ManyToOne`.

## Configuración MySQL

Crear la base:

```sql
CREATE DATABASE plantas_db;
```

Configurar variables de entorno. Las credenciales reales no se guardan en el repositorio:

```text
DB_URL=jdbc:mysql://localhost:3306/plantas_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contrasena
```

PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/plantas_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="TU_PASSWORD"
mvn spring-boot:run
```

## Endpoints principales

### Categorías
- `POST /api/categorias`
- `GET /api/categorias`
- `GET /api/categorias/{id}`
- `PUT /api/categorias/{id}`
- `DELETE /api/categorias/{id}`

Ejemplo:

```json
{
  "nombre": "Tropical",
  "descripcion": "Plantas adaptadas a ambientes cálidos y húmedos"
}
```

### Plantas
- `POST /api/plantas`
- `GET /api/plantas`
- `GET /api/plantas/{id}`
- `PUT /api/plantas/{id}`
- `DELETE /api/plantas/{id}`
- `GET /api/plantas/buscar?nombre=lav`
- `GET /api/plantas/buscar?categoria=Tropical`

Ejemplo:

```json
{
  "nombre": "Monstera deliciosa",
  "ubicacion": "Sala",
  "necesitaSolDirecto": false,
  "categoriaId": 1
}
```

## API externa
Se utiliza Open-Meteo, una API pública sin token:

```text
https://api.open-meteo.com/v1/forecast
```

Consultar clima:

```http
GET /api/clima?lat=5.07&lon=-75.52
```

Recomendación de riego usando datos de la planta y del clima:

```http
GET /api/clima/plantas/1/recomendacion-riego?lat=5.07&lon=-75.52
```

Si falla Open-Meteo, la API maneja el error y responde `502 Bad Gateway`.

## Observabilidad

Actuator:

```http
GET /actuator/health
GET /actuator/metrics
GET /actuator/prometheus
```

Métricas personalizadas:

```text
plantas.creadas
clima.consultas
```

Consulta:

```http
GET /actuator/metrics/plantas.creadas
GET /actuator/metrics/clima.consultas
```

Se incluye un `HealthIndicator` personalizado llamado `plantas`, que consulta la base de datos y muestra la cantidad de plantas registradas.

## Logs
- `INFO`: consultas, creación, actualización y eliminación.
- `WARN`: planta no encontrada.
- `ERROR`: error al consumir Open-Meteo.

## Ejecución

```bash
mvn spring-boot:run
```

Aplicación:

```text
http://localhost:8080
```

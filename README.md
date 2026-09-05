# API REST de Plantas con JPA e Hibernate

## Descripción
API REST para gestionar una colección de plantas mediante operaciones CRUD y consultas personalizadas.

## Contexto
Colección personal de plantas.

## Integrante
Fabio Esteban Betancourt Camacho.

## Tecnologías
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Bean Validation

## Endpoints

| Método | Endpoint | Función |
|---|---|---|
| POST | `/api/plantas` | Crear |
| GET | `/api/plantas` | Consultar todas |
| GET | `/api/plantas/{id}` | Consultar por ID |
| PUT | `/api/plantas/{id}` | Actualizar |
| DELETE | `/api/plantas/{id}` | Eliminar |
| GET | `/api/plantas/buscar?tipo=tropical` | Buscar por tipo |
| GET | `/api/plantas/buscar-nombre?nombre=lav` | Buscar por nombre |

## Ejecutar

```bash
mvn spring-boot:run
```

API:
```text
http://localhost:8080
```

Consola H2:
```text
http://localhost:8080/h2-console
```

JDBC URL:
```text
jdbc:h2:file:./data/plantasdb
```

Usuario:
```text
sa
```

Contraseña: vacía.

## Ejemplo POST

```json
{
  "nombre": "Lavanda",
  "tipo": "aromática",
  "ubicacion": "balcón",
  "necesitaSolDirecto": true
}
```

Respuesta esperada: `201 Created`.

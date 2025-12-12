# 🎫 Events API

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)

**API REST para la gestión de eventos con autenticación JWT y control de acceso basado en roles**

[Características](#-características) •
[Instalación](#-instalación) •
[Endpoints](#-endpoints-de-la-api) •
[Tecnologías](#%EF%B8%8F-tecnologías)

</div>

---

## ✨ Características

- 🔐 **Autenticación JWT** - Sistema seguro de login y registro
- 👥 **Control de Roles** - Permisos diferenciados para ADMIN y USER
- 📅 **Gestión de Eventos** - CRUD completo para administrar eventos
- ✅ **Validación de Datos** - Validación robusta con Bean Validation
- 🗃️ **Migraciones** - Control de versiones de BD con Flyway
- 🔄 **Mapeo DTO** - Transformación de entidades con MapStruct

---

## 🛠️ Tecnologías

| Tecnología | Descripción |
|------------|-------------|
| **Spring Boot 3.3.5** | Framework principal |
| **Spring Security** | Seguridad y autenticación |
| **Spring Data JPA** | Persistencia de datos |
| **PostgreSQL** | Base de datos relacional |
| **Flyway** | Migraciones de base de datos |
| **JWT (jjwt 0.11.5)** | Tokens de autenticación |
| **MapStruct** | Mapeo de objetos |
| **Lombok** | Reducción de boilerplate |
| **Maven** | Gestión de dependencias |

---

## 📋 Requisitos Previos

- ☕ **Java 17** o superior
- 🐘 **PostgreSQL** instalado y ejecutándose
- 📦 **Maven 3.6+**

---

## 🚀 Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/demoapi.git
cd demoapi
```

### 2. Configurar la base de datos

Crear una base de datos en PostgreSQL:

```sql
CREATE DATABASE demoapi;
```

### 3. Configurar variables de entorno

Puedes configurar las credenciales mediante variables de entorno o modificar `application.properties`:

```bash
# Variables de entorno (opcional)
export DB_URL=jdbc:postgresql://localhost:5432/demoapi
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_password
```

### 4. Ejecutar la aplicación

```bash
# Con Maven Wrapper
./mvnw spring-boot:run

# O con Maven instalado
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`

---

## 🔗 Endpoints de la API

### 🔑 Autenticación

| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| `POST` | `/api/v1/auth/register` | Registrar nuevo usuario | Público |
| `POST` | `/api/v1/auth/login` | Iniciar sesión | Público |

#### Registro de Usuario

```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "usuario1",
  "password": "password123",
  "email": "usuario@email.com",
  "name": "Nombre Usuario"
}
```

#### Login

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "usuario1",
  "password": "password123"
}
```

**Respuesta:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer"
}
```

---

### 📅 Eventos

> ⚠️ Todos los endpoints de eventos requieren autenticación JWT

| Método | Endpoint | Descripción | Roles Permitidos |
|--------|----------|-------------|------------------|
| `GET` | `/api/v1/events` | Obtener todos los eventos | ADMIN, USER |
| `GET` | `/api/v1/events/{id}` | Obtener evento por ID | ADMIN, USER |
| `POST` | `/api/v1/events` | Crear nuevo evento | ADMIN |
| `PUT` | `/api/v1/events/{id}` | Actualizar evento | ADMIN, USER |
| `DELETE` | `/api/v1/events/{id}` | Eliminar evento | ADMIN, USER |

#### Crear Evento (Solo ADMIN)

```http
POST /api/v1/events
Authorization: Bearer <tu_token_jwt>
Content-Type: application/json

{
  "name": "Conferencia de Tecnología",
  "date": "2025-06-15",
  "location": "Centro de Convenciones"
}
```

#### Respuesta de Evento

```json
{
  "id": 1,
  "name": "Conferencia de Tecnología",
  "date": "2025-06-15",
  "location": "Centro de Convenciones"
}
```

---

## 📁 Estructura del Proyecto

```
src/main/java/com/isaacjava/demoapi/
├── 📂 config/
│   ├── SecurityConfig.java          # Configuración de Spring Security
│   └── 📂 jwt/
│       ├── JwtAuthenticationFilter.java
│       ├── JwtAuthEntryPoint.java
│       └── JwtGenerator.java
├── 📂 controllers/
│   ├── AuthController.java          # Endpoints de autenticación
│   └── EventController.java         # Endpoints de eventos
├── 📂 domain/
│   ├── Event.java                   # Entidad Evento
│   ├── Role.java                    # Entidad Rol
│   └── User.java                    # Entidad Usuario
├── 📂 dto/
│   ├── EventRequestDto.java
│   ├── EventResponseDto.java
│   ├── JwtAuthResponseDto.java
│   ├── LoginDto.java
│   └── RegisterDto.java
├── 📂 exception/
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── 📂 mapper/
│   ├── IEventMapper.java
│   └── UserMapper.java
├── 📂 repository/
│   ├── EventRepository.java
│   ├── RolRepository.java
│   └── UserRepository.java
├── 📂 services/
│   ├── EventService.java
│   ├── IEventService.java
│   └── UserDetailsServiceImpl.java
└── DemoapiApplication.java          # Clase principal
```

---

## ⚙️ Configuración

### Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `DB_URL` | URL de conexión a PostgreSQL | `jdbc:postgresql://localhost:5432/demoapi` |
| `DB_USERNAME` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | - |

---

## 🧪 Testing

Ejecutar los tests con:

```bash
./mvnw test
```

---

## 📝 Licencia

Este proyecto fue creado como parte de un tutorial de aprendizaje.

---

<div align="center">

**⭐ Si te resultó útil, no olvides dejar una estrella ⭐**

Desarrollado con ❤️ usando Spring Boot

</div>


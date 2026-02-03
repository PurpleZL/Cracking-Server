# CONTEXTO DEL PROYECTO - Cracking Server

## Descripcion
Servidor de cracking de hashes en Spring Boot con SQLite. Proyecto educativo/ligero para pruebas de seguridad y fuerza bruta a hashes, diseñado para despliegue rapido en droplets.

## Estilo de Trabajo
**EDUCATIVO**: No hacer las cosas directamente, sino enseñar la forma correcta de implementar. El objetivo es aprender las mejores practicas aplicandolas uno mismo.

## Stack Tecnologico
- Java 21
- Spring Boot 4.0.2
- SQLite + Hibernate Community Dialect
- Lombok
- Spring Security (deshabilitado para desarrollo)

## Arquitectura Actual
```
src/main/java/com/security/cracking/
├── CrackingApplication.java          # Main
├── config/
│   └── SecurityConfig.java           # Config de seguridad (CSRF disabled)
├── controller/
│   └── CrackingController.java       # REST API /api/hashcracking
├── dto/
│   ├── HashRequestDTO.java           # Request: hash, hashType, passwd, passListF
│   └── HashResponseDTO.java          # Response: success, message, passwdCracked
└── service/
    ├── CrackingService.java          # Orquesta los crackers
    ├── HashCracker.java              # Interface Strategy pattern
    └── BCryptCracker.java            # Implementacion BCrypt
```

## Endpoints
- `POST /api/hashcracking` - Recibe hash y password/wordlist, intenta crackear

---

# PROXIMOS PASOS

## Fase 1: Base de Datos (JPA + SQLite)

### 1.1 Entidad User (primero)
```java
User
├── id
├── username
├── passwordHash
├── role (ADMIN, USER)
└── createdAt
```

### 1.2 Entidad CrackedHash (segundo)
```java
CrackedHash
├── id
├── userId
├── hash
├── hashType
├── password
└── crackedAt
```
### 1.3 Entidad Wordlist (tercero)
```java
Wordlist
├── id
├── name ("rockyou", "custom-es")
├── path (ruta al archivo)
├── isSystem (true = del servidor)
└── userId (nullable, si es custom)
```

## Fase 2: Autenticacion JWT

## Fase 3: Funcionalidades Avanzadas (futuro)

- Rate limiting por usuario
- Reverse lookup (una password contra N hashes)
- Documentacion OpenAPI/Swagger

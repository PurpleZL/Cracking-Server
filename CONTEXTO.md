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

# PROBLEMAS CORREGIDOS (Sesion 1)

## 1. BCryptCracker NO tiene @Component/@Service - CORREGIDO
**Problema**: La clase no esta anotada, Spring no la inyectara en `List<HashCracker>`.

**Solucion**: Agregar `@Component` o `@Service` a la clase.

```java
@Component  // <-- AGREGAR ESTO
public class BCryptCracker implements HashCracker {
```

---

## 2. Inconsistencia en tipo de retorno de HashCracker - CORREGIDO
**Problema**: La interface declara `Optional<String>` pero BCryptCracker retorna `String`.

```java
// Interface dice:
Optional<String> crack(HashRequestDTO request);

// Implementacion dice:
public String crack(HashRequestDTO hashReq) { ... }
```

**Solucion**: Decidir uno. Lo correcto es usar `Optional<String>` y ajustar la implementacion:

```java
@Override
public Optional<String> crack(HashRequestDTO hashReq) {
    // ... logica ...
    return Optional.ofNullable(resultado);
}
```

---

## 3. Error de tipos en Controller - CORREGIDO
**Problema**: El catch devuelve un String en lugar de HashResponseDTO.

```java
}catch (IllegalArgumentException iae){
    return ResponseEntity.badRequest().body(iae.getMessage()); // ERROR: String, no DTO
}
```

**Solucion**: Crear un DTO de error:

```java
} catch (IllegalArgumentException iae) {
    HashResponseDTO errorResp = new HashResponseDTO();
    errorResp.setMessage(iae.getMessage());
    return ResponseEntity.badRequest().body(errorResp);
}
```

---

## 4. Nombre de metodo no sigue convenciones Java - CORREGIDO
**Problema**: `HashCracking` empieza con mayuscula.

```java
public ResponseEntity<HashResponseDTO> HashCracking(...) // MAL
```

**Solucion**: camelCase para metodos:

```java
public ResponseEntity<HashResponseDTO> hashCracking(...) // BIEN
```

---

## 5. BCryptPasswordEncoder se instancia en cada request - CORREGIDO
**Problema**: Crear instancia cada vez es ineficiente.

```java
public String crack(HashRequestDTO hashReq) {
    BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(); // CADA VEZ
```

**Solucion**: Inyectarlo como Bean o hacerlo campo final:

```java
private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
```

O mejor, como Bean en SecurityConfig y luego inyectar.

---

## 6. Falta @Valid en el Controller - CORREGIDO
**Problema**: Los DTOs tienen anotaciones de validacion pero no se activan.

```java
public ResponseEntity<HashResponseDTO> hashCracking(@ModelAttribute HashRequestDTO hashReq)
```

**Solucion**:

```java
public ResponseEntity<HashResponseDTO> hashCracking(@Valid @ModelAttribute HashRequestDTO hashReq)
```

---

## 7. CrackingService.crackPasswd() tiene incompatibilidad de tipos - CORREGIDO
**Problema**: El crack() de la interface retorna `Optional<String>` pero el service lo trata como `String`:

```java
String passwordCracked = crackers.stream()
    .filter(cracker -> cracker.supports(hashReq.getHashType()))
    .findFirst().orElseThrow(...)
    .crack(hashReq);  // Esto seria Optional<String>, no String
```

**Solucion**: Despues de arreglar la interface:

```java
Optional<String> result = crackers.stream()
    .filter(cracker -> cracker.supports(hashReq.getHashType()))
    .findFirst()
    .orElseThrow(() -> new IllegalArgumentException("Unsupported hash"))
    .crack(hashReq);

return result
    .map(pwd -> success(hashReq, pwd))
    .orElse(error(hashReq, "Hash not cracked"));
```

---

## 8. No hay manejo global de excepciones
**Recomendacion**: Crear un `@ControllerAdvice` para manejar excepciones de forma centralizada.

---

## 9. No hay cache de hashes ya crackeados
**Recomendacion**: Usar la BD SQLite para guardar hash -> password encontrado. Antes de crackear, buscar si ya existe.

---

# PROXIMOS PASOS (Pendientes)

1. Agregar `@ControllerAdvice` para manejo global de excepciones
2. Agregar capa de persistencia/cache para hashes ya crackeados
3. Agregar mas tipos de hash (MD5, SHA1, SHA256, etc.)
4. Implementar rate limiting
5. Agregar documentacion OpenAPI/Swagger

---

# HASHES QUE SE INCORPORARAN
- MD5
- SHA-1
- SHA-256
- NTLM
- PostgreSQL MD5

# NOTAS PARA FUTURAS SESIONES
- El usuario quiere aprender, no que le haga el codigo directamente
- Explicar el "por que" de cada cambio
- Proyecto orientado a seguridad defensiva/educativa

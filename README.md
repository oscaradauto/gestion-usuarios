# Gestión de Usuarios

Aplicación **Spring Boot 3** que expone un conjunto de endpoints REST para la **gestión de usuarios**.  

---

## Tecnologías usadas
- **Java 17**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring Web (REST API)**
- **Spring Validation**
- **H2 Database (en memoria)**
- **ModelMapper** (para mapear DTOs ↔ entidades)
- **JUnit 5 + Mockito** (para pruebas unitarias)
- **Maven** (gestor de dependencias)

---

## Estructura del proyecto
```
src/main/java/com/app/evaluacion/gestion
 ├── controller      # Controladores REST
 ├── dto             # Objetos de transferencia (UserRequestDto)
 ├── exception       # Excepciones personalizadas y manejo global
 ├── mapper          # Clases para conversión Entity <-> DTO
 ├── model           # Entidades JPA (User, Phone)
 ├── repository      # Repositorios JPA
 ├── service         # Lógica de negocio (UserService)
 └── GestionApplication.java
```

---

## Ejecución de la aplicación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/oscaradauto/gestion-usuario.git
   cd gestion-usuario
   ```

2. Compilar y ejecutar con Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

3. La aplicación estará disponible en:
   ```
   http://localhost:8080/api/users
   ```

---

##  Endpoints principales

**1. Importar con Postman app el siguiente archivo:  postman/Test_Gestion.postman_collection.json**

### Crear usuario
```http
POST /api/users/register
Content-Type: application/json
```
Body:
```json
{
  "name": "Oscar",
  "email": "oscar@email.com",
  "password": "Admin123",
  "phones": [
    { "number": "1234567", "cityCode": "1", "countryCode": "57" }
  ]
}
```

### Consultar usuario por ID
```http
GET /api/users/{id}
```

### Actualizar usuario
```http
PUT /api/users/update/{id}
```

### Eliminar usuario
```http
DELETE /api/users/{id}
```

---

## Pruebas unitarias

Ejecutar las pruebas:
```bash
./mvnw test
```

Ejemplo de prueba unitaria en `UserServiceTest`:
```java
@Test
void register_UserNew() {
    when(userRepository.findByEmail(userRequestDto.getEmail())).thenReturn(Optional.empty());

    User saved = userService.register(userRequestDto);

    assertNotNull(saved);
    assertEquals("Oscar", saved.getName());
    verify(userRepository, times(1)).save(any(User.class));
}
```

---

## Diagrama de la solución

```
[ Cliente (Postman) ]
         |
         v
 [ UserController ]
         |
         v
 [ UserService ] ---> [ UserMapper ]
         |
         v
 [ UserRepository ]
         |
         v
 [ Base de Datos ]

```

---

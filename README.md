# Backend Equinorte - Prueba Técnica

## Descripción

Este proyecto corresponde al desarrollo del backend para la prueba técnica de Equinorte utilizando Spring Boot, JPA y MySQL.

Hasta el momento se ha implementado el módulo de gestión de usuarios siguiendo una arquitectura en capas para mantener una adecuada separación de responsabilidades y facilitar el mantenimiento del código.

---

# Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Lombok
- ModelMapper
- Maven

---

### 📌 data.sql (Spring Boot)
El archivo `data.sql`  permite insertar datos iniciales.

📂 Ubicación:
src/main/resources/data.sql

# Estructura del Proyecto

```text
src/main/java

├── controller
│   ├── UsersController
│   └── FacturaController
│
├── dto
│   ├── UsersDto
│   ├── FacturaDto
│   └── DetalleFacturaDto
│
├── entity
│   ├── Users
│   ├── TipoUsuario
│   ├── Factura
│   └── DetalleFactura
│
├── mapper
│   ├── UsersMapper
│   ├── FacturaMapper
│   └── DetalleFacturaMapper
│
├── repository
│   ├── UsersRepository
│   ├── FacturaRepository
│   └── DetalleFacturaRepository
│
├── service
│   ├── UsersService
│   └── FacturaService
│
└── service/impl
    ├── UsersServiceImpl
    └── FacturaServiceImpl

---

# Entidad Users

Se creó la entidad Users para representar los usuarios del sistema.

Cada usuario posee:

- idUser
- nombre
- email
- tipoUsuario

El campo tipoUsuario permite identificar el rol del usuario dentro de la aplicación.

Tipos disponibles:

```java
OPERADOR
SUPERVISOR
```

Estos roles serán utilizados posteriormente para validar las restricciones de modificación de facturas solicitadas en la prueba.

---

# DTO (UsersDto)

Se implementó un DTO para desacoplar la información expuesta por la API de la entidad persistida en base de datos.

Objetivos:

- Validar datos de entrada.
- Evitar exponer directamente las entidades.
- Mantener una arquitectura más limpia.

Campos:

```java
private Long idUser;
private String nombre;
private String email;
private TipoUsuario tipoUsuario;
```

---

# Repository (UsersRepository)

Se implementó el repositorio utilizando Spring Data JPA.

Responsabilidades:

- Acceso a la base de datos.
- Consultas de usuarios.
- Persistencia de información.

Métodos implementados:

```java
findByIdUser()
findByEmail()
findByTipoUsuario()
findByNombre()
```

---

# Mapper (UsersMapper)

Se implementó ModelMapper para convertir automáticamente entre DTOs y entidades.

Objetivos:

- Reducir código repetitivo.
- Facilitar conversiones entre capas.
- Mantener el Service más limpio.

Métodos:

```java
toEntity()
toDto()
```

Conversión:

```text
UsersDto → Users
Users → UsersDto
```

---

# Service (UsersService)

Se creó una interfaz para definir los contratos del módulo de usuarios.

Operaciones disponibles:

```java
crearUser()
listarUsers()
obtenerUserPorId()
obtenerUserPorEmail()
listarPorTipoUsuario()
actualizarUser()
eliminarUser()
```

Beneficios:

- Separación de responsabilidades.
- Facilita futuras implementaciones.
- Mejora mantenibilidad y escalabilidad.

---

# ServiceImpl (UsersServiceImpl)

Se desarrolló la implementación de la lógica de negocio para usuarios.

Responsabilidades:

- Validar existencia de usuarios.
- Gestionar operaciones CRUD.
- Convertir entidades y DTOs mediante el mapper.
- Interactuar con el repositorio.

Funciones implementadas:

### Crear usuario

Permite registrar nuevos usuarios.

### Listar usuarios

Obtiene todos los usuarios registrados.

### Buscar usuario por ID

Consulta un usuario específico.

### Buscar usuario por Email

Permite localizar usuarios mediante correo electrónico.

### Filtrar por tipo de usuario

Permite obtener usuarios:

```java
OPERADOR
SUPERVISOR
```

### Actualizar usuario

Actualiza información existente.

### Eliminar usuario

Elimina un usuario del sistema.

---

# Controller (UsersController)

Se implementó la capa REST para exponer los servicios mediante endpoints HTTP.

Endpoints disponibles:

## Crear Usuario

```http
POST /api/users/registrar
```

## Listar Usuarios

```http
GET /api/users
```

## Buscar Usuario por ID

```http
GET /api/users/buscar/id/{id}
```

## Buscar Usuario por Email

```http
GET /api/users/buscar/email/{email}
```

## Obtener Usuarios por Tipo

```http
GET /api/users/tipo/{tipoUsuario}
```

Ejemplo:

```http
GET /api/users/tipo/OPERADOR
```

## Actualizar Usuario

```http
PUT /api/users/actualizar/{id}
```

## Eliminar Usuario

```http
DELETE /api/users/{id}
```

---



# Módulo de Facturación

## Entidad Factura

Representa la cabecera de una factura.

Campos principales:

- idFactura
- numeroFactura (único)
- subtotal
- iva
- total
- fechaCreacion
- fechaActualizacion

Relación:

- Una Factura tiene una relación OneToMany con DetalleFactura.

---

## Entidad DetalleFactura

Campos principales:

- idDetalle
- producto
- cantidad
- precioUnitario
- subtotal

Relación:

- Muchos DetalleFactura pertenecen a una Factura.

---

## DTO FacturaDto

- idFactura
- numeroFactura
- lista de detalles

---

## DTO DetalleFacturaDto

- idDetalle
- producto
- cantidad
- precioUnitario

---

## Reglas de Negocio

- Una factura tiene múltiples detalles.
- El número de factura es único.
- El cálculo de subtotal, IVA y total está definido en la entidad Factura.
- La persistencia de detalles se realiza en cascada desde Factura.
- No existe relación con Users.

---

## Repositories

### FacturaRepository
- findByNumeroFactura()
- existsByNumeroFactura()

### DetalleFacturaRepository
- findByFacturaIdFactura()

---

## Estado del Proyecto

✔ Usuarios implementado  
✔ Facturación implementado  
✔ Relación Factura → DetalleFactura  
✔ Arquitectura en capas  


## Estructura del módulo Factura

### Endpoints disponibles

#### Crear factura

-   **POST** `/api/facturas/crear`

#### Listar facturas

-   **GET** `/api/facturas`

#### Obtener factura por ID

-   **GET** `/api/facturas/buscar/{id}`

#### Actualizar factura

-   **PUT** `/api/facturas/actualizar/{id}`

#### Recalcular factura

-   **PUT** `/api/facturas/recalcular/{id}`

#### Eliminar factura

-   **DELETE** `/api/facturas/{id}`


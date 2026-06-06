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

### data.sql (Spring Boot)
El archivo `data.sql`  permite insertar datos iniciales.

Ubicación:
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



##  Factura

Representa la cabecera de la factura.

### Campos

| Campo | Tipo | Descripción |
|------|------|------|
| idFactura | Long | Identificador único |
| numeroFactura | String | Número único de factura |
| subtotal | BigDecimal | Suma de los detalles |
| iva | BigDecimal | IVA calculado (19%) |
| total | BigDecimal | Total final |
| fechaCreacion | LocalDateTime | Fecha automática de creación |
| fechaActualizacion | LocalDateTime | Fecha automática de actualización |

### Relaciones

- Una factura tiene múltiples `DetalleFactura`
- Relación `OneToMany`
- `CascadeType.ALL`
- `orphanRemoval = true`

---

##  DetalleFactura

Representa cada producto asociado a una factura.

### Campos

| Campo | Tipo | Descripción |
|------|------|------|
| idDetalle | Long | Identificador único |
| producto | String | Nombre del producto |
| cantidad | Integer | Cantidad del producto |
| precioUnitario | BigDecimal | Precio unitario |
| subtotal | BigDecimal | Subtotal calculado |

### Relaciones

- Muchos detalles pertenecen a una factura
- Relación `ManyToOne`

---

#  DTOs

##  FacturaDto

```java
Long idFactura;
String numeroFactura;
List<DetalleFacturaDto> detalles;
```

##  DetalleFacturaDto

```java
Long idDetalle;
String producto;
Integer cantidad;
BigDecimal precioUnitario;
```

##  RecalculoFacturaDto

DTO utilizado para modificar el subtotal con validación de roles.

```java
BigDecimal nuevoSubtotal;
TipoUsuario tipoUsuario;
```

---

#  Repositorios

##  FacturaRepository

Métodos implementados:

```java
findByNumeroFactura()
existsByNumeroFactura()
```

##  DetalleFacturaRepository

Métodos implementados:

```java
findByFacturaIdFactura()
```

---

#  Service

##  FacturaService

Define las operaciones principales del módulo:

```java
crearFactura()
actualizarFactura()
eliminarFactura()
obtenerPorId()
listarFacturas()
recalcularFactura()
```

---

# Lógica de Negocio

##  Crear Factura

### Validaciones

- El número de factura debe ser único.

### Proceso

1. Calcula el subtotal de cada detalle:

```java
subtotal = precioUnitario * cantidad
```

2. Calcula:
   - Subtotal general
   - IVA (19%)
   - Total final

3. Guarda la factura junto con sus detalles usando cascada.

---

##  Actualizar Factura

### Proceso

1. Busca la factura por ID.
2. Elimina detalles anteriores.
3. Agrega los nuevos detalles.
4. Recalcula:
   - Subtotal
   - IVA
   - Total

---

##  Eliminar Factura

### Proceso

- Valida existencia.
- Elimina factura y detalles asociados.

---

##  Obtener Factura

### Proceso

- Busca factura por ID.
- Lanza excepción si no existe.

---

##  Listar Facturas

### Proceso

- Retorna todas las facturas registradas.

---

#  Recalcular Factura

Permite modificar el subtotal aplicando reglas de negocio según el tipo de usuario.

##  Reglas por Rol

| Tipo Usuario | Incremento Máximo Permitido |
|------|------|
| OPERADOR | 20.000 |
| SUPERVISOR | 50.000 |

##  Proceso de Recalculo

1. Calcula la diferencia entre subtotales.
2. Valida permisos según el rol.
3. Calcula el factor proporcional:

```java
factor = nuevoSubtotal / subtotalActual
```

4. Ajusta proporcionalmente cada detalle.
5. Recalcula:
   - Subtotal
   - IVA (19%)
   - Total final

---

# API REST - Facturas

## Base URL

```http
/api/facturas
```

## Crear Factura

```http
POST /api/facturas/crear
```

## Listar Facturas

```http
GET /api/facturas
```

## Obtener Factura por ID

```http
GET /api/facturas/buscar/{id}
```

## Actualizar Factura

```http
PUT /api/facturas/actualizar/{id}
```

## Recalcular Factura

```http
PUT /api/facturas/recalcular/{id}
```

##  Eliminar Factura

```http
DELETE /api/facturas/{id}
```

---


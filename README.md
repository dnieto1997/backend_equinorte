# Backend Equinorte - Prueba Técnica

## Descripción

Este proyecto corresponde al desarrollo del backend para la prueba técnica de Equinorte utilizando **Spring Boot**, **Spring Data JPA** y **MySQL**.

Se implementó un sistema de gestión de usuarios y facturación siguiendo una arquitectura en capas (**Controller, Service, Repository, DTO y Mapper**) con el objetivo de garantizar escalabilidad, mantenibilidad y una correcta separación de responsabilidades.

---

# Tecnologías Utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL 8+
* Lombok
* ModelMapper
* Maven

---

# Base de Datos

El proyecto utiliza **MySQL** como motor de base de datos.

## Creación de la Base de Datos

Antes de ejecutar la aplicación, se debe crear una base de datos en MySQL. Puede utilizar el siguiente script como ejemplo:

```sql
CREATE DATABASE equinorte;
```

> **Importante:** Si decide utilizar un nombre diferente para la base de datos, deberá actualizar la propiedad `spring.datasource.url` en el archivo `application.properties` para que coincida con el nombre configurado que esta en la carpeta src/main/resources/application.properties


# Configuración del Proyecto



Editar el archivo:

```text
src/main/resources/application.properties
```

Configurar los datos de conexión según la base de datos creada en el entorno local:

spring.datasource.url=jdbc:mysql://localhost:3306/equinorte
spring.datasource.username=root
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

Importante:

Reemplazar equinorte por el nombre de la base de datos creada, en caso de utilizar uno diferente.
Verificar que el puerto configurado en la URL corresponda al puerto donde se encuentra ejecutándose MySQL (por defecto 3306).
Actualizar username y password con las credenciales configuradas en el servidor MySQL local.

---

# Requisitos

* Java 21
* Maven 3+
* MySQL 8+

---

# Ejecución del Proyecto

## Ejecutar en modo desarrollo

```bash
mvn spring-boot:run
```


---

## Configuración de CORS

El proyecto incluye una configuración CORS ubicada en:

```text
src/main/java/com/equinorte/backend_equinorte/config/CorsConfig.java
```

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

> **Importante:** Si el frontend se ejecuta en una URL o puerto diferente al configurado (`http://localhost:4200`), deberá actualizar el valor de `allowedOrigins()` para permitir las peticiones desde el nuevo origen.


# Generación Automática de Tablas

Las tablas se generan automáticamente mediante JPA al iniciar la aplicación gracias a la siguiente configuración:

```properties
spring.jpa.hibernate.ddl-auto=update
```

No es necesario crear manualmente la estructura de la base de datos.

---

# Datos Iniciales

El proyecto incluye el archivo:

```text
src/main/resources/data.sql
```

Importante: El archivo data.sql contiene sentencias INSERT con datos iniciales necesarios para las pruebas del sistema. Es necesario ejecutar este archivo en la base de datos para cargar los registros de ejemplo y garantizar el correcto funcionamiento de las funcionalidades implementadas.

---

# Arquitectura del Proyecto

```text
src/main/java

├── controller
├── dto
├── entity
├── mapper
├── repository
├── service
└── service/impl
```

### Descripción de Capas

| Capa       | Responsabilidad                   |
| ---------- | --------------------------------- |
| Controller | Exposición de endpoints REST      |
| Service    | Lógica de negocio                 |
| Repository | Acceso a datos mediante JPA       |
| DTO        | Transferencia de datos            |
| Mapper     | Conversión entre entidades y DTOs |
| Entity     | Modelado de la base de datos      |

---

# Módulo de Usuarios

## Entidad Users

### Campos

* idUser
* nombre
* email
* tipoUsuario

### Tipos de Usuario

```text
OPERADOR
SUPERVISOR
```

## Funcionalidades

* Crear usuario
* Listar usuarios
* Buscar usuario por ID
* Buscar usuario por email
* Filtrar usuarios por tipo
* Actualizar usuario
* Eliminar usuario

---

# Módulo de Facturación

## Factura

Representa la cabecera de la factura.

### Campos

* idFactura
* numeroFactura
* subtotal
* iva
* total
* fechaCreacion
* fechaActualizacion

### Relación

```text
Factura (1) ------ (N) DetalleFactura
```

Una factura puede contener múltiples detalles.

---

## DetalleFactura

### Campos

* idDetalle
* producto
* cantidad
* precioUnitario
* subtotal

### Relación

```text
DetalleFactura (N) ------ (1) Factura
```

---

# Lógica de Negocio

## Creación de Facturas

Para cada producto:

```text
subtotalDetalle = cantidad × precioUnitario
```

Posteriormente se calcula:

```text
Subtotal General
IVA (19%)
Total Factura
```

### Fórmulas

```text
IVA = subtotal × 0.19

TOTAL = subtotal + IVA
```

---

## Recalcular Factura

Permite modificar el subtotal de una factura redistribuyendo proporcionalmente el valor entre todos los detalles asociados.

### Restricciones por Tipo de Usuario

| Tipo Usuario | Incremento Máximo |
| ------------ | ----------------- |
| OPERADOR     | $20.000           |
| SUPERVISOR   | $50.000           |

### Proceso

1. Obtener subtotal actual.
2. Validar incremento permitido según el tipo de usuario.
3. Calcular diferencia.
4. Obtener factor proporcional:

```text
factor = nuevoSubtotal / subtotalActual
```

5. Ajustar subtotales de los detalles.
6. Recalcular subtotal general.
7. Recalcular IVA.
8. Recalcular total de la factura.

---

# API REST

## Base URL

```text
http://localhost:8080/api
```

---

# Endpoints de Usuarios

## Crear Usuario

```http
POST /users/registrar
```

### Body de Ejemplo

```json
{
  "nombre": "Dairo",
  "email": "operador@gmail.com",
  "tipoUsuario": "OPERADOR"
}
```

> **Nota:** El campo `tipoUsuario` únicamente admite los valores `OPERADOR` y `SUPERVISOR`.


## Listar Usuarios

```http
GET /users
```

## Buscar por ID

```http
GET /users/buscar/id/{id}
```

## Buscar por Email

```http
GET /users/buscar/email/{email}
```

## Filtrar por Tipo de Usuario

```http
GET /users/tipo/{tipoUsuario}
```

> **Nota:** El campo `tipoUsuario` únicamente admite los valores `OPERADOR` y `SUPERVISOR`.

## Actualizar Usuario

```http id="2"
PUT /users/actualizar/{id}
```

### Parámetros

| Parámetro | Descripción                                       |
| --------- | ------------------------------------------------- |
| id        | Identificador del usuario que se desea actualizar |

### Body de Ejemplo

```json id="2"
{
  "nombre": "Dairo",
  "email": "dnieto6198@gmail.com",
  "tipoUsuario": "OPERADOR"
}
```

> **Nota:** El parámetro `{id}` corresponde al identificador del usuario que será actualizado. El campo `tipoUsuario` únicamente admite los valores `OPERADOR` y `SUPERVISOR`.


## Eliminar Usuario

```http
DELETE /users/{id}
```

---

# Endpoints de Facturación

## Crear Factura

```http
POST /facturas/crear
```

### Body de Ejemplo

```json
{
  "numeroFactura": "FAC-0001",
  "detalles": [
    {
      "producto": "Alquiler de excavadora hidráulica 320D",
      "cantidad": 1,
      "precioUnitario": 850000
    }
  ]
}
```

> El sistema calcula automáticamente el subtotal, IVA (19%) y total de la factura.


## Listar Facturas

```http
GET /facturas
```

## Buscar Factura por ID

```http
GET /facturas/buscar/{id}
```

## Actualizar Factura

```http
PUT /facturas/actualizar/{id}
```

### Body de Ejemplo

```json
{
  "detalles": [
    {
      "producto": "Impresora multifuncional Epson",
      "cantidad": 1,
      "precioUnitario": 950000
    }
  ]
}
```


## Recalcular Factura

```http id="d8r6km"
PUT /facturas/recalcular/{id}
```

### Parámetros

| Parámetro | Descripción                                         |
| --------- | --------------------------------------------------- |
| id        | Identificador de la factura que se desea recalcular |

### Body de Ejemplo

```json id="10"
{
  "nuevoSubtotal": 20000,
  "tipoUsuario": "SUPERVISOR"
}
```

> El parámetro `{id}` corresponde al identificador de la factura que será recalculada.


## Eliminar Factura

```http
DELETE /facturas/{id}
```

---

# Características Implementadas

 Arquitectura en capas

 DTOs para intercambio de información

 Mapeo de entidades mediante ModelMapper

 Persistencia con Spring Data JPA

 Relación OneToMany / ManyToOne

 Generación automática de tablas

 Datos iniciales mediante data.sql

 Validación de reglas de negocio

 Cálculo automático de IVA y total

 Recalculo proporcional de facturas según rol de usuario

---

# Autor

Dairo Nieto

Prueba Técnica Backend Equinorte

Desarrollado con Spring Boot, JPA y MySQL.

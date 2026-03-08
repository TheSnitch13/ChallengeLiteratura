# 📚 Challenge Literatura
Aplicación de consola desarrollada en **Java con Spring Boot** que permite buscar libros utilizando la **API de Gutendex**, almacenar la información en una **base de datos PostgreSQL** y consultar distintos datos literarios desde un menú interactivo.


# 🚀 Funcionalidades
La aplicación permite realizar las siguientes operaciones desde un menú en consola:

1️⃣ **Buscar libro por título**

* Consulta la API de Gutendex.
* Guarda el libro y su autor en la base de datos si no existen.

2️⃣ **Listar libros registrados**

* Muestra todos los libros almacenados en la base de datos.

3️⃣ **Listar autores registrados**

* Presenta todos los autores guardados junto con sus libros.

4️⃣ **Listar autores vivos en un determinado año**

* Permite ingresar un año y muestra los autores que estaban vivos en ese periodo.

5️⃣ **Listar libros por idioma**

* Filtra los libros registrados según el idioma.

0️⃣ **Salir del programa**


# 🛠️ Tecnologías utilizadas
* **Java 17**
* **Spring Boot**
* **Spring Data JPA**
* **PostgreSQL**
* **Hibernate**
* **Jackson (JSON Mapping)**
* **Maven**
* **Git / GitHub**


# 🌐 API utilizada
El proyecto utiliza la API pública:

**Gutendex**

[https://gutendex.com/](https://gutendex.com/)

Esta API proporciona información de libros del catálogo de **Project Gutenberg**, incluyendo:

* Título
* Autor
* Idioma
* Número de descargas


# 🗄️ Base de datos
El proyecto utiliza **PostgreSQL** para almacenar:

* Libros
* Autores
* Relación entre autores y libros

---

# ⚙️ Configuración del proyecto
### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/TheSnitch13/ChallengeLiteratura.git
```
---

### 2️⃣ Crear base de datos en PostgreSQL
Ejemplo:

```sql
CREATE DATABASE literatura;
```
---

### 3️⃣ Configurar `application.properties`
Ubicado en:

```
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost/literatura
spring.datasource.username=postgres
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
---

### 4️⃣ Ejecutar la aplicación
Puedes ejecutar el proyecto desde tu IDE (IntelliJ o Eclipse) ejecutando la clase:

```
ChallengerLiteraturaApplication
```

o desde terminal:

```bash
mvn spring-boot:run
```
---

# 📂 Estructura del proyecto
```
src
 └─ main
     └─ java
         └─ com.snitch.ChallengerLiteratura
             ├─ principal
             │   └─ Principal.java
             │
             ├─ model
             │   ├─ Libro.java
             │   ├─ Autor.java
             │   ├─ DatosLibros.java
             │   └─ DatosAutor.java
             │
             ├─ repository
             │   ├─ LibrosRepository.java
             │   └─ AutorRepository.java
             │
             └─ service
                 ├─ ConsumoAPI.java
                 └─ ConvierteDatos.java
```
---

# 💡 Aprendizajes del proyecto
Este proyecto permitió practicar:

* Consumo de APIs REST
* Conversión de JSON a objetos Java
* Persistencia de datos con **JPA / Hibernate**
* Relaciones entre entidades
* Consultas personalizadas con **JPQL**
* Manejo de errores y validaciones
* Uso de **Streams y Optional**

---
# 👨‍💻 Autor
**Alan Herrera**

GitHub:
[https://github.com/TheSnitch13](https://github.com/TheSnitch13)

---

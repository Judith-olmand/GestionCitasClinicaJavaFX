# 🏥 Sistema de Gestión de Citas Clínicas

Aplicación de escritorio desarrollada en Java con JavaFX que implementa un sistema integral para la gestión de citas médicas de pacientes, conectividad a base de datos relacional y un sistema de autenticación seguro.

> ⚠️ **INFORMACIÓN IMPORTANTE PARA PRUEBAS:**
> Para acceder al sistema y realizar pruebas, utiliza las siguientes credenciales:
> * **Usuarios (ID):** `1`, `3`, `4` y `5` ➔ **Contraseña:** `1234`
> * **Usuario (ID):** `2` ➔ **Contraseña:** `9876`
>
>

## 📋 Descripción

Este es un software de gestión clínica donde los pacientes pueden iniciar sesión en su portal personal para visualizar sus datos, consultar su historial de citas y gestionar (crear, modificar o borrar) sus próximas visitas médicas en diferentes especialidades, todo respaldado por una base de datos MySQL y seguridad criptográfica avanzada.

## 🌟 Características Principales

* 🎨 **Interfaz Gráfica (GUI)** intuitiva desarrollada con JavaFX (FXML) y estilizada con CSS.
* 🔒 **Seguridad Avanzada** utilizando el algoritmo de hashing `Argon2` para las contraseñas.
* 💾 **Persistencia de Datos** mediante conexión JDBC a base de datos MySQL.
* 📅 **CRUD Completo** (Crear, Leer, Actualizar, Borrar) para el control de citas médicas.
* ⏱️ **Formateo Dinámico de Fechas** en la tabla de registros, implementado con una solución proporcionada por Gemini.
* 🏗️ **Arquitectura Robusta** basada en el patrón de diseño MVC (Modelo-Vista-Controlador) y DAO (Data Access Object).
* 🛡️ **Validación de Datos** (ej. prevención de fechas pasadas) y alertas interactivas.

## 🏗️ Estructura del Proyecto

```text
GestionCitasClinica/
│
├── Clases/                       # Paquete de modelos de datos (POJOs)
│   ├── Cita.java                 # Entidad para las citas médicas
│   ├── Cliente.java              # Entidad para los pacientes/usuarios
│   └── Especialidad.java         # Entidad para las áreas médicas
│
├── Controller/                   # Controladores de la interfaz gráfica (MVC)
│   ├── LoginController.java      # Gestión de autenticación y paso de datos
│   └── MainController.java       # Gestión del panel principal y CRUD de citas
│
├── DAO/                          # Paquete de acceso a datos
│   ├── CitaDAO.java              # Consultas SQL para Citas
│   ├── ClienteDAO.java           # Consultas SQL para Clientes
│   ├── EspecialidadDAO.java      # Consultas SQL para Especialidades
│   ├── Conexion.java             # Gestor de conexión JDBC
│   └── DBConfig.java             # Lector de credenciales de DB
│
├── Seguridad/                    # Módulo de criptografía
│   └── PasswordService.java      # Generación y verificación de hashes Argon2
│
├── util/                         # Utilidades generales de la app
│   ├── AlertUtils.java           # Generador de ventanas emergentes (Alerts)
│   └── R.java                    # Gestor de recursos (imágenes, properties, UI)
│
├── com.example.../               # Arranque de la aplicación
│   ├── HelloApplication.java     # Carga de la primera escena JavaFX
│   └── Launcher.java             # Clase main principal
│
├── resources/
│   ├── BasesDatos/                   # Scripts SQL
│   │   └── CreacionBBDD.sql
│   │
│   ├── com.example.gestioncitasclinica/ # Vistas vinculadas a los controladores
│   │   ├── login-view.fxml
│   │   └── main-view.fxml
│   │
│   ├── configuration/                # Credenciales DB
│   │   └── db.properties
│   │
│   └── CSS/                          # Hojas de estilo
│       └── estilos.css
│
├── pom.xml                           # Configuración de dependencias Maven
└── README.md                         # Documentación

```

## 🗄️ Base de Datos y Entidades

El sistema funciona sobre una base de datos relacional (MySQL) estructurada en 3 tablas principales:

### 👤 Cliente (Paciente)

* Almacena los datos personales: ID, DNI, Nombre, Dirección, Teléfono, Email.
* Contiene la contraseña hasheada y con *salt* (`Argon2`).

### 🩺 Especialidad

* Catálogo de áreas médicas disponibles:
* 🧬 Medicina general
* 👶 Pediatría
* 🦴 Traumatología
* 🧴 Dermatología
* ❤️ Cardiología



### 📅 Cita

* Relaciona al Cliente con una Especialidad en una Fecha concreta.
* Generación de `NumeroCita` (ID) automático al solicitar una nueva visita.

## 🎮 Mecánicas de Uso

### Sistema de Autenticación

1. El usuario introduce su `ID` (numérico) y su `Contraseña`.
2. El sistema recupera el hash de la base de datos mediante `ClienteDAO`.
3. `PasswordService` verifica la contraseña usando BouncyCastle (Argon2).
4. Si es correcto, el ID se transfiere al `MainController` para inicializar el panel del paciente.

### Gestión de Citas (CRUD)

* **Ver Citas**: Carga en una `TableView` el historial del paciente logueado. Las fechas se visualizan en un formato limpio (`DD-MM-YYYY`) gracias a una adaptación de código ofrecida por Gemini.
* **Nueva Cita**: Requiere seleccionar una especialidad del `ComboBox` y una fecha del `DatePicker`. Valida que la fecha sea futura.
* **Modificar Cita**: Seleccionando una cita de la tabla, permite alterar la especialidad o la fecha.
* **Borrar Cita**: Elimina el registro seleccionado tras una confirmación de seguridad.

## 🚀 Ejecución y Configuración

### Requisitos

* Java JDK 17 o superior (recomendado para JavaFX reciente).
* JavaFX SDK configurado en el proyecto.
* Servidor MySQL ejecutándose.
* Librería externa: `BouncyCastle` (para Argon2).

### Configuración de Base de Datos

1. Ejecuta el script SQL proporcionado en tu gestor MySQL para crear la base de datos `CLINICA` y poblar los datos de prueba.
2. Asegúrate de que el archivo `db.properties` en la ruta `src/main/resources/configuration/` tenga las credenciales correctas de tu servidor local:
```properties
host=localhost
port=3306
name=Clinica
username=root
password=xxxx

```



## 📊 Flujo del Programa

```text
┌─────────────────────┐
│   Pantalla Login    │
│  • ID de Usuario    │
│  • Contraseña       │
└──────────┬──────────┘
           │ (Verificación Argon2)
           ▼
┌─────────────────────────┐
│     Panel Principal     │
│  (Carga datos paciente) │
└──────────┬──────────────┘
           │
     ┌─────┼─────────┬─────────┐
     ▼     ▼         ▼         ▼
┌──────┐ ┌───────┐ ┌────────┐ ┌───────┐
│ Ver  │ │ Crear │ │ Modif. │ │Borrar │
│ Citas│ │ Cita  │ │ Cita   │ │Cita   │
└──────┘ └───────┘ └────────┘ └───────┘
     │     │         │         │
     └─────┴─────────┴─────────┘
           │ (Actualización UI)
           ▼
┌─────────────────────────┐
│ TableView Sincronizada  │
└─────────────────────────┘

```

## 🎓 Conceptos de Programación Aplicados

### Arquitectura y Patrones

* 🏗️ **MVC (Model-View-Controller)**: Separación clara entre la vista (FXML + CSS), la lógica de negocio (Controller) y los datos (Clases/DAO).
* 📦 **Patrón DAO**: Abstracción y encapsulamiento de todas las consultas SQL, aislando la lógica de la base de datos del resto de la aplicación.
* 🔑 **Seguridad Criptográfica**: Implementación de `Argon2id` (el estándar recomendado actual) para el almacenamiento seguro de contraseñas, protegiendo contra ataques de fuerza bruta y diccionarios.

### JavaFX y UI

* 🪟 Inyección de dependencias con `@FXML`.
* 🔄 Uso de `ObservableList` y `PropertyValueFactory` para la actualización dinámica de tablas.
* ⚙️ **Formateo Visual de Celdas**: Uso de `setCellFactory` (código generado por Gemini) para aplicar un formato amigable y personalizado (`dd-MM-yyyy`) a los objetos fecha dentro de la tabla.
* 🚦 Gestión de eventos (`ActionEvent`, `MouseEvent`).
* 🔔 Modularización de notificaciones interactivas a través de `AlertUtils`.

## ⚠️ Manejo de Errores

El sistema cuenta con prevención y notificaciones visuales (`Alerts`) para:

* ⛔ Campos vacíos en el Login.
* ⛔ Formato de ID incorrecto (`NumberFormatException`).
* ⛔ Credenciales inválidas.
* ⛔ Intento de crear/modificar citas en fechas pasadas.
* ⛔ Acciones sin haber seleccionado un elemento en la tabla.
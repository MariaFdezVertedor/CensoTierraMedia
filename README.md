# Proyecto de Censo de la Tierra Media

Este proyecto es una aplicación de censo para la Tierra Media, desarrollada utilizando Java y Kotlin, con Gradle como sistema de construcción y SQLite para la gestión de la base de datos.

## Tabla de Contenidos

- Introducción
- Características
- Tecnologías Utilizadas
- Instalación
- Uso
- Estructura del Proyecto
- Contribuciones
- Licencia

## Introducción

La aplicación permite gestionar un censo de los habitantes de la Tierra Media, proporcionando operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre una base de datos de SQLite. Los datos de los habitantes incluyen información como nombre, apellidos, edad, raza, ubicación y profesión.

## Características

- Crear: Añadir nuevos habitantes al censo.
- Leer: Consultar la información de los habitantes.
- Actualizar: Modificar la información de los habitantes existentes.
- Eliminar: Borrar registros de habitantes del censo.
- Listar por raza: Obtener un listado de habitantes filtrados por raza.
- Listar por profesión: Obtener un listado de habitantes filtrados por profesión.

## Tecnologías Utilizadas

- Lenguajes: Java, Kotlin
- Base de Datos: SQLite
- Sistema de Construcción: Gradle
- Android SDK: Para el desarrollo de la aplicación móvil

## Instalación

1. Clona el repositorio:
   git clone https://github.com/tu-usuario/tu-repositorio.git
2. Abre el proyecto en Android Studio.
3. Sincroniza el proyecto con Gradle.
4. Ejecuta la aplicación en un emulador o dispositivo Android.

## Uso

Una vez instalada la aplicación, puedes realizar las siguientes operaciones:

- Añadir un nuevo habitante: Completa el formulario de registro y guarda la información.
-Consultar habitantes: Navega por la lista de habitantes para ver sus detalles.
- Actualizar información: Selecciona un habitante y actualiza su información.
- Eliminar habitante: Elimina un registro del censo.
- Listar por raza o profesión: Usa las opciones de filtrado para ver habitantes específicos.


## Estructura del Proyecto

├── app
│   ├── java/com/elsda/tierramedia_pac
│   │   ├── database
│   │   │   └── HabitantesSQLite.kt
│   │   ├── habitantes
│   │   │   └── HabitanteTierraMedia.kt
│   │   ├── ui
│   │   │   └── MainActivity.kt
│   ├── res
│   │   ├── layout
│   │   └── values
│   └── AndroidManifest.xml
├── build.gradle
└── settings.gradle

HabitantesSQLite.kt: Implementación de las operaciones CRUD y consultas adicionales.
HabitanteTierraMedia.kt: Clase de modelo que representa a un habitante de la Tierra Media.
MainActivity.kt: Actividad principal de la aplicación.
layout: Archivos XML para las interfaces de usuario.
values: Archivos de recursos como strings y estilos.

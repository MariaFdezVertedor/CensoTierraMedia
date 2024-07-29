package bbdd

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.elsda.tierramedia_pac.habitantes.HabitanteTierraMedia

/**
 * Clase HabitantesSQLite para crear el CRUD de la Base de Datos
 *
 * Vamos a recordar que es un CRUD:
 * - C de Create => Crear registro / insertar registro en la BBDD
 * - R de Read => leer registro de la BBDD
 * - U de Update => actualizar un registro en la BBDD
 * - D de Delete => borrar un registro en la BBDD
 */
class HabitantesSQLite(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Nombre y versión de la Base de Datos
    companion object {
        private const val DATABASE_NAME = "tierraMedia.db"
        private const val DATABASE_VERSION = 1
    }

    // Método crear => establece las esctructuras de tablas de la Base de Datos
    override fun onCreate(db: SQLiteDatabase?) {
        // Creamos la tabla habitantes
        val crearTablaHabitantes = """
            CREATE TABLE habitantes(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellidos TEXT NOT NULL,
                edad INTEGER NOT NULL,
                raza TEXT NOT NULL,
                ubicacion TEXT NOT NULL,
                profesion TEXT NOT NULL
            )
        """.trimIndent()

        // Creamos la tabla de habitantes
        db?.execSQL(crearTablaHabitantes)

    }

    /**
     * Método para actualizar la versión de la BBDD
     */
    override fun onUpgrade(db: SQLiteDatabase?, viejaVersion: Int, nuevaVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS habitantes")
        onCreate(db)
    }

    /**
     * Inserta un habitante en la BBDD
     * @param habitante datos del nuevo habitante
     * @return el identificador del habitante
     */
    fun insertarHabitante(habitante: HabitanteTierraMedia): Long {
        // TODO completar el método insertar habitante

        // Acceso a la base de datos en modo escritura
        val db = writableDatabase

        // Creación de un objeto ContentValues para almacenar los valores de un nuevo habitante
        val values = ContentValues()
        values.put("nombre", habitante.nombre) // Inserción del nombre del habitante en el ContentValues
        values.put("apellidos", habitante.apellidos) // Inserción de los apellidos del habitante
        values.put("edad", habitante.edad) // Inserción de la edad del habitante
        values.put("raza", habitante.raza) // Inserción de la raza del habitante
        values.put("ubicacion", habitante.ubicacion) // Inserción de la ubicación del habitante
        values.put("profesion", habitante.profesion) // Inserción de la profesión del habitante

        // Inserción de los valores en la tabla "habitantes" de la base de datos
        val id = db.insert("habitantes", null, values)

        // Cerrar la conexión a la base de datos
        db.close()

        // Devolver el ID del nuevo habitante insertado en la base de datos
        return id
    }

    /**
     * Actualiza los datos de un Habitante
     * @param idHabitante el identificador del habitante
     * @param habitante los datos del habitante
     */
    fun actualizarHabitante(idHabitante: Int, habitante: HabitanteTierraMedia) {
        val db = writableDatabase

        //Creamos un objeto ContentValues para almacenar los nuevos valores del habitante
        val values = ContentValues().apply {
            put("nombre", habitante.nombre)
            put("apellidos", habitante.apellidos)
            put("edad", habitante.edad)
            put("raza", habitante.raza)
            put("ubicacion", habitante.ubicacion)
            put("profesion", habitante.profesion)
        }

        // Actualizar el registro del habitante en la tabla "habitantes" usando los nuevos valores
        // y especificando el ID del habitante que se desea actualizar
        db.update("habitantes", values, "id = ?", arrayOf(idHabitante.toString()))

        // Cerramos conexión con la base de datos
        db.close()
    }

    /**
     * Borra un habitante de la BBDD
     * @param idHabitante el identificador del habitante
     */
    fun borrarHabitante(idHabitante: Int): Int {
        // TODO completar el método borrar habitante

        // Acceso a la base de datos en modo escritura
        val db = writableDatabase

        // Eliminación del habitante con el ID proporcionado
        val affectedRows = db.delete("habitantes", "id = ?", arrayOf(idHabitante.toString()))

        // Cerrar la conexión a la base de datos
        db.close()

        // Devolver el número de filas afectadas por la eliminación (debería ser 1 si se eliminó correctamente)
        return affectedRows
    }

    /**
     * Obtenemos el número total de habitantes de la Tierra Media
     * @return el número total de habitantes de la Tierra Media
     */
    fun getNumeroHabitantes(): Int {
        val db = readableDatabase // Accedemos a la BBDD en sólo lectura
        val consulta = "SELECT count(*) as numHabitantes FROM habitantes"
        val cursor = db.rawQuery(consulta, null)

        // Accedemos a los datos de la consulta
        cursor.use {
            if (it.moveToFirst())
                return it.getInt(it.getColumnIndexOrThrow("numHabitantes"))
        }

        // Si no obtenemos el número de habitantes => devuelve -1
        return -1
    }

    /**
     * Obtenemos el número de habitantes de una raza de la Tierra Media
     * @param raza la raza que se quiere revisar
     * @return el número de habitantes de una raza de la Tierra Media
     */
    fun getNumeroHabitantesPorRaza(raza: String): Int {
        // TODO crear el código para obtener el número de habitantes por raza

        // Inicializar el contador del número de habitantes
        var numeroHabitantes = 0

        // Acceso a la base de datos en modo lectura
        val db = readableDatabase

        // Consulta SQL para obtener el número de habitantes de una raza específica
        val consulta = "SELECT count(*) as numeroHabitantes FROM habitantes WHERE raza=?"

        // Ejecución de la consulta con un parámetro de selección
        val cursor = db.rawQuery(consulta, arrayOf(raza))

        // Comprobar si hay resultados en el cursor
        if (cursor.moveToFirst()) {
            // Obtener el número de habitantes de la columna "numeroHabitantes" del cursor
            numeroHabitantes = cursor.getInt(cursor.getColumnIndexOrThrow("numeroHabitantes"))
        }

        // Cerrar el cursor para liberar recursos
        cursor.close()

        // Cerrar la conexión a la base de datos
        db.close()

        // Devolver el número total de habitantes de la raza especificada
        return numeroHabitantes
    }

    /**
     * Método para listar los habitantes por raza
     * @param raza la raza escogida para listar
     * @return listado de habitantes por raza
     */
    fun getListadoPorRaza(raza: String): List<HabitanteTierraMedia> {
        val db = readableDatabase // Accedemos a la BBDD en modo lectura
        val listaHabitantes = mutableListOf<HabitanteTierraMedia>()

        // Recorremos los valores de la consulta
        try {
            val consulta = "SELECT * FROM habitantes WHERE raza=? ORDER BY id DESC"
            db.rawQuery(consulta, arrayOf(raza)).use { cursor ->
                // Recorremos los valores de la consulta
                if (cursor.moveToFirst()) {
                    do {
                        // Cogemos los valores de un habitante
                        val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                        val apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos"))
                        val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                        val razaObtenida = cursor.getString(cursor.getColumnIndexOrThrow("raza"))
                        val ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"))
                        val profesion = cursor.getString(cursor.getColumnIndexOrThrow("profesion"))
                        // Creamos el objeto habitante con los datos y lo añadimos a la lista
                        val habitante = HabitanteTierraMedia(
                            nombre,
                            apellidos,
                            edad,
                            razaObtenida,
                            ubicacion,
                            profesion
                        )
                        listaHabitantes.add(habitante)
                    } while (cursor.moveToNext())
                }
            }
        } catch (e: Exception) {
            // Lanzar una RuntimeException para no requerir manejo explícito
            throw RuntimeException("Error al obtener el listado por raza: ${e.message}", e)
        } finally {
            // Cerramos la BBDD aquí si no usamos el bloque use{}
            db.close()
        }
        return listaHabitantes
    }

    /**
     * Método para listar los habitantes por profesión
     * @param profesion la raza escogida para listar
     * @return listado de habitantes por profesión
     */
    fun getListadoPorProfesion(profesion: String): List<HabitanteTierraMedia> {
        //TODO crear el listado de habitantes por profesión

        // Acceso a la base de datos en modo lectura
        val db = readableDatabase

        // Lista mutable para almacenar los habitantes obtenidos de la base de datos
        val listaHabitantes = mutableListOf<HabitanteTierraMedia>()

        try {
            // Consulta SQL para seleccionar habitantes por su profesión y ordenarlos por ID de forma descendente
            val consulta = "SELECT * FROM habitantes WHERE profesion=? ORDER BY id DESC"
            // Ejecución de la consulta con un parámetro de selección
            db.rawQuery(consulta, arrayOf(profesion)).use { cursor ->

                // Comprobar si hay resultados en el cursor
                if (cursor.moveToFirst()) {
                    // Iterar sobre los resultados del cursor
                    do {
                        // Obtener los valores de las columnas del cursor para cada habitante
                        val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                        val apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos"))
                        val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                        val razaObtenida = cursor.getString(cursor.getColumnIndexOrThrow("raza"))
                        val ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"))
                        val profesion = cursor.getString(cursor.getColumnIndexOrThrow("profesion"))

                        // Crear un objeto HabitanteTierraMedia con los valores obtenidos y agregarlo a la lista
                        val habitante = HabitanteTierraMedia(
                            nombre,
                            apellidos,
                            edad,
                            razaObtenida,
                            ubicacion,
                            profesion
                        )
                        listaHabitantes.add(habitante)
                    } while (cursor.moveToNext()) // Mover al siguiente registro del cursor
                }
            }
        } catch (e: Exception) {
            // Captura de excepciones y lanzamiento de una nueva excepción con un mensaje detallado
            throw RuntimeException("Error al obtener el listado por profesion: ${e.message}", e)
        } finally {
            // Cerrar la conexión a la base de datos, independientemente de si se produjo una excepción o no
            db.close()
        }

        // Devolver la lista de habitantes obtenida de la base de datos
        return listaHabitantes
    }
}
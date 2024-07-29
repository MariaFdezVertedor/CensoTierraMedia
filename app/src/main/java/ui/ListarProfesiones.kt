package ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import bbdd.HabitantesSQLite
import com.elsda.tierramedia_pac.R

class ListarProfesiones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_profesiones)
        //TODO crear toda la lógica para que se muestre bien la información en el listado de profesiones

        // Recuperar el valor de la profesión pasado por el Intent
        val profesion = intent.getStringExtra("PROFESION")
        Log.d("ListarProfesiones", "Profesion: $profesion")

        // Crear una instancia de la clase HabitantesSQLite para acceder a la base de datos
        val db = HabitantesSQLite(this)
        // Obtener el listado de habitantes por la profesión de cada uno de ellos
        val listadoHabitantes = profesion?.let { db.getListadoPorProfesion(it) }

        // Asignar los elementos de la interfaz de usuario a las variables
        val nombreProfesion = findViewById<TextView>(R.id.txtNombreProfesion)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtrasListadoProfesion)
        val tableLayout = findViewById<LinearLayout>(R.id.layoutListadoProfesion)

        // Actualizar el nombre de la profesión en el TextView correspondiente
        // según la profesión obtenida del Intent
        when (profesion) {
            "Caballero" -> nombreProfesion.text = "Caballero"
            "Arquero", -> nombreProfesion.text = "Arquero"
            "Herrero", -> nombreProfesion.text = "Herrero"
            "Juglar", -> nombreProfesion.text = "Juglar"
            "Campesino", -> nombreProfesion.text = "Campesino"
            "Alquimista", -> nombreProfesion.text = "Alquimista"
            "Escriba", -> nombreProfesion.text = "Escriba"
            "Mercader", -> nombreProfesion.text = "Mercader"
            "Monje", -> nombreProfesion.text = "Monje"
            "Carpintero" -> nombreProfesion.text = "Carpintero"
            else -> nombreProfesion.text = "Profesión desconocida: $profesion"
        }

        // Verificar si el listado de habitantes no es nulo y contiene al menos un habitante
        if (listadoHabitantes != null && listadoHabitantes.size > 0) {
            listadoHabitantes.forEach { habitante ->
                // Crear una nueva fila (TableRow) para cada habitante
                val fila = TableRow(this@ListarProfesiones)
                // Insertar los datos del habitante en la fila de la tabla
                insertarTextoEnTabla(habitante.nombre, fila)
                insertarTextoEnTabla(habitante.apellidos, fila)
                insertarTextoEnTabla(habitante.raza, fila)
                insertarTextoEnTabla(habitante.ubicacion, fila)
                // Agregar la fila a la tabla de habitantes
                tableLayout.addView(fila)
            }
        }

        // Configurar el evento de clic para el botón de atrás
        btnAtras.setOnClickListener {
            // Finalizar la actividad actual y volver a la actividad anterior
            finish()
        }
    }

    /**
     * Método para insertar texto en la tabla
     * @param texto cadena de texto a añadir en la fila
     * @param fila fila de la tabla donde añadiremos el texto
     */
    private fun insertarTextoEnTabla(texto: String, fila: TableRow) {
        val textView = TextView(this@ListarProfesiones).apply {
            text = texto
            // Ajusta estos layoutParams dependiendo de tu contenedor final
            layoutParams = TableRow.LayoutParams(
                0, // Usar 0 para el ancho en TableRow con layout_weight
                TableRow.LayoutParams.WRAP_CONTENT,
                1f // Peso para distribución equitativa en TableRow
            )
            textSize = 11f
            setBackgroundColor(Color.WHITE)
            setTextColor(Color.BLACK)
        }

        fila.addView(textView)
    }
}

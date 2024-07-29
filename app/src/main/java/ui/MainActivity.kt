package ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.elsda.tierramedia_pac.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TierraMedia_Pac)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Buscamos el botón censo
        val btnCenso = findViewById<Button>(R.id.btnCenso)

        // Establece el OnClickListener para el botón Censo
        btnCenso.setOnClickListener {
            // Crea un Intent para iniciar EligeOpcion
            val intent = Intent(this, EligeOpcion::class.java)
            startActivity(intent)
        }
    }
}
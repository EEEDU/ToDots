package com.example.todots

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todots.model.EstadoTarea
import com.example.todots.model.Tarea
import com.example.todots.ui.components.ListaTareas
import com.example.todots.ui.screens.Hoy
import com.example.todots.ui.theme.ToDots_theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDots_theme(dynamicColor = false) {
                Scaffold { innerPadding ->
                    Hoy(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ListaTareasPreview() {
    ToDots_theme(dynamicColor = false) {
        ListaTareas(
            tareas = listOf(
                Tarea(id = 1, texto = "Comprar pan", estado = EstadoTarea.POR_HACER),
                Tarea(id = 2, texto = "Terminar el informe", estado = EstadoTarea.EMPEZADO)
            ),
            onEstadoCambiado = { _, _ -> },
            onTextoCambiado = { _, _ -> },
            onEliminarTarea = { }
        )
    }
}
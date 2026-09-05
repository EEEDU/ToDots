package com.example.todots.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todots.ui.components.BotonAgregar
import com.example.todots.ui.components.ListaTareas
import com.example.todots.ui.components.Titulo
import com.example.todots.viewmodel.TareaViewModel

/**
 * Pantalla principal que muestra las tareas del día.
 * Gestiona el estado de la lista y los callbacks de modificación.
 */
@Composable
fun Hoy(
    modifier: Modifier = Modifier,
    viewModel: TareaViewModel = viewModel()
) {
    // Observa el flujo de datos de Room en tiempo real
    val tareas by viewModel.tareas.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Titulo(
                    name = "Hoy",
//                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            ListaTareas(
                tareas = tareas,
                onEstadoCambiado = viewModel::cambiarEstado,
                onTextoCambiado = viewModel::cambiarTexto,
                onEliminarTarea = viewModel::eliminarTarea,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 90.dp),
            )

            BotonAgregar(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {
                    viewModel.agregarTarea("")
                }
            )
        }
    }
}

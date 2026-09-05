package com.example.todots.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todots.model.GrupoTarea
import com.example.todots.ui.components.BotonAgregar
import com.example.todots.ui.components.ListaTareas
import com.example.todots.ui.components.Titulo
import com.example.todots.viewmodel.TareaViewModel

private val GRUPOS = listOf(
    GrupoTarea.HOY to "Hoy",
    GrupoTarea.MANANA to "Mañana",
    GrupoTarea.ALGUN_DIA to "Algún día"
)

@Composable
fun TareasScreen(
    modifier: Modifier = Modifier,
    viewModel: TareaViewModel = viewModel()
) {
    var pestanaSeleccionada by remember { mutableIntStateOf(0) }
    val grupoActual = GRUPOS[pestanaSeleccionada].first

    val tareasHoy by viewModel.tareasHoy.collectAsStateWithLifecycle()
    val tareasManana by viewModel.tareasManana.collectAsStateWithLifecycle()
    val tareasAlgunDia by viewModel.tareasAlgunDia.collectAsStateWithLifecycle()

    val tareas = when (grupoActual) {
        GrupoTarea.HOY -> tareasHoy
        GrupoTarea.MANANA -> tareasManana
        GrupoTarea.ALGUN_DIA -> tareasAlgunDia
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Titulo(
                    name = GRUPOS[pestanaSeleccionada].second,
                    modifier = Modifier.padding(top = 16.dp)
                )
                TabRow(selectedTabIndex = pestanaSeleccionada) {
                    GRUPOS.forEachIndexed { index, (_, nombre) ->
                        Tab(
                            selected = pestanaSeleccionada == index,
                            onClick = { pestanaSeleccionada = index },
                            text = { Text(nombre) }
                        )
                    }
                }
            }

            ListaTareas(
                tareas = tareas,
                onEstadoCambiado = viewModel::cambiarEstado,
                onTextoCambiado = viewModel::cambiarTexto,
                onEliminarTarea = viewModel::eliminarTarea,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 130.dp) // un poco más abajo para dejar sitio al TabRow
            )

            BotonAgregar(
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                onClick = { viewModel.agregarTarea("", grupoActual) }
            )
        }
    }
}
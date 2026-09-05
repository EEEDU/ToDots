package com.example.todots.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todots.model.GrupoTarea
import com.example.todots.ui.components.BotonAgregar
import com.example.todots.ui.components.ListaTareas
import com.example.todots.ui.components.SelectorGrupo
import com.example.todots.ui.components.Titulo
import com.example.todots.ui.theme.Dimens
import com.example.todots.viewmodel.TareaViewModel
import kotlinx.coroutines.launch

private val GRUPOS = listOf(
    GrupoTarea.HOY to "Hoy",
    GrupoTarea.MANANA to "Mañana",
    GrupoTarea.ALGUN_DIA to "Algún día"
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TareasScreen(
    modifier: Modifier = Modifier,
    viewModel: TareaViewModel = viewModel()
) {
    var pestanaSeleccionada by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { GRUPOS.size })
    val scope = rememberCoroutineScope()
    var menuAbierto by remember { mutableStateOf(false) }

    val grupoActual = GRUPOS[pagerState.currentPage].first

    val tareasHoy by viewModel.tareasHoy.collectAsStateWithLifecycle()
    val tareasManana by viewModel.tareasManana.collectAsStateWithLifecycle()
    val tareasAlgunDia by viewModel.tareasAlgunDia.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Titulo(
                    name = GRUPOS[pagerState.currentPage].second,
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = { menuAbierto = true }
                )
//                TabRow(selectedTabIndex = pestanaSeleccionada) {
//                    GRUPOS.forEachIndexed { index, (_, nombre) ->
//                        Tab(
//                            selected = pestanaSeleccionada == index,
//                            onClick = { pestanaSeleccionada = index },
//                            text = { Text(nombre) }
//                        )
//                    }
//                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 90.dp)
            ) { page ->
                val tareas = when (grupoActual) {
                    GrupoTarea.HOY -> tareasHoy
                    GrupoTarea.MANANA -> tareasManana
                    GrupoTarea.ALGUN_DIA -> tareasAlgunDia
                }
                ListaTareas(
                    tareas = tareas,
                    onEstadoCambiado = viewModel::cambiarEstado,
                    onTextoCambiado = viewModel::cambiarTexto,
                    onEliminarTarea = viewModel::eliminarTarea,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = Dimens.PaddingLista)
                )
            }

            BotonAgregar(
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                onClick = { viewModel.agregarTarea("", grupoActual) }
            )

            if (menuAbierto) {
                SelectorGrupo(
                    opciones = GRUPOS,
                    onSeleccionar = { grupo ->
                        val index = GRUPOS.indexOfFirst { it.first == grupo }
                        scope.launch { pagerState.animateScrollToPage(index) }
                        menuAbierto = false
                    },
                    onCerrar = { menuAbierto = false }
                )
            }

        }
    }
}
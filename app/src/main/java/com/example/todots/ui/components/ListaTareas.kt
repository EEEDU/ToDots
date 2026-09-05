package com.example.todots.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todots.R
import com.example.todots.model.EstadoTarea
import com.example.todots.model.Tarea
import com.example.todots.ui.theme.Dimens
import com.example.todots.ui.theme.black


/**
 * Lista vertical de tareas.
 *
 * @param tareas Lista de tareas a mostrar
 * @param onEstadoCambiado Callback cuando cambia el estado de una tarea
 * @param onTextoCambiado Callback cuando se edita el texto de una tarea
 * @param onEliminarTarea Callback cuando se elimina una tarea
 * @param modifier Modificador
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTareas(
    tareas: List<Tarea>,
    onEstadoCambiado: (Tarea, EstadoTarea) -> Unit ,
    onTextoCambiado: (Tarea, String) -> Unit ,
    onEliminarTarea: (Tarea) -> Unit,
    modifier: Modifier = Modifier
) {
    // LazyColumn optimiza el rendimiento cuando la lista crece
    LazyColumn(
        modifier = modifier
            .padding(start = Dimens.PaddingLista, end = Dimens.PaddingLista)
            .fillMaxWidth()
    ) {
        items(
            items = tareas,  // Define una lista de objetos que Compose debe dibujar en pantalla
            key = { it.id}
        ) { tarea ->  // Función lambda que actua como bucle {

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    when (dismissValue) {
                        SwipeToDismissBoxValue.EndToStart -> {
                            onEliminarTarea(tarea)
                            true
                        }
                        SwipeToDismissBoxValue.StartToEnd -> {
                            onEstadoCambiado(tarea, EstadoTarea.CANCELADO)
                            false
                        }
                        else -> {
                            false
                        }
                    }
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    val deslizarHaciaDerecha = dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = Dimens.PaddingItem, bottom = Dimens.PaddingItem)
                            .clip(RoundedCornerShape(16.dp))
                            .background(black),
                        contentAlignment = if (deslizarHaciaDerecha) Alignment.CenterStart else Alignment.CenterEnd
                    ) {
                        if (deslizarHaciaDerecha) {
                            Icon(
                                painter = painterResource(id = R.drawable.circulo_cancelado), // tu drawable
                                contentDescription = stringResource(R.string.cd_cancelar_tarea),
                                tint = Color.White,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Delete, // icono por defecto
                                contentDescription = stringResource(R.string.cd_eliminar_tarea),
                                tint = Color.White,
                                modifier = Modifier.padding(end = 20.dp)
                            )
                        }
                    }
                }
            ) {

                TareaItem(
                    tarea = tarea,
                    onEstadoCambiado = { nuevoEstado ->
                        onEstadoCambiado(
                            tarea,
                            nuevoEstado
                        )
                    },
                    onTextoCambiado = { nuevoTexto ->
                        onTextoCambiado(
                            tarea,
                            nuevoTexto
                        )
                    }
                )
            }
        }
    }
}
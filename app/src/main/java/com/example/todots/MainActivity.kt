package com.example.todots

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todots.model.EstadoTarea
import com.example.todots.model.Tarea
import com.example.todots.ui.theme.ToDots_jetpackTheme
import com.example.todots.ui.theme.black
import com.example.todots.ui.theme.colorPrincipal
import com.example.todots.viewmodel.TareaViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDots_jetpackTheme(dynamicColor = false) {
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
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            ListaTareas(
                tareas = tareas,
                onEstadoCambiado = { tarea, nuevoEstado ->
                    viewModel.cambiarEstado(tarea, nuevoEstado)
                },
                onTextoCambiado = { tarea, nuevoTexto ->
                    viewModel.cambiarTexto(tarea, nuevoTexto)
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 90.dp),
                onEliminarTarea = { tarea ->
                    viewModel.eliminarTarea(tarea)
                },
            )

            BotonAgregar(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {
                    viewModel.agregarTarea("Nueva tarea")
                }
            )
        }
    }
}


/**
 * Cabecera de la pantalla con el titulo.
 *
 * @param name Texto a mostrar como título
 */
@Composable
fun Titulo(name: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.primary
    ) {
        Column {
            Row(
                modifier = Modifier.padding(start = 41.dp, top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 14.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                thickness = 3.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

/**
 * Lista vertical de tareas.
 *
 * @param tareas Lista de tareas a mostrar
 * @param onEstadoCambiado Callback cuando cambia el estado de una tarea
 * @param onTextoCambiado Callback cuando se edita el texto de una tarea
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
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxWidth()
    ) {
        items(
            items = tareas,  // Define una lista de objetos que Compose debe dibujar en pantalla
            key = { it.id}
        ) { tarea ->  // Función lambda que actua como bucle {

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                        onEliminarTarea(tarea)
                        true
                    } else {
                        false
                    }
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                enableDismissFromStartToEnd = false, // Esto evita que se deslice hacia la derecha
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(black)
                            .padding(top = 12.dp, bottom = 12.dp)
                        ,
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar tarea",
                            tint = Color.White
                        )
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

@Composable
fun TareaItem(
    tarea: Tarea,
    onEstadoCambiado: (EstadoTarea) -> Unit,
    onTextoCambiado: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var texto by remember { mutableStateOf(tarea.texto) }
    val focusRequester = remember { FocusRequester() }  // ✅ creado una sola vez

    Surface(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EstadoTarea(
                estadoActual = tarea.estado,        // ✅ estado del modelo
                onEstadoCambiado = onEstadoCambiado // ✅ notifica arriba
            )
            Spacer(modifier = Modifier.width(12.dp))

            TextField(
                value = texto,
                onValueChange = {
                    texto = it
                    onTextoCambiado(it)
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focusRequester.freeFocus() }  // ✅ cierra el teclado
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun BotonAgregar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_add),
            contentDescription = "Agregar tarea",
            modifier = Modifier.size(50.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EstadoTarea(
    estadoActual: EstadoTarea = EstadoTarea.POR_HACER,  // ✅ recibe el estado
    onEstadoCambiado: (EstadoTarea) -> Unit = {}         // ✅ notifica cambios
) {
    val haptic = LocalHapticFeedback.current
    val estados = EstadoTarea.entries  // ✅ usa el enum directamente

    Image(
        painter = painterResource(id = estadoActual.icono),  // ✅ icono del enum
        contentDescription = "Estado de la tarea",
        modifier = Modifier
            .size(40.dp)
            .combinedClickable(
                onClick = {
                    val siguiente = estados[(estadoActual.ordinal + 1) % estados.size]
                    onEstadoCambiado(siguiente)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                onLongClick = {
                    onEstadoCambiado(EstadoTarea.COMPLETADO)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            )
    )
}

//@Preview(showBackground = true)
//@Composable
//fun HoyPreview() {
//    ToDots_jetpackTheme(dynamicColor = false) {
//        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//            Hoy(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .background(color = MaterialTheme.colorScheme.primary)
//            )
//        }
//    }
//}
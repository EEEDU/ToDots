package com.example.todots_jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.example.todots_jetpack.ui.theme.ToDots_jetpackTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDots_jetpackTheme (dynamicColor = false) {
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

@Composable
fun Hoy(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        val tareas = remember { mutableStateListOf("Tarea 11", "Tarea 12") }


        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Titulo(name = "Hoy",
                    modifier = Modifier
//                        .align(Alignment.TopStart)
                        .padding(top = 16.dp)
                )
            }
            // Arriba a la izquierda
//            Titulo(
//                name = "Hoy",
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .padding(top = 16.dp)
//            )


            Lista_tareas(
                tareas = tareas,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 90.dp)
            )


            // Abajo a la derecha: botón que añade una tarea
            BotonAgregar(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {
                    // Añadimos una tarea nueva
                    val nuevaTarea = "Tarea ${tareas.size + 1}"
                    tareas.add(nuevaTarea)
                }
            )
        }
    }
}

@Composable
fun Titulo(name: String, modifier: Modifier = Modifier){
    Row(
        modifier = modifier.padding(start = 41.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.circulo_completado_sombra),
            contentDescription = "Icono de la aplicación, un círculo negro",
            modifier = Modifier.size(50.dp)
        )
        Text(text = name, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(start = 14.dp), color = MaterialTheme.colorScheme.onPrimary)
    }
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        thickness = 3.dp,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun Lista_tareas(tareas: List<String> = listOf("Tarea 1", "Tarea 2"), modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxWidth(),
    ) {
        for (tarea in tareas) {
            Tarea(tarea, modifier)
        }
    }
}

@Composable
fun Tarea(tarea: String = "Tarea de prueba", modifier: Modifier = Modifier){
    var texto by remember { mutableStateOf(tarea) }
    var editable by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
//        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.primary
    ){
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically

        ){
            EstadoTarea()
            Spacer(modifier = Modifier.width(12.dp))

            if (editable) {
                TextField(
                    value = texto,
                    onValueChange = { texto = it },
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .weight(1f) // ocupa todo el espacio restante
                        .focusRequester(FocusRequester())
                        .background(MaterialTheme.colorScheme.primary)
                )
            } else {
                Text(
                    text = texto,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            editable = true
                        }
                )
            }        }
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
fun EstadoTarea() {
    // lista de imágenes del ciclo de una tarea
    val iconosCicloTarea = listOf(
        R.drawable.circulo_porhacer,
        R.drawable.circulo_empezado,
        R.drawable.circulo_amitad,
        R.drawable.circulo_completado
    )

    // Estado actual
    var indiceEstadoActual by remember { mutableIntStateOf(0) }
    // Para la vibración
    val haptic = LocalHapticFeedback.current


    Image(
        painter = painterResource(id = iconosCicloTarea[indiceEstadoActual]),
        contentDescription = "Estado de la tarea",
        modifier = Modifier
            .size(40.dp)
            .combinedClickable(
                onClick = {
                    // click normal -> pasa al siguiente estado
                    indiceEstadoActual = (indiceEstadoActual + 1) % iconosCicloTarea.size
                    // vibración corta
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                onLongClick = {
                    // Si mantienes pulsado -> la tarea pasa a estar completada
                    indiceEstadoActual = 3
                    // vibración corta
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            )

    )
}

@Preview(showBackground = true)
@Composable
fun HoyPreview() {
    ToDots_jetpackTheme (dynamicColor = false) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Hoy(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(color = MaterialTheme.colorScheme.primary)
            )
        }
    }
}


////Pruebas
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    val expanded = remember { mutableStateOf(false) }
//    val extraPadding by animateDpAsState(
//        if (expanded.value) 48.dp else 0.dp
//    )
//    Surface(
//        color = MaterialTheme.colorScheme.primary,
//        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//    ) {
//        Row(modifier = Modifier.padding(24.dp)) {
//            Column(modifier = Modifier
//                .weight(1f)
//                .padding(bottom = extraPadding)) {
//                Text(text = "Hello ")
//                Text(text = name)
//            }
//            ElevatedButton(
//                onClick = { expanded.value = !expanded.value }
//            ) {
//                Text(if (expanded.value) "Show less" else "Show more")
//            }
//        }
//
//    }
//}
//
//@Composable
//private fun Greetings(
//    modifier: Modifier = Modifier,
//    names: List<String> = listOf("World", "Compose")
//) {
//    Column(modifier = modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
//}
//
//// Pruebas
//@Composable
//fun MyApp(
//    modifier: Modifier = Modifier,
//    names: List<String> = listOf("World", "Compose")
//) {
//    Column(modifier = modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name, modifier = modifier)
//        }
//    }
//
//    var shouldShowOnboarding by remember { mutableStateOf(true) }
//
//    Surface(modifier) {
//    if (shouldShowOnboarding) {
//            OnboardingScreen(/* TODO */)
//        } else {
//            Greetings()
//        }
//    }
//}
//
//@Composable
//fun MyApp(modifier: Modifier = Modifier) {
//
//    var shouldShowOnboarding by remember { mutableStateOf(true) }
//
//    Surface(modifier) {
//        if (shouldShowOnboarding) {
//        } else {
//            Greetings()
//        }
//    }
//}


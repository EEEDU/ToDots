package com.example.todots.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todots.model.EstadoTarea


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EstadoTarea(
    estadoActual: EstadoTarea = EstadoTarea.POR_HACER,  // recibe el estado
    onEstadoCambiado: (EstadoTarea) -> Unit = {}         // notifica cambios
) {
    val haptic = LocalHapticFeedback.current
    val estadosCiclo = listOf(
        EstadoTarea.POR_HACER,
        EstadoTarea.EMPEZADO,
        EstadoTarea.A_MITAD,
        EstadoTarea.COMPLETADO
    )
    Image(
        painter = painterResource(id = estadoActual.icono),  // icono del enum
        contentDescription = "Estado de la tarea",
        modifier = Modifier
            .size(40.dp)
            .combinedClickable(
                onClick = {
                    val siguiente = estadosCiclo[(estadoActual.ordinal + 1) % estadosCiclo.size]
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
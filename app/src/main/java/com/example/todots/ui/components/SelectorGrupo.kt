package com.example.todots.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todots.model.GrupoTarea

/**
 * Overlay a pantalla completa que permite elegir entre los grupos de tareas.
 *
 * @param opciones Lista de (grupo, nombre visible) a mostrar
 * @param onSeleccionar Callback al elegir un grupo; el overlay debe cerrarse desde fuera
 * @param onCerrar Callback al tocar fuera de las opciones (cerrar sin elegir)
 */
@Composable
fun SelectorGrupo(
    opciones: List<Pair<GrupoTarea, String>>,
    onSeleccionar: (GrupoTarea) -> Unit,
    onCerrar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)) // scrim oscuro semitransparente
            .clickable(onClick = onCerrar), // tocar fuera de las opciones cierra el menú
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            opciones.forEach { (grupo, nombre) ->
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSeleccionar(grupo) }
                        .padding(vertical = 24.dp)
                )
            }
        }
    }
}
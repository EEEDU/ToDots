package com.example.todots.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.todots.model.EstadoTarea
import com.example.todots.model.Tarea
import com.example.todots.ui.theme.Dimens


@Composable
fun TareaItem(
    tarea: Tarea,
    onEstadoCambiado: (EstadoTarea) -> Unit,
    onTextoCambiado: (String) -> Unit,
//    modifier: Modifier = Modifier
) {
    var texto by remember(tarea.id) { mutableStateOf(tarea.texto) }
    val focusRequester = remember { FocusRequester() }  // creado una sola vez
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .padding(top = Dimens.PaddingItem, bottom = Dimens.PaddingItem)
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CornerRadius),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.padding(Dimens.PaddingItem),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EstadoTarea(
                estadoActual = tarea.estado,        // estado del modelo
                onEstadoCambiado = onEstadoCambiado // notifica arriba
            )
            Spacer(modifier = Modifier.width(Dimens.PaddingItem))

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
                    onDone = { focusManager.clearFocus() }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,   // quita la línea inferior
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
            )
        }
    }
}
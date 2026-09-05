package com.example.todots.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todots.ui.theme.Dimens
import com.example.todots.ui.theme.ToDots_theme

/**
 * Cabecera de la pantalla con el titulo.
 *
 * @param name Texto a mostrar como título
 */
@Composable
fun Titulo(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(bottomStart = Dimens.CornerRadius, bottomEnd = Dimens.CornerRadius),
        shadowElevation = 12.dp,
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
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TituloPreview() {
    ToDots_theme(dynamicColor = false) {
        Titulo(
            name = "Hoy",
//                    modifier = Modifier.padding(top = 16.dp)
        )
    }
}
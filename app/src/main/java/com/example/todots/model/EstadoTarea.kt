package com.example.todots.model

import com.example.todots.R

enum class EstadoTarea(val icono: Int) {
    POR_HACER(R.drawable.circulo_porhacer),
    EMPEZADO(R.drawable.circulo_empezado),
    A_MITAD(R.drawable.circulo_amitad),
    COMPLETADO(R.drawable.circulo_completado),
    CANCELADO(R.drawable.circulo_cancelado)
}

package com.example.todots.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.todots.R

enum class EstadoTarea(val icono: Int) {
    POR_HACER(R.drawable.circulo_porhacer),
    EMPEZADO(R.drawable.circulo_empezado),
    A_MITAD(R.drawable.circulo_amitad),
    COMPLETADO(R.drawable.circulo_completado),
    CANCELADO(R.drawable.circulo_cancelado)
}

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val texto: String,
    val estado: EstadoTarea = EstadoTarea.POR_HACER
)

class Converters {
    @TypeConverter
    fun fromEstado(estado: EstadoTarea): String = estado.name

    @TypeConverter
    fun toEstado(valor: String): EstadoTarea = EstadoTarea.valueOf(valor)
}
package com.example.todots.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val texto: String,
    val estado: EstadoTarea = EstadoTarea.POR_HACER
)

class Converters {
    @TypeConverter
    fun fromEstado(estado: EstadoTarea): String = estado.name

    @TypeConverter
    fun toEstado(valor: String): EstadoTarea = EstadoTarea.valueOf(valor)
}
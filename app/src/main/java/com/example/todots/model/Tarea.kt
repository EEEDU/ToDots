package com.example.todots.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val texto: String,
    val estado: EstadoTarea = EstadoTarea.POR_HACER,
    val grupo: GrupoTarea = GrupoTarea.HOY
)

class Converters {
    @TypeConverter
    fun fromEstado(estado: EstadoTarea): String = estado.name

    @TypeConverter
    fun toEstado(valor: String): EstadoTarea = EstadoTarea.valueOf(valor)

    @TypeConverter
    fun fromGrupo(grupo: GrupoTarea): String = grupo.name

    @TypeConverter
    fun toGroup(valor: String): GrupoTarea = GrupoTarea.valueOf(valor)
}
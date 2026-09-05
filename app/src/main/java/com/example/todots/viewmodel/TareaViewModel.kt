package com.example.todots.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todots.data.AppDatabase
import com.example.todots.model.EstadoTarea
import com.example.todots.model.GrupoTarea
import com.example.todots.model.Tarea
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TareaViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).tareaDao()

//    val tareas: StateFlow<List<Tarea>> = dao.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val tareasHoy: StateFlow<List<Tarea>> = dao.obtenerPorGrupo(GrupoTarea.HOY).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val tareasManana: StateFlow<List<Tarea>> = dao.obtenerPorGrupo(GrupoTarea.MANANA).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val tareasAlgunDia: StateFlow<List<Tarea>> = dao.obtenerPorGrupo(GrupoTarea.ALGUN_DIA).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarTarea(texto: String, grupoTarea: GrupoTarea) {
        viewModelScope.launch {
            dao.insertar(Tarea(texto = texto, grupo = grupoTarea))
        }
    }

    fun cambiarEstado(tarea: Tarea, nuevoEstado: EstadoTarea) {
        viewModelScope.launch {
            dao.actualizar(tarea.copy(estado = nuevoEstado))
        }
    }

    fun cambiarTexto(tarea: Tarea, nuevoTexto: String) {
        viewModelScope.launch {
            dao.actualizar(tarea.copy(texto = nuevoTexto))
        }
    }

    fun cambiarGrupo(tarea: Tarea, nuevoGrupo: GrupoTarea) {
        viewModelScope.launch {
            dao.actualizar(tarea.copy(grupo = nuevoGrupo))
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            dao.eliminar(tarea)
        }
    }
}
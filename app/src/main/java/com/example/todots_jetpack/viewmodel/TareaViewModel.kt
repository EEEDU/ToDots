package com.example.todots_jetpack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todots_jetpack.data.AppDatabase
import com.example.todots_jetpack.model.EstadoTarea
import com.example.todots_jetpack.model.Tarea
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TareaViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).tareaDao()

    val tareas: StateFlow<List<Tarea>> = dao.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarTarea(texto: String) {
        viewModelScope.launch {
            dao.insertar(Tarea(texto = texto))
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

    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            dao.eliminar(tarea)
        }
    }
}
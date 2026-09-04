package com.example.todots.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todots.model.Tarea
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {
    @Query("SELECT * FROM tareas ORDER BY id ASC")
    fun getAll(): Flow<List<Tarea>>  // Flow: flujo de datos que puede emitir varios valores a lo largo del tiempo, en lugar de un único valor. Es la forma moderna de decir "observa esto y avísame cada vez que cambie".

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(tarea: Tarea): Long  // suspend: funcion suspendible. Es decir, puede pausearse y reaunudarse sin parar el hilo (se ejecuta en segundo plano)

    @Update
    suspend fun actualizar(tarea: Tarea)

    @Delete
    suspend fun eliminar(tarea: Tarea)
}
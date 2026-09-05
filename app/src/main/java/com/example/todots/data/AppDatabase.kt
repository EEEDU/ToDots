package com.example.todots.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todots.model.Converters
import com.example.todots.model.Tarea

// Esta clase representa mi base de datos SQLite
@Database(entities = [Tarea::class], version = 2, exportSchema = false)  // Con una sola tabla Tarea
@TypeConverters(Converters::class)  // Usan esta clase cuando te encuentre un tipo que no sea Tarea
abstract class AppDatabase : RoomDatabase() {
    abstract fun tareaDao(): TareaDao

    // Singleton: Garantiza que solo hay una instancia de la base de datos en toda la aplicación
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tareas_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
            }
        }
    }
}
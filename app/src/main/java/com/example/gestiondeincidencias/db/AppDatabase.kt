package com.example.gestiondeincidencias.db

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gestiondeincidencias.db.dao.IncidenciaDao
import com.example.gestiondeincidencias.db.dao.UsuarioDao
import com.example.gestiondeincidencias.db.model.Incidencia
import com.example.gestiondeincidencias.db.model.Usuario

@SuppressLint("RestrictedApi")
@Database(entities = [Incidencia::class, Usuario::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incidenciaDao(): IncidenciaDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "incidencias_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

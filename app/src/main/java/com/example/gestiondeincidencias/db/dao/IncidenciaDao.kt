package com.example.gestiondeincidencias.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gestiondeincidencias.db.model.Incidencia

@Dao
interface IncidenciaDao {
    @Query("SELECT * FROM Incidencias")
    suspend fun getAll(): List<Incidencia>

    @Query("SELECT * FROM Incidencias WHERE id = :id")
    suspend fun getById(id: Int): Incidencia?

    @Insert
    suspend fun insert(incidencia: Incidencia): Long

    @Update
    suspend fun update(incidencia: Incidencia): Int

    @Delete
    suspend fun delete(incidencia: Incidencia): Int

    @Query("DELETE FROM Incidencias")
    suspend fun deleteAll(): Int
}

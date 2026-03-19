package com.example.gestiondeincidencias.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gestiondeincidencias.db.model.Usuario

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM Usuarios")
    suspend fun getAll(): List<Usuario>

    @Query("SELECT * FROM Usuarios WHERE id = :id")
    suspend fun getById(id: Int): Usuario?

    @Query("SELECT * FROM Usuarios WHERE email = :email AND contrasena = :contrasena LIMIT 1")
    suspend fun getByCredentials(email: String, contrasena: String): Usuario?

    @Query("SELECT * FROM Usuarios WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): Usuario?

    @Insert
    suspend fun insert(usuario: Usuario): Long

    @Update
    suspend fun update(usuario: Usuario): Int

    @Delete
    suspend fun delete(usuario: Usuario): Int

    @Query("DELETE FROM Usuarios")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM Usuarios WHERE email = :email AND contrasena = :contrasena LIMIT 1")
    suspend fun existsByCredentials(email: String, contrasena: String): Usuario?

    @Query("SELECT * FROM Usuarios WHERE id = :id LIMIT 1")
    suspend fun existsById(id: Int): Usuario?
}

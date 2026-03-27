package com.example.gestiondeincidencias.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Usuarios",
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["nombre"], unique = true)
    ]

)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String = "",
    val email: String = "",
    val contrasena: String = "",
    val rol: Rol = Rol.ADMINISTRADOR,
)
{
    fun isAdmin() = this.rol == Rol.ADMINISTRADOR
}
enum class Rol(val displayName: String) {
    ADMINISTRADOR("Administrador"),
    USUARIO("Usuario")
}

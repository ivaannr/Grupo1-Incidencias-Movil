package com.example.gestiondeincidencias.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Incidencias")
data class Incidencia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String = "",
    val descripcion: String = "",
    val usuarioId: String = "",
    val categoria: String = "",
    val fechaRegistro: String = "",
    val nivelUrgencia: String = "",
    val estado: String = "",
    val ubicacion: String = ""
)

enum class Categoria(val displayName: String) {
    HARDWARE("Hardware"),
    SOFTWARE("Software"),
    CONECTIVIDAD("Conectividad"),
    USUARIOS("Usuarios"),
    INFRAESTRUCTURA("Infraestructura")
}

enum class NivelUrgencia(val displayName: String) {
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja")
}

enum class Estado(val displayName: String) {
    ABIERTA("Abierta"),
    EN_CURSO("En curso"),
    RESUELTA("Resuelta"),
    CERRADA("Cerrada")
}


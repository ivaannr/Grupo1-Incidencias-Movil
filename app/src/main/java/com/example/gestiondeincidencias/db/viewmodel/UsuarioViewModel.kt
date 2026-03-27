package com.example.gestiondeincidencias.db.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestiondeincidencias.db.dao.UsuarioDao
import com.example.gestiondeincidencias.db.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(private val dao: UsuarioDao) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios
    private val _usuarioLoggeado = MutableStateFlow<Usuario?>(null)
    val usuarioLoggeado: StateFlow<Usuario?> = _usuarioLoggeado

    init {
        cargarUsuarios()
    }

    suspend fun login(indentifier: String, contrasena: String): Usuario {
        val possibleUser = dao.getByEmail(indentifier, contrasena)
        val possibleUserByName = dao.getUserByName(indentifier, contrasena)

        if (possibleUser != null) {
            _usuarioLoggeado.value = possibleUser
            return possibleUser
        }

        if (possibleUserByName != null) {
            _usuarioLoggeado.value = possibleUserByName
            return possibleUserByName
        }

        throw Exception("Usuario no válido.")
    }

    fun getById(id: String): Usuario {
        return usuarios.value.find { it.id.toString() == id } ?: throw Exception("Usuario no encontrado.")
    }
    fun logById(id: Int) {
        viewModelScope.launch {
            _usuarioLoggeado.value = dao.getById(id)
        }
    }

    fun registrar(usuario: Usuario) {
        viewModelScope.launch {
            dao.insert(usuario)
            cargarUsuarios()
        }
    }

    fun update(usuario: Usuario) {
        viewModelScope.launch {
            dao.update(usuario)
            cargarUsuarios()
            if (_usuarioLoggeado.value?.id == usuario.id) {
                _usuarioLoggeado.value = usuario
            }
        }
    }

    fun delete(usuario: Usuario) {
        viewModelScope.launch {
            dao.delete(usuario)
            cargarUsuarios()
        }
    }

    fun logout() {
        _usuarioLoggeado.value = null
    }

    fun cargarUsuarios() {
        viewModelScope.launch {
            _usuarios.value = dao.getAll()
        }
    }
}

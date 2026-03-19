package com.example.gestiondeincidencias.db.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestiondeincidencias.db.dao.UsuarioDao
import com.example.gestiondeincidencias.db.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel (private val dao: UsuarioDao) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios
    private val _usuarioLoggeado = MutableStateFlow<Usuario?>(null)
    val usuarioLoggeado: StateFlow<Usuario?> = _usuarioLoggeado

    init {
        cargarUsuarios()
    }

    suspend fun login(email: String, contrasena: String): Usuario {
        val user = dao.getByCredentials(email, contrasena)
        if (user != null) {
            _usuarioLoggeado.value = user
            return user;
        } else {
            throw Exception("Usuario o contraseña incorrectos.")
        }
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

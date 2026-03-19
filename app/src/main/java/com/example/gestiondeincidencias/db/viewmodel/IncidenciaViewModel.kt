package com.example.gestiondeincidencias.db.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestiondeincidencias.db.dao.IncidenciaDao
import com.example.gestiondeincidencias.db.model.Incidencia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IncidenciaViewModel (private val dao: IncidenciaDao) : ViewModel() {

    private val _incidencias = MutableStateFlow<List<Incidencia>>(emptyList())
    val incidencias: StateFlow<List<Incidencia>> = _incidencias

    init {
        cargarIncidencias()
    }

    fun insert(incidencia: Incidencia) {
        viewModelScope.launch {
            dao.insert(incidencia)
            cargarIncidencias()
        }
    }

    fun update(incidencia: Incidencia) {
        viewModelScope.launch {
            dao.update(incidencia)
            cargarIncidencias()
        }
    }

    fun delete(incidencia: Incidencia) {
        viewModelScope.launch {
            dao.delete(incidencia)
            cargarIncidencias()
        }
    }

    fun cargarIncidencias() {
        viewModelScope.launch {
            _incidencias.value = dao.getAll()
        }
    }
}

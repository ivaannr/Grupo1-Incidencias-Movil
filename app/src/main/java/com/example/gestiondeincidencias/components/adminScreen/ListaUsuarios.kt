package com.example.gestiondeincidencias.components.adminScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestiondeincidencias.db.model.Usuario
import com.example.gestiondeincidencias.db.viewmodel.UsuarioViewModel

@Composable
fun ListaUsuarios(usuarios: List<Usuario>, usuarioViewModel: UsuarioViewModel, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(usuarios) { usuario ->
            UsuarioItem(usuario, usuarioViewModel, navController)
        }
    }
}
package com.example.gestiondeincidencias.components.adminScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestiondeincidencias.db.model.Rol
import com.example.gestiondeincidencias.db.model.Usuario
import com.example.gestiondeincidencias.db.viewmodel.IncidenciaViewModel
import com.example.gestiondeincidencias.db.viewmodel.UsuarioViewModel

class AdminScreen(
    val navController: NavController,
    val usuarioViewModel: UsuarioViewModel,
    val incidenciaViewModel: IncidenciaViewModel
) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Render() {
        val usuarios by usuarioViewModel.usuarios.collectAsState()
        val incidencias by incidenciaViewModel.incidencias.collectAsState()
        var selectedTab by remember { mutableIntStateOf(0) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Panel de Administrador") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1565C0),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF0F7FF))
            ) {
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Usuarios") }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Incidencias") }
                    )
                }

                when (selectedTab) {
                    0 -> ListaUsuarios(usuarios)
                    1 -> ListaIncidencias(incidencias, incidenciaViewModel, navController)
                }
            }
        }
    }

    @Composable
    private fun ListaUsuarios(usuarios: List<Usuario>) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(usuarios) { usuario ->
                UsuarioItem(usuario)
            }
        }
    }

    @Composable
    private fun UsuarioItem(usuario: Usuario) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF1565C0))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = usuario.nombre, fontWeight = FontWeight.Bold)
                        Text(text = usuario.email, fontSize = 12.sp, color = Color.Gray)
                        Text(
                            text = usuario.rol.displayName,
                            fontSize = 11.sp,
                            color = if (usuario.rol == Rol.ADMINISTRADOR) Color.Red else Color.Blue
                        )
                    }
                }
                Row {
                    IconButton(onClick = { navController.navigate("edit-usuario/${usuario.id}") }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Color.Gray
                        )
                    }
                    IconButton(onClick = { usuarioViewModel.delete(usuario) }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Borrar",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }




}
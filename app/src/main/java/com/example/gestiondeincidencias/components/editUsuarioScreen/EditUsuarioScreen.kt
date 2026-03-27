package com.example.gestiondeincidencias.components.editUsuarioScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestiondeincidencias.components.general.AlertDialog
import com.example.gestiondeincidencias.db.model.Rol
import com.example.gestiondeincidencias.db.viewmodel.UsuarioViewModel

class EditUsuarioScreen(
    val navController: NavController,
    val usuarioViewModel: UsuarioViewModel,
    val usuarioId: Int
) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Render() {
        val usuarios by usuarioViewModel.usuarios.collectAsState()
        val usuario = usuarios.find { it.id == usuarioId }

        if (usuario == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Usuario no encontrado", modifier = Modifier.padding(16.dp))
            }
            return
        }

        val context = LocalContext.current

        var nombre by remember { mutableStateOf(usuario.nombre) }
        var email by remember { mutableStateOf(usuario.email) }
        var rol by remember { mutableStateOf(usuario.rol) }
        var dialogOpen by remember { mutableStateOf(false) }

        if (dialogOpen) {
            AlertDialog(
                onDismissRequest = {
                    Toast.makeText(
                        context,
                        "Cambios no guardados",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialogOpen = false;
                },
                onConfirmation = {
                    usuarioViewModel.update(
                        usuario.copy(
                            nombre = nombre,
                            email = email,
                            rol = rol
                        )
                    )
                    Toast.makeText(
                        context,
                        "Usuario actualizado",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                    dialogOpen = false;
                },
                dialogTitle = "Alerta",
                dialogText = "¿Quieres guardar los cambios?",
                icon = Icons.Default.Person
            )
        }


        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Editar Usuario") },
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
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre Completo") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )

                Text(
                    "Rol del Usuario",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Rol.entries.forEach { r ->
                        FilterChip(
                            selected = rol == r,
                            onClick = { rol = r },
                            label = { Text(r.displayName) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        dialogOpen = true;
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                ) {
                    Text(
                        "Guardar Cambios",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }
}
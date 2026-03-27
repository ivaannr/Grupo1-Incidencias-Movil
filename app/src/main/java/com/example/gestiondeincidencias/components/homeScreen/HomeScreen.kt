package com.example.gestiondeincidencias.components.homeScreen

import android.content.Context
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import com.example.gestiondeincidencias.db.model.Incidencia
import com.example.gestiondeincidencias.db.model.Rol
import com.example.gestiondeincidencias.db.model.Usuario
import com.example.gestiondeincidencias.db.viewmodel.UsuarioViewModel

class HomeScreen() {

    @Composable
    fun Render(
        navController: NavController,
        usuario: Usuario?,
        onLogout: () -> Unit,
        incidencias: List<Incidencia> = emptyList(),
        context: Context,
        usersViewModel: UsuarioViewModel
    ) {
        val primaryBlue = Color(0xFF2196F3)
        val backgroundBlue = Color(0xFFF0F7FF)
        val darkBlue = Color(0xFF1565C0)
        val nameColor = if (usuario?.rol == Rol.ADMINISTRADOR) Color(0xFFD7D7D7) else Color.White

        LaunchedEffect(Unit) {
            val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val id = prefs.getInt("rememberId", 0)
            if (id != 0 && usuario == null) {
                usersViewModel.logById(id)
            }
        }

        val incidenciasAbiertas = incidencias.filter { it.estado == "Abierta" }
        val incidenciasEnCurso = incidencias.filter { it.estado == "En curso" }
        val incidenciasCerradas = incidencias.filter { it.estado == "Cerrada" }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBlue)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(darkBlue, primaryBlue)
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(top = 48.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Hola de nuevo,",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 16.sp
                            )
                            Text(
                                text = usuario?.nombre ?: "Usuario",
                                color = nameColor,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Surface(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Perfil",
                                tint = Color.White,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Text(
                                text = "Buscar incidencias...",
                                color = Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Acciones Rápidas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionCard(
                        title = "Nueva Incidencia",
                        subtitle = "Reportar problema",
                        icon = Icons.Default.Add,
                        color = primaryBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("add") },
                        fontSize = 15
                    )
                    ActionCard(
                        title = "Ver Todas",
                        subtitle = "Listado completo",
                        icon = Icons.AutoMirrored.Filled.List,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("see") }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (usuario?.rol == Rol.ADMINISTRADOR) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ActionCard(
                            title = "Panel Admin",
                            subtitle = "Gestionar sistema",
                            icon = Icons.Default.AdminPanelSettings,
                            color = Color(0xFFE91E63),
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("admin") }
                        )
                        ActionCard(
                            title = "Calendario",
                            subtitle = "Ver por fechas",
                            icon = Icons.Default.CalendarMonth,
                            color = Color(0xFF673AB7),
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("calendar") },
                            fontSize = 15
                        )
                    }
                } else {
                    ActionCard(
                        title = "Calendario de Incidencias",
                        subtitle = "Visualización temporal de reportes",
                        icon = Icons.Default.CalendarMonth,
                        color = Color(0xFF673AB7),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate("calendar") }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Resumen de Estado",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        StatRow(
                            "Incidencias Abiertas",
                            incidenciasAbiertas.size.toString(),
                            primaryBlue
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = backgroundBlue
                        )
                        StatRow("En Curso", incidenciasEnCurso.size.toString(), Color(0xFFFF9800))
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = backgroundBlue
                        )
                        StatRow("Cerradas", incidenciasCerradas.size.toString(), Color(0xFF4CAF50))
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedButton(
                    onClick = {
                        onLogout()
                        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        prefs.edit { remove("rememberId") }
                        navController.navigate("login") { popUpTo(0) }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        1.dp,
                        Color.Red.copy(alpha = 0.5f)
                    )
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cerrar Sesión", color = Color.Red)
                }
            }
        }
    }
}
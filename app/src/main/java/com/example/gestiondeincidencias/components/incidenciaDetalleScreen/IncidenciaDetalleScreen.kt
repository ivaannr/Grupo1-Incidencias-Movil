package com.example.gestiondeincidencias.components.incidenciaDetalleScreen

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestiondeincidencias.db.viewmodel.IncidenciaViewModel
import com.example.gestiondeincidencias.db.viewmodel.UsuarioViewModel
import com.example.gestiondeincidencias.ui.theme.Colors

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun IncidenciaDetalleScreen(
    id: Int,
    incidenciaViewModel: IncidenciaViewModel,
    usuarioViewModel: UsuarioViewModel,
    navController: NavController
) {
    val incidencias by incidenciaViewModel.incidencias.collectAsState()
    val usuarios by usuarioViewModel.usuarios.collectAsState()

    val incidencia = incidencias.find { it.id == id }
    val usuarioReporta = usuarios.find { it.id.toString() == incidencia?.usuarioId }

    if (incidencia == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Colors.Primary)
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Incidencia", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Colors.Secondary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Colors.Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = incidencia.titulo,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Colors.TextDark,
                        modifier = Modifier.weight(1f)
                    )
                    StatusBadge(status = incidencia.estado)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Category,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Colors.TextGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = incidencia.categoria,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Colors.TextGray
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Description, contentDescription = null, tint = Colors.Primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Descripción",
                            fontWeight = FontWeight.Bold,
                            color = Colors.Primary,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = incidencia.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Colors.TextDark,
                        lineHeight = 22.sp
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    DetailItem(
                        icon = Icons.Default.CalendarToday,
                        label = "Registrado el",
                        value = incidencia.fechaRegistro
                    )
                    HorizontalDivider(color = Color(0xFFF0F0F0))
                    DetailItem(
                        icon = Icons.Default.PriorityHigh,
                        label = "Urgencia",
                        value = incidencia.nivelUrgencia,
                        valueColor = getUrgenciaColor(incidencia.nivelUrgencia)
                    )
                    HorizontalDivider(color = Color(0xFFF0F0F0))
                    DetailItem(
                        icon = Icons.Default.LocationOn,
                        label = "Ubicación",
                        value = incidencia.ubicacion
                    )
                }
            }

            Text(
                "Información del Reportero",
                fontWeight = FontWeight.Bold,
                color = Colors.TextDark,
                modifier = Modifier.padding(top = 8.dp, start = 4.dp)
            )
            
            Surface(
                onClick = { navController.navigate("edit-usuario/${incidencia.usuarioId}") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        color = Colors.Secondary.copy(alpha = 0.1f)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp),
                            tint = Colors.Secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = usuarioReporta?.nombre ?: "Usuario Desconocido",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Colors.TextDark
                        )
                        Text(
                            text = usuarioReporta?.email ?: "Sin correo",
                            fontSize = 13.sp,
                            color = Colors.TextGray
                        )
                    }
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowRight,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("edit-incidencia/${incidencia.id}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.Secondary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Editar Incidencia", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun getUrgenciaColor(nivel: String): Color {
    return when (nivel) {
        "Alta" -> Color(0xFFD32F2F)
        "Media" -> Color(0xFFFBC02D)
        else -> Color(0xFF388E3C)
    }
}

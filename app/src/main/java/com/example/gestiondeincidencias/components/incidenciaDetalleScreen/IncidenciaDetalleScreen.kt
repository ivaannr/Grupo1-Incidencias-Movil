package com.example.gestiondeincidencias.components.incidenciaDetalleScreen

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestiondeincidencias.db.viewmodel.IncidenciaViewModel

class IncidenciaDetalleScreen(
    val navController: NavController,
    val incidenciaViewModel: IncidenciaViewModel,
    val incidenciaId: Int
) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Render() {
        val incidencias by incidenciaViewModel.incidencias.collectAsState()
        val incidencia = incidencias.find { it.id == incidenciaId }

        val primaryBlue = Color(0xFF2196F3)
        val backgroundBlue = Color(0xFFF0F7FF)
        val darkBlue = Color(0xFF1565C0)

        if (incidencia == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return
        }

        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(darkBlue, primaryBlue)
                            )
                        )
                        .statusBarsPadding()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "Detalle de Incidencia",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            },
            containerColor = backgroundBlue
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "INC-${incidencia.id}",
                                color = primaryBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            StatusBadge(status = incidencia.estado)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = incidencia.titulo,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = incidencia.fechaRegistro,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }

                InfoSection(title = "Descripción") {
                    Text(
                        text = incidencia.descripcion,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        lineHeight = 24.sp
                    )
                }

                InfoSection(title = "Detalles Técnicos") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        DetailItem(
                            icon = Icons.Default.Category,
                            label = "Categoría",
                            value = incidencia.categoria
                        )
                        DetailItem(
                            icon = Icons.Default.PriorityHigh,
                            label = "Urgencia",
                            value = incidencia.nivelUrgencia,
                            valueColor = getUrgenciaColor(incidencia.nivelUrgencia)
                        )
                        DetailItem(
                            icon = Icons.Default.LocationOn,
                            label = "Ubicación",
                            value = incidencia.ubicacion
                        )
                        DetailItem(
                            icon = Icons.Default.Person,
                            label = "Reportado por",
                            value = incidencia.usuarioId
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("edit-incidencia/${incidencia.id}") },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Editar Incidencia", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    @Composable
    private fun InfoSection(title: String, content: @Composable () -> Unit) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                content()
            }
        }
    }

    @Composable
    private fun DetailItem(icon: ImageVector, label: String, value: String, valueColor: Color = Color.Black) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(36.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                color = Color(0xFFF0F7FF)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = Color(0xFF1565C0)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = label, fontSize = 12.sp, color = Color.Gray)
                Text(
                    text = value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = valueColor
                )
            }
        }
    }

    @Composable
    private fun StatusBadge(status: String) {
        val (bgColor, textColor) = when (status) {
            "Abierta" -> Color(0xFFE3F2FD) to Color(0xFF1976D2)
            "En curso" -> Color(0xFFFFF3E0) to Color(0xFFE65100)
            "Resuelta" -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
            else -> Color(0xFFF5F5F5) to Color.Gray
        }

        Surface(
            color = bgColor,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {
            Text(
                text = status,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }

    private fun getUrgenciaColor(urgencia: String): Color {
        return when (urgencia) {
            "Alta" -> Color(0xFFF44336)
            "Media" -> Color(0xFFFF9800)
            "Baja" -> Color(0xFF4CAF50)
            else -> Color.Gray
        }
    }
}
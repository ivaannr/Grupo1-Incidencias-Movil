package com.example.gestiondeincidencias.components.incidenciasScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestiondeincidencias.db.model.Incidencia

class IncidenciasScreen {

    @Composable
    fun Render(
        incidencias: List<Incidencia> = emptyList(),
        onAddClick: () -> Unit = { },
        onBackClick: () -> Unit = { },
        onIncidenciaClick: (Incidencia) -> Unit = { }
    ) {
        var searchQuery by remember { mutableStateOf("") }
        var filtroCategoria by remember { mutableStateOf<String?>(null) }
        var filtroUrgencia by remember { mutableStateOf<String?>(null) }

        val primaryBlue = Color(0xFF2196F3)
        val backgroundBlue = Color(0xFFF0F7FF)
        val darkBlue = Color(0xFF1565C0)

        val incidenciasFiltradas = incidencias.filter { incidencia ->
            val matchBusqueda = incidencia.titulo.contains(searchQuery, ignoreCase = true) ||
                    incidencia.id.toString().contains(searchQuery, ignoreCase = true) ||
                    incidencia.ubicacion.contains(searchQuery, ignoreCase = true)
            val matchCategoria = filtroCategoria == null || incidencia.categoria == filtroCategoria
            val matchUrgencia = filtroUrgencia == null || incidencia.nivelUrgencia == filtroUrgencia
            matchBusqueda && matchCategoria && matchUrgencia
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
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "Listado de Incidencias",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddClick,
                    containerColor = primaryBlue,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir")
                }
            },
            containerColor = backgroundBlue
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Buscar por título, ID o aula...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    tint = primaryBlue
                                )
                            },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryBlue,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterBadge(
                                label = "Prioridad",
                                isActive = filtroUrgencia != null,
                                onClick = { }
                            )
                            FilterBadge(
                                label = "Categoría",
                                isActive = filtroCategoria != null,
                                onClick = { }
                            )
                        }
                    }
                }

                if (incidenciasFiltradas.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No se encontraron incidencias", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp, start = 16.dp, end = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(incidenciasFiltradas) { incidencia ->
                            IncidenciaListItem(
                                incidencia = incidencia,
                                onClick = { onIncidenciaClick(incidencia) }
                            )
                        }
                    }
                }
            }
        }
    }
}

package com.example.gestiondeincidencias.components.adminScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestiondeincidencias.db.model.Incidencia
import com.example.gestiondeincidencias.db.viewmodel.IncidenciaViewModel

@Composable
fun IncidenciaItem(incidencia: Incidencia, incidenciaViewModel: IncidenciaViewModel, navController: NavController) {
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
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Report, contentDescription = null, tint = Color(0xFFFF9800))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = incidencia.titulo, fontWeight = FontWeight.Bold)
                    Text(text = incidencia.descripcion, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
                }
            }
            Row {
                IconButton(onClick = { navController.navigate("edit-incidencia/${incidencia.id}")    }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray)
                }
                IconButton(onClick = { incidenciaViewModel.delete(incidencia) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
                }
            }
        }
    }
}
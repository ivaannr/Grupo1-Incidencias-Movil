package com.example.gestiondeincidencias.components.adminScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
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
import com.example.gestiondeincidencias.components.general.IconWrapper
import com.example.gestiondeincidencias.db.model.Incidencia
import com.example.gestiondeincidencias.db.viewmodel.IncidenciaViewModel

@Composable
fun IncidenciaItem(incidencia: Incidencia, incidenciaViewModel: IncidenciaViewModel, navController: NavController) {
    val reportIconColor = Color(0xFFFF9800)

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

                IconWrapper(color = reportIconColor) {
                    Icon(Icons.Default.Report, contentDescription = null, tint = reportIconColor)
                }

                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = incidencia.titulo, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(text = incidencia.descripcion, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray, maxLines = 1,                             modifier = Modifier
                        .background(Color.Gray.copy(0.1f), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 12.dp))
                }
            }
            Row {
                IconButton(onClick = { navController.navigate("edit-incidencia/${incidencia.id}")    }) {
                    IconWrapper(color = Color(0xFF1565C0)) {
                        Icon(Icons.Default.EditNote, contentDescription = "Editar", tint = Color(0xFF1565C0))
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(onClick = { incidenciaViewModel.delete(incidencia) }) {
                    IconWrapper(color = Color.Red) {
                        Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
                    }
                }
            }
        }
    }
}
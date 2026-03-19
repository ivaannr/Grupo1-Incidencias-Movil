package com.example.gestiondeincidencias.components.incidenciasScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterBadge(label: String, isActive: Boolean, onClick: () -> Unit) {
    val primaryBlue = Color(0xFF2196F3)
    Surface(
        modifier = Modifier.clickable { onClick() },
        color = if (isActive) primaryBlue else primaryBlue.copy(alpha = 0.1f),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.FilterList,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = if (isActive) Color.White else primaryBlue
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                color = if (isActive) Color.White else primaryBlue,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
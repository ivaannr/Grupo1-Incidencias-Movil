package com.example.gestiondeincidencias.components.addIncidenciaScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReadOnlyField(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        readOnly = true,
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = Color.LightGray,
            disabledTextColor = Color.DarkGray,
            disabledLabelColor = Color.Gray
        )
    )
}
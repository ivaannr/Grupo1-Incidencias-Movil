package com.example.gestiondeincidencias.components.addIncidenciaScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestiondeincidencias.db.model.Categoria
import com.example.gestiondeincidencias.db.model.Estado
import com.example.gestiondeincidencias.db.model.Incidencia
import com.example.gestiondeincidencias.db.model.NivelUrgencia
import com.example.gestiondeincidencias.db.model.Usuario
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddIncidenciaScreen {

    @Composable
    fun Render(
        onBackClick: () -> Unit = { },
        onSubmitClick: (incidencia: Incidencia) -> Unit = { },
        usuario: Usuario?
    ) {
        var titulo by remember { mutableStateOf("") }
        var descripcion by remember { mutableStateOf("") }
        var ubicacion by remember { mutableStateOf("") }
        var categoriaSeleccionada by remember { mutableStateOf("") }
        var nivelUrgenciaSeleccionado by remember { mutableStateOf("") }
        var estadoSeleccionado by remember { mutableStateOf(Estado.ABIERTA.displayName) }

        val fechaActual = remember {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        }

        var errorTitulo by remember { mutableStateOf(false) }
        var errorDescripcion by remember { mutableStateOf(false) }
        var errorCategoria by remember { mutableStateOf(false) }
        var errorUrgencia by remember { mutableStateOf(false) }
        var errorUbicacion by remember { mutableStateOf(false) }

        val primaryBlue = Color(0xFF2196F3)
        val darkBlue = Color(0xFF1565C0)

        val backgroundGradient = Brush.verticalGradient(
            colors = listOf(darkBlue, primaryBlue)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Nueva Incidencia",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    color = Color.White,
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "Completa los detalles",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = darkBlue,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        ReadOnlyField(label = "Fecha de Registro", value = fechaActual)

                        Spacer(modifier = Modifier.height(16.dp))

                        StyledTextField(
                            value = titulo,
                            onValueChange = { titulo = it; errorTitulo = false },
                            label = "Título de la incidencia *",
                            icon = Icons.Default.Edit,
                            isError = errorTitulo,
                            errorMessage = "El título es obligatorio",
                            primaryColor = primaryBlue
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        StyledTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it; errorDescripcion = false },
                            label = "Descripción detallada *",
                            icon = Icons.Default.Info,
                            isError = errorDescripcion,
                            errorMessage = "La descripción es obligatoria",
                            singleLine = false,
                            modifier = Modifier.height(120.dp),
                            primaryColor = primaryBlue
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        StyledDropdownField(
                            label = "Categoría *",
                            selectedValue = categoriaSeleccionada,
                            options = Categoria.entries.map { it.displayName },
                            onValueChange = { categoriaSeleccionada = it; errorCategoria = false },
                            isError = errorCategoria,
                            primaryColor = primaryBlue
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        StyledDropdownField(
                            label = "Nivel de Urgencia *",
                            selectedValue = nivelUrgenciaSeleccionado,
                            options = NivelUrgencia.entries.map { it.displayName },
                            onValueChange = {
                                nivelUrgenciaSeleccionado = it; errorUrgencia = false
                            },
                            isError = errorUrgencia,
                            primaryColor = primaryBlue
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        StyledTextField(
                            value = ubicacion,
                            onValueChange = { ubicacion = it; errorUbicacion = false },
                            label = "Ubicación / Aula *",
                            icon = Icons.Default.LocationOn,
                            isError = errorUbicacion,
                            errorMessage = "La ubicación es obligatoria",
                            primaryColor = primaryBlue
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
                                errorTitulo = titulo.isBlank()
                                errorDescripcion = descripcion.isBlank()
                                errorCategoria = categoriaSeleccionada.isBlank()
                                errorUrgencia = nivelUrgenciaSeleccionado.isBlank()
                                errorUbicacion = ubicacion.isBlank()

                                if (!errorTitulo && !errorDescripcion &&
                                    !errorCategoria && !errorUrgencia && !errorUbicacion
                                ) {
                                    val incidencia = Incidencia(
                                        id = 0,
                                        titulo = titulo,
                                        descripcion = descripcion,
                                        usuarioId = usuario?.id.toString(),
                                        categoria = categoriaSeleccionada,
                                        fechaRegistro = fechaActual,
                                        nivelUrgencia = nivelUrgenciaSeleccionado,
                                        estado = estadoSeleccionado,
                                        ubicacion = ubicacion
                                    )
                                    onSubmitClick(incidencia)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                "CREAR INCIDENCIA",
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, primaryBlue)
                        ) {
                            Text(
                                "CANCELAR",
                                color = primaryBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }






}
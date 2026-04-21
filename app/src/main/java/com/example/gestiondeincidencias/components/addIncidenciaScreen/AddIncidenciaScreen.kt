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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
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

    private val problemasPorCategoria = mapOf(
        Categoria.HARDWARE.displayName to listOf(
            "Ordenador no enciende" to NivelUrgencia.ALTA,
            "Pantalla rota" to NivelUrgencia.ALTA,
            "Teclado no funciona" to NivelUrgencia.MEDIA,
            "Ratón no funciona" to NivelUrgencia.BAJA,
            "Impresora no funciona" to NivelUrgencia.MEDIA,
            "Puerto USB dañado" to NivelUrgencia.BAJA,
            "Portátil no carga" to NivelUrgencia.ALTA,
            "Webcam no funciona" to NivelUrgencia.BAJA,
            "Altavoces no suenan" to NivelUrgencia.BAJA,
            "Monitor parpadea" to NivelUrgencia.MEDIA,
            "Ordenador muy lento" to NivelUrgencia.MEDIA,
            "Disco duro haciendo ruido" to NivelUrgencia.ALTA,
            "Ventilador muy ruidoso" to NivelUrgencia.MEDIA,
            "Tecla del teclado atascada" to NivelUrgencia.BAJA,
            "Cable de alimentación roto" to NivelUrgencia.ALTA,
            "Teclado escribe caracteres incorrectos" to NivelUrgencia.MEDIA,
            "Papel atascado en la impresora" to NivelUrgencia.MEDIA,
            "Impresora imprime en blanco" to NivelUrgencia.MEDIA,
            "Auriculares con micrófono no graban" to NivelUrgencia.MEDIA,
            "Tóner de impresora agotado" to NivelUrgencia.MEDIA,
        ),
        Categoria.SOFTWARE.displayName to listOf(
            "Error de sistema" to NivelUrgencia.ALTA,
            "Problemas con el correo" to NivelUrgencia.MEDIA,
            "Instalación de programa" to NivelUrgencia.BAJA,
            "Actualización pendiente" to NivelUrgencia.BAJA,
            "Virus detectado" to NivelUrgencia.ALTA,
            "Problema con la conexión" to NivelUrgencia.MEDIA,
            "Licencia expirada" to NivelUrgencia.MEDIA,
            "Software se cierra solo" to NivelUrgencia.MEDIA,
            "Archivos corruptos" to NivelUrgencia.ALTA,
            "Idioma incorrecto" to NivelUrgencia.BAJA,
            "Outlook no sincroniza el calendario" to NivelUrgencia.MEDIA,
            "No arranca después de actualización" to NivelUrgencia.ALTA,
            "Antivirus bloquea appa" to NivelUrgencia.MEDIA,
            "Barra de tareas desaparecida" to NivelUrgencia.MEDIA,
            "Actualizaciones de Windows no terminan" to NivelUrgencia.MEDIA,
        ),
        Categoria.CONECTIVIDAD.displayName to listOf(
            "Sin conexión a Internet" to NivelUrgencia.ALTA,
            "WiFi no funciona en aula" to NivelUrgencia.ALTA,
            "Conexión lenta" to NivelUrgencia.MEDIA,
            "No accede a carpetas red" to NivelUrgencia.ALTA,
            "VPN no conecta" to NivelUrgencia.ALTA,
            "Error de DNS" to NivelUrgencia.MEDIA,
            "Firewall bloquea App" to NivelUrgencia.MEDIA,
            "No puede enviar correos" to NivelUrgencia.ALTA,
            "WiFi pide pass constante" to NivelUrgencia.MEDIA,
            "Carpeta compartida pide contraseña" to NivelUrgencia.MEDIA,
            "Descarga de archivos grandes falla" to NivelUrgencia.MEDIA,
            "Correo tarda más de 10 min en llegar" to NivelUrgencia.MEDIA,
            "Página de la intranet no carga" to NivelUrgencia.ALTA,
        ),
        Categoria.USUARIOS.displayName to listOf(
            "Cuenta bloqueada" to NivelUrgencia.ALTA,
            "Olvido de contraseña" to NivelUrgencia.MEDIA,
            "Alta de nuevo usuario" to NivelUrgencia.BAJA,
            "Modificación de permisos" to NivelUrgencia.BAJA,
            "Cambio de datos" to NivelUrgencia.BAJA,
            "Acceso a carpeta protegida" to NivelUrgencia.BAJA,
            "Baja de usuario" to NivelUrgencia.BAJA,
            "No puede cambiar contraseña" to NivelUrgencia.MEDIA,
            "No puede acceder a la intranet" to NivelUrgencia.MEDIA,
        ),
        Categoria.INFRAESTRUCTURA.displayName to listOf(
            "Proyector no funciona" to NivelUrgencia.MEDIA,
            "Bombilla fundida" to NivelUrgencia.BAJA,
            "Persiana rota" to NivelUrgencia.BAJA,
            "Goteras en el techo" to NivelUrgencia.ALTA,
            "Calefacción no funciona" to NivelUrgencia.MEDIA,
            "Puerta no cierra bien" to NivelUrgencia.MEDIA,
            "Pantalla del proyector no baja" to NivelUrgencia.BAJA,
            "Muebles rotos" to NivelUrgencia.BAJA,
            "Humedad en la pared" to NivelUrgencia.BAJA,
            "Ventana no cierra" to NivelUrgencia.MEDIA,
            "Falta tiza/rotuladores" to NivelUrgencia.BAJA,
        )
    )

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
        var tipoDeProblema by remember { mutableStateOf("") }
        var nivelDeUrgencia by remember { mutableStateOf("") }
        var estadoSeleccionado by remember { mutableStateOf(Estado.ABIERTA.displayName) }

        val fechaActual = remember {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        }

        var errorTitulo by remember { mutableStateOf(false) }
        var errorDescription by remember { mutableStateOf(false) }
        var errorCategoria by remember { mutableStateOf(false) }
        var errorTipoProblema by remember { mutableStateOf(false) }
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

                        StyledDropdownField(
                            label = "Categoría *",
                            selectedValue = categoriaSeleccionada,
                            options = Categoria.entries.map { it.displayName },
                            onValueChange = { 
                                categoriaSeleccionada = it
                                errorCategoria = false
                                tipoDeProblema = ""
                                nivelDeUrgencia = ""
                            },
                            isError = errorCategoria,
                            primaryColor = primaryBlue
                        )

                        if (categoriaSeleccionada.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            val opciones = problemasPorCategoria[categoriaSeleccionada] ?: emptyList()
                            StyledDropdownField(
                                label = "Tipo de problema *",
                                selectedValue = tipoDeProblema,
                                options = opciones.map { it.first },
                                onValueChange = { selected ->
                                    tipoDeProblema = selected
                                    errorTipoProblema = false
                                    val urgencia = opciones.find { it.first == selected }?.second
                                    nivelDeUrgencia = urgencia?.displayName ?: ""
                                },
                                isError = errorTipoProblema,
                                primaryColor = primaryBlue
                            )
                        }

                        if (nivelDeUrgencia.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            ReadOnlyField(
                                "Nivel de Urgencia",
                                value = nivelDeUrgencia
                            )
                        }

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

                        Spacer(modifier = Modifier.height(16.dp))

                        StyledTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it; errorDescription = false },
                            label = "Descripción detallada *",
                            icon = Icons.Default.Info,
                            isError = errorDescription,
                            errorMessage = "La descripción es obligatoria",
                            singleLine = false,
                            modifier = Modifier.height(120.dp),
                            primaryColor = primaryBlue
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
                                errorTitulo = titulo.isBlank()
                                errorDescription = descripcion.isBlank()
                                errorCategoria = categoriaSeleccionada.isBlank()
                                errorTipoProblema = categoriaSeleccionada.isNotEmpty() && tipoDeProblema.isBlank()
                                errorUbicacion = ubicacion.isBlank()

                                if (!errorTitulo && !errorDescription &&
                                    !errorCategoria && !errorTipoProblema && !errorUbicacion
                                ) {
                                    val incidencia = Incidencia(
                                        id = 0,
                                        titulo = titulo,
                                        descripcion = "$tipoDeProblema: $descripcion",
                                        usuarioId = usuario?.id.toString(),
                                        categoria = categoriaSeleccionada,
                                        fechaRegistro = fechaActual,
                                        nivelUrgencia = nivelDeUrgencia,
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

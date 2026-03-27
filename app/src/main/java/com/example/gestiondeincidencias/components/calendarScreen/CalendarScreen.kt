package com.example.gestiondeincidencias.components.calendarScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestiondeincidencias.db.model.Incidencia
import dev.alejo.compose_calendar.CalendarEvent
import dev.alejo.compose_calendar.ComposeCalendar
import dev.alejo.compose_calendar.util.CalendarColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class CalendarScreen(private val incidencias: List<Incidencia>, private val navController: NavController) {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Render() {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        
        val allEvents = remember(incidencias) {
            incidencias.map {
                val date = try {
                    LocalDate.parse(it.fechaRegistro.split(" ")[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                } catch (e: Exception) {
                    LocalDate.now()
                }
                CalendarEvent(data = it, date = date)
            }
        }

        val selectedEvents = remember(selectedDate, allEvents) {
            allEvents.filter { it.date == selectedDate }
        }

        val calendarColors = CalendarColors(
            backgroundColor = Color.Transparent,
            contentColor = Color(0xFF1A1C1E),
            headerBackgroundColor = Color.Transparent,
            headerContentColor = Color(0xFF0D47A1),
            navigationContainerColor = Color(0xFFE3F2FD),
            navigationContentColor = Color(0xFF0D47A1),
            navigationDisableContainerColor = Color(0xFFF1F3F4),
            navigationDisableContentColor = Color(0xFF9AA0A6),
            eventBackgroundColor = Color(0xFF1976D2),
            eventContentColor = Color.White
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF0F7FF)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color(0xFF0D47A1)
                        )
                    }
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Agenda Mensual",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF0D47A1),
                            letterSpacing = (-0.5).sp
                        )
                        Text(
                            text = "Supervisión de incidencias registradas",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF5F6368),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    ComposeCalendar(
                        calendarColors = calendarColors,
                        events = allEvents,
                        onDayClick = { date, _ ->
                            selectedDate = date
                        },
                        eventIndicator = { event, position, _ ->
                            if (position < 2) {
                                val color = when (event.data?.nivelUrgencia) {
                                    "Alta" -> Color(0xFFD32F2F)
                                    "Media" -> Color(0xFFFBC02D)
                                    else -> Color(0xFF388E3C)
                                }
                                Box(
                                    Modifier
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                        .fillMaxWidth()
                                        .height(3.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(color)
                                )
                            }
                        }
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    shadowElevation = 12.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp, vertical = 28.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp, 24.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color(0xFF0D47A1))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = selectedDate.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("es", "ES")))
                                    .replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1C1E)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        if (selectedEvents.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.EventNote,
                                    contentDescription = null,
                                    modifier = Modifier.size(56.dp),
                                    tint = Color(0xFFD1D5DB)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "No hay incidencias para este día",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF9CA3AF)
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(bottom = 16.dp)
                            ) {
                                items(selectedEvents) { event ->
                                    event.data?.let { incidencia ->
                                        IncidenciaCalendarItem(incidencia, navController)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

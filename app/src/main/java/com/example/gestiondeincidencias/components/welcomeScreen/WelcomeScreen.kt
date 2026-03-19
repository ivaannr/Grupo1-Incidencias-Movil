package com.example.gestiondeincidencias.components.welcomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestiondeincidencias.ui.theme.Colors
import com.example.gestiondeincidencias.R

@Composable
fun WelcomeScreen(navController: NavController) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Colors.Primary, Colors.Secondary)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-150).dp, y = (-100).dp)
                .blur(80.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
        )
        
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterEnd)
                .offset(x = 100.dp, y = (-50).dp)
                .blur(60.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f))
        )

        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-50).dp, y = 50.dp)
                .blur(50.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.12f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(240.dp)
                            .blur(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                    )
                    
                    Surface(
                        modifier = Modifier
                            .size(190.dp)
                            .clip(CircleShape),
                        color = Color.White.copy(alpha = 0.15f),
                        tonalElevation = 0.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.zoom),
                                contentDescription = "Logo",
                                modifier = Modifier.size(140.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Gestor de Incidencias",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 44.sp,
                    letterSpacing = (-0.5).sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "IES Nº1",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                }
            }
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Colors.Primary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 15.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "COMENZAR",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

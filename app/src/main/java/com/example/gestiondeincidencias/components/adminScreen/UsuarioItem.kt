package com.example.gestiondeincidencias.components.adminScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonRemove
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
import com.example.gestiondeincidencias.db.model.Rol
import com.example.gestiondeincidencias.db.model.Usuario
import com.example.gestiondeincidencias.db.viewmodel.UsuarioViewModel

@Composable
fun UsuarioItem(
    usuario: Usuario,
    usuarioViewModel: UsuarioViewModel,
    navController: NavController
) {
    val roleColor = if (usuario.rol == Rol.ADMINISTRADOR) Color.Red else Color(0xFF1565C0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 6.dp)
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(roleColor)
            )

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(roleColor.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = roleColor)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = usuario.nombre,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(text = usuario.email, fontSize = 12.sp, color = Color.Gray)

                        Text(
                            text = usuario.rol.displayName,
                            fontSize = 11.sp,
                            color = roleColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(roleColor.copy(0.1f), shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 12.dp)
                        )

                    }
                }

                Row {
                    IconButton(
                        onClick = { navController.navigate("edit-usuario/${usuario.id}") },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFF1565C0).copy(alpha = 0f), CircleShape)
                    ) {
                        IconWrapper(color = Color(0xFF1565C0)) {
                            Icon(
                                Icons.Default.ManageAccounts,
                                contentDescription = "Editar",
                                tint = Color(0xFF1565C0),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(
                        onClick = { usuarioViewModel.delete(usuario) },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Red.copy(alpha = 0f), CircleShape)
                    ) {

                        IconWrapper(color = Color.Red) {
                            Icon(
                                Icons.Default.PersonRemove,
                                contentDescription = "Borrar",
                                tint = Color.Red,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
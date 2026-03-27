package com.example.gestiondeincidencias

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.gestiondeincidencias.components.incidenciasScreen.*
import com.example.gestiondeincidencias.db.AppDatabase
import com.example.gestiondeincidencias.db.viewmodel.UsuarioViewModel
import com.example.gestiondeincidencias.db.viewmodel.IncidenciaViewModel
import com.example.gestiondeincidencias.ui.theme.GestionDeIncidenciasTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.example.gestiondeincidencias.components.addIncidenciaScreen.AddIncidenciaScreen
import com.example.gestiondeincidencias.components.adminScreen.AdminScreen
import com.example.gestiondeincidencias.components.editIncidenciaScreen.EditIncidenciaScreen
import com.example.gestiondeincidencias.components.editUsuarioScreen.EditUsuarioScreen
import com.example.gestiondeincidencias.components.homeScreen.HomeScreen
import com.example.gestiondeincidencias.components.incidenciaDetalleScreen.IncidenciaDetalleScreen
import com.example.gestiondeincidencias.components.loginScreen.LoginScreen
import com.example.gestiondeincidencias.components.registerScreen.RegisterScreen
import com.example.gestiondeincidencias.components.welcomeScreen.WelcomeScreen
import androidx.core.content.edit
import com.example.gestiondeincidencias.components.calendarScreen.CalendarScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestionDeIncidenciasTheme {
                App()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ViewModelConstructorInComposable")
    @Composable
    fun App() {
        val context = LocalContext.current
        val navController = rememberNavController()
        val database = remember { AppDatabase.getInstance(context) }
        val usuarioViewModel = remember { UsuarioViewModel(database.usuarioDao()) }
        val incidenciasViewModel = remember { IncidenciaViewModel(database.incidenciaDao()) }
        val scope = rememberCoroutineScope()


        NavHost(navController = navController, startDestination = "welcome") {
            composable("welcome") {
                WelcomeScreen(navController)
            }

            composable("calendar") {
                CalendarScreen(incidenciasViewModel.incidencias.collectAsState().value, navController).Render()
            }

            composable("login") {
                LoginScreen(navController).Render(
                    onLoginClick = { identifier, password, remember ->
                         scope.launch {
                            try {
                                val user = usuarioViewModel.login(identifier, password)
                                if (remember) {
                                    val prefs = context.getSharedPreferences("prefs", MODE_PRIVATE)
                                    prefs.edit { putInt("rememberId", user.id) }
                                }
                                Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show()
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate("register")
                    }
                )
            }

            composable("register") {
                RegisterScreen(navController).Render(
                    onRegisterClick = { usuario ->
                        scope.launch {
                            try {
                                usuarioViewModel.registrar(usuario)
                                Toast.makeText(context, "Registro completado", Toast.LENGTH_SHORT).show()
                                navController.navigate("login") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error al registrar: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }
            
            composable("home") {
                HomeScreen().Render(
                    navController = navController,
                    usuario = usuarioViewModel.usuarioLoggeado.collectAsState().value,
                    onLogout = { usuarioViewModel.logout() },
                    incidencias = incidenciasViewModel.incidencias.collectAsState().value,
                    context = context,
                    usersViewModel = usuarioViewModel
                )
            }

            composable("see") {
                IncidenciasScreen().Render(
                    incidencias = incidenciasViewModel.incidencias.collectAsState().value,
                    onAddClick = { navController.navigate("add") },
                    onBackClick = { navController.popBackStack() },
                    onIncidenciaClick = { incidencia ->
                        navController.navigate("detail/${incidencia.id}")
                    }
                )
            }

            composable("add") {
                AddIncidenciaScreen().Render(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSubmitClick = {
                        incidenciasViewModel.insert(it)
                        navController.popBackStack()
                    },
                    usuarioViewModel.usuarioLoggeado.collectAsState().value
                )
            }

            composable("admin") {
                AdminScreen(navController, usuarioViewModel, incidenciasViewModel).Render()
            }

            composable(
                "detail/{incidenciaId}",
                arguments = listOf(navArgument("incidenciaId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("incidenciaId") ?: return@composable
                IncidenciaDetalleScreen(id, incidenciasViewModel, usuarioViewModel, navController)
            }

            composable(
                "edit-usuario/{usuarioId}",
                arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
            ) { backStackEntry ->
                val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: return@composable
                EditUsuarioScreen(navController, usuarioViewModel, usuarioId).Render()
            }

            composable(
                "edit-incidencia/{incidenciaId}",
                arguments = listOf(navArgument("incidenciaId") { type = NavType.IntType })
            ) { backStackEntry ->
                val incidenciaId = backStackEntry.arguments?.getInt("incidenciaId") ?: return@composable
                EditIncidenciaScreen(navController, incidenciasViewModel, incidenciaId).Render()
            }
        }
    }
}

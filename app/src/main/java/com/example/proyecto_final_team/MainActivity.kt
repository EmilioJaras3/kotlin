package com.example.proyecto_final_team

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_final_team.ui.navigation.NavGraph
import com.example.proyecto_final_team.ui.theme.ProyectofinalteamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectofinalteamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val database = com.example.proyecto_final_team.data.local.AppDatabase.getDatabase(applicationContext)
                    val repository = com.example.proyecto_final_team.data.repository.RoutineRepository(database.routineDao())
                    NavGraph(navController = navController, repository = repository)
                }
            }
        }
    }
}
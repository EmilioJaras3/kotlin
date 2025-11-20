package com.example.proyecto_final_team.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.proyecto_final_team.ui.screens.auth.LoginScreen
import com.example.proyecto_final_team.ui.screens.auth.RegisterScreen
import com.example.proyecto_final_team.ui.screens.detail.DetailScreen
import com.example.proyecto_final_team.ui.screens.home.HomeScreen
import com.example.proyecto_final_team.ui.screens.live.LiveClassesScreen
import com.example.proyecto_final_team.ui.screens.welcome.WelcomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getInt("routineId") ?: 0
            DetailScreen(navController, routineId)
        }
        composable(Screen.LiveClasses.route) {
            LiveClassesScreen(navController)
        }
    }
}

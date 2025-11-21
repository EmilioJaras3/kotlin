package com.example.proyecto_final_team.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.proyecto_final_team.ui.screens.admin.AddRoutineScreen
import com.example.proyecto_final_team.ui.screens.auth.LoginScreen
import com.example.proyecto_final_team.ui.screens.auth.RegisterScreen
import com.example.proyecto_final_team.ui.screens.detail.DetailScreen
import com.example.proyecto_final_team.ui.screens.following.FollowingScreen
import com.example.proyecto_final_team.ui.screens.home.HomeScreen
import com.example.proyecto_final_team.ui.screens.live.LiveClassesScreen
import com.example.proyecto_final_team.ui.screens.player.PlayerScreen
import com.example.proyecto_final_team.ui.screens.saved.SavedScreen
import com.example.proyecto_final_team.ui.screens.trainers.AllTrainersScreen
import com.example.proyecto_final_team.ui.screens.welcome.WelcomeScreen
import com.example.proyecto_final_team.viewmodel.FavoritesViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val favoritesViewModel: FavoritesViewModel = viewModel()
    
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
            HomeScreen(navController, favoritesViewModel)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getInt("routineId") ?: 0
            DetailScreen(navController, routineId, favoritesViewModel)
        }
        composable(Screen.LiveClasses.route) {
            LiveClassesScreen(navController)
        }
        composable(Screen.Saved.route) {
            SavedScreen(navController, favoritesViewModel)
        }
        composable(Screen.Following.route) {
            FollowingScreen(navController)
        }
        composable(Screen.AllTrainers.route) {
            AllTrainersScreen(navController)
        }
        composable(
            route = Screen.Player.route,
            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getInt("routineId") ?: 0
            PlayerScreen(navController, routineId, favoritesViewModel)
        }
        composable(Screen.AddRoutine.route) {
            AddRoutineScreen(navController)
        }
    }
}

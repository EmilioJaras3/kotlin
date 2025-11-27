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

import com.example.proyecto_final_team.data.repository.RoutineRepository
import com.example.proyecto_final_team.viewmodel.HomeViewModel
import com.example.proyecto_final_team.viewmodel.HomeViewModelFactory

import com.example.proyecto_final_team.viewmodel.FavoritesViewModelFactory
import android.app.Application
import androidx.compose.ui.platform.LocalContext

@Composable
fun NavGraph(navController: NavHostController, repository: RoutineRepository) {
    val context = LocalContext.current
    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(context.applicationContext as Application, repository)
    )
    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(repository)
    )
    
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController, homeViewModel)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController, homeViewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController, favoritesViewModel, homeViewModel)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getInt("routineId") ?: 0
            DetailScreen(navController, routineId, favoritesViewModel, homeViewModel)
        }
        composable(Screen.LiveClasses.route) {
            LiveClassesScreen(navController, homeViewModel)
        }
        composable(Screen.Saved.route) {
            SavedScreen(navController, favoritesViewModel)
        }
        composable(Screen.Following.route) {
            FollowingScreen(navController, homeViewModel)
        }
        composable(Screen.AllTrainers.route) {
            AllTrainersScreen(navController, homeViewModel)
        }
        composable(
            route = Screen.Player.route,
            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getInt("routineId") ?: 0
            PlayerScreen(navController, routineId, favoritesViewModel, homeViewModel)
        }
        composable(Screen.AddRoutine.route) {
            AddRoutineScreen(navController, homeViewModel)
        }
    }
}

package com.example.proyecto_final_team.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Detail : Screen("detail/{routineId}") {
        fun createRoute(routineId: Int) = "detail/$routineId"
    }
    object LiveClasses : Screen("live_classes")
    object Saved : Screen("saved")
    object Following : Screen("following")
    object AllTrainers : Screen("all_trainers")
    object Player : Screen("player/{routineId}") {
        fun createRoute(routineId: Int) = "player/$routineId"
    }
    object AddRoutine : Screen("add_routine")
}

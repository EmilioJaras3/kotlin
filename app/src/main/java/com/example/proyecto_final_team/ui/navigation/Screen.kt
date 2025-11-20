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
}

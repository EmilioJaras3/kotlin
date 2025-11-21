package com.example.proyecto_final_team.model

data class Routine(
    val id: Int,
    val title: String,
    val category: String,
    val duration: String,
    val level: String,
    val equipment: String,
    val description: String,
    val trainer: Trainer,
    val imageUrl: String,
    val videoUrl: String = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    val isFavorite: Boolean = false
)

data class Trainer(
    val id: Int,
    val name: String,
    val role: String,
    val imageUrl: String
)

data class LiveClass(
    val id: Int,
    val title: String,
    val trainerName: String,
    val time: String,
    val isLiveNow: Boolean,
    val imageUrl: String
)

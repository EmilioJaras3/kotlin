package com.example.proyecto_final_team.data

import com.example.proyecto_final_team.model.LiveClass
import com.example.proyecto_final_team.model.Routine
import com.example.proyecto_final_team.model.Trainer
import com.example.proyecto_final_team.model.User

object FakeData {
    val users = mutableListOf<User>()
    
    val trainers = listOf(
        Trainer(1, "John Doe", "Expert in HIIT", "https://randomuser.me/api/portraits/men/1.jpg"),
        Trainer(2, "Jane Smith", "Yoga Instructor", "https://randomuser.me/api/portraits/women/2.jpg")
    )
    
    val routines = mutableListOf(
        Routine(
            id = 1,
            title = "Full Body HIIT",
            category = "HIIT",
            duration = "20 min",
            level = "Intermediate",
            equipment = "Dumbbells",
            description = "A high-intensity interval training session to burn calories.",
            trainer = trainers[0],
            imageUrl = "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=2070&auto=format&fit=crop",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            isFavorite = false
        ),
        Routine(
            id = 2,
            title = "Morning Yoga",
            category = "Yoga",
            duration = "15 min",
            level = "Beginner",
            equipment = "Mat",
            description = "Start your day with this relaxing yoga flow.",
            trainer = trainers[1],
            imageUrl = "https://images.unsplash.com/photo-1544367563-121955377d68?q=80&w=2070&auto=format&fit=crop",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", // Another sample video
            isFavorite = true
        )
    )

    val liveClasses = listOf(
       LiveClass(
           id = 1,
           title = "Power Yoga",
           trainerName = "Jane Smith",
           time = "10:00 AM",
           isLiveNow = true,
           imageUrl = "https://images.unsplash.com/photo-1544367563-121955377d68?q=80&w=2070&auto=format&fit=crop",
           isReminderSet = false
       ),
       LiveClass(
           id = 2,
           title = "Cardio Blast",
           trainerName = "John Doe",
           time = "02:00 PM",
           isLiveNow = false,
           imageUrl = "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=2070&auto=format&fit=crop",
           isReminderSet = true
       )
    )
}

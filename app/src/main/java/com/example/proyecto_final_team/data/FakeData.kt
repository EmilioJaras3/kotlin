package com.example.proyecto_final_team.data

import com.example.proyecto_final_team.model.LiveClass
import com.example.proyecto_final_team.model.Routine
import com.example.proyecto_final_team.model.Trainer
import com.example.proyecto_final_team.model.User

object FakeData {
    val users = mutableListOf<User>()
    val trainers = listOf<Trainer>()
    val routines = mutableListOf<Routine>()
    val liveClasses = listOf<LiveClass>()
}

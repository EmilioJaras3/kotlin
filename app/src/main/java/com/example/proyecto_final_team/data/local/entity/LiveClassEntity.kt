package com.example.proyecto_final_team.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyecto_final_team.model.LiveClass

@Entity(tableName = "live_classes")
data class LiveClassEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val trainerName: String,
    val time: String,
    val isLiveNow: Boolean,
    val imageUrl: String
) {
    fun toDomain() = LiveClass(id, title, trainerName, time, isLiveNow, imageUrl)
}

fun LiveClass.toEntity() = LiveClassEntity(id, title, trainerName, time, isLiveNow, imageUrl)

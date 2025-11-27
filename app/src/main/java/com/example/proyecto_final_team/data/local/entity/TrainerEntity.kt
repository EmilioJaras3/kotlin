package com.example.proyecto_final_team.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyecto_final_team.model.Trainer

@Entity(tableName = "trainers")
data class TrainerEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val role: String,
    val imageUrl: String,
    val isFollowed: Boolean = false
) {
    fun toDomain() = Trainer(id, name, role, imageUrl)
}

fun Trainer.toEntity() = TrainerEntity(id, name, role, imageUrl)

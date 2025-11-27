package com.example.proyecto_final_team.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyecto_final_team.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val password: String
) {
    fun toDomain() = User(email, password)
}

fun User.toEntity() = UserEntity(email, password)

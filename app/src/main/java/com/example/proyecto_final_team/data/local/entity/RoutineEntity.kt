package com.example.proyecto_final_team.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyecto_final_team.model.Routine
import com.example.proyecto_final_team.model.Trainer
import androidx.room.Embedded
import androidx.room.Relation

@Entity(tableName = "routines")
data class RoutineEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val category: String,
    val duration: String,
    val level: String,
    val equipment: String,
    val description: String,
    val trainerId: Int, // Foreign key reference
    val imageUrl: String,
    val videoUrl: String,
    val isFavorite: Boolean
)



data class RoutineWithTrainer(
    @Embedded val routine: RoutineEntity,
    @Relation(
        parentColumn = "trainerId",
        entityColumn = "id"
    )
    val trainer: TrainerEntity
) {
    fun toDomain(): Routine {
        return Routine(
            id = routine.id,
            title = routine.title,
            category = routine.category,
            duration = routine.duration,
            level = routine.level,
            equipment = routine.equipment,
            description = routine.description,
            trainer = trainer.toDomain(),
            imageUrl = routine.imageUrl,
            videoUrl = routine.videoUrl,
            isFavorite = routine.isFavorite
        )
    }
}

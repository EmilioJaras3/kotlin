package com.example.proyecto_final_team.data.repository

import com.example.proyecto_final_team.data.FakeData
import com.example.proyecto_final_team.data.local.dao.RoutineDao
import com.example.proyecto_final_team.data.local.entity.RoutineEntity
import com.example.proyecto_final_team.data.local.entity.toEntity
import com.example.proyecto_final_team.model.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.proyecto_final_team.model.LiveClass
import com.example.proyecto_final_team.model.User
import com.example.proyecto_final_team.model.Trainer

class RoutineRepository(private val routineDao: RoutineDao) {

    val routines: Flow<List<Routine>> = routineDao.getRoutines().map { list ->
        list.map { it.toDomain() }
    }

    fun getRoutinesByCategory(category: String): Flow<List<Routine>> {
        return routineDao.getRoutinesByCategory(category).map { list ->
            list.map { it.toDomain() }
        }
    }

    suspend fun getRoutineById(id: Int): Routine? {
        return routineDao.getRoutineById(id)?.toDomain()
    }

    suspend fun addRoutine(routine: Routine) {
        val routineEntity = RoutineEntity(
            id = routine.id,
            title = routine.title,
            category = routine.category,
            duration = routine.duration,
            level = routine.level,
            equipment = routine.equipment,
            description = routine.description,
            trainerId = routine.trainer.id,
            imageUrl = routine.imageUrl,
            videoUrl = routine.videoUrl,
            isFavorite = routine.isFavorite
        )
        routineDao.insertRoutine(routineEntity)
    }

    fun getTrainers(): Flow<List<Trainer>> {
        return routineDao.getTrainers().map { list ->
            list.map { it.toDomain() }
        }
    }

    suspend fun toggleFollowTrainer(trainerId: Int, isFollowed: Boolean) {
        routineDao.updateTrainerFollowStatus(trainerId, isFollowed)
    }

    suspend fun getLiveClasses(): List<LiveClass> {
        return routineDao.getLiveClasses().map { it.toDomain() }
    }

    suspend fun getUser(email: String): User? {
        return routineDao.getUser(email)?.toDomain()
    }

    suspend fun addUser(user: User) {
        routineDao.insertUser(user.toEntity())
    }

    suspend fun initializeData() {
        // No initial data seeding as per user request
    }
}

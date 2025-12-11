package com.example.proyecto_final_team.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.proyecto_final_team.data.local.entity.RoutineEntity
import com.example.proyecto_final_team.data.local.entity.RoutineWithTrainer
import com.example.proyecto_final_team.data.local.entity.TrainerEntity
import com.example.proyecto_final_team.data.local.entity.LiveClassEntity
import com.example.proyecto_final_team.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Transaction
    @Query("SELECT * FROM routines")
    fun getRoutines(): Flow<List<RoutineWithTrainer>>

    @Transaction
    @Query("SELECT * FROM routines WHERE category = :category")
    fun getRoutinesByCategory(category: String): Flow<List<RoutineWithTrainer>>

    @Transaction
    @Query("SELECT * FROM routines WHERE id = :id")
    suspend fun getRoutineById(id: Int): RoutineWithTrainer?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: RoutineEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainer(trainer: TrainerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRoutines(routines: List<RoutineEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTrainers(trainers: List<TrainerEntity>)
    
    @Query("SELECT * FROM trainers")
    fun getTrainers(): Flow<List<TrainerEntity>>

    @Query("UPDATE trainers SET isFollowed = :isFollowed WHERE id = :trainerId")
    suspend fun updateTrainerFollowStatus(trainerId: Int, isFollowed: Boolean)

    @Query("SELECT * FROM live_classes")
    fun getLiveClasses(): Flow<List<LiveClassEntity>>

    @Query("UPDATE live_classes SET isReminderSet = :isReminderSet WHERE id = :id")
    suspend fun updateLiveClassReminder(id: Int, isReminderSet: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLiveClasses(liveClasses: List<LiveClassEntity>)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUser(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT COUNT(*) FROM routines")
    suspend fun getRoutineCount(): Int
}

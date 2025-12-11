package com.example.proyecto_final_team.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyecto_final_team.data.local.dao.RoutineDao
import com.example.proyecto_final_team.data.local.entity.RoutineEntity
import com.example.proyecto_final_team.data.local.entity.TrainerEntity

import com.example.proyecto_final_team.data.local.entity.LiveClassEntity

import com.example.proyecto_final_team.data.local.entity.UserEntity

@Database(entities = [RoutineEntity::class, TrainerEntity::class, LiveClassEntity::class, UserEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

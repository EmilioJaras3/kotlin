package com.example.proyecto_final_team.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_team.data.dataStore
import com.example.proyecto_final_team.model.Routine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

import com.example.proyecto_final_team.data.repository.RoutineRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoritesViewModel(application: Application, private val repository: RoutineRepository) : AndroidViewModel(application) {
    private val _favoriteRoutines = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteRoutines: StateFlow<Set<Int>> = _favoriteRoutines.asStateFlow()

    private val FAVORITES_KEY = stringPreferencesKey("favorites_list")

    init {
        viewModelScope.launch {
            getApplication<Application>().dataStore.data
                .map { preferences ->
                    val favoritesString = preferences[FAVORITES_KEY] ?: ""
                    if (favoritesString.isNotEmpty()) {
                        favoritesString.split(",").mapNotNull { it.toIntOrNull() }.toSet()
                    } else {
                        emptySet()
                    }
                }
                .collect { favorites ->
                    _favoriteRoutines.value = favorites
                }
        }
    }

    fun toggleFavorite(routineId: Int) {
        viewModelScope.launch {
            val currentFavorites = _favoriteRoutines.value.toMutableSet()
            if (currentFavorites.contains(routineId)) {
                currentFavorites.remove(routineId)
            } else {
                currentFavorites.add(routineId)
            }
            _favoriteRoutines.value = currentFavorites
            saveFavorites(currentFavorites)
        }
    }

    private suspend fun saveFavorites(favorites: Set<Int>) {
        getApplication<Application>().dataStore.edit { preferences ->
            preferences[FAVORITES_KEY] = favorites.joinToString(",")
        }
    }

    fun isFavorite(routineId: Int): Boolean {
        return _favoriteRoutines.value.contains(routineId)
    }

    suspend fun getFavoriteRoutinesList(): List<Routine> {
        val favorites = _favoriteRoutines.value
        // This is not efficient if we have many routines, but for now it's fine.
        // Better would be to have a query in DAO: SELECT * FROM routines WHERE id IN (:ids)
        // But I'll just fetch all and filter, or fetch individually.
        // Since I have getRoutineById, I can fetch them.
        return favorites.mapNotNull { repository.getRoutineById(it) }
    }
}

class FavoritesViewModelFactory(private val application: Application, private val repository: RoutineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

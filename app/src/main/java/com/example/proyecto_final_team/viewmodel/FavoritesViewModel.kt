package com.example.proyecto_final_team.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_team.data.FakeData
import com.example.proyecto_final_team.model.Routine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {
    private val _favoriteRoutines = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteRoutines: StateFlow<Set<Int>> = _favoriteRoutines.asStateFlow()

    fun toggleFavorite(routineId: Int) {
        viewModelScope.launch {
            val currentFavorites = _favoriteRoutines.value.toMutableSet()
            if (currentFavorites.contains(routineId)) {
                currentFavorites.remove(routineId)
            } else {
                currentFavorites.add(routineId)
            }
            _favoriteRoutines.value = currentFavorites
        }
    }

    fun isFavorite(routineId: Int): Boolean {
        return _favoriteRoutines.value.contains(routineId)
    }

    fun getFavoriteRoutines(): List<Routine> {
        return FakeData.routines.filter { _favoriteRoutines.value.contains(it.id) }
    }
}

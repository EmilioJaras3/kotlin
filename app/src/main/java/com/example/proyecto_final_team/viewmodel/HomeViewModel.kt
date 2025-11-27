package com.example.proyecto_final_team.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_team.data.repository.RoutineRepository
import com.example.proyecto_final_team.model.Routine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RoutineRepository) : ViewModel() {

    private val _routines = MutableStateFlow<List<Routine>>(emptyList())
    val routines: StateFlow<List<Routine>> = _routines.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeData()
            loadRoutines()
        }
    }

    fun selectCategory(category: String) {
        if (_selectedCategory.value == category) {
            _selectedCategory.value = null // Deselect if already selected
        } else {
            _selectedCategory.value = category
        }
        loadRoutines()
    }

    private fun loadRoutines() {
        viewModelScope.launch {
            val category = _selectedCategory.value
            if (category == null) {
                repository.routines.collectLatest {
                    _routines.value = it
                }
            } else {
                repository.getRoutinesByCategory(category).collectLatest {
                    _routines.value = it
                }
            }
        }
    }

    suspend fun getRoutineById(id: Int): Routine? {
        return repository.getRoutineById(id)
    }

    fun addRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.addRoutine(routine)
            // Refresh routines if needed, but Flow should handle it automatically if we were observing database.
            // However, loadRoutines() uses collectLatest which might need a trigger if it's not observing a Room Flow directly (it is).
            // But we are collecting a Flow from Room, so it should update automatically.
        }
    }

    val trainers = repository.getTrainers()

    fun toggleFollowTrainer(trainerId: Int, isFollowed: Boolean) {
        viewModelScope.launch {
            repository.toggleFollowTrainer(trainerId, isFollowed)
        }
    }

    suspend fun getLiveClasses(): List<com.example.proyecto_final_team.model.LiveClass> {
        return repository.getLiveClasses()
    }

    suspend fun getUser(email: String): com.example.proyecto_final_team.model.User? {
        return repository.getUser(email)
    }

    fun addUser(user: com.example.proyecto_final_team.model.User) {
        viewModelScope.launch {
            repository.addUser(user)
        }
    }
}

class HomeViewModelFactory(private val repository: RoutineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.example.proyecto_final_team

import com.example.proyecto_final_team.data.repository.RoutineRepository
import com.example.proyecto_final_team.model.LiveClass
import com.example.proyecto_final_team.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var repository: RoutineRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `toggleLiveClassReminder calls repository with correct negated status`() = runTest {
        // Arrange
        val classId = 1
        val currentStatus = false
        // Act
        viewModel.toggleLiveClassReminder(classId, currentStatus)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        verify(repository).toggleLiveClassReminder(classId, true)
    }
}

package com.example.proyecto_final_team.ui.screens.saved

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyecto_final_team.model.Routine
import com.example.proyecto_final_team.ui.navigation.Screen
import com.example.proyecto_final_team.ui.theme.AccentOrange
import com.example.proyecto_final_team.viewmodel.FavoritesViewModel
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(navController: NavController, favoritesViewModel: FavoritesViewModel) {
    val favoriteIds by favoritesViewModel.favoriteRoutines.collectAsState()
    val favoriteRoutines by produceState<List<Routine>>(initialValue = emptyList(), key1 = favoriteIds) {
        value = favoritesViewModel.getFavoriteRoutinesList()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Guardados", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (favoriteRoutines.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No tienes rutinas guardadas", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(favoriteRoutines) { routine ->
                    SavedRoutineItem(routine, navController, favoritesViewModel)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun SavedRoutineItem(routine: Routine, navController: NavController, favoritesViewModel: FavoritesViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.Detail.createRoute(routine.id)) }
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(routine.imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = routine.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = routine.category, color = Color.Gray, fontSize = 12.sp)
            Text(text = routine.duration, color = AccentOrange, fontSize = 12.sp)
        }
        IconButton(onClick = { favoritesViewModel.toggleFavorite(routine.id) }) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = "Remove from favorites",
                tint = AccentOrange
            )
        }
    }
}

package com.example.proyecto_final_team.ui.screens.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.proyecto_final_team.data.FakeData
import com.example.proyecto_final_team.ui.theme.AccentBlue
import com.example.proyecto_final_team.ui.theme.PrimaryGreen
import com.example.proyecto_final_team.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(navController: NavController, routineId: Int, favoritesViewModel: FavoritesViewModel) {
    val routine = FakeData.routines.find { it.id == routineId } ?: return
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    val isFavorite = favoritesViewModel.favoriteRoutines.collectAsState().value.contains(routineId)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reproduciendo", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { favoritesViewModel.toggleFavorite(routineId) }) {
                        Icon(
                            if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) AccentBlue else Color.Gray
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Image placeholder for video
            Image(
                painter = rememberAsyncImagePainter(routine.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(24.dp))
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = routine.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = routine.trainer.name,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Progress bar
            Slider(
                value = progress,
                onValueChange = { progress = it },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${(progress * 15).toInt()}:00", fontSize = 12.sp, color = Color.Gray)
                Text(routine.duration, fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Play/Pause button
            FloatingActionButton(
                onClick = {
                    isPlaying = !isPlaying
                    if (isPlaying && progress < 1f) {
                        progress += 0.1f
                    }
                },
                containerColor = PrimaryGreen,
                modifier = Modifier.size(72.dp)
            ) {
                Icon(
                    if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = routine.description,
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

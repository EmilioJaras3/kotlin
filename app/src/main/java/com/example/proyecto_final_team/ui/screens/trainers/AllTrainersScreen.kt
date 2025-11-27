package com.example.proyecto_final_team.ui.screens.trainers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.proyecto_final_team.viewmodel.HomeViewModel
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue
import com.example.proyecto_final_team.model.Trainer
import com.example.proyecto_final_team.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTrainersScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val trainersList by homeViewModel.trainers.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Todos los Entrenadores", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(trainersList) { trainer ->
                TrainerCard(trainer, homeViewModel)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TrainerCard(trainer: Trainer, homeViewModel: HomeViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(trainer.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = trainer.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = trainer.role, color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { homeViewModel.toggleFollowTrainer(trainer.id, !trainer.isFollowed) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (trainer.isFollowed) Color.Gray else PrimaryGreen
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(if (trainer.isFollowed) "Siguiendo" else "Seguir")
            }
        }
    }
}

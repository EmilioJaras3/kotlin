package com.example.proyecto_final_team.ui.screens.live

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.proyecto_final_team.model.LiveClass
import com.example.proyecto_final_team.ui.theme.AccentOrange
import com.example.proyecto_final_team.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveClassesScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val liveClasses by produceState<List<LiveClass>>(initialValue = emptyList()) {
        value = homeViewModel.getLiveClasses()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Clases", fontWeight = FontWeight.Bold) },
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
            item {
                // Filter chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(selected = true, label = "Todo")
                    FilterChip(selected = false, label = "Entrenamineto")
                    FilterChip(selected = false, label = "otro")
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Encurso ahora",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                val liveNow = liveClasses.find { it.isLiveNow }
                if (liveNow != null) {
                    LiveNowCard(liveNow)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Proximo hoy",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(liveClasses.filter { !it.isLiveNow }) { liveClass ->
                UpcomingClassCard(liveClass)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun FilterChip(selected: Boolean, label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) Color(0xFFE0F2F1) else Color(0xFFF3F4F6))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = if (selected) PrimaryGreen else Color.Gray,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun LiveNowCard(liveClass: LiveClass) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = liveClass.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = liveClass.trainerName, color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text("Entrar")
                    }
                }
                Image(
                    painter = rememberAsyncImagePainter(liveClass.imageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Now", color = PrimaryGreen, fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                        .height(4.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(2.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .fillMaxHeight()
                            .background(PrimaryGreen, RoundedCornerShape(2.dp))
                    )
                }
                Text("15:30", color = Color.Gray)
            }
        }
    }
}

@Composable
fun UpcomingClassCard(liveClass: LiveClass) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = liveClass.title, fontWeight = FontWeight.Bold)
            Text(text = liveClass.trainerName, color = Color.Gray, fontSize = 12.sp)
            Text(text = "Inicia a las ${liveClass.time}", color = PrimaryGreen, fontSize = 12.sp)
        }
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F2F1)),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            modifier = Modifier.height(32.dp)
        ) {
            Text("Recordatorio", color = PrimaryGreen, fontSize = 12.sp)
        }
    }
}

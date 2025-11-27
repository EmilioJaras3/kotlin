package com.example.proyecto_final_team.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
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
import com.example.proyecto_final_team.viewmodel.HomeViewModel
import com.example.proyecto_final_team.model.Routine
import com.example.proyecto_final_team.ui.navigation.Screen
import com.example.proyecto_final_team.ui.theme.AccentOrange
import com.example.proyecto_final_team.ui.theme.PrimaryGreen
import com.example.proyecto_final_team.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, favoritesViewModel: FavoritesViewModel, homeViewModel: HomeViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val routines by homeViewModel.routines.collectAsState()
    val selectedCategory by homeViewModel.selectedCategory.collectAsState()
    
    val filteredRoutines = remember(searchQuery, routines) {
        if (searchQuery.isBlank()) {
            routines
        } else {
            routines.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true) ||
                it.trainer.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentColor = Color.Gray
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text("Inicio", color = PrimaryGreen, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { navController.navigate(Screen.Home.route) })
                    Text("Guardados", modifier = Modifier.clickable { navController.navigate(Screen.Saved.route) })
                    Text("Seguidos", modifier = Modifier.clickable { navController.navigate(Screen.Following.route) })
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Bienvenido",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = { navController.navigate(Screen.AddRoutine.route) },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("+ Video")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar rutinas o entrenadores...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFF3F4F6)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF3F4F6),
                        unfocusedContainerColor = Color(0xFFF3F4F6),
                        disabledContainerColor = Color(0xFFF3F4F6),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                // Categories
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Dynamic categories can be fetched from ViewModel if needed, or just allow user to add them.
                    // For now, we'll keep it empty or simple as requested to remove hardcoded ones.
                    // User said "remove pre-established routines of yoga, core, etc" and "remove hardcoded categories".
                    // We will leave this empty for now or just show categories from existing routines if we implemented that logic.
                    // Since we cleared data, there are no routines, so no categories to show yet.
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Explora entrenador",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Ver todo",
                        color = PrimaryGreen,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { navController.navigate(Screen.AllTrainers.route) }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(filteredRoutines) { routine ->
                TrainerRoutineItem(routine, favoritesViewModel) {
                    navController.navigate(Screen.Detail.createRoute(routine.id))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RoutineCard(routine: Routine, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(routine.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = routine.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = routine.duration, color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                    modifier = Modifier.fillMaxWidth().height(36.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("iniciar", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun LibraryCard(title: String, color: Color) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).background(Color.White, CircleShape))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TrainerRoutineItem(routine: Routine, favoritesViewModel: FavoritesViewModel, onClick: () -> Unit) {
    val isFavorite = favoritesViewModel.favoriteRoutines.collectAsState().value.contains(routine.id)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(routine.imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = routine.title, fontWeight = FontWeight.Bold)
            Text(text = "${routine.duration} • ${routine.category}", color = Color.Gray, fontSize = 12.sp)
        }
        IconButton(onClick = { favoritesViewModel.toggleFavorite(routine.id) }) {
            Icon(
                if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                tint = if (isFavorite) AccentOrange else Color.Gray
            )
        }
    }
}

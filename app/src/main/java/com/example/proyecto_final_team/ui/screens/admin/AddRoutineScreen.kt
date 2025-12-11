package com.example.proyecto_final_team.ui.screens.admin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_final_team.viewmodel.HomeViewModel
import com.example.proyecto_final_team.model.Trainer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.proyecto_final_team.model.Routine
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.proyecto_final_team.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineScreen(navController: NavController, homeViewModel: HomeViewModel) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("General") }
    var expandedLevel by remember { mutableStateOf(false) }
    val levels = listOf("Beginner", "Intermediate", "Advanced", "General")

    val trainers by homeViewModel.trainers.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var imageUrl by remember { mutableStateOf("https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=2070&auto=format&fit=crop") }
    var videoUri by remember { mutableStateOf<android.net.Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        videoUri = uri
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Subir Video", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Detalles de la Rutina", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expandedLevel,
                onExpandedChange = { expandedLevel = !expandedLevel },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = level,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Nivel") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLevel) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedLevel,
                    onDismissRequest = { expandedLevel = false }
                ) {
                    levels.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                level = selectionOption
                                expandedLevel = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duración (min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (videoUri != null) "Video Seleccionado" else "Seleccionar Video")
            }
            if (videoUri != null) {
                Text(
                    text = "URI: ${videoUri?.lastPathSegment}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty() && category.isNotEmpty()) {
                        scope.launch(Dispatchers.IO) {
                            val newId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt() // Simple ID generation
                            val newRoutine = Routine(
                                id = newId,
                                title = title,
                                category = category,
                                duration = "$duration min",
                                level = level,
                                equipment = "None",
                                description = description,
                                trainer = trainers.firstOrNull() ?: Trainer(0, "Trainer", "Unknown", ""), // Fallback if no trainers
                                imageUrl = imageUrl,
                                videoUrl = videoUri?.toString()
                                    ?: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                                isFavorite = false
                            )
                            
                            // Try to take persistable permission for the video URI if it exists
                            videoUri?.let { uri ->
                                try {
                                    val contentResolver = context.contentResolver
                                    val takeFlags: Int = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    contentResolver.takePersistableUriPermission(uri, takeFlags)
                                } catch (e: Exception) {
                                    // Ignore if we can't take permission (e.g. if it's not a document URI)
                                    e.printStackTrace()
                                }
                            }

                            withContext(Dispatchers.Main) {
                                homeViewModel.addRoutine(newRoutine)
                                navController.popBackStack()
                                Toast.makeText(context, "Rutina agregada exitosamente", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Por favor completa el título y la categoría", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.run { buttonColors(containerColor = PrimaryGreen) },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Subir Rutina", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

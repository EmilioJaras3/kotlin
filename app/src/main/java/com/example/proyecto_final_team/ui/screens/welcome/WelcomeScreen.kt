package com.example.proyecto_final_team.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyecto_final_team.ui.navigation.Screen
import com.example.proyecto_final_team.ui.theme.DarkButton
import com.example.proyecto_final_team.ui.theme.PrimaryGreen

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = rememberAsyncImagePainter("https://images.unsplash.com/photo-1506126613408-eca07ce68773?q=80&w=1999&auto=format&fit=crop"),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Encuentra tu calma.",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "guias de relajacion y encuentrar la paz.",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { navController.navigate(Screen.Register.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(text = "Registrate", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkButton),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(text = "Inicia sesión", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.size(12.dp).background(Color.White.copy(alpha = 0.5f), androidx.compose.foundation.shape.CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.size(12.dp).background(Color.White, androidx.compose.foundation.shape.CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.size(12.dp).background(Color.White.copy(alpha = 0.5f), androidx.compose.foundation.shape.CircleShape))
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

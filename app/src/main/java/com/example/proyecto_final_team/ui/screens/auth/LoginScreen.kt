package com.example.proyecto_final_team.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_final_team.ui.navigation.Screen
import com.example.proyecto_final_team.ui.theme.PrimaryGreen

import com.example.proyecto_final_team.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, homeViewModel: HomeViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0F2F1), Color(0xFFB2DFDB), Color(0xFF80CBC4))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Auto mejora",
                color = Color.Gray,
                fontSize = 24.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Bienvenido de regreso",
                color = Color.DarkGray,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Email",
                modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = email,
                onValueChange = { 
                    email = it
                    emailError = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(24.dp),
                isError = emailError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Red
                )
            )
            if (emailError) {
                Text(
                    text = "El email es requerido",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Password",
                modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = password,
                onValueChange = { 
                    password = it
                    passwordError = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(24.dp),
                isError = passwordError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Red
                )
            )
            if (passwordError) {
                Text(
                    text = "La contraseña es requerida",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            if (loginError != null) {
                Text(
                    text = loginError!!,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { 
                    emailError = email.isEmpty()
                    passwordError = password.isEmpty()
                    loginError = null
                    
                    if (!emailError && !passwordError) {
                        scope.launch {
                            val user = homeViewModel.getUser(email)
                            if (user != null && user.password == password) {
                                navController.navigate(Screen.Home.route)
                            } else {
                                loginError = "Credenciales inválidas"
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA7D7E7)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(text = "Log in", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Aun no te haz registrado ", color = Color.Black)
                Text(
                    text = "Register",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate(Screen.Register.route) }
                )
            }
        }
    }
}

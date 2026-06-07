package com.example.firstaidapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    mainActivity: MainActivity,
    onLoginSuccess: (String) -> Unit,
    onGoToSignup: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .background(Color(0xFFFAFAFA)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFE53935)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.MedicalServices,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "First Aid Helper",
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C3E50)
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Parola") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp)
        )

        if (errorMsg.isNotEmpty()) {
            Text(errorMsg, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (mainActivity.checkUser(email, pass)) {
                    onLoginSuccess(email)
                } else {
                    errorMsg = "Email sau parola incorecta!"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
        ) {
            Text("AUTENTIFICARE", fontWeight = FontWeight.Bold)
        }

        TextButton(onClick = onGoToSignup) {
            Text("Nu ai cont? Inregistreaza-te", color = Color(0xFF2C3E50))
        }
    }
}
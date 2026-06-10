package com.proiect.firstaidapp

import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SignupScreen(onSignupSuccess: (String, String) -> Unit, onBack: () -> Unit) {
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
        Text("Cont Nou", style = MaterialTheme.typography.headlineLarge, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Adresa Email") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Parola (min. 4)") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation(), shape = RoundedCornerShape(12.dp))

        if (errorMsg.isNotEmpty()) Text(errorMsg, color = Color.Red, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && pass.length >= 4) {
                    onSignupSuccess(email, pass)
                } else {
                    errorMsg = "Date invalide!"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C3E50))
        ) {
            Text("CREEAZA CONT", fontWeight = FontWeight.Bold)
        }
        TextButton(onClick = onBack) { Text("Inapoi la Login") }
    }
}


@Composable
fun HomeScreen(
    userEmail: String,
    onGhidClick: () -> Unit,
    onProfileClick: () -> Unit,
    onTriajClick: () -> Unit,
    onPostsClick: () -> Unit,
    onCreatePostClick: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {


        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Utilizator,", fontSize = 12.sp, color = Color.Gray)
                    Text(userEmail.split("@")[0].uppercase(), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                IconButton(onClick = onLogout) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = Color(0xFFE53935))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))


        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Button(
                onClick = onTriajClick,
                modifier = Modifier.fillMaxWidth().height(70.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("TRIAJ RAPID", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGhidClick,
                modifier = Modifier.fillMaxWidth().height(70.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("GHID PRIM AJUTOR", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onProfileClick,
                modifier = Modifier.fillMaxWidth().height(70.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF455A64))
            ) {
                Text("PROFILE MEDICALE (ICE)", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onPostsClick,
                modifier = Modifier.fillMaxWidth().height(70.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) {
                Text("POSTARI API", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = onCreatePostClick,
                modifier = Modifier.fillMaxWidth().height(70.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text("CREEAZA POSTARE API", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=farmacie"))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(70.dp),
                shape = RoundedCornerShape(16.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp)
            ) {
                Text("CAUTA FARMACII", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

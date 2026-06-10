package com.proiect.firstaidapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import kotlinx.coroutines.*

@Composable
fun CreatePostScreen(onBack: () -> Unit) {

    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Creeaza Postare API",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titlu") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Continut") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))


        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {

                    try {

                        PostTestRetrofit.api.sendData(
                            mapOf(
                                "title" to title,
                                "body" to body
                            )
                        )


                        val newPost = PostEntity(title = title, body = body)
                        db.postDao().insertPost(newPost)

                        withContext(Dispatchers.Main) {
                            result = "POST TRIMIS SI SALVAT!"
                        }

                        delay(1500)

                        withContext(Dispatchers.Main) {
                            onBack()
                        }

                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            result = "Eroare: ${e.message}"
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Trimite POST")
        }

        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF455A64))
        ) {
            Text("INAPOI")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(result)
    }
}

package com.proiect.firstaidapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*

@Composable
fun CreatePostScreen(onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

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

        Button(
            onClick = {
                loading = true
                CoroutineScope(Dispatchers.IO).launch {
                    try {

                        db.postDao().insertPost(
                            PostEntity(
                                title = title,
                                body = body
                            )
                        )

                        withContext(Dispatchers.Main) {
                            result = "Postare salvata!"
                        }

                        delay(1500)

                        withContext(Dispatchers.Main) {
                            onBack()
                        }

                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            result = "Eroare: ${e.message}"
                        }
                    } finally {
                        loading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salveaza Postarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
        }

        if (result.isNotEmpty()) {
            Text(result)
        }
    }
}

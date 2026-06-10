package com.proiect.firstaidapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PostsScreen(onBack: () -> Unit) {
    var posts by remember { mutableStateOf(listOf<PostEntity>()) }
    val scope = rememberCoroutineScope()


    LaunchedEffect(true) {
        posts = withContext(Dispatchers.IO) {
            db.postDao().getAllPosts()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text("Inapoi la ecranul principal")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Postarile Tale",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (posts.isEmpty()) {
            Text("Nu exista postari inca.")
        } else {
            LazyColumn {
                items(posts) { post ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(post.title, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(post.body)

                            Spacer(modifier = Modifier.height(12.dp))


                            Button(
                                onClick = {
                                    scope.launch(Dispatchers.IO) {
                                        db.postDao().deletePost(post)


                                        posts = db.postDao().getAllPosts()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Sterge Postarea")
                            }
                        }
                    }
                }
            }
        }
    }
}

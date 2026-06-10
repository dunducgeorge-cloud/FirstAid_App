package com.proiect.firstaidapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import com.proiect.firstaidapp.ui.theme.FirstAidAppTheme
import kotlinx.coroutines.*

lateinit var db: AppDatabase
lateinit var dao: ProfilDao
lateinit var postDao: PostDao

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "first_aid_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        dao = db.profilDao()
        postDao = db.postDao()

        enableEdgeToEdge()
        setContent {
            FirstAidAppTheme {

                var currentScreen by remember { mutableStateOf("login") }
                var emailLogat by remember { mutableStateOf("") }
                var urgentaSelectata by remember { mutableStateOf("") }
                val context = LocalContext.current
                val profileState = remember { mutableStateListOf<ProfilMedical>() }

                LaunchedEffect(emailLogat) {
                    if (emailLogat.isNotEmpty()) {
                        val lista = withContext(Dispatchers.IO) { dao.getAllProfiles() }
                        profileState.clear()
                        profileState.addAll(lista)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        if (currentScreen != "login" && currentScreen != "signup") {
                            FloatingActionButton(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:112")
                                    }
                                    context.startActivity(intent)
                                },
                                containerColor = Color(0xFFE53935),
                                contentColor = Color.White
                            ) {
                                Icon(Icons.Default.Call, contentDescription = null)
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        when (currentScreen) {

                            "login" -> LoginScreen(
                                mainActivity = this@MainActivity,
                                onLoginSuccess = { email ->
                                    emailLogat = email
                                    currentScreen = "home"
                                },
                                onGoToSignup = { currentScreen = "signup" }
                            )

                            "signup" -> SignupScreen(
                                onSignupSuccess = { userEmail, userPass ->
                                    saveUser(User(userEmail, userPass))
                                    currentScreen = "login"
                                },
                                onBack = { currentScreen = "login" }
                            )

                            "home" -> HomeScreen(
                                userEmail = emailLogat,
                                onGhidClick = { currentScreen = "ghid" },
                                onProfileClick = { currentScreen = "lista_profile" },
                                onTriajClick = { currentScreen = "triaj" },
                                onPostsClick = { currentScreen = "posts" },
                                onCreatePostClick = { currentScreen = "create_post" },
                                onCovidClick = { currentScreen = "covid" },
                                onLogout = {
                                    emailLogat = ""
                                    profileState.clear()
                                    currentScreen = "login"
                                }
                            )

                            "triaj" -> TriajScreen { currentScreen = "home" }

                            "ghid" -> GhidScreen(
                                onBackClick = { currentScreen = "home" },
                                onUrgentaClick = { urgenta ->
                                    urgentaSelectata = urgenta
                                    currentScreen = "detalii"
                                }
                            )

                            "detalii" -> DetaliiScreen(urgentaSelectata) {
                                currentScreen = "ghid"
                            }

                            "lista_profile" -> ListaProfileScreen(
                                profile = profileState,
                                onAddClick = { currentScreen = "adauga_profil" },
                                onBackClick = { currentScreen = "home" },
                                onDelete = { profil ->
                                    CoroutineScope(Dispatchers.IO).launch {
                                        dao.deleteProfil(profil)
                                    }
                                    profileState.remove(profil)
                                }
                            )

                            "adauga_profil" -> AdaugaProfilScreen(
                                onSave = { numeProfil, grupaMedicala, alergiiProfil ->

                                    val profilNou = ProfilMedical(
                                        nume = numeProfil,
                                        grupa = grupaMedicala,
                                        alergii = alergiiProfil
                                    )

                                    CoroutineScope(Dispatchers.IO).launch {
                                        dao.insertProfil(profilNou)
                                    }

                                    profileState.add(profilNou)
                                    currentScreen = "lista_profile"
                                },
                                onBackClick = { currentScreen = "lista_profile" }
                            )

                            "posts" -> PostsScreen(
                                onBack = { currentScreen = "home" }
                            )


                            "create_post" -> CreatePostScreen(
                                onBack = { currentScreen = "home" }
                            )

                            "covid" -> CovidScreen(
                                onBack = { currentScreen = "home" }
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveUser(user: User) {
        val sharedPref = getSharedPreferences("UserAccounts", MODE_PRIVATE)
        sharedPref.edit().putString("user_${user.email}", user.parola).apply()
    }

    fun checkUser(email: String, parola: String): Boolean {
        val sharedPref = getSharedPreferences("UserAccounts", MODE_PRIVATE)
        val passSaved = sharedPref.getString("user_$email", null)
        return passSaved != null && passSaved == parola
    }
}

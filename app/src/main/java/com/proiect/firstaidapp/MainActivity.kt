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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

lateinit var db: AppDatabase
lateinit var dao: ProfilDao

class MainActivity : ComponentActivity() {
    private val gson = Gson()

    fun saveUser(user: User) {
        val sharedPref = getSharedPreferences("UserAccounts", MODE_PRIVATE)
        sharedPref.edit().putString("user_${user.email}", user.parola).apply()
    }

    fun checkUser(email: String, parola: String): Boolean {
        val sharedPref = getSharedPreferences("UserAccounts", MODE_PRIVATE)
        val passSaved = sharedPref.getString("user_$email", null)
        return passSaved != null && passSaved == parola
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔵 INITIALIZARE ROOM
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "first_aid_db"
        ).build()

        dao = db.profilDao()

        enableEdgeToEdge()
        setContent {
            FirstAidAppTheme {
                var currentScreen by remember { mutableStateOf("login") }
                var emailLogat by remember { mutableStateOf("") }
                var urgentaSelectata by remember { mutableStateOf("") }
                val context = LocalContext.current
                val profileState = remember { mutableStateListOf<ProfilMedical>() }

                // 🔵 ÎNCĂRCARE PROFILE DIN ROOM
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
                            ) { Icon(Icons.Default.Call, contentDescription = null) }
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

                            "detalii" -> DetaliiScreen(urgentaSelectata) { currentScreen = "ghid" }

                            "lista_profile" -> ListaProfileScreen(
                                profile = profileState,
                                onAddClick = { currentScreen = "adauga_profil" },
                                onBackClick = { currentScreen = "home" },
                                onDelete = { profil ->
                                    // 🔵 ȘTERGERE DIN ROOM
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

                                    // 🔵 SALVARE ÎN ROOM
                                    CoroutineScope(Dispatchers.IO).launch {
                                        dao.insertProfil(profilNou)
                                    }

                                    profileState.add(profilNou)
                                    currentScreen = "lista_profile"
                                },
                                onBackClick = { currentScreen = "lista_profile" }
                            )
                        }
                    }
                }
            }
        }
    }
}

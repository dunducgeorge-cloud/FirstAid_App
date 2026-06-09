package com.proiect.firstaidapp

data class ProfilMedical(
    val id: String = java.util.UUID.randomUUID().toString(),
    val nume: String,
    val varsta: String,
    val alergii: String
)

data class User(val email: String, val parola: String)
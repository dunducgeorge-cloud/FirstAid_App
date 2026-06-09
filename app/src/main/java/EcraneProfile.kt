package com.proiect.firstaidapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ListaProfileScreen(profile: List<ProfilMedical>, onAddClick: () -> Unit, onBackClick: () -> Unit, onDelete: (ProfilMedical) -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF5F5F5))
        .padding(16.dp)) {
        Text("Profile Salvate", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(profile) { p ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(p.nume, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Varsta: ${p.grupa}", color = Color.Gray)
                            Text("Alergii: ${p.alergii}", fontSize = 14.sp)
                        }
                        IconButton(onClick = { onDelete(p) }) { Icon(Icons.Default.Delete, contentDescription = null, tint = Color(0xFFE53935)) }
                    }
                }
            }
        }
        Button(onClick = onAddClick, modifier = Modifier
            .fillMaxWidth()
            .height(55.dp), shape = RoundedCornerShape(12.dp)) { Icon(Icons.Default.Add, contentDescription = null); Text(" ADAUGA PROFIL") }
        TextButton(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) { Text("INAPOI LA MENIU", color = Color.Gray) }
    }
}

@Composable
fun AdaugaProfilScreen(onSave: (String, String, String) -> Unit, onBackClick: () -> Unit) {
    var numeInput by remember { mutableStateOf("") }
    var varstaInput by remember { mutableStateOf("") }
    var alergiiInput by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Text("Profil Nou", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(value = numeInput, onValueChange = { numeInput = it }, label = { Text("Nume Complet") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = varstaInput, onValueChange = { varstaInput = it }, label = { Text("Varsta") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = alergiiInput, onValueChange = { alergiiInput = it }, label = { Text("Alergii sau Boli") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (numeInput.isNotBlank()) {
                    onSave(numeInput, varstaInput, alergiiInput)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("SALVEAZA")
        }
        TextButton(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) { Text("ANULEAZA", color = Color.Gray) }
    }
}
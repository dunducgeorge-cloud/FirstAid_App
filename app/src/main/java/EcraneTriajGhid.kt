package com.proiect.firstaidapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TriajScreen(onBack: () -> Unit) {
    var step by remember { mutableStateOf(0) }
    val questions = listOf("Victima este constienta?", "Victima respira normal?", "Exista sangerari masive?")

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
        .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if (step < questions.size) {
            Text("TRIAJ RAPID", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(40.dp))
            Text(questions[step], color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 40.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { if (step == 2) step = 4 else step++ }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                    Text("DA", color = Color.Red, fontWeight = FontWeight.Bold)
                }
                Button(onClick = { if (step == 2) step = 3 else step = 4 }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("NU", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        } else if (step == 3) {
            Text("Victima pare stabila.\nMonitorizeaza constant!", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) { Text("INAPOI", color = Color.Red) }
        } else {
            Text("SUNA IMEDIAT LA 112!", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) { Text("INAPOI") }
        }
    }
}

@Composable
fun GhidScreen(onBackClick: () -> Unit, onUrgentaClick: (String) -> Unit) {
    val listaUrgente = listOf("Arsuri", "Lesin", "Fracturi", "Inecare", "Infarct", "Insolatie")
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF5F5F5))
        .padding(16.dp)) {
        Text("Alege Urgenta", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(listaUrgente) { item ->
                Card(onClick = { onUrgentaClick(item) }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(item, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                        Text(">", color = Color.LightGray)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onBackClick, modifier = Modifier
            .fillMaxWidth()
            .height(55.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF455A64))) { Text("INAPOI") }
    }
}

@Composable
fun DetaliiScreen(urgenta: String, onBack: () -> Unit) {
    val textGhid = when(urgenta) {
        "Arsuri" -> "1.Zona afectata se tine sub apa calduta timp de 15-20 de minute.\n" + "2.Nu se sparg veziculele formate sub nicio forma.\n" + "3. Ulterior se acopera totul cu un pansament steril lasat mai lejer."
        "Inecare" -> "1.Persoana trebuie incurajata sa tuseasca puternic. \n" + "2.Se aplica maxim 5 lovituri ferme pe spate, intre omoplati.\n" + "3. Daca blocajul nu cedeaza, se trece la aplicarea manevrei Heimlich."
        "Infarct" -> "1. Se apeleaza imediat numarul de urgenta 112.\n" + "2.Victima trebuie asezata intr-o pozitie confortabila, semi-sezand.\n" + "3. Se descheie hainele stranse pentru a usura respiratia si se incearca calmarea persoanei."
        "Lesin" -> "1. Victima se asaza intinsa pe spate, ridicandu-i picioarele la aproximativ 30 de centimetri de sol.\n" + "2. Este important sa aiba acces la aer curat si sa i se verifice constant respiratia."
        "Fracturi" -> "1. Nu incercati sa miscati sau sa realiniati osul rupt.\n" + "2. Zona se imobilizeaza rapid folosind o atela improvizata din materiale la indemana si se pune gheata local pentru a reduce umflatura."
        else -> "1. Se suna de urgenta la 112. \n" + "2. Ramaneti langa persoana afectata si monitorizati-i starea pana sosesc medicii.\n" + "3. Nu ii dati apa sau mancare daca este in stare de soc."
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Text(urgenta.uppercase(), style = MaterialTheme.typography.headlineLarge, color = Color(0xFFE53935), fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Serif)
        Spacer(modifier = Modifier.height(24.dp))
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))) {
            Text(textGhid, modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.bodyLarge, lineHeight = 28.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onBack, modifier = Modifier
            .fillMaxWidth()
            .height(55.dp), shape = RoundedCornerShape(12.dp)) { Text("AM INTELES") }
    }
}
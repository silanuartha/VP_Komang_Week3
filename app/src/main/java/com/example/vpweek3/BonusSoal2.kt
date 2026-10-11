package com.example.vpweek3

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BonusSoal2() {
    val context = LocalContext.current
    var spotWisata by remember { mutableStateOf("") }
    var pengalaman by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var kepuasan by remember { mutableStateOf("Luar Biasa") }

    val bgColor = Color(0xFF131722)
    val cardColor = Color(0xFF1F2633)
    val accentColor = Color(0xFF5D9CFF)

    Scaffold(
        containerColor = bgColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val pesan = if (spotWisata.isNotBlank()) {
                        "Draft $spotWisata berhasil disimpan"
                    } else {
                        "Isi spot wisata terlebih dahulu."
                    }
                    Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show()
                },
                containerColor = accentColor,
                contentColor = Color.Black,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("+", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF235594)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("LOG PERJALANAN", fontSize = 12.sp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Tromso, Norway", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Ekspedisi Aurora Borealis", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            val textFieldColors = TextFieldDefaults.colors(
                focusedContainerColor = cardColor,
                unfocusedContainerColor = cardColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.LightGray,
                cursorColor = accentColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

            Text("SPOT WISATA FAVORIT", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = spotWisata,
                onValueChange = { spotWisata = it },
                placeholder = { Text("Fjellheisen Cable Car", color = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("APA YANG PALING KAMU NIKMATI?", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = pengalaman,
                onValueChange = { pengalaman = it },
                placeholder = { Text("Pemandangan langit malam hijau aurora spektakuler dari puncak gunung...", color = Color.Gray) },
                singleLine = false,
                minLines = 3,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("CATATAN TAMBAHAN/PERLENGKAPAN", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = catatan,
                onValueChange = { catatan = it },
                placeholder = { Text("(Ketik perlengkapan ekstra di sini...)", color = Color.Gray) },
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("TINGKAT KEPUASAN:", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val opsiKepuasan = listOf("Biasa", "Seru", "Luar Biasa \u2605")
                opsiKepuasan.forEach { opsi ->
                    val isSelected = kepuasan == opsi
                    Button(
                        onClick = { kepuasan = opsi },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) accentColor else Color.Transparent
                        ),
                        border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, Color.DarkGray) else null,
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(opsi, color = if (isSelected) Color.Black else Color.LightGray)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Text("Status: Draft Tersimpan", color = accentColor, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BonusSoal2Preview() {
    BonusSoal2()
}
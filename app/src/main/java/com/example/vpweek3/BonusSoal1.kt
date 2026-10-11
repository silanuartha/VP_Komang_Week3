package com.example.vpweek3

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BonusSoal1() {
    val context = LocalContext.current
    var quantity by remember { mutableStateOf(1) }
    var isLargeSize by remember { mutableStateOf(false) }
    val basePrice = 25000
    val sizeExtra = if (isLargeSize) 6000 else 0
    val unitPrice = basePrice + sizeExtra
    val subtotal = unitPrice * quantity
    val tax = (subtotal * 0.1).toInt()
    val total = subtotal + tax

    fun formatRp(amount: Int): String {
        return "Rp %,d".format(amount).replace(',', '.')
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9F5))
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kopi Kenangan Senja",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5D4037)
            )
            Icon(
                painter = painterResource(id = R.drawable.kopikecil),
                contentDescription = "Logo",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(16.dp)
                        .background(Color(0xFF8D5524), shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.kopigede),
                        contentDescription = "Coffee Image",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Text("Caramel Latte", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Espresso shot, steamed milk & caramel syrup",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                Text(
                    text = "${formatRp(basePrice)} / cup",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD87D4A)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("PILIHAN UKURAN:", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { isLargeSize = false },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, if (!isLargeSize) Color(0xFFD87D4A) else Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (!isLargeSize) Color(0xFFFFF0E6) else Color.Transparent
                )
            ) {
                Text("Regular (+0)", color = if (!isLargeSize) Color(0xFFD87D4A) else Color.Gray)
            }

            OutlinedButton(
                onClick = { isLargeSize = true },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, if (isLargeSize) Color(0xFFD87D4A) else Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isLargeSize) Color(0xFFFFF0E6) else Color.Transparent
                )
            ) {
                Text("Large (+6k)", color = if (isLargeSize) Color(0xFFD87D4A) else Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Jumlah Pesanan:", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { if (quantity > 1) quantity-- },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text("-", fontSize = 20.sp, color = Color.Black)
                    }

                    Text(
                        text = quantity.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Button(
                        onClick = { quantity++ },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBE9E7))
                    ) {

                        Text("+", fontSize = 20.sp, color = Color(0xFFD87D4A))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Subtotal:", color = Color.Gray)
                    Text(formatRp(subtotal), fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Pajak Resto (10%):", color = Color.Gray)
                    Text(formatRp(tax), fontWeight = FontWeight.SemiBold)
                }
                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Tagihan:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(formatRp(total), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFD87D4A))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Berhasil! $quantity Caramel Latte ditambahkan ke keranjang.",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD87D4A))
        ) {
            Text("TAMBAH KE KERANJANG", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BonusSoal1Preview() {
    BonusSoal1()
}
package com.example.vpweek3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Soal2() {
    var coins by remember { mutableStateOf(0) }
    var coinsPerTap by remember { mutableStateOf(1.0) }
    var upgradeCost by remember { mutableStateOf(10) }
    var isCatPressed by remember { mutableStateOf(false) }

    val nextCoinsPerTap = coinsPerTap * 1.5
    val increaseAmount = (nextCoinsPerTap - coinsPerTap).toInt()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text("Your Coins", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = coins.toString(),
                        fontSize = 36.sp,
                        color = Color.Green,
                        fontWeight = FontWeight.Bold
                    )
                    Text("${coinsPerTap.toInt()} coins per tap", fontSize = 14.sp, color = Color.DarkGray)
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tap the Cat!",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val catImageRes = if (isCatPressed) {
                    R.drawable.mingkem
                } else {
                    R.drawable.mangap
                }

                Image(
                    painter = painterResource(id = catImageRes),
                    contentDescription = "Cat Clicker",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isCatPressed = true
                                    coins += coinsPerTap.toInt()

                                    tryAwaitRelease()
                                    isCatPressed = false
                                }
                            )
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isCatPressed) "meow" else "pur",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Give Me Your Coin", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "Next upgrade: +$increaseAmount coins per tap",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    val canUpgrade = coins >= upgradeCost

                    Button(
                        onClick = {
                            if (canUpgrade) {
                                coins -= upgradeCost
                                coinsPerTap *= 1.5
                                upgradeCost *= 2
                            }
                        },
                        enabled = canUpgrade,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green,
                            disabledContainerColor = Color.LightGray
                        ),
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        if (canUpgrade) {
                            Text("Pay for $upgradeCost coins", color = Color.White, fontSize = 16.sp)
                        } else {
                            val shortage = upgradeCost - coins
                            Text("Find $shortage more coins", color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal2Preview() {
    Soal2()
}
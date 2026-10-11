package com.example.vpweek3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class GameStateRps { INITIAL, PICK, REVEAL, FINISHED }
enum class Rps(val emoji: String) { ROCK("✊"), PAPER("✋"), SCISSORS("✌️") }
enum class RpsResult(val message: String) { WIN("You Win!"), LOSE("You Lose"), DRAW("Draw") }

@Composable
fun Soal4() {
    var gameState by remember { mutableStateOf(GameStateRps.INITIAL) }
    var playerScore by rememberSaveable { mutableStateOf(0) }
    var cpuScore by rememberSaveable { mutableStateOf(0) }
    var bestScore by rememberSaveable { mutableStateOf(0) }

    val maxGames = 5
    val winCondition = (maxGames / 2) + 1
    var playerPick by remember { mutableStateOf<Rps?>(null) }
    var cpuPick by remember { mutableStateOf<Rps?>(null) }
    var roundResult by remember { mutableStateOf<RpsResult?>(null) }

    var buttonOptions by remember { mutableStateOf(Rps.values().toList()) }

    fun determineWinner(player: Rps, cpu: Rps): RpsResult {
        return when {
            player == cpu -> RpsResult.DRAW
            player == Rps.ROCK && cpu == Rps.SCISSORS -> RpsResult.WIN
            player == Rps.PAPER && cpu == Rps.ROCK -> RpsResult.WIN
            player == Rps.SCISSORS && cpu == Rps.PAPER -> RpsResult.WIN
            else -> RpsResult.LOSE
        }
    }

    LaunchedEffect(gameState) {
        if (gameState == GameStateRps.REVEAL) {
            delay(700)

            if (playerScore >= winCondition || cpuScore >= winCondition) {
                val currentDiff = playerScore - cpuScore
                if (currentDiff > bestScore) {
                    bestScore = currentDiff
                }
                gameState = GameStateRps.FINISHED
            } else {
                playerPick = null
                cpuPick = null
                roundResult = null
                buttonOptions = Rps.values().toList().shuffled()
                gameState = GameStateRps.PICK
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (gameState != GameStateRps.INITIAL) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "👦 $playerScore - $cpuScore 🤖", fontSize = 18.sp)
                Text(text = "Best of $maxGames", fontSize = 14.sp, color = Color.Gray)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            when (gameState) {
                GameStateRps.INITIAL -> {
                    Text(
                        text = "Rock • Paper • Scissors",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            playerScore = 0
                            cpuScore = 0
                            buttonOptions = Rps.values().toList().shuffled()
                            gameState = GameStateRps.PICK
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text("Start", color = Color.Black)
                    }
                }

                GameStateRps.PICK -> {
                    Text("Pick your move!", fontSize = 18.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("❔", fontSize = 48.sp)
                        Text(" VS ", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 16.dp))
                        Text("❔", fontSize = 48.sp)
                    }

                    Spacer(modifier = Modifier.height(64.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        buttonOptions.forEach { move ->
                            Button(
                                onClick = {
                                    playerPick = move
                                    cpuPick = Rps.values().random()
                                    val result = determineWinner(move, cpuPick!!)
                                    roundResult = result

                                    if (result == RpsResult.WIN) playerScore++
                                    else if (result == RpsResult.LOSE) cpuScore++

                                    gameState = GameStateRps.REVEAL
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier.width(100.dp).height(50.dp)
                            ) {
                                Text("${move.emoji} ${move.name.lowercase().capitalize()}", color = Color.Black)
                            }
                        }
                    }
                }

                GameStateRps.REVEAL -> {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(playerPick?.emoji ?: "", fontSize = 48.sp)
                        Text(" VS ", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 16.dp))
                        Text(cpuPick?.emoji ?: "", fontSize = 48.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = roundResult?.message ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (roundResult == RpsResult.DRAW) Color.Gray else Color.Black
                    )
                }

                GameStateRps.FINISHED -> {
                    val isMatchWon = playerScore > cpuScore
                    Text(
                        text = if (isMatchWon) "You Win the Match!" else "You Lose the Match",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Best Score: $bestScore", fontSize = 16.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(48.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                playerScore = 0
                                cpuScore = 0
                                buttonOptions = Rps.values().toList().shuffled()
                                gameState = GameStateRps.PICK
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                        ) {
                            Text("Restart", color = Color.Black)
                        }

                        Button(
                            onClick = { gameState = GameStateRps.INITIAL },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                        ) {
                            Text("Exit", color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal4Preview() {
    Soal4()
}
package com.example.vpweek3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class GameState3 { INITIAL, COUNTDOWN, RUNNING, GAME_OVER }
enum class Mode { COLOR, TEXT }
enum class Choice { LEFT, RIGHT }

enum class ColorName(val displayName: String, val colorValue: Color) {
    RED("RED", Color.Red),
    BLUE("BLUE", Color.Blue),
    GREEN("GREEN", Color.Green),
    YELLOW("YELLOW", Color.Yellow)
}

@Composable
fun Soal3() {
    var gameState by remember { mutableStateOf(GameState3.INITIAL) }
    var score by remember { mutableStateOf(0) }
    var bestScore by remember { mutableStateOf(0) }
    var strikes by remember { mutableStateOf(0) }

    var countdownValue by remember { mutableStateOf(3) }
    var timeLeft by remember { mutableStateOf(5) }

    var currentMode by remember { mutableStateOf(Mode.COLOR) }
    var currentWord by remember { mutableStateOf(ColorName.RED) }
    var currentInk by remember { mutableStateOf(ColorName.BLUE) }
    var buttonOptions by remember { mutableStateOf(listOf(ColorName.RED, ColorName.BLUE)) }

    fun generateNewQuestion() {
        currentMode = Mode.values().random()
        currentWord = ColorName.values().random()

        var newInk = ColorName.values().random()
        while (newInk == currentWord) {
            newInk = ColorName.values().random()
        }
        currentInk = newInk

        buttonOptions = listOf(currentWord, currentInk).shuffled()
        timeLeft = 5
    }

    fun handleAnswer(selectedColor: ColorName) {
        val isCorrect = when (currentMode) {
            Mode.COLOR -> selectedColor == currentInk
            Mode.TEXT -> selectedColor == currentWord
        }

        if (isCorrect) {
            score++
        } else {
            strikes++
        }

        if (strikes >= 3) {
            if (score > bestScore) bestScore = score
            gameState = GameState3.GAME_OVER
        } else {
            generateNewQuestion()
        }
    }

    LaunchedEffect(gameState) {
        if (gameState == GameState3.COUNTDOWN) {
            countdownValue = 3
            while (countdownValue > 0) {
                delay(1000)
                countdownValue--
            }
            delay(700)
            score = 0
            strikes = 0
            generateNewQuestion()
            gameState = GameState3.RUNNING
        }
    }

    LaunchedEffect(gameState, timeLeft) {
        if (gameState == GameState3.RUNNING && timeLeft > 0) {
            delay(1000)
            timeLeft--
            if (timeLeft == 0) {
                strikes++
                if (strikes >= 3) {
                    if (score > bestScore) bestScore = score
                    gameState = GameState3.GAME_OVER
                } else {
                    generateNewQuestion()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            when (gameState) {
                GameState3.INITIAL -> {
                    Text("Welcome", fontSize = 24.sp)
                    Text("to", fontSize = 20.sp)
                    Text("Color Word Matching", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { gameState = GameState3.COUNTDOWN },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text("Start Game", color = Color.Black)
                    }
                }

                GameState3.COUNTDOWN -> {
                    val display = if (countdownValue > 0) countdownValue.toString() else "Start!"
                    Text(display, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                }

                GameState3.RUNNING -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Mode: ${currentMode.name}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.benar),
                                contentDescription = "Score",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("$score", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(16.dp))

                            Image(
                                painter = painterResource(id = R.drawable.salah),
                                contentDescription = "Strikes",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("$strikes/3", fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("${timeLeft} s", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = currentWord.displayName,
                        fontSize = 64.sp,
                        color = currentInk.colorValue,
                        fontWeight = FontWeight.Light
                    )

                    Spacer(modifier = Modifier.height(64.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        buttonOptions.forEach { option ->
                            Button(
                                onClick = { handleAnswer(option) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier.width(120.dp).height(50.dp)
                            ) {
                                Text(option.displayName, color = Color.Black)
                            }
                        }
                    }
                }

                GameState3.GAME_OVER -> {
                    Text("Game Over!", fontSize = 36.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Your Score", fontSize = 20.sp)
                    Text("$score", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Best Score", fontSize = 16.sp, color = Color.Gray)
                    Text("$bestScore", fontSize = 20.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { gameState = GameState3.COUNTDOWN },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text("Restart Game", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { gameState = GameState3.INITIAL },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text("Exit", color = Color.Black)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal3Preview() {
    Soal3()
}
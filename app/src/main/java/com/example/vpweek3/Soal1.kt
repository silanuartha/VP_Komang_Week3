package com.example.vpweek3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class GameState {
    START, WAITING, READY, TRIAL_RESULT, FAIL, FINAL_RESULT
}

@Composable
fun Soal1() {
    var gameState by remember { mutableStateOf(GameState.START) }
    var currentTrial by remember { mutableStateOf(1) }
    var trialResults by remember { mutableStateOf(MutableList<Long?>(3) { null }) }
    var startTime by remember { mutableStateOf(0L) }
    var reactionTime by remember { mutableStateOf(0L) }

    LaunchedEffect(gameState) {
        if (gameState == GameState.WAITING) {
            val randomDelay = Random.nextLong(500, 4500)
            delay(randomDelay)
            if (gameState == GameState.WAITING) {
                startTime = System.currentTimeMillis()
                gameState = GameState.READY
            }
        }
    }

    val validTrials = trialResults.filterNotNull()
    val average = if (validTrials.isNotEmpty()) validTrials.average().toLong() else 0L
    val isAllFailed = validTrials.isEmpty()

    val backgroundColor = when (gameState) {
        GameState.START -> Color.Cyan
        GameState.WAITING -> Color.LightGray
        GameState.READY -> Color.Green
        GameState.TRIAL_RESULT -> Color.Cyan
        GameState.FAIL -> Color.Red
        GameState.FINAL_RESULT -> {
            when {
                isAllFailed -> Color.Red
                average < 180 -> Color.Green
                average < 280 -> Color.Blue
                average < 450 -> Color.Yellow
                else -> Color.Red
            }
        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                when (gameState) {
                    GameState.START -> {
                        gameState = GameState.WAITING
                    }
                    GameState.WAITING -> {
                        gameState = GameState.FAIL
                    }
                    GameState.READY -> {
                        reactionTime = System.currentTimeMillis() - startTime
                        val newResults = trialResults.toMutableList()
                        newResults[currentTrial - 1] = reactionTime
                        trialResults = newResults

                        if (currentTrial == 3) {
                            gameState = GameState.FINAL_RESULT
                        } else {
                            gameState = GameState.TRIAL_RESULT
                        }
                    }
                    GameState.TRIAL_RESULT, GameState.FAIL -> {
                        if (currentTrial < 3) {
                            currentTrial++
                            gameState = GameState.WAITING
                        } else {
                            gameState = GameState.FINAL_RESULT
                        }
                    }
                    GameState.FINAL_RESULT -> {
                        currentTrial = 1
                        trialResults = MutableList(3) { null }
                        gameState = GameState.START
                    }
                }
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(24.dp)
        ) {
            when (gameState) {
                GameState.START -> {
                    Text("Reaction", fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Image(
                        painter = painterResource(id = R.drawable.thunder),
                        contentDescription = "Petir",
                        modifier = Modifier.size(100.dp).padding(vertical = 16.dp)
                    )
                    Text("Test", fontSize = 24.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Click to Start", fontSize = 16.sp, color = Color.White)
                }
                GameState.WAITING -> {
                    Text("Get Ready", fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Image(
                        painter = painterResource(id = R.drawable.warning),
                        contentDescription = "Warning",
                        modifier = Modifier.size(100.dp).padding(vertical = 16.dp)
                    )
                    Text("Wait for green light...", fontSize = 16.sp, color = Color.White)
                }
                GameState.READY -> {
                    Text("GO!", fontSize = 64.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("CLICK NOW!", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Text("TAP AS FAST AS YOU CAN", fontSize = 16.sp, color = Color.White)
                }
                GameState.TRIAL_RESULT -> {
                    Text("Trial $currentTrial Complete!", fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Time: ${reactionTime}ms", fontSize = 24.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(32.dp))
                    Text("Continue to Trial ${currentTrial + 1}", fontSize = 16.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(48.dp))
                    TrialResultsCard(trialResults = trialResults)
                }
                GameState.FAIL -> {
                    Text("FAIL!", fontSize = 36.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Image(
                        painter = painterResource(id = R.drawable.dislike),
                        contentDescription = "Fail",
                        modifier = Modifier.size(120.dp).padding(vertical = 16.dp)
                    )
                    Text(
                        text = "You clicked too early, \nTRY TO READ THE RULE BRO",
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("TRY AGAIN", fontSize = 16.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(48.dp))
                    TrialResultsCard(trialResults = trialResults)
                }

                GameState.FINAL_RESULT -> {
                    val (message, imageRes) = when {
                        isAllFailed -> Pair("YOU LIKE A SNAIL\nBRO", R.drawable.kurang)
                        average < 180 -> Pair("DANG YOU ARE SO\nFAST BRO!", R.drawable.hormat)
                        average < 280 -> Pair("YOUR REFLEX IS\nGOOD", R.drawable.oke)
                        average < 450 -> Pair("MEH LIKE OTHER\nPERSON", R.drawable.biasa)
                        else -> Pair("YOU LIKE A SNAIL\nBRO", R.drawable.kurang)
                    }

                    Text(
                        text = message,
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Result Image",
                        modifier = Modifier.size(150.dp).padding(vertical = 16.dp)
                    )

                    val averageText = if (isAllFailed) "Average: FAILED" else "Average: ${average}ms"
                    Text(averageText, fontSize = 18.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Click to Start New Test", fontSize = 14.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(32.dp))

                    TrialResultsCard(trialResults = trialResults, isFinal = true, average = average)
                }
            }
        }
    }
}

@Composable
fun TrialResultsCard(trialResults: List<Long?>, isFinal: Boolean = false, average: Long = 0L) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Trial Results", color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("1", color = Color.Gray, fontSize = 14.sp)
                Text("2", color = Color.Gray, fontSize = 14.sp)
                Text("3", color = Color.Gray, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                trialResults.forEach { time ->
                    val displayTime = if (time != null) "${time}ms" else "-"
                    Text(text = displayTime, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            if (isFinal) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Average Score", color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("${average}ms", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal1Preview() {
    Soal1()
}
package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstapp.ui.theme.FirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstAppTheme {
                val questions = listOf(
                    "Android is an operating system." to true,
                    "Kotlin is officially supported for Android development." to true,
                    "The Earth is flat." to false
                )

                var questionIndex by remember { mutableStateOf(0) }
                var isCorrect: Boolean? by remember { mutableStateOf(null) }
                var score by remember { mutableStateOf(0) }
                var isQuizFinished by remember { mutableStateOf(false) }

                val currentQuestion = questions.getOrNull(questionIndex)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (isQuizFinished) {
                        Text(
                            text = "Quiz Complete!\nYour score: $score / ${questions.size}",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = {
                            questionIndex = 0
                            score = 0
                            isCorrect = null
                            isQuizFinished = false
                        }) {
                            Text("Restart Game")
                        }
                    } else {
                        Text(
                            text = currentQuestion?.first ?: "",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )

                        isCorrect?.let { correct ->
                            AnswerFeedback(correct)
                        }

                        if (isCorrect == null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = {
                                        isCorrect = true == currentQuestion?.second
                                        if (isCorrect == true) score++
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("True")
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Button(
                                    onClick = {
                                        isCorrect = false == currentQuestion?.second
                                        if (isCorrect == true) score++
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("False")
                                }
                            }
                        }

                        if (isCorrect != null) {
                            Button(onClick = {
                                if (questionIndex + 1 < questions.size) {
                                    questionIndex++
                                    isCorrect = null
                                } else {
                                    isQuizFinished = true
                                }
                            }) {
                                Text("Next Question")
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun AnswerFeedback(isCorrect: Boolean) {
    val text = if (isCorrect) "Correct Answer" else "Wrong Answer"
    val color = if (isCorrect) Color.Green else Color.Red

    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.headlineSmall)
    }
}

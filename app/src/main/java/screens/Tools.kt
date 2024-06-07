package screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yugiohproject.R
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun Tools(navController:NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .paint(
            painterResource(R.drawable.background_6),
            contentScale = ContentScale.FillBounds
        )
    )

    var diceResult by remember {
        mutableIntStateOf(0)
    }

    var coinResult by remember {
        mutableIntStateOf(0)
    }

    var imageD by remember {
        mutableIntStateOf(R.drawable.dice_0)
    }
    var imageC by remember {
        mutableIntStateOf(R.drawable.coin_0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement
            .spacedBy(
                25.dp,
                alignment = Alignment.CenterVertically
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val random : Random = Random

        val angleD = remember {
            Animatable(0f)
        }
        val angleC = remember {
            Animatable(0f)
        }

        val coroutineScope = rememberCoroutineScope()


        Image(painter = painterResource(imageD),
            contentDescription = null,
            Modifier
                .size(100.dp)
                .graphicsLayer {
                    rotationZ = angleD.value
                    rotationY = angleD.value
                }
        )


        Text(
            text = if(diceResult!=0) "$diceResult"
            else "Throw the dice!",
            fontWeight= FontWeight.Bold,
            fontSize = 25.sp
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    angleD.snapTo(0f)
                    angleD.animateTo(
                        targetValue = 270f,
                        animationSpec = tween(durationMillis = 300,easing = LinearEasing)
                    )

                    diceResult= random.nextInt(1,7)
                    when(diceResult){
                        1 -> imageD=R.drawable.dice_1
                        2 -> imageD=R.drawable.dice_2
                        3 -> imageD=R.drawable.dice_3
                        4 -> imageD=R.drawable.dice_4
                        5 -> imageD=R.drawable.dice_5
                        6 -> imageD=R.drawable.dice_6
                    }

                    angleD.animateTo(
                        targetValue = 360f,
                        animationSpec = tween(durationMillis = 100,easing = LinearEasing)
                    )
                }
            }
        ) {
            Text(text = "Dice Roll")
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    angleC.snapTo(0f)
                    angleC.animateTo(
                        targetValue = 270f,
                        animationSpec = tween(durationMillis = 300,easing = LinearEasing)
                    )
                    coinResult = random.nextInt(1, 3)
                    imageC = if (coinResult == 1) {
                        R.drawable.coin_h
                    } else R.drawable.coin_t

                    angleC.animateTo(
                        targetValue = 360f,
                        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
                    )
                }
            }
        ) {
            Text(text = "Coin Toss")
        }

        Text(
            text = when (coinResult) {
                1 -> "Heads"
                2 -> "Tails"
                else -> "Toss a coin!"
            },
            fontWeight= FontWeight.Bold,
            fontSize = 25.sp
        )

        Image(painter = painterResource(imageC),
            contentDescription = null,
            Modifier
                .size(100.dp)
                .graphicsLayer {
                    rotationX = angleC.value
                }
        )

        Button(
            onClick = {
            navController.popBackStack()
            }
        ) {
           Text(text = "Back")
        }
    }
}

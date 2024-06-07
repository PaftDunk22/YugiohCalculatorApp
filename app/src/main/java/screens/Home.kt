package screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.yugiohproject.R
import com.example.yugiohproject.Screen
import com.example.yugiohproject.ui.theme.Log
import com.example.yugiohproject.ui.theme.YugiohViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import java.util.Locale


//@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(navController: NavController,
){

    Box(modifier = Modifier
        .fillMaxSize()
        .paint(
            painterResource(R.drawable.background_6),
            contentScale = ContentScale.FillBounds
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally)

    {
        Text(
            text = "Yu-Gi-OhCalculatorApp",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight= FontWeight.Bold,
            )

        val startTime = 2700L

        var currentTime by rememberSaveable {
            mutableStateOf(startTime*1000L)
        }

        var isTimerRunning by rememberSaveable {
            mutableStateOf(false)
        }

        val minutes:Long = (currentTime/1000L)/60%60
        val seconds:Long = (currentTime/1000L)%60

        LaunchedEffect(key1 = currentTime,key2=isTimerRunning) {
            if(currentTime>0 && isTimerRunning){
                delay(100L)
                currentTime-=100L
            }
        }

        Button(
            modifier = Modifier.padding(0.dp,30.dp,0.dp,10.dp),
            onClick = {
                if(currentTime<=0L){
                    currentTime=startTime
                    isTimerRunning=true
                }else{
                    isTimerRunning=!isTimerRunning
                }
            })
        {
            Text(
                text = String.format(Locale.ENGLISH,"%02d:%02d", minutes,seconds),
                color = Color.Black,
                fontWeight= FontWeight.Bold,
                fontSize = 35.sp
            )
        }

    Button(
        onClick = {
            currentTime=startTime * 1000L
            isTimerRunning=false
        },
    ) {
        Text(text = "Restart")
    }
}
    var LP1change by rememberSaveable {
        mutableStateOf("1000")
    }
    var LP2change by rememberSaveable {
        mutableStateOf("1000")
    }
    var LP1 by rememberSaveable {
        mutableIntStateOf(8000)
    }
    var LP2 by rememberSaveable {
        mutableIntStateOf(8000)
    }

    var result by remember {
        mutableStateOf("Error")
    }

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbLogs: CollectionReference = db.collection("Logs")

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center

    )
    {
        Text(
            text = "$LP1",
            fontWeight= FontWeight.Bold,
            fontSize = 35.sp
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    if(LP1change.isNotEmpty()){LP1+=LP1change.toInt()}
                }
            ) {
                Text(text = "+")
            }
            Button(
                onClick = {
                    if(LP1change.isNotEmpty()){LP1-=LP1change.toInt()}
                }
            ) {
                Text(text = "-")
            }
            Button(
                onClick =
                {
                    LP1/=2
                })
            {
                Text(text = "/2")
            }
            TextField(value = LP1change, onValueChange = {if (it.isDigitsOnly()) LP1change = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(85.dp)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp,125.dp,0.dp,0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement
            .spacedBy(
                10.dp,
                alignment = Alignment.CenterVertically
            ),

    )
    {

        Button(
            onClick = {
                LP1change = 1000.toString()
                LP2change = 1000.toString()
                LP1=8000
                LP2=8000

            })
        {
            Text(text = "Reset")
        }
        Button(
            onClick = {
                navController.navigate(Screen.ToolScreen.route)
            })
        {
            Text(text = "TOOLS")
        }
        Button(
            onClick = {
                navController.navigate(Screen.LogScreen.route)
            })
        {
            Text(text = "LOGS")
        }

        Button(onClick = {
            result = if(LP1 > LP2){
                "Player 1 won at "
            }else if(LP2 > LP1){
                "Player 2 won at "
            }else{
                "Players drew at "
            }

            val LogResult = Log(result=result)

            val newRef = dbLogs.document()

            LogResult.ID = newRef.id

            newRef.set(LogResult).addOnSuccessListener {
                Toast.makeText(
                    context,"Result logged successfully",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{e->
                Toast.makeText(
                    context, "Fail to log \n$e", Toast.LENGTH_SHORT).show()
            }

        }) {
            Text(text = "Log Result")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "$LP2",
            fontWeight= FontWeight.Bold,
            fontSize = 35.sp
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    if(LP2change.isNotEmpty()){LP2+=LP2change.toInt()}
                }
            ) {
                Text(text = "+")
            }
            Button(
                onClick = {
                    if(LP2change.isNotEmpty()){LP2-=LP2change.toInt()}
                }
            ) {
                Text(text = "-")
            }
            Button(
                onClick =
                {
                    LP2/=2
                })
            {
                Text(text = "/2")
            }
            TextField(value = LP2change,
                onValueChange = {if (it.isDigitsOnly()) LP2change = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(85.dp)
            )
        }
    }
}







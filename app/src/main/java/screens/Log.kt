package screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yugiohproject.R
import com.example.yugiohproject.ui.theme.Log
import com.example.yugiohproject.ui.theme.YugiohViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Logs(navController: NavController,
         viewModel:YugiohViewModel
){
    Box(modifier = Modifier
        .fillMaxSize()
        .paint(
            painterResource(R.drawable.background_6),
            contentScale = ContentScale.FillBounds
        )
    )
    val sfd = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbLogs: CollectionReference = db.collection("Logs")

    val LogResults by remember {
        mutableStateOf(viewModel.LogsData)
    }

    fun retriveLogs() = CoroutineScope(Dispatchers.IO).launch {
        val querySnapshot = dbLogs.get().await()
        for (document in querySnapshot){
            val log = document.toObject<Log>()
            if(!LogResults.contains(log)){
                LogResults.add(log)
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        retriveLogs()

        LazyColumn(Modifier.weight(weight = 1f, fill = false)){
            itemsIndexed(LogResults){ _, it ->
                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .combinedClickable(
                        onLongClick ={
                            dbLogs.document(it.ID).delete()
                            LogResults.remove(it)
                        }
                    ) {
                    }
                ) {
                    val date = it.date?.let { it1 -> sfd.format(it1) }
                    Text(
                        text = it.result + "\n" + date,
                        fontWeight= FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        Button(onClick = {
            navController.popBackStack() },
            modifier = Modifier.padding(vertical = 25.dp)
        ) {
            Text(text = "Back")
        }
    }

}

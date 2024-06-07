package com.example.yugiohproject.ui.theme

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.firestore
import java.util.Date

class YugiohViewModel: ViewModel() {
    private val db = Firebase.firestore
    val LogsData = mutableStateListOf<Log>()
    init {
        fetchDatabaseData()
    }
    private fun fetchDatabaseData() {
        db.collection("Logs")
            .get()
            .addOnSuccessListener { result ->
                for (data in result.documents) {
                    val log = data.toObject(Log::class.java)
                    if (log != null) {
                        log.ID=data.id
                        LogsData.add(log)
                    }
                }
            }
    }
}

data class Log(
    var ID: String = "",
    val result: String = "",
    @ServerTimestamp
    var date: Date? = null
)


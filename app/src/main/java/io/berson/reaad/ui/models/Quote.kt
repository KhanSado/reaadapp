package io.berson.reaad.ui.models

import com.google.firebase.Timestamp

data class Quote (
    val userId: String = "",
    val bookId: String = "",
    val quoteDescription: String = "",
    val createAt: Timestamp = Timestamp.now(),
    val updateAt: Timestamp = Timestamp.now(),
    val documentId: String = "",
)

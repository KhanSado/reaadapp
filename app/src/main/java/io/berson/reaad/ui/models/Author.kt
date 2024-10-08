package io.berson.reaad.ui.models

import com.google.firebase.Timestamp


data class Author(
    val userId: String = "",
    val books: List<String>? = null,
    val firstname: String = "",
    val lastname: String = "",
    val createAt: Timestamp = Timestamp.now(),
    val updateAt: Timestamp = Timestamp.now(),
    val documentId: String = "",
)

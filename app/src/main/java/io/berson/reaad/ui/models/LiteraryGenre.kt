package io.berson.reaad.ui.models

import com.google.firebase.Timestamp

data class LiteraryGenre(
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val createAt: Timestamp = Timestamp.now(),
    val updateAt: Timestamp = Timestamp.now(),
    val documentId: String = "",
)

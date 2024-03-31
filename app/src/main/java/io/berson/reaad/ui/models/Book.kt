package io.berson.reaad.ui.models

import com.google.firebase.Timestamp

data class Book(
    val userId: String = "",
    val title: String = "",
    val subtitle: String = "",
    val cover: String = "",
    val authorId: String = "",
    val literaryGenreId: String = "",
    val publishingCoId: String = "",
    val createAt: Timestamp = Timestamp.now(),
    val updateAt: Timestamp = Timestamp.now(),
    val documentId: String = "",
)

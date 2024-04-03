package io.berson.reaad.ui.models

import com.google.firebase.Timestamp


data class User(
    val userId: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val friends: List<String>? = null,
    val createAt: Timestamp = Timestamp.now(),
    val updateAt: Timestamp = Timestamp.now(),
    val documentId: String = "",
)

package io.berson.reaad.ui.models

import com.google.firebase.Timestamp


data class PublishingCo(
    val userId: String = "",
    val name: String = "",
    val logo: String = "",
    val createAt: Timestamp = Timestamp.now(),
    val updateAt: Timestamp = Timestamp.now(),
    val documentId: String = "",
)

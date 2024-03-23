package io.berson.reaad.ui.utils

import androidx.compose.runtime.MutableState
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.models.PublishingCo

fun mountPublishingCoList(publishingCo: List<PublishingCo>?): MutableList<PublishingCo>{
    val publishingCoList = mutableListOf<PublishingCo>()
    if (publishingCo != null) {
        for (author in publishingCo) {
            publishingCoList.addAll(
                arrayOf(
                    PublishingCo(
                        userId = author.userId,
                        name = author.name,
                        logo = author.logo,
                        documentId = author.documentId
                    )
                )
            )
        }
    }
    return publishingCoList
}
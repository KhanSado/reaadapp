package io.berson.reaad.ui.utils

import androidx.compose.runtime.MutableState
import io.berson.reaad.ui.models.Author

fun mountAuthorList(authors: List<Author>?): MutableList<Author>{
    val authorList = mutableListOf<Author>()
    if (authors != null) {
        for (author in authors) {
            authorList.addAll(
                arrayOf(
                    Author(
                        userId = author.userId,
                        firstname = author.firstname,
                        lastname = author.lastname,
                        documentId = author.documentId
                    )
                )
            )
        }
    }
    return authorList
}
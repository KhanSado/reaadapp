package io.berson.reaad.ui.utils

import androidx.compose.runtime.MutableState
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.models.LiteraryGenre
import io.berson.reaad.ui.models.PublishingCo

fun mountLiteraryGenreList(literaryGenres: List<LiteraryGenre>?): MutableList<LiteraryGenre>{
    val literaryGenreList = mutableListOf<LiteraryGenre>()
    if (literaryGenres != null) {
        for (literaryGenre in literaryGenres) {
            literaryGenreList.addAll(
                arrayOf(
                    LiteraryGenre(
                        userId = literaryGenre.userId,
                        name = literaryGenre.name,
                        description = literaryGenre.description,
                        documentId = literaryGenre.documentId
                    )
                )
            )
        }
    }
    return literaryGenreList
}
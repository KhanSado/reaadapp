package io.berson.reaad.ui.utils

import io.berson.reaad.ui.models.Book

fun mountBookList(books: List<Book>?): MutableList<Book>{
    val authorList = mutableListOf<Book>()
    if (books != null) {
        for (book in books) {
            authorList.addAll(
                arrayOf(
                    Book(
                        userId = book.userId,
                        title = book.title,
                        subtitle = book.subtitle,
                        authorId = book.authorId,
                        documentId = book.documentId
                    )
                )
            )
        }
    }
    return authorList
}
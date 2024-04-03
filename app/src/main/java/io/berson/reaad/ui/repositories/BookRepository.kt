package io.berson.reaad.ui.repositories

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.berson.reaad.ui.models.Book

const val BOOK_COLLECTION_REF = "books"

class BookRepository {
    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val bookRef: CollectionReference = Firebase
        .firestore.collection(BOOK_COLLECTION_REF)

    fun getAllBookToUser(
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (List<Book>?) -> Unit
    ) {
        bookRef
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(
                    it.toObjects(Book::class.java)
                )
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun getBookByAuthorListToUser(
        authorId: String?,
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (List<Book>?) -> Unit
    ) {
        bookRef
            .whereEqualTo("userId", userId)
            .whereEqualTo("authorId", authorId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(
                    it.toObjects(Book::class.java)
                )
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun getBookByLiteraryGenreListToUser(
        literaryGenreId: String?,
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (List<Book>?) -> Unit
    ) {
        bookRef
            .whereEqualTo("userId", userId)
            .whereEqualTo("literaryGenreId", literaryGenreId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(
                    it.toObjects(Book::class.java)
                )
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun getBookByPublishingCoListToUser(
        publishingCoId: String?,
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (List<Book>?) -> Unit
    ) {
        bookRef
            .whereEqualTo("userId", userId)
            .whereEqualTo("publishingCoId", publishingCoId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(
                    it.toObjects(Book::class.java)
                )
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun getBook(
        bookId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Book?) -> Unit
    ) {
        bookRef
            .document(bookId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Book::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addBook(
        userId: String,
        authorId: String,
        publishingCoId: String,
        literaryGenreId: String,
        title: String,
        subtitle: String,
        cover: String,
        timestamp: Timestamp,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = bookRef.document().id
        val book = Book(
            userId = userId,
            title = title,
            subtitle = subtitle,
            cover = cover,
            authorId = authorId,
            publishingCoId = publishingCoId,
            literaryGenreId = literaryGenreId,
            createAt = timestamp,
            documentId = documentId
        )
        bookRef
            .document(documentId)
            .set(book)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    fun deleteAuthor(authorId: String, onComplete: (Boolean) -> Unit) {
        bookRef.document(authorId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun updateBook(
        title: String,
        subtitle: String,
        bookId: String,
        updateAt: Timestamp,
        onResult: (Boolean) -> Unit
    ) {
        val updateData = hashMapOf<String, Any>(
            "firstName" to title,
            "lastName" to subtitle,
            "updateAt" to updateAt,
        )

        bookRef.document(bookId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }
}

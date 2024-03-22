package io.berson.reaad.ui.repositories

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import io.berson.reaad.ui.models.Author
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val AUTHOR_COLLECTION_REF = "authors"

class AuthorRepository {
    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    val authorList: List<Author?> = mutableListOf()

    private val authorsRef: CollectionReference = Firebase
        .firestore.collection(AUTHOR_COLLECTION_REF)


    fun getAuthorsListToUser(
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (List<Author>?) -> Unit
    ){
        authorsRef
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it.toObjects(Author::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun getAuthor(
        authorId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Author?) -> Unit
    ) {
        authorsRef
            .document(authorId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Author::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addAuthor(
        userId: String,
        firstName: String,
        lastName: String,
        timestamp: Timestamp,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = authorsRef.document().id
        val author = Author(
            userId = userId,
            firstname = firstName,
            lastname = lastName,
            createAt = timestamp,
            documentId = documentId
        )
        authorsRef
            .document(documentId)
            .set(author)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    fun deleteAuthor(authorId: String, onComplete: (Boolean) -> Unit) {
        authorsRef.document(authorId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun updateAuthor(
        firstName: String,
        lastName: String,
        authorId: String,
        updateAt: Timestamp,
        onResult: (Boolean) -> Unit
    ) {
        val updateData = hashMapOf<String, Any>(
            "firstName" to firstName,
            "lastName" to lastName,
            "updateAt" to updateAt,
        )

        authorsRef.document(authorId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }
}

sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)

}
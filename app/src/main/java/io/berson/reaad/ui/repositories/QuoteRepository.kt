package io.berson.reaad.ui.repositories

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.models.Book
import io.berson.reaad.ui.models.Quote
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val QUOTES_COLLECTION_REF = "quotes"

class QuoteRepository {
    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val quoteRef: CollectionReference = Firebase
        .firestore.collection(QUOTES_COLLECTION_REF)

    fun getAllQuotesToUser(
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (List<Quote>?) -> Unit
    ) {
        quoteRef
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(
                    it.toObjects(Quote::class.java)
                )
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun getQuotesByBooksListToUser(
        bookId: String,
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (List<Quote>?) -> Unit
    ) {
        quoteRef
            .whereEqualTo("userId", userId)
            .whereEqualTo("bookId", bookId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(
                    it.toObjects(Quote::class.java)
                )
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }


    fun getQuotes(
        quoteId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Quote?) -> Unit
    ) {
        quoteRef
            .document(quoteId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Quote::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addQuote(
        userId: String,
        bookId: String,
        quoteDescription: String,
        timestamp: Timestamp,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = quoteRef.document().id
        val quote = Quote(
            userId = userId,
            quoteDescription = quoteDescription,
            bookId = bookId,
            createAt = timestamp,
            documentId = documentId
        )
        quoteRef
            .document(documentId)
            .set(quote)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    fun deleteQuote(quoteId: String, onComplete: (Boolean) -> Unit) {
        quoteRef.document(quoteId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun updateQuote(
        quoteDescription: String,
        bookId: String,
        quoteId: String,
        updateAt: Timestamp,
        onResult: (Boolean) -> Unit
    ) {
        val updateData = hashMapOf<String, Any>(
            "quoteDescription" to quoteDescription,
            "bookId" to bookId,
            "updateAt" to updateAt,
        )

        quoteRef.document(quoteId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }
}

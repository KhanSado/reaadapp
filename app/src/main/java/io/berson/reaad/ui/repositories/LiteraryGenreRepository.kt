package io.berson.reaad.ui.repositories

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.berson.reaad.ui.models.LiteraryGenre

const val LITERARY_GENRE_COLLECTION_REF = "literaryGenre"

class LiteraryGenreRepository {
    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    val literaryGenreList: List<LiteraryGenre?> = mutableListOf()

    private val literaryGenreRef: CollectionReference = Firebase
        .firestore.collection(LITERARY_GENRE_COLLECTION_REF)


    fun getLiteraryGenreListToUser(
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (List<LiteraryGenre>?) -> Unit
    ){
        literaryGenreRef
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it.toObjects(LiteraryGenre::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun getLiteraryGenre(
        literaryGenreId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (LiteraryGenre?) -> Unit
    ) {
        literaryGenreRef
            .document(literaryGenreId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(LiteraryGenre::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addLiteraryGenre(
        userId: String,
        name: String,
        description: String,
        timestamp: Timestamp,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = literaryGenreRef.document().id
        val literaryGenre = LiteraryGenre(
            userId = userId,
            name = name,
            description = description,
            createAt = timestamp,
            documentId = documentId
        )
        literaryGenreRef
            .document(documentId)
            .set(literaryGenre)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    fun deleteLiteraryGenre(literaryGenreId: String, onComplete: (Boolean) -> Unit) {
        literaryGenreRef.document(literaryGenreId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun updateLiteraryGenre(
        name: String,
        description: String,
        literaryGenreId: String,
        updateAt: Timestamp,
        onResult: (Boolean) -> Unit
    ) {
        val updateData = hashMapOf<String, Any>(
            "name" to name,
            "description" to description,
            "updateAt" to updateAt,
        )

        literaryGenreRef.document(literaryGenreId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }
}
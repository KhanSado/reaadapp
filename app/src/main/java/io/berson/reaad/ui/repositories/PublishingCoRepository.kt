package io.berson.reaad.ui.repositories

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.berson.reaad.ui.models.PublishingCo

const val PUBLISHING_CO_COLLECTION_REF = "publishingCo"

class PublishingCoRepository {
    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val publishingCoRef: CollectionReference = Firebase
        .firestore.collection(PUBLISHING_CO_COLLECTION_REF)


    fun getPublishingCoListToUser(
        userId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (List<PublishingCo>?) -> Unit
    ){
        publishingCoRef
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it.toObjects(PublishingCo::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun getPublishingCo(
        publishingCoId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (PublishingCo?) -> Unit
    ) {
        publishingCoRef
            .document(publishingCoId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(PublishingCo::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addPublishingCo(
        userId: String,
        name: String,
        logo: String,
        timestamp: Timestamp,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = publishingCoRef.document().id
        val publishingCo = PublishingCo(
            userId = userId,
            name = name,
            logo = logo,
            createAt = timestamp,
            documentId = documentId
        )
        publishingCoRef
            .document(documentId)
            .set(publishingCo)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    fun deletePublisherCo(publisherCoId: String, onComplete: (Boolean) -> Unit) {
        publishingCoRef.document(publisherCoId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun updatePublishingCo(
        name: String,
        logo: String,
        publishingCoId: String,
        updateAt: Timestamp,
        onResult: (Boolean) -> Unit
    ) {
        val updateData = hashMapOf<String, Any>(
            "name" to name,
            "logo" to logo,
            "updateAt" to updateAt,
        )

        publishingCoRef.document(publishingCoId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }
}
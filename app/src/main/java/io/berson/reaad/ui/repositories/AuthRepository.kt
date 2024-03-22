package io.berson.reaad.ui.repositories

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val USER_COLLECTION_REF = "user"

class AuthRepository {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val userRef: CollectionReference = Firebase
        .firestore.collection(USER_COLLECTION_REF)

    fun createFullUser(
        firstName: String,
        lastName: String,
        email: String,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = userRef.document().id
        val user = User(
            userId = getUserId(),
            firstname = firstName,
            lastname = lastName,
            email = email,
            documentId = documentId
        )
        userRef
            .document(documentId)
            .set(user)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    suspend fun createUser(
        email: String,
        password: String,
        onCompleteSignup: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isComplete) {
                    onCompleteSignup.invoke(true)
                } else {
                    onCompleteSignup.invoke(false)
                }
            }.await()
    }

    suspend fun loginUser(
        email: String,
        password: String,
        onCompleteLogin: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isComplete) {
                    onCompleteLogin.invoke(true)
                } else {
                    onCompleteLogin.invoke(false)
                }
            }.await()
    }
}
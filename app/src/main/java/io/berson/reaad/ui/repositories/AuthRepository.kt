package io.berson.reaad.ui.repositories

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

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
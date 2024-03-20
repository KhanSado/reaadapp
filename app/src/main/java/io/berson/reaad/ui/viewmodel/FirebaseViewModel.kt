package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(
    val auth: FirebaseAuth
) : ViewModel() {
    var signedIn = mutableStateOf(false)
    var error = mutableStateOf(false)
    var inProgress = mutableStateOf(false)
    var popUpNotification = mutableStateOf(null)

    fun onSignup(email: String, pass: String){
        inProgress.value = true

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signedIn.value = true
                } else {
                    error.value = true
                }
            }
        inProgress.value = false
    }

    fun login(email: String, pass: String) {
        inProgress.value = true

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signedIn.value = true
                } else {
                    error.value = true
                }
            }

        inProgress.value = false
    }
}
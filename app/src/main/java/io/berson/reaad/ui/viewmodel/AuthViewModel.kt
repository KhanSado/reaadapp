package io.berson.reaad.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.berson.reaad.ui.models.User
import io.berson.reaad.ui.repositories.AUTHOR_COLLECTION_REF
import io.berson.reaad.ui.repositories.AuthRepository
import io.berson.reaad.ui.repositories.USER_COLLECTION_REF
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
    ) : ViewModel() {
    val currentUser = repository.currentUser

    val hasUser: Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(userName: String) {
        loginUiState = loginUiState.copy(email = userName)
    }

    fun onPasswordNameChange(password: String) {
        loginUiState = loginUiState.copy(password = password)
    }

    fun onEmailChangeSignup(userName: String) {
        loginUiState = loginUiState.copy(emailSignUp = userName)
    }

    fun onPasswordChangeSignup(password: String) {
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }

    fun onConfirmPasswordChange(password: String) {
        loginUiState = loginUiState.copy(confirmPasswordSignUp = password)
    }

    fun onFirstNameChangeSignup(firstName: String) {
        loginUiState = loginUiState.copy(firstname = firstName)
    }

    fun onLastNameChangeSignup(lastname: String) {
        loginUiState = loginUiState.copy(lastname = lastname)
    }

    private fun validateLoginForm() =
        loginUiState.email.isNotBlank() &&
                loginUiState.password.isNotBlank()

    private fun validateSignupForm() =
        loginUiState.emailSignUp.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank() &&
                loginUiState.confirmPasswordSignUp.isNotBlank() &&
                loginUiState.firstname.isNotBlank() &&
                loginUiState.lastname.isNotBlank()


    fun createUser() = viewModelScope.launch {
        try {
            if (!validateSignupForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            if (loginUiState.passwordSignUp !=
                loginUiState.confirmPasswordSignUp
            ) {
                throw IllegalArgumentException(
                    "senhas não são iguais"
                )
            }
            loginUiState = loginUiState.copy(signUpError = null)
            repository.createUser(
                loginUiState.emailSignUp,
                loginUiState.passwordSignUp
            ) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    addUserFull()
                    loginUiState.copy(isSuccessSignup = true)
                } else {
                    loginUiState.copy(isSuccessSignup = false)
                }

            }
        } catch (e: Exception) {
            loginUiState = loginUiState.copy(signUpError = "verifique suas credenciais e tente novamente!")
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }


    }

    fun loginUser() = viewModelScope.launch {
        try {
            if (!validateLoginForm()) {
                throw IllegalArgumentException("email e/ou senha não podem ficar vazios")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(loginError = null)
            repository.loginUser(
                loginUiState.email,
                loginUiState.password
            ) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    loginUiState.copy(isSuccessLogin = true)
                } else {
                    loginUiState.copy(isSuccessLogin = false)
                }

            }


        } catch (e: Exception) {
            loginUiState = loginUiState.copy(loginError = "verifique suas credenciais e tente novamente!")
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }


    private fun addUserFull(){
        if (hasUser){
            repository.createFullUser(
                firstName = loginUiState.firstname,
                lastName = loginUiState.lastname,
                email = loginUiState.emailSignUp
            ){
                loginUiState = loginUiState.copy(userAddedStatus = it)
                loginUiState.copy(isSuccessCreate = true)
            }
        }
    }



}

data class LoginUiState(
    var email: String = "",
    val password: String = "",
    val firstname: String = "",
    val lastname: String = "",

    val emailSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",

    val isSuccessCreate: Boolean = false,
    val userAddedStatus: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val isSuccessSignup: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null,
    val loginSignupError: String? = null,
)
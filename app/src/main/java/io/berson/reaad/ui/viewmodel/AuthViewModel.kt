package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.models.User
import io.berson.reaad.ui.repositories.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    val currentUser = repository.currentUser

    val userId: String
        get() = repository.getUserId()

    val hasUser: Boolean
        get() = repository.hasUser()

    fun logout() {
        repository.logout()
        loginUiState.copy(isLogoutSuccess = true)
    }

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

    fun onEmailRecoveryChange(email: String) {
        loginUiState = loginUiState.copy(emailRecovery = email)
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

    private fun validateRecoveryPassForm() =
        loginUiState.emailRecovery.isNotBlank()

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

    fun recoveryPass() = viewModelScope.launch {
        try {
            if (!validateRecoveryPassForm()) {
                throw IllegalArgumentException("você precisa informar seu email da conta a recuperar")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(recoveryError = null)
            repository.recoveryPass(
                loginUiState.emailRecovery,
            ) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    loginUiState.copy(isSuccessRecovery = true)
                } else {
                    loginUiState.copy(isSuccessRecovery = false)
                }

            }
        } catch (e: Exception) {
            loginUiState = loginUiState.copy(recoveryError = "verifique suas credenciais e tente novamente!")
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
                loginUiState = loginUiState.copy(isSuccessCreate = true)
            }
        }
    }

    fun getUser(){
        repository.getUser(
            userId,
            onError = {}
        ){
            loginUiState = loginUiState.copy(userLogged = it)
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

    var emailRecovery: String = "",

    val isSuccessCreate: Boolean = false,
    val isSuccessRecovered: Boolean = false,
    val userAddedStatus: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    var isSuccessRecovery: Boolean = false,
    val isSuccessSignup: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null,
    val recoveryError: String? = null,
    val loginSignupError: String? = null,

    val userLogged: User? = null,

    val isLogoutSuccess: Boolean = false,
)
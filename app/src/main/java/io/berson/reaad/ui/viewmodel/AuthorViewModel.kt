package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.repositories.AuthorRepository
import kotlinx.coroutines.launch

class AuthorViewModel(
    private val repository: AuthorRepository = AuthorRepository(),
) : ViewModel() {
    var authorUiState by mutableStateOf(AuthorUiState())
        private set

    val hasUser: Boolean
        get() = repository.hasUser()

    val userId: String
        get() = repository.getUserId()

    private val user: FirebaseUser?
        get() = repository.user()

    fun onFirstNameChange(firstName: String) {
        authorUiState = authorUiState.copy(firstName = firstName)
    }

    fun onLastNameChange(lastName: String) {
        authorUiState = authorUiState.copy(lastName = lastName)
    }

    private fun validateRegisterForm() =
        authorUiState.firstName.isNotBlank() &&
                authorUiState.lastName.isNotBlank()

    fun addAuthor() = viewModelScope.launch {
        try {
            if (!validateRegisterForm()) {
                throw IllegalArgumentException("preencha todos os campos obrigatórios")
            }
            authorUiState = authorUiState.copy(isLoading = true)

            authorUiState = authorUiState.copy(registerError = null)
            if (hasUser){
                repository.addAuthor(
                    userId = user!!.uid,
                    firstName = authorUiState.firstName,
                    lastName = authorUiState.lastName,
                    timestamp = Timestamp.now()
                ){
                    authorUiState = authorUiState.copy(authorAddedStatus = it)
                    authorUiState = authorUiState.copy(isLoading = false)
                    authorUiState = authorUiState.copy(isSuccessCreate = true)
                }
            }
        } catch (e: Exception) {
            authorUiState = authorUiState.copy(registerError = "não foi possivel registrar seu livro")
            e.printStackTrace()
        } finally {
            authorUiState = authorUiState.copy(isLoading = false)
        }
    }

    private fun setEditFields(author: Author){
        authorUiState = authorUiState.copy(
            firstName = author.firstname,
            lastName = author.lastname,
        )
    }

    fun getAuthorsList(){
        repository.getAuthorsListToUser(
            userId,
            onError = {}
        ){
            authorUiState = authorUiState.copy(authorList = it)
        }
    }

    fun getAuthorById(authorId:String){
        repository.getAuthor(
            authorId = authorId,
            onError = {},
        ){
            authorUiState = authorUiState.copy(selectedAuthor = it)
            authorUiState.selectedAuthor?.let { it1 -> setEditFields(it1) }
        }
    }

    fun updateAuthors(
        authorId: String
    ){
        repository.updateAuthor(
            authorUiState.firstName,
            authorUiState.lastName,
            authorId,
            Timestamp.now()
        ){
            authorUiState = authorUiState.copy(updateAuthorStatus = it)
        }
    }

    fun resetAuthorAddedStatus(){
        authorUiState = authorUiState.copy(
            authorAddedStatus = false,
            updateAuthorStatus = false,
        )
    }

    fun resetState(){
        authorUiState = AuthorUiState()
    }
}

data class AuthorUiState(
    val lastName: String = "",
    val firstName: String = "",
    val authorAddedStatus: Boolean = false,
    val updateAuthorStatus: Boolean = false,
    val selectedAuthor: Author? = null,
    val authorList: List<Author>? = null,
    val isLoading: Boolean = false,
    var isSuccessCreate: Boolean = false,

    val registerError: String? = null
)
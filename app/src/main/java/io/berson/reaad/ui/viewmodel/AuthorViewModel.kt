package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.repositories.AuthorRepository

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

    fun addAuthor(){
        authorUiState = authorUiState.copy(isLoading = true)
        if (hasUser){
            repository.addAuthor(
                userId = user!!.uid,
                firstName = authorUiState.firstName,
                lastName = authorUiState.lastName,
                timestamp = Timestamp.now()
            ){
                authorUiState = authorUiState.copy(authorAddedStatus = it)
                authorUiState = authorUiState.copy(isLoading = false)
                authorUiState.copy(isSuccessCreate = true)
            }
        }
    }

    private fun setEditFields(author: Author){
        authorUiState = authorUiState.copy(
            firstName = author.firstname,
            lastName = author.lastname,
        )

    }

    fun getAuthors(){
        repository.getUserAuthors(
            userId = userId
        )
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
    val isLoading: Boolean = false,
    val isSuccessCreate: Boolean = false,
    )
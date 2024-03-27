package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import io.berson.reaad.ui.models.LiteraryGenre
import io.berson.reaad.ui.repositories.LiteraryGenreRepository
import kotlinx.coroutines.launch

class LiteraryGenreViewModel(
    private val repository: LiteraryGenreRepository = LiteraryGenreRepository(),
) : ViewModel() {
    var literaryGenreUiState by mutableStateOf(LiteraryGenreUiState())
        private set

    val hasUser: Boolean
        get() = repository.hasUser()

    val userId: String
        get() = repository.getUserId()

    private val user: FirebaseUser?
        get() = repository.user()


    fun onNameChange(name: String) {
        literaryGenreUiState = literaryGenreUiState.copy(name = name)
    }

    fun onDescriptionChange(description: String) {
        literaryGenreUiState = literaryGenreUiState.copy(description = description)
    }

    private fun validateRegisterForm() =
        literaryGenreUiState.name.isNotBlank() &&
                literaryGenreUiState.description.isNotBlank()

    fun addLiteraryGenre() = viewModelScope.launch{
        try {
            if (!validateRegisterForm()) {
                throw IllegalArgumentException("preencha todos os campos obrigatórios")
            }
            literaryGenreUiState = literaryGenreUiState.copy(isLoading = true)

            literaryGenreUiState = literaryGenreUiState.copy(registerError = null)
            if (hasUser){
                repository.addLiteraryGenre(
                    userId = user!!.uid,
                    name = literaryGenreUiState.name,
                    description = literaryGenreUiState.description,
                    timestamp = Timestamp.now()
                ){
                    literaryGenreUiState = literaryGenreUiState.copy(literaryGenreAddedStatus = it)
                    literaryGenreUiState = literaryGenreUiState.copy(isLoading = false)
                    literaryGenreUiState = literaryGenreUiState.copy(isSuccessCreate = true)
                }
            }
        } catch (e: Exception) {
            literaryGenreUiState = literaryGenreUiState.copy(registerError = "não foi possivel registrar seu livro")
            e.printStackTrace()
        } finally {
            literaryGenreUiState = literaryGenreUiState.copy(isLoading = false)
        }
    }

    private fun setEditFields(literaryGenre: LiteraryGenre){
        literaryGenreUiState = literaryGenreUiState.copy(
            name = literaryGenre.name,
            description = literaryGenre.description,
        )
    }

    fun getLiteraryGenreList(){
        repository.getLiteraryGenreListToUser(
            userId,
            onError = {}
        ){
            literaryGenreUiState = literaryGenreUiState.copy(literaryGenreList = it)
        }
    }

    fun getLiteraryGenreById(literaryGenreId:String){
        repository.getLiteraryGenre(
            literaryGenreId = literaryGenreId,
            onError = {},
        ){
            literaryGenreUiState = literaryGenreUiState.copy(selectedLiteraryGenre = it)
            literaryGenreUiState.selectedLiteraryGenre?.let { it1 -> setEditFields(it1) }
        }
    }

    fun updateLiteraryGenre(
        literaryGenreId: String
    ){
        repository.updateLiteraryGenre(
            literaryGenreUiState.name,
            literaryGenreUiState.description,
            literaryGenreId,
            Timestamp.now()
        ){
            literaryGenreUiState = literaryGenreUiState.copy(updateLiteraryGenreStatus = it)
        }
    }

    fun resetLiteraryGenreAddedStatus(){
        literaryGenreUiState = literaryGenreUiState.copy(
            literaryGenreAddedStatus = false,
            updateLiteraryGenreStatus = false,
        )
    }

    fun resetState(){
        literaryGenreUiState = LiteraryGenreUiState()
    }
}

data class LiteraryGenreUiState(
    val name: String = "",
    val description: String = "",
    val literaryGenreAddedStatus: Boolean = false,
    val updateLiteraryGenreStatus: Boolean = false,
    val selectedLiteraryGenre: LiteraryGenre? = null,
    val literaryGenreList: List<LiteraryGenre>? = null,
    val isLoading: Boolean = false,
    var isSuccessCreate: Boolean = false,

    val registerError: String? = null
)
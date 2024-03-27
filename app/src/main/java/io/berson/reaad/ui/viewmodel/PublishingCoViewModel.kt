package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import io.berson.reaad.ui.models.PublishingCo
import io.berson.reaad.ui.repositories.PublishingCoRepository
import kotlinx.coroutines.launch

class PublishingCoViewModel(
    private val repository: PublishingCoRepository = PublishingCoRepository(),
) : ViewModel() {
    var publisingCoUiState by mutableStateOf(PublishingCoUiState())
        private set

    val hasUser: Boolean
        get() = repository.hasUser()

    val userId: String
        get() = repository.getUserId()

    private val user: FirebaseUser?
        get() = repository.user()


    fun onNameChange(name: String) {
        publisingCoUiState = publisingCoUiState.copy(name = name)
    }

    fun onLogoChange(logo: String) {
        publisingCoUiState = publisingCoUiState.copy(logo = logo)
    }

    private fun validateRegisterForm() =
        publisingCoUiState.name.isNotBlank()

    fun addPublishingCo() = viewModelScope.launch {
        try {
            if (!validateRegisterForm()) {
                throw IllegalArgumentException("preencha todos os campos obrigatórios")
            }
            publisingCoUiState = publisingCoUiState.copy(isLoading = true)

            publisingCoUiState = publisingCoUiState.copy(registerError = null)
            if (hasUser) {
                repository.addPublishingCo(
                    userId = user!!.uid,
                    name = publisingCoUiState.name,
                    logo = publisingCoUiState.logo,
                    timestamp = Timestamp.now()
                ) {
                    publisingCoUiState = publisingCoUiState.copy(publishingCoAddedStatus = it)
                    publisingCoUiState = publisingCoUiState.copy(isLoading = false)
                    publisingCoUiState = publisingCoUiState.copy(isSuccessCreate = true)
                }
            }
        } catch (e: Exception) {
            publisingCoUiState =
                publisingCoUiState.copy(registerError = "não foi possivel registrar seu livro")
            e.printStackTrace()
        } finally {
            publisingCoUiState = publisingCoUiState.copy(isLoading = false)
        }
    }

    private fun setEditFields(publishingCo: PublishingCo) {
        publisingCoUiState = publisingCoUiState.copy(
            name = publishingCo.name,
            logo = publishingCo.logo,
        )
    }

    fun getPublishingCoList() {
        repository.getPublishingCoListToUser(
            userId,
            onError = {}
        ) {
            publisingCoUiState = publisingCoUiState.copy(publishingCoList = it)
        }
    }

    fun getPublishingCoById(publishingCoId: String) {
        repository.getPublishingCo(
            publishingCoId = publishingCoId,
            onError = {},
        ) {
            publisingCoUiState = publisingCoUiState.copy(selectedPublishingCo = it)
            publisingCoUiState.selectedPublishingCo?.let { it1 -> setEditFields(it1) }
        }
    }

    fun updatePublishingCo(
        publishingCo: String
    ) {
        repository.updatePublishingCo(
            publisingCoUiState.name,
            publisingCoUiState.logo,
            publishingCo,
            Timestamp.now()
        ) {
            publisingCoUiState = publisingCoUiState.copy(updatePublishingCoStatus = it)
        }
    }

    fun resetAuthorAddedStatus() {
        publisingCoUiState = publisingCoUiState.copy(
            publishingCoAddedStatus = false,
            updatePublishingCoStatus = false,
        )
    }

    fun resetState() {
        publisingCoUiState = PublishingCoUiState()
    }
}

data class PublishingCoUiState(
    val name: String = "",
    val logo: String = "",
    val publishingCoAddedStatus: Boolean = false,
    val updatePublishingCoStatus: Boolean = false,
    val selectedPublishingCo: PublishingCo? = null,
    val publishingCoList: List<PublishingCo>? = null,
    val isLoading: Boolean = false,
    var isSuccessCreate: Boolean = false,

    val registerError: String? = null
)
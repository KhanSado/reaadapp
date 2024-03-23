package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import io.berson.reaad.ui.models.PublishingCo
import io.berson.reaad.ui.repositories.PublishingCoRepository

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

    fun addPublishingCo(){
        publisingCoUiState = publisingCoUiState.copy(isLoading = true)
        if (hasUser){
            repository.addPublishingCo(
                userId = user!!.uid,
                name = publisingCoUiState.name,
                logo = publisingCoUiState.logo,
                timestamp = Timestamp.now()
            ){
                publisingCoUiState = publisingCoUiState.copy(publishingCoAddedStatus = it)
                publisingCoUiState = publisingCoUiState.copy(isLoading = false)
                publisingCoUiState.copy(isSuccessCreate = true)
            }
        }
    }

    private fun setEditFields(publishingCo: PublishingCo){
        publisingCoUiState = publisingCoUiState.copy(
            name = publishingCo.name,
            logo = publishingCo.logo,
        )
    }

    fun getPublishingCoList(){
        repository.getPublishingCoListToUser(
            userId,
            onError = {}
        ){
            publisingCoUiState = publisingCoUiState.copy(publishingCoList = it)
        }
    }

    fun getPublishingCoById(publishingCoId:String){
        repository.getPublishingCo(
            publishingCoId = publishingCoId,
            onError = {},
        ){
            publisingCoUiState = publisingCoUiState.copy(selectedPublishingCo = it)
            publisingCoUiState.selectedPublishingCo?.let { it1 -> setEditFields(it1) }
        }
    }

    fun updatePublishingCo(
        publishingCo: String
    ){
        repository.updatePublishingCo(
            publisingCoUiState.name,
            publisingCoUiState.logo,
            publishingCo,
            Timestamp.now()
        ){
            publisingCoUiState = publisingCoUiState.copy(updatePublishingCoStatus = it)
        }
    }

    fun resetAuthorAddedStatus(){
        publisingCoUiState = publisingCoUiState.copy(
            publishingCoAddedStatus = false,
            updatePublishingCoStatus = false,
        )
    }

    fun resetState(){
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
    val isSuccessCreate: Boolean = false,
    )
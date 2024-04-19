package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import io.berson.reaad.R
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var _showGenre by mutableStateOf(false)
    var _showAuthor by mutableStateOf(false)
    var _showPublishingCo by mutableStateOf(false)

    init {
        viewModelScope.launch {
            val firebaseRemoteConfig = Firebase.remoteConfig
            firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

            firebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _showGenre = firebaseRemoteConfig.getBoolean("showGenre")
                        _showAuthor = firebaseRemoteConfig.getBoolean("showAuthor")
                        _showPublishingCo = firebaseRemoteConfig.getBoolean("showPublishingCo")
                    }
                }
        }
    }
}
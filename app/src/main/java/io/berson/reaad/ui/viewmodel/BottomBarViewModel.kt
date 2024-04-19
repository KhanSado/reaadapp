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

class BottomBarViewModel : ViewModel() {
    var _showHome by mutableStateOf(false)
    var _showProfile by mutableStateOf(false)
    var _showBooks by mutableStateOf(false)
    var _showQuotes by mutableStateOf(false)

    init {
        viewModelScope.launch {
            val firebaseRemoteConfig = Firebase.remoteConfig
            firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

            firebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _showProfile = firebaseRemoteConfig.getBoolean("showProfile")
                        _showHome = firebaseRemoteConfig.getBoolean("showHome")
                        _showBooks = firebaseRemoteConfig.getBoolean("showBooks")
                        _showQuotes = firebaseRemoteConfig.getBoolean("showQuote")
                    }
                }
        }
    }
}
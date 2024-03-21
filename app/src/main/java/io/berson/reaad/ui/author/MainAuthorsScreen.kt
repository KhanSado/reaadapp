package io.berson.reaad.ui.author

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.viewmodel.AuthorViewModel

@Composable
fun MainAuthorsScreen(vm: AuthorViewModel){

    val authorUiState = vm.authorUiState

    var asd = vm.getAuthors()

    Log.d("AASS", "MainAuthorsScreen: $asd")

    GradientSurface {
        Text(text = "")
    }
}
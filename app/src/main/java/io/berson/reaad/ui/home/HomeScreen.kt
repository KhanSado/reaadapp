package io.berson.reaad.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.viewmodel.AuthViewModel
import io.berson.reaad.ui.viewmodel.AuthorUiState
import io.berson.reaad.ui.viewmodel.AuthorViewModel

@Composable
fun HomeScreen(vm: AuthorViewModel) {

    vm.onFirstNameChange("Jhon")
    vm.onLastNameChange("Pipe")

    vm.addAuthor()



}
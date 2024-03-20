package io.berson.reaad.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.viewmodel.AuthViewModel
import io.berson.reaad.ui.viewmodel.AuthorViewModel

@Composable
fun HomeScreen(navController: NavController, vm: AuthorViewModel) {

    GradientSurface {
        Text(text = "Home")
    }
    
}
package io.berson.reaad.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.berson.reaad.ui.auth.LoginScreen
import io.berson.reaad.ui.auth.MainScreen
import io.berson.reaad.ui.auth.SignupScreen
import io.berson.reaad.ui.author.CreateNewAuthorScreen
import io.berson.reaad.ui.author.MainAuthorsScreen
import io.berson.reaad.ui.home.HomeScreen
import io.berson.reaad.ui.viewmodel.AuthViewModel
import io.berson.reaad.ui.viewmodel.AuthorViewModel

@Composable
fun ReaadNavigation(){
    val navController = rememberNavController()
    val authVm = AuthViewModel()
    val authorVm = AuthorViewModel()

    NavHost(navController = navController, startDestination = DestinationScreen.MainScreen.name) {
        composable(DestinationScreen.MainScreen.name){
            MainScreen(navController = navController)
        }

        composable(DestinationScreen.SignupScreen.name){
            SignupScreen(vm = authVm, onNavToHomePage = {navController.navigate(DestinationScreen.CreateNewAuthorScreen.name)}, onNavToLoginPage = {})
        }

        composable(DestinationScreen.LoginScreen.name){
            LoginScreen(vm = authVm, onNavToHomePage = {navController.navigate(DestinationScreen.CreateNewAuthorScreen.name)})
        }

        composable(DestinationScreen.HomeScreen.name){
            HomeScreen(vm = authorVm)
        }

        composable(DestinationScreen.MainAuthorsScreen.name){
            MainAuthorsScreen(vm = authorVm)
        }
        
        composable(DestinationScreen.CreateNewAuthorScreen.name){
            CreateNewAuthorScreen(vm = authorVm, onNavToMainAuthorsPage = {navController.navigate(DestinationScreen.MainAuthorsScreen.name)})
        }
    }
}
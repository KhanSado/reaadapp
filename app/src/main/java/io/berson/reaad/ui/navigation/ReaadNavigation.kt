package io.berson.reaad.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.berson.reaad.ui.auth.LoginScreen
import io.berson.reaad.ui.auth.MainScreen
import io.berson.reaad.ui.auth.SignupScreen
import io.berson.reaad.ui.home.HomeScreen
import io.berson.reaad.ui.viewmodel.FirebaseViewModel

@Composable
fun ReaadNavigation(){
    val vm = hiltViewModel<FirebaseViewModel>()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = DestinationScreen.MainScreen.name) {
        composable(DestinationScreen.MainScreen.name){
            MainScreen(navController = navController, vm = vm)
        }

        composable(DestinationScreen.SignupScreen.name){
            SignupScreen(navController = navController, vm = vm)
        }

        composable(DestinationScreen.LoginScreen.name){
            LoginScreen(navController = navController, vm = vm)
        }

        composable(DestinationScreen.HomeScreen.name){
            HomeScreen(navController = navController, vm = vm)
        }
    }
}
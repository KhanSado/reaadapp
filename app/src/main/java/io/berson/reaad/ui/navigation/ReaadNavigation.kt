package io.berson.reaad.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.berson.reaad.ui.splash.SplashScreen
import io.berson.reaad.ui.auth.LoginScreen
import io.berson.reaad.ui.auth.MainScreen
import io.berson.reaad.ui.auth.SignupScreen
import io.berson.reaad.ui.author.AuthorDetailScreen
import io.berson.reaad.ui.author.CreateNewAuthorScreen
import io.berson.reaad.ui.author.MainAuthorsScreen
import io.berson.reaad.ui.home.HomeScreen
import io.berson.reaad.ui.profileUser.ProfileUserScreen
import io.berson.reaad.ui.viewmodel.AuthViewModel
import io.berson.reaad.ui.viewmodel.AuthorViewModel

@Composable
fun ReaadNavigation(){
    val navController = rememberNavController()
    val authVm = AuthViewModel()
    val authorVm = AuthorViewModel()

    NavHost(navController = navController, startDestination = DestinationScreen.SplashScreen.name) {
        composable(DestinationScreen.SplashScreen.name) {
            SplashScreen(navController = navController, vm = authVm)
        }

        composable(DestinationScreen.MainScreen.name){
            MainScreen(navController = navController)
        }

        composable(DestinationScreen.SignupScreen.name){
            SignupScreen(vm = authVm, onNavToHomePage = {navController.navigate(DestinationScreen.HomeScreen.name)}, onNavToLoginPage = {})
        }

        composable(DestinationScreen.LoginScreen.name){
            LoginScreen(vm = authVm, onNavToHomePage = {navController.navigate(DestinationScreen.HomeScreen.name)})
        }

        composable(DestinationScreen.HomeScreen.name){
            HomeScreen(vm = authorVm, navController = navController)
        }

        composable(DestinationScreen.MainAuthorsScreen.name){
            MainAuthorsScreen(navController = navController, vm = authorVm)
        }

        composable(DestinationScreen.ProfileUserScreen.name){
            ProfileUserScreen(navController = navController, vm = authVm)
        }

        composable(
            "${DestinationScreen.AuthorDetailScreen.name}/{authorId}",
            listOf(
                navArgument("authorId") { type = NavType.StringType },
            )
        ){
            AuthorDetailScreen(vm = authorVm, authorId = "${it.arguments?.getString("authorId")}")
        }
        
        composable(DestinationScreen.CreateNewAuthorScreen.name){
            CreateNewAuthorScreen(vm = authorVm, onNavToMainAuthorsPage = {navController.navigate(DestinationScreen.MainAuthorsScreen.name)})
        }
    }
}
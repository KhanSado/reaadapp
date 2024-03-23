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
import io.berson.reaad.ui.publishingCo.CreateNewPublishingCoScreen
import io.berson.reaad.ui.publishingCo.MainPublishingCoScreen
import io.berson.reaad.ui.publishingCo.PublishingCoDetailScreen
import io.berson.reaad.ui.viewmodel.AuthViewModel
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel

@Composable
fun ReaadNavigation(){
    val navController = rememberNavController()
    val authVm = AuthViewModel()
    val authorVm = AuthorViewModel()
    val publishingCoVM = PublishingCoViewModel()

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
            HomeScreen(publishingCoVm = publishingCoVM, authorVm = authorVm, navController = navController)
        }

        composable(DestinationScreen.MainAuthorsScreen.name){
            MainAuthorsScreen(navController = navController, vm = authorVm)
        }

        composable(DestinationScreen.MainPublishingCoScreen.name){
            MainPublishingCoScreen(navController = navController, vm = publishingCoVM)
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

        composable(
            "${DestinationScreen.PublishingCoDetailScreen.name}/{publishingCoId}",
            listOf(
                navArgument("publishingCoId") { type = NavType.StringType },
            )
        ){
            PublishingCoDetailScreen(vm = publishingCoVM, publishingCoId = "${it.arguments?.getString("publishingCoId")}")
        }

        composable(DestinationScreen.CreateNewPublishingCoScreen.name){
            CreateNewPublishingCoScreen(vm = publishingCoVM, onNavToMainAuthorsPage = {navController.navigate(DestinationScreen.MainPublishingCoScreen.name)})
        }
    }
}
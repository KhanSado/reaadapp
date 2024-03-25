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
import io.berson.reaad.ui.auth.PassRecoveryScreen
import io.berson.reaad.ui.auth.SignupScreen
import io.berson.reaad.ui.author.AuthorDetailScreen
import io.berson.reaad.ui.author.CreateNewAuthorScreen
import io.berson.reaad.ui.author.MainAuthorsScreen
import io.berson.reaad.ui.book.CreateNewBookScreen
import io.berson.reaad.ui.book.MainBookScreen
import io.berson.reaad.ui.home.HomeScreen
import io.berson.reaad.ui.literaryGenre.CreateNewLiteraryGenreScreen
import io.berson.reaad.ui.literaryGenre.LiteraryGenreDetailScreen
import io.berson.reaad.ui.literaryGenre.MainLiteraryGenreScreen
import io.berson.reaad.ui.profileUser.ProfileUserScreen
import io.berson.reaad.ui.publishingCo.CreateNewPublishingCoScreen
import io.berson.reaad.ui.publishingCo.MainPublishingCoScreen
import io.berson.reaad.ui.publishingCo.PublishingCoDetailScreen
import io.berson.reaad.ui.viewmodel.AuthViewModel
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel

@Composable
fun ReaadNavigation() {
    val navController = rememberNavController()
    val authVm = AuthViewModel()
    val authorVm = AuthorViewModel()
    val publishingCoVm = PublishingCoViewModel()
    val literaryGenreVm = LiteraryGenreViewModel()
    val bookVm = BookViewModel()

    NavHost(navController = navController, startDestination = DestinationScreen.SplashScreen.name) {
        composable(DestinationScreen.SplashScreen.name) {
            SplashScreen(navController = navController, vm = authVm)
        }

        composable(DestinationScreen.MainScreen.name) {
            MainScreen(navController = navController)
        }

        composable(DestinationScreen.SignupScreen.name) {
            SignupScreen(
                vm = authVm,
                onNavToHomePage = { navController.navigate(DestinationScreen.HomeScreen.name) },
                onNavToLoginPage = {})
        }

        composable(DestinationScreen.LoginScreen.name) {
            LoginScreen(
                vm = authVm,
                onNavToHomePage = { navController.navigate(DestinationScreen.HomeScreen.name) })
        }

        composable(DestinationScreen.PassRecoveryScreen.name) {
            PassRecoveryScreen(
                vm = authVm,
                onNavToHomePage = { navController.navigate(DestinationScreen.LoginScreen.name) })
        }

        composable(DestinationScreen.HomeScreen.name) {
            HomeScreen(
                publishingCoVm = publishingCoVm,
                authorVm = authorVm,
                literaryGenreVm = literaryGenreVm,
                bookVm = bookVm,
                navController = navController
            )
        }

        composable(DestinationScreen.MainAuthorsScreen.name) {
            MainAuthorsScreen(navController = navController, vm = authorVm)
        }

        composable(DestinationScreen.MainPublishingCoScreen.name) {
            MainPublishingCoScreen(navController = navController, vm = publishingCoVm)
        }

        composable(DestinationScreen.MainLiteraryGenreScreen.name) {
            MainLiteraryGenreScreen(navController = navController, vm = literaryGenreVm)
        }

        composable(DestinationScreen.MainBookScreen.name) {
            MainBookScreen(navController = navController, vm = bookVm)
        }

        composable(DestinationScreen.ProfileUserScreen.name) {
            ProfileUserScreen(navController = navController, vm = authVm)
        }

        composable(
            "${DestinationScreen.AuthorDetailScreen.name}/{authorId}",
            listOf(
                navArgument("authorId") { type = NavType.StringType },
            )
        ) {
            AuthorDetailScreen(
                vm = authorVm,
                authorId = "${it.arguments?.getString("authorId")}",
                bookVm = bookVm,
                navController = navController
            )
        }

        composable(DestinationScreen.CreateNewAuthorScreen.name) {
            CreateNewAuthorScreen(
                vm = authorVm,
                onNavToMainAuthorsPage = { navController.navigate(DestinationScreen.MainAuthorsScreen.name) })
        }

        composable(
            "${DestinationScreen.PublishingCoDetailScreen.name}/{publishingCoId}",
            listOf(
                navArgument("publishingCoId") { type = NavType.StringType },
            )
        ) {
            PublishingCoDetailScreen(
                vm = publishingCoVm,
                bookVm = bookVm,
                publishingCoId = "${it.arguments?.getString("publishingCoId")}",
                navController = navController
            )
        }

        composable(DestinationScreen.CreateNewPublishingCoScreen.name) {
            CreateNewPublishingCoScreen(
                vm = publishingCoVm,
                onNavToMainAuthorsPage = { navController.navigate(DestinationScreen.MainPublishingCoScreen.name) })
        }

        composable(
            "${DestinationScreen.LiteraryGenreDetailScreen.name}/{literaryGenreId}",
            listOf(
                navArgument("literaryGenreId") { type = NavType.StringType },
            )
        ) {
            LiteraryGenreDetailScreen(
                vm = literaryGenreVm,
                bookVm = bookVm,
                literaryGenreId = "${it.arguments?.getString("literaryGenreId")}",
                navController = navController
            )
        }

        composable(DestinationScreen.CreateNewLiteraryGenreScreen.name) {
            CreateNewLiteraryGenreScreen(
                vm = literaryGenreVm,
                onNavToMainAuthorsPage = { navController.navigate(DestinationScreen.MainLiteraryGenreScreen.name) })
        }



        composable(DestinationScreen.CreateNewBookScreen.name) {
            CreateNewBookScreen(
                vm = bookVm,
                authorVm = authorVm,
                publishingCoVm = publishingCoVm,
                literaryGenreVm = literaryGenreVm,
                onNavToMainAuthorsPage = { navController.navigate(DestinationScreen.MainBookScreen.name) })
        }
    }
}
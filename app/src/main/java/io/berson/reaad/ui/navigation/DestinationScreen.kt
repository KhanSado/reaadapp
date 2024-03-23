package io.berson.reaad.ui.navigation

enum class DestinationScreen {
    SplashScreen,
    MainScreen,
    SignupScreen,
    HomeScreen,
    MainAuthorsScreen,
    CreateNewAuthorScreen,
    AuthorDetailScreen,
    MainPublishingCoScreen,
    CreateNewPublishingCoScreen,
    PublishingCoDetailScreen,
    MainLiteraryGenreScreen,
    CreateNewLiteraryGenreScreen,
    LiteraryGenreDetailScreen,
    LoginScreen,
    ProfileUserScreen;

    companion object {
        fun fromRoute(route: String?): DestinationScreen
                = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            MainScreen.name -> MainScreen
            SignupScreen.name -> SignupScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            MainAuthorsScreen.name -> MainAuthorsScreen
            CreateNewAuthorScreen.name -> CreateNewAuthorScreen
            AuthorDetailScreen.name -> AuthorDetailScreen
            MainPublishingCoScreen.name -> MainPublishingCoScreen
            CreateNewPublishingCoScreen.name -> CreateNewPublishingCoScreen
            PublishingCoDetailScreen.name -> PublishingCoDetailScreen
            MainLiteraryGenreScreen.name -> MainLiteraryGenreScreen
            CreateNewLiteraryGenreScreen.name -> CreateNewLiteraryGenreScreen
            LiteraryGenreDetailScreen.name -> LiteraryGenreDetailScreen
            ProfileUserScreen.name -> ProfileUserScreen
            null -> MainScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}
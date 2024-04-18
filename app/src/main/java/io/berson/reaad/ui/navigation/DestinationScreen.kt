package io.berson.reaad.ui.navigation

enum class DestinationScreen {
    SplashScreen,
    MainScreen,
    SignupScreen,
    SignupStep2Screen,
    SignupStep3Screen,
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
    MainBookScreen,
    CreateNewBookScreen,
    BookDetailScreen,
    LoginScreen,
    PassRecoveryScreen,
    CreateNewQuoteScreen,
    MainQuoteScreen,
    QuoteDetailScreen,
    ProfileUserScreen;

    companion object {
        fun fromRoute(route: String?): DestinationScreen
                = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            MainScreen.name -> MainScreen
            SignupScreen.name -> SignupScreen
            SignupStep2Screen.name -> SignupStep2Screen
            SignupStep3Screen.name -> SignupStep3Screen
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
            MainBookScreen.name -> MainBookScreen
            CreateNewBookScreen.name -> CreateNewBookScreen
            ProfileUserScreen.name -> ProfileUserScreen
            PassRecoveryScreen.name -> PassRecoveryScreen
            BookDetailScreen.name -> BookDetailScreen
            QuoteDetailScreen.name -> QuoteDetailScreen
            null -> MainScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}
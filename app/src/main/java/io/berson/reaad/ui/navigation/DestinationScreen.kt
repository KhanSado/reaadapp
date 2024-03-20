package io.berson.reaad.ui.navigation

enum class DestinationScreen {
    SplashScreen,
    MainScreen,
    SignupScreen,
    HomeScreen,
    LoginScreen;

    companion object {
        fun fromRoute(route: String?): DestinationScreen
                = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            MainScreen.name -> MainScreen
            SignupScreen.name -> SignupScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            null -> MainScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}
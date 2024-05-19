package org.d3if3011.resepi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.d3if3011.resepi.ui.screen.HomeScreen
import org.d3if3011.resepi.ui.screen.LoginScreen
import org.d3if3011.resepi.ui.screen.ProfileTopAppBar
import org.d3if3011.resepi.ui.screen.RegistrasiScreen
import org.d3if3011.resepi.ui.screen.SearchTopBar

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController, startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Registrasi.route) {
            RegistrasiScreen(navController)
        }
        composable(route = Screen.HomePage.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.ProfilePage.route) {
            ProfileTopAppBar(navController)
        }
        composable(route = Screen.SearchPage.route) {
            SearchTopBar(navController)
        }
    }
}
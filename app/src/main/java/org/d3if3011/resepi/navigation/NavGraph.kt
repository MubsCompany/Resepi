package org.d3if3011.resepi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.d3if3011.resepi.ui.screen.DetailScreen
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
            RegistrasiScreen(navController, false)
        }
        composable(route = Screen.RegistrasiError.route) {
            RegistrasiScreen(navController, true)
        }
        composable(route = Screen.HomePage.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.ProfilePage.route) {
            ProfileTopAppBar(navController)
        }
        composable(route = Screen.SearchPage.route) {
            SearchTopBar(navController, 1)
        }
        composable(route = Screen.SearchChicken.route) {
            SearchTopBar(navController,2)
        }
        composable(route = Screen.SearchMeat.route) {
            SearchTopBar(navController,3)
        }
        composable(route = Screen.SearchFish.route) {
            SearchTopBar(navController,4)
        }
        composable(route = Screen.SearchVegies.route) {
            SearchTopBar(navController,5)
        }
        composable(route = Screen.DetailPage.route+"/{idResep}") {
            val idResep = it.arguments?.getString("idResep")
            DetailScreen(navController, idResep?:"")
        }
//        composable()
    }
}
package org.d3if3011.resepi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.d3if3011.resepi.ui.screen.LoginScreen
import org.d3if3011.resepi.ui.screen.RegistrasiScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()){
NavHost(
    navController = navController,    startDestination = Screen.Login.route
){
    composable(route = Screen.Login.route){
        LoginScreen(navController)
    }
    composable(route = Screen.Registrasi.route){
        RegistrasiScreen(navController)
    }
}
}
package org.d3if3011.resepi.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("loginScreen")
    data object Registrasi: Screen("registrasiScreen")
}
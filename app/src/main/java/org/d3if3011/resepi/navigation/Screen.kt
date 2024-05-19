package org.d3if3011.resepi.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("loginScreen")
    data object Registrasi: Screen("registrasiScreen")

    data object BookmarkPage: Screen("bookmarkScreen")
    data object HomePage: Screen("homeScreen")
    data object ProfilePage: Screen("profileScreen")
    data object SearchPage: Screen("searchScreen")
}
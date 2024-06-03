package org.d3if3011.resepi.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("loginScreen")
    data object Registrasi: Screen("registrasiScreen")
    data object RegistrasiError: Screen("registrasiScreenErrorEmailExisted")
    data object BookmarkPage: Screen("bookmarkScreen")
    data object HomePage: Screen("homeScreen")
    data object ProfilePage: Screen("profileScreen")
    data object SearchPage: Screen("searchScreen")
    data object SearchChicken: Screen("searchChicken")
    data object SearchMeat: Screen("searchMeat")
    data object SearchFish: Screen("searchFish")
    data object SearchVegies: Screen("searchVegies")

    data object BottomNavigationBar: Screen("bottomNavigationBar")

    data object DetailPage: Screen("detailScreen")
}
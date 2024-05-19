package org.d3if3011.resepi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

//initializing the data class with default parameters
data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screen.Home.route
            ),
            BottomNavigationItem(
                label = "Bookmark",
                icon = Icons.Filled.Home,
                route = Screen.Bookmark.route
            ),
//            BottomNavigationItem(
//                label = "Profile",
//                icon = Icons.Filled.AccountCircle,
//                route = Screens.Profile.route
//            ),
        )
    }
}
package org.d3if3011.resepi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import org.d3if3011.resepi.R

//initializing the data class with default parameters
data class BottomNavigationItem(
    val label : String = "",
    val icon : Int = R.drawable.ic_home,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = R.drawable.ic_home,
                route = Screen.HomePage.route
            ),
            BottomNavigationItem(
                label = "Bookmark",
                icon = R.drawable.ic_bookmark,
                route = Screen.BookmarkPage.route
            ),
//            BottomNavigationItem(
//                label = "Profile",
//                icon = Icons.Filled.AccountCircle,
//                route = Screens.Profile.route
//            ),
        )
    }
}
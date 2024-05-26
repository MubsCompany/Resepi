package org.d3if3011.resepi.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3011.resepi.navigation.BottomNavigationItem
import org.d3if3011.resepi.navigation.Screen
import org.d3if3011.resepi.ui.theme.ResepiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavHostController) {
//initializing the default selected item
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    /**
     * by using the rememberNavController()
     * we can get the instance of the navController
     */
    Scaffold(
        bottomBar = {
            NavigationBar (
                containerColor = Color.White,
            ) {
                //getting the list of bottom navigation items for our data class
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->

                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = navigationItem.icon),
                                contentDescription = navigationItem.label,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                    )
                }
            }
        },
    ) { paddingValues ->
        //We need to setup our NavHost in here
//        NavHost(
//            navController = navController,
//            startDestination = Screen.HomePage.route,
//            modifier = Modifier.padding(paddingValues = paddingValues)) {
//            composable(Screen.HomePage.route) {
//                HomeScreen(
//                    navController
//                )
//            }
//            composable(Screen.BookmarkPage.route) {
//                BookmarkScreen(
//                    navController = navController
//                )
//            }
////            composable(Screens.Profile.route) {
////                //call our composable screens here
////            }
//        }
    }



//scaffold to hold our bottom navigation Bar
}

@Preview
@Composable
fun BottomNavigationPreview() {
    ResepiTheme {
        BottomNavigationBar(rememberNavController())
    }
}

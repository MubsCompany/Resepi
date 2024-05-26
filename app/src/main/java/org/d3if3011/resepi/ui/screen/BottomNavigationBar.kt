package org.d3if3011.resepi.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
fun BottomNavigationBar() {
//initializing the default selected item
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    /**
     * by using the rememberNavController()
     * we can get the instance of the navController
     */
    val navController = rememberNavController()



//scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
//        topBar = HomeTopBar(),
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
        }
    ) { paddingValues ->
        //We need to setup our NavHost in here
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(Screen.HomePage.route) {
                HomeScreen(
                    navController
                )
            }
            composable(Screen.BookmarkPage.route) {
                BookmarkScreen(
                    navController = navController
                )
            }
            composable(route = Screen.Login.route) {
                LoginScreen(navController)
            }
            composable(route = Screen.Registrasi.route) {
                RegistrasiScreen(navController)
            }
            composable(route = Screen.ProfilePage.route) {
                ProfileTopAppBar(navController)
            }
            composable(route = Screen.SearchPage.route) {
                SearchTopBar(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar (navController: NavController) {
    var searchText by remember {
        mutableStateOf("")
    }

    TopAppBar(
        title = { Text("") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            BorderStroke(
                                0.1.dp,
                                SolidColor(MaterialTheme.colorScheme.onSurface)
                            ),
                            RoundedCornerShape(12.dp)
                        )
                        .weight(1.0f),
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("cari resep kamu", color = Color.DarkGray) },
                    leadingIcon = {
                        IconButton(onClick = { navController.navigate(Screen.SearchPage.route) }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    },

                    )


                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { navController.navigate(Screen.ProfilePage.route) }) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "Profile")
                }
            }
        },
    )
}

@Preview
@Composable
fun BottomNavigationPreview() {
    ResepiTheme {
        BottomNavigationBar()
    }
}

package org.d3if3011.resepi.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3011.resepi.R
import org.d3if3011.resepi.navigation.Screen

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            HomeTopBar(navController)
        }
    ) {paddingValues ->
        HomeScreenContent(navController, modifier = Modifier.padding(paddingValues))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar (navController: NavHostController) {
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

@Composable
fun HomeScreenContent(navController: NavHostController, modifier: Modifier = Modifier) {

    Column (
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.home_banner_atas),
            contentDescription = "Banner atas"
        )


        CategoryButton(
            containerColor = Color(0xFFFFDBB9),
            categoryTitleRes = R.string.ayam,
            categoryTextRes = R.string.resep_berbahan_ayam,
            iconRes = painterResource(id = R.drawable.ic_ayam),
        )

        CategoryButton(
            containerColor = Color(0xFFFFC6C2),
            categoryTitleRes = R.string.daging,
            categoryTextRes = R.string.resep_berbahan_daging,
            iconRes = painterResource(id = R.drawable.ic_ayam),
        )
        
        CategoryButton(
            containerColor = Color(0xFFCFDCFF),
            categoryTitleRes = R.string.ikan,
            categoryTextRes = R.string.resep_berbahan_ikan,
            iconRes = painterResource(id = R.drawable.ic_ikan),
        )

        CategoryButton(
            containerColor = Color(0xFFE4F2BB),
            categoryTitleRes = R.string.sayuran,
            categoryTextRes = R.string.resep_berbahan_sayuran,
            iconRes = painterResource(id = R.drawable.ic_brokoli),
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            painter = painterResource(id = R.drawable.home_banner_tengah),
            contentDescription = "Banner tengah"
        )
        
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.rekomendasi_untuk_kamu),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )

            Text(
                text = stringResource(id = R.string.lihat_semua),
                color = Color(0xFFFF7A00)
            )
        }

        ResepListItem(
            resepTitle = "Ayam Goreng Krispi",
            resepDesc = "Ayam goreng dicampur dengan taburan krispi",
            resepTime = "60 menit",
            navController
        )

        ResepListItem(
            resepTitle = "Ayam Goreng Krispi",
            resepDesc = "Ayam goreng dicampur dengan taburan krispi",
            resepTime = "60 menit",
            navController
        )

        ResepListItem(
            resepTitle = "Ayam Goreng Krispi",
            resepDesc = "Ayam goreng dicampur dengan taburan krispi",
            resepTime = "60 menit",
            navController
        )

    }
}

@Composable
fun CustomBottomBar() {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
    }
}


@Composable
fun CategoryButton(containerColor: Color, categoryTitleRes: Int, categoryTextRes: Int, iconRes: Painter) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
        onClick = {  },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Image(
            painter = iconRes,
            contentDescription = null,
            modifier = Modifier.width(32.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
//        Column () {
//            Text(
//                text = stringResource(categoryTitleRes),
//                color = Color.DarkGray,
//                fontSize = 14.sp
//            )
            Text(
                text = stringResource(categoryTextRes),
                color = Color.DarkGray,
                fontSize = 12.sp
            )
//        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = stringResource(R.string.ke_resep_ayam),
            tint = Color.Black
        )
    }
}

@Composable
fun ResepListItem(resepTitle: String, resepDesc: String, resepTime: String, navController: NavHostController) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable { navController.navigate(Screen.DetailPage.route) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .weight(1.0f)
            ) {
                Text(
                    text = resepTitle,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )

                Text(
                    text = resepDesc,
                    color = Color.DarkGray
                )

                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = stringResource(R.string.waktu_memasak),
                    )
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = resepTime,
                        color = Color.DarkGray
                    )
                }
            }

            Image(
                modifier = Modifier
                    .weight(0.4f),
                painter = painterResource(id = R.drawable.ic_ayam),
                contentDescription = stringResource(R.string.gambar_makanan),
            )
        }
        Divider(
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}


//@Preview
@Composable
fun ResepListItemPreview() {
    ResepListItem(
        resepTitle = "Ayam Goreng Krispi",
        resepDesc = "Ayam goreng dicampur dengan taburan krispi",
        resepTime = "60 menit",
        navController = rememberNavController()
    )
}

@Preview
@Composable
fun HomeScreenPreview () {
    HomeScreen(rememberNavController())
}


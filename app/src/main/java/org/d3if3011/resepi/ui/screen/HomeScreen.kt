package org.d3if3011.resepi.ui.screen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3011.resepi.R
import org.d3if3011.resepi.controller.Profile
import org.d3if3011.resepi.controller.ambilDaftarResepDariFirestore
import org.d3if3011.resepi.controller.downloadImageFromFirebase
import org.d3if3011.resepi.controller.getImageBitmapFromFirebaseStorage
import org.d3if3011.resepi.controller.uploadImageToFirebaseStorage
import org.d3if3011.resepi.model.ResepMasakan
import org.d3if3011.resepi.model.UserLogin
import org.d3if3011.resepi.navigation.BottomNavigationItem
import org.d3if3011.resepi.navigation.Screen

@Composable
fun HomeScreen(navController: NavHostController) {
    var daftarResepMasakan by remember { mutableStateOf<List<ResepMasakan>>(emptyList()) }

    LaunchedEffect(Unit) {
        daftarResepMasakan = ambilDaftarResepDariFirestore()
    }
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    Scaffold (
        topBar = {
            HomeTopBar(navController)
        },
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
                            if (navigationSelectedItem == 0) navigationSelectedItem++
                            else navigationSelectedItem--

                        }

                    )
                }
            }
        }, modifier = Modifier.fillMaxSize()
    ) {paddingValues ->
        if (navigationSelectedItem == 0)
        HomeScreenContent(modifier = Modifier.padding(paddingValues), navController, daftarResepMasakan)
        else
            BookmarkScreen(modifier = Modifier.padding(paddingValues), navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar (navController: NavHostController) {
    var searchText by remember {
        mutableStateOf("")
    }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var bitmap = remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(Unit) {
        bitmap.value = getImageBitmapFromFirebaseStorage()
    }
    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28){
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }
    TopAppBar(
        title = { Text("") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1.0f),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            unfocusedLabelColor = Color.Gray,
                            unfocusedLeadingIconColor = Color.Gray
                        ),
                        value = searchText,
                        onValueChange = { searchText = it },

                        //put on a placeholder
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
                if(bitmap.value == null){
                    Image(
                        painter = painterResource(id = R.drawable.baseline_account_circle_24),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            //                        .padding(20.dp)
                            .clip(CircleShape)
                            .clickable { navController.navigate(Screen.ProfilePage.route) }
                    )
                } else {
                    bitmap.value?.let {
                            btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                //                        .padding(20.dp)
                                .clip(CircleShape)
                                .clickable { navController.navigate(Screen.ProfilePage.route) }
    //                        .border(3.dp, Color.Red, CircleShape)
                        )
                    }
                }
            }
        },
    )
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier, navController: NavHostController, resepMasakanList: List<ResepMasakan>) {
    Column(
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
            navController,
            2

        )

        CategoryButton(
            containerColor = Color(0xFFFFC6C2),
            categoryTitleRes = R.string.daging,
            categoryTextRes = R.string.resep_berbahan_daging,
            iconRes = painterResource(id = R.drawable.ic_daging),
            navController,
            3
        )

        CategoryButton(
            containerColor = Color(0xFFCFDCFF),
            categoryTitleRes = R.string.ikan,
            categoryTextRes = R.string.resep_berbahan_ikan,
            iconRes = painterResource(id = R.drawable.ic_ikan),
            navController,
            4
        )

        CategoryButton(
            containerColor = Color(0xFFE4F2BB),
            categoryTitleRes = R.string.sayuran,
            categoryTextRes = R.string.resep_berbahan_sayuran,
            iconRes = painterResource(id = R.drawable.ic_brokoli),
            navController,
            5
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            painter = painterResource(id = R.drawable.home_banner_tengah),
            contentDescription = "Banner tengah"
        )

        Row(
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
        var maxResep = 1
        resepMasakanList.forEach{
            if (resepMasakanList.size >= 3){
                if (maxResep <= 3){
                    ResepListItem(
                        idResep = it.uid,
                        resepTitle = it.nama_resep,
                        resepDesc = it.deskripsi_resep,
                        resepTime = it.waktu,
                        imageUrl = it.gambar,
                        navController
                    )
                    maxResep++
                }
            } else {
                ResepListItem(
                    idResep = it.uid,
                    resepTitle = it.nama_resep,
                    resepDesc = it.deskripsi_resep,
                    resepTime = it.waktu,
                    imageUrl = it.gambar,
                    navController
                )
            }
        }
    }

}
@Composable
fun CategoryButton(containerColor: Color, categoryTitleRes: Int, categoryTextRes: Int, iconRes: Painter, navController: NavHostController, tipe: Int) {
    //Tambahkan tipe int pada onclick agar title bisa diganti, masih Ayam semua :))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
        onClick = {
            if (tipe == 2) navController.navigate(Screen.SearchChicken.route)
                else if (tipe == 3) navController.navigate(Screen.SearchMeat.route)
                else if (tipe == 4) navController.navigate(Screen.SearchFish.route)
                else if (tipe == 5) navController.navigate(Screen.SearchVegies.route)
                  },
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
fun ResepListItem(idResep: String,resepTitle: String, resepDesc: String, resepTime: String, imageUrl: String, navController: NavHostController) {
    Column (
        modifier = Modifier.clickable {
            navController.navigate(Screen.DetailPage.route +"/"+ idResep)
        }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
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

            LoadImageFromBitmap(imagePath = imageUrl)
        }
        Divider(
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}
@Composable
fun LoadImageFromBitmap(imagePath: String){
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    // Memanggil downloadImageFromFirebase ketika komposisi pertama kali diload
    DisposableEffect(imagePath) {
        downloadImageFromFirebase(imagePath) { fetchedBitmap ->
            bitmap = fetchedBitmap
        }
        onDispose {  }
    }
    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = stringResource(id = R.string.gambar_makanan),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp),
        )
    } else {

    }
}

//@Preview
@Composable
fun ResepListItemPreview() {
    ResepListItem(
        idResep = "dsaasdsadda",
        resepTitle = "Ayam Goreng Krispi",
        resepDesc = "Ayam goreng dicampur dengan taburan krispi",
        resepTime = "60 menit",
        imageUrl = "",
        rememberNavController()
    )
}

@Preview
@Composable
fun HomeScreenPreview () {
    HomeScreen(rememberNavController())
}


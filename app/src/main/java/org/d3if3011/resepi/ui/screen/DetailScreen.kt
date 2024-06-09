package org.d3if3011.resepi.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3011.resepi.R
import org.d3if3011.resepi.controller.DetailResep
import org.d3if3011.resepi.controller.addBookmark
import org.d3if3011.resepi.controller.bookmarkChecker
import org.d3if3011.resepi.controller.deleteBookmark
import org.d3if3011.resepi.controller.downloadImageFromFirebase
import org.d3if3011.resepi.model.ResepMasakan
import org.d3if3011.resepi.ui.theme.ResepiTheme

const val KEY_ID_RESEP = "idResep"

@Composable
fun DetailScreen(navController: NavHostController, idResep: String) {
    var listDetailResep by remember {
        mutableStateOf<List<ResepMasakan>>(emptyList())
    }
    LaunchedEffect(Unit){
        listDetailResep = DetailResep(idResep)
    }
    Scaffold (
        topBar = { DetailTopBar(listDetailResep, navController) }
    ) {paddingValues ->
        DetailScreenContent(listDetailResep, modifier = Modifier.padding(paddingValues), idResep)
    }
}

@Composable
fun DetailScreenContent(listDetailResep: List<ResepMasakan>,modifier: Modifier = Modifier, idResep: String) {

    listDetailResep.forEach {
    Column (
        modifier = modifier
    ) {
        LoadImageFromBitmapDetailResep(imagePath = it.gambar)

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.waktu_memasak),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
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
                    text = it.waktu,
                    color = Color.DarkGray
                )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8E6D5))
                .padding(20.dp),
        ) {
            Text(
                text = stringResource(R.string.bahan_masakan),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            var noBahan:Int = 1
            it.bahan_resep.forEach {
            Text(
                text = "$noBahan. $it\n"
            )
                noBahan++
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.alat_masak),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            var noAlatMasak:Int = 1
            it.alat_resep.forEach {

                Text(
                text = "$noAlatMasak. $it\n"
            )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8E6D5))
                .padding(20.dp),
        ) {
            Text(
                text = stringResource(R.string.cara_memasak),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            var noCaraMasak: Int = 1
            it.cara_masak.forEach {
            Text(
                text = "$noCaraMasak. $it\n"
            )
            }
        }

    }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(listDetailResep: List<ResepMasakan>, navController: NavHostController) {
    var kondisiBookmark by remember {
        mutableStateOf(false)
    }
    var iconBookmark by remember {
        mutableIntStateOf(0)
    }
    listDetailResep.forEach {
        LaunchedEffect(Unit){
            kondisiBookmark = bookmarkChecker(
                ResepMasakan(
                    alat_resep = it.alat_resep,
                    bahan_resep = it.bahan_resep,
                    cara_masak = it.cara_masak,
                    deskripsi_resep = it.deskripsi_resep,
                    gambar = it.gambar,
                    nama_resep = it.nama_resep,
                    kategori = it.kategori,
                    uid = it.uid,
                    waktu = it.waktu,
                )
            )
        }
    }
    iconBookmark =
        if (kondisiBookmark){
            R.drawable.ic_detail_bookmark_terisi
        } else {
            R.drawable.ic_bookmark
        }
    var namaResep = ""
    var resepMasakan = ResepMasakan()
    listDetailResep.forEach {
        namaResep = it.nama_resep
        resepMasakan = ResepMasakan(
            alat_resep = it.alat_resep,
            bahan_resep = it.bahan_resep,
            cara_masak = it.cara_masak,
            deskripsi_resep = it.deskripsi_resep,
            gambar = it.gambar,
            nama_resep = it.nama_resep,
            kategori = it.kategori,
            uid = it.uid,
            waktu = it.waktu,
        )
    }

    TopAppBar(
        title = { 
                Text(text = namaResep)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.kembali),
                    tint = Color.Black
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    if (kondisiBookmark){
                        deleteBookmark(resepMasakan)
                        kondisiBookmark = false
                    } else {
                        addBookmark(resepMasakan)
                        kondisiBookmark = true
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = iconBookmark),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    )
}
@Composable
fun LoadImageFromBitmapDetailResep(imagePath: String){
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
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop,
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = stringResource(id = R.string.gambar_makanan))
    } else {

    }
}
@Preview
@Composable
fun DetailScreenPreview() {
    ResepiTheme {
        DetailScreen(rememberNavController(), "")
    }
}
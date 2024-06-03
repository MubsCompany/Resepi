package org.d3if3011.resepi.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3011.resepi.R
import org.d3if3011.resepi.controller.SearchResep
import org.d3if3011.resepi.controller.ambilResepSearch
import org.d3if3011.resepi.model.ResepMasakan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(navController: NavHostController, tipe: Int) {
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = Color.Black
                        )
                    }
                },
                title = { Text(text = stringResource(
                    id =
                    if (tipe == 1) R.string.search_text
                    else if (tipe == 2) R.string.search_ayam
                    else if (tipe == 3) R.string.search_daging
                    else if (tipe == 4) R.string.search_ikan
                    else R.string.search_sayur
                ),
                    fontWeight = FontWeight.Medium
                ) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        SearchContent(modifier = Modifier.padding(paddingValues), tipe)
    }
}

@Composable
fun SearchContent(modifier: Modifier, tipe: Int){
    var listResep by remember { mutableStateOf<List<ResepMasakan>>(emptyList()) }
    LaunchedEffect(Unit){
        ambilResepSearch(tipe)
    }
    val roundSize = 30
    var searchText by remember {
        mutableStateOf("")
    }
Column (
    modifier = modifier,
    verticalArrangement = Arrangement.Center
){
    OutlinedTextField(
        modifier = Modifier
            .height(70.dp)
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(roundSize.dp))
            .border(
                BorderStroke(
                    1.dp,
                    SolidColor(MaterialTheme.colorScheme.onSurface)
                ),
                RoundedCornerShape(roundSize.dp)
            ),
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("cari resep kamu", color = Color.DarkGray) },
        leadingIcon = {
            IconButton(onClick = {
                listResep = SearchResep(searchText, tipe)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.size(22.dp)
                )
            }
        },
        )
    if (listResep.isNotEmpty()){
    listResep.forEach {
    Column (
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = it.nama_resep,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = it.deskripsi_resep,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ){
                    Image(painter = painterResource(id = R.drawable.clock), contentDescription = null)
                    Text(
                        text = it.waktu,
                        maxLines = 1,
                        )
                }

            }
            Image(
                painter = painterResource(id = R.drawable.example_chicken),
                contentDescription = stringResource(id = R.string.food),
                modifier = Modifier
                    .size(124.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
            )
        }
        Divider(modifier = Modifier.padding(top = 24.dp))
    }
    }
    } else {
        Column (
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = stringResource(id = R.string.resep_kosong))
            }
            Divider(modifier = Modifier.padding(top = 24.dp))
        }
    }
}

@Preview
@Composable
fun SearchPreview () {
    SearchTopBar(rememberNavController(), 3)
}
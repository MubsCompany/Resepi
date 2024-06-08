package org.d3if3011.resepi.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.d3if3011.resepi.R
import org.d3if3011.resepi.controller.ambilDaftarResepBookmark
import org.d3if3011.resepi.model.UserLogin
import org.d3if3011.resepi.navigation.Screen
import org.d3if3011.resepi.ui.theme.ResepiTheme

@Composable
fun BookmarkScreen(modifier: Modifier, navController: NavHostController) {
    var user by remember { mutableStateOf<List<UserLogin>>(emptyList()) }

    LaunchedEffect(Unit) {
        user = ambilDaftarResepBookmark()
    }
    ResepiTheme {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            if (user.isNotEmpty()){
                user.forEach{
                    it.bookmarkResep.forEach {
                        Column (
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.DetailPage.route +"/"+ it.uid)
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
                                        text = it.nama_resep,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp
                                    )

                                    Text(
                                        text = it.deskripsi_resep,
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
                                            text = it.waktu,
                                            color = Color.DarkGray
                                        )
                                    }
                                }

                                LoadImageFromBitmap(it.gambar)
                            }
                            Divider(
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                        }
                    }
                }

            } else {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        stringResource(id = R.string.bookmark_kosong),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            }
        }
    }
}
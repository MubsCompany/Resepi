package org.d3if3011.resepi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.d3if3011.resepi.R
import org.d3if3011.resepi.controller.ambilDaftarResepBookmark
import org.d3if3011.resepi.model.ResepMasakan
import org.d3if3011.resepi.model.UserLogin
import org.d3if3011.resepi.ui.theme.ResepiTheme

@Composable
fun BookmarkScreen(modifier: Modifier) {
    var listBookmark by remember { mutableStateOf<List<UserLogin>>(emptyList()) }
    LaunchedEffect(Unit){
        listBookmark = ambilDaftarResepBookmark()
    }
    ResepiTheme {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            if (listBookmark.isNotEmpty()){
            listBookmark.forEach{
                it.bookmarkResepMasakan.forEach{
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    )  {
                        Text(
                            "ADA ISI",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                    }
                }
            }
            } else {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(text = "TIDAK ADA ISI")
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
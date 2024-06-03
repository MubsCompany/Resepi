package org.d3if3011.resepi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import org.d3if3011.resepi.controller.Profile
import org.d3if3011.resepi.controller.signOut
import org.d3if3011.resepi.model.UserLogin
import org.d3if3011.resepi.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(navController: NavHostController) {
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
                title = { Text(text = stringResource(id = R.string.profile_text),
                    fontWeight = FontWeight.Medium
                    ) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) {paddingValues ->
       ProfileContent(modifier = Modifier.padding(paddingValues), navController = navController)
    }
}

@Composable
fun ProfileContent(modifier: Modifier, navController: NavHostController){
    var listUser by remember { mutableStateOf<List<UserLogin>>(emptyList()) }
    LaunchedEffect(Unit) {
        listUser = Profile()
    }
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp),
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = stringResource(id = R.string.profile_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(123.dp)
                    .clip(CircleShape)
            )
            listUser.forEach{
                Column (
                    modifier = Modifier.padding(start = 12.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    Text(text = it.nama_lengkap, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    Text(text = it.email)
                }
            }
        }
        Row (
            modifier = Modifier.padding(top = 24.dp)
        ){
            Button(
                onClick = { signOut(navController) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_outline_24),
                    contentDescription = stringResource(id = R.string.profile),
                    modifier = Modifier.size(34.dp)
                )
                Text(text = stringResource(id = R.string.logout),
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 12.dp)
                    )
            }
        }
        Divider()
    }


}
@Preview
@Composable
fun ProfilePreview () {
    ProfileTopAppBar(rememberNavController())
}
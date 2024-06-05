package org.d3if3011.resepi.ui.screen

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.d3if3011.resepi.R
import org.d3if3011.resepi.controller.Profile
import org.d3if3011.resepi.controller.getImageBitmapFromFirebaseStorage
import org.d3if3011.resepi.controller.id_user
import org.d3if3011.resepi.controller.signOut
import org.d3if3011.resepi.controller.uploadImageToFirebaseStorage
import org.d3if3011.resepi.model.UserLogin
import org.d3if3011.resepi.navigation.Screen
import java.io.ByteArrayOutputStream
import java.util.UUID
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.HomePage.route) }) {
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
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
            uri: Uri? ->
        imageUri = uri
        // Setelah gambar dipilih, panggil fungsi untuk mengirimkan ke Firebase Storage
        uploadImageToFirebaseStorage(uri)
    }
    var listUser: List<UserLogin> by remember { mutableStateOf<List<UserLogin>>(emptyList()) }
    LaunchedEffect(Unit) {
        listUser = Profile()
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
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp),
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
                    //AMBIL GAMBAR DARI GALERI
                    launcher.launch("image/*")
                },
                modifier = Modifier.size(123.dp)
            ) {
                listUser.forEach {
                    if(it.imageUrl.equals("")){
                        Image(
                            imageVector = Icons.Default.Face,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(123.dp)
                                .clip(CircleShape))
                    } else {
                        //Tampilkan gambar dari firebase storage
                        bitmap.value?.let {
                                btm ->
                            Image(
                                bitmap = btm.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(123.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
                }
            }
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
                    containerColor = Color.Transparent
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
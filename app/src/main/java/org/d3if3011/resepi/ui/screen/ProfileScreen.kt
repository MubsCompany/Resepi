package org.d3if3011.resepi.ui.screen

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
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
import org.d3if3011.resepi.controller.id_user
import org.d3if3011.resepi.controller.signOut
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
        },
    ) {paddingValues ->
       ProfileContent(modifier = Modifier.padding(paddingValues), navController = navController)
    }
}

@Composable
fun ProfileContent(modifier: Modifier, navController: NavHostController){
    var listUser: List<UserLogin> by remember { mutableStateOf<List<UserLogin>>(emptyList()) }
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp),
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
//            IconButton(
//                onClick = {
//
//            },
//                modifier = Modifier
//                    .scale(3f)
//                    .clip(CircleShape)) {
//                    Image(imageVector = Icons.Filled.Face, contentDescription = null)
//                listUser.forEach {
//                if (it.imageUrl.isNotEmpty()){
//
//                } else {
//
//                }
//                }
//            }
            Text(text = "TESTING")
            listUser.forEach{
                Column (

                ){
                    Text(text = "it.nama_lengkap", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
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
private fun uploadImageToFirebase(bitmap: Bitmap) {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos) // Convert bitmap to ByteArray
    val imageData = baos.toByteArray()

    // Get a reference to the 'profile' folder in Firebase Storage
    val storageRef = Firebase.storage.reference.child("profile")
    val imageRef = storageRef.child("${UUID.randomUUID()}.jpg")

    imageRef.putBytes(imageData)
        .addOnSuccessListener { taskSnapshot ->
            // Image successfully uploaded
            Log.d("UPLOAD", "Image uploaded successfully: ${taskSnapshot.metadata?.path}")

            // Get the download URL of the uploaded image
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                // Insert the image URL into Firestore
                insertImageUrlToFirestore(uri.toString())
            }.addOnFailureListener { exception ->
                // Handle failure to get download URL
                Log.e("UPLOAD", "Failed to get download URL: $exception")
            }
        }
        .addOnFailureListener { exception ->
            // Failed to upload image
            Log.e("UPLOAD", "Image upload failed: $exception")
            // Handle the failed upload situation
        }
}

private fun insertImageUrlToFirestore(imageUrl: String) {
    // Access the Firestore instance
    val db = Firebase.firestore

    // Create a data object containing the image URL
    val data = hashMapOf(
        "gambarUrl" to imageUrl
        // Add other fields as needed
    )

    // Query to find the document with matching uid
    db.collection("users")
        .whereEqualTo("uid", id_user.id_user)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                // Update the document with the new data
                db.collection("users")
                    .document(document.id)
                    .update(data.toMap())
                    .addOnSuccessListener {
                        Log.d("Firestore", "DocumentSnapshot successfully updated!")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error updating document", e)
                    }
            }
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Error getting documents", e)
        }
}
@Preview
@Composable
fun ProfilePreview () {
    ProfileTopAppBar(rememberNavController())
}
package org.d3if3011.resepi.controller

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import org.d3if3011.resepi.model.UserLogin
import org.d3if3011.resepi.navigation.Screen

object id_user {
    lateinit var id_user: String
}
public fun signUp(email: String, namaLengkap: String, password: String, navController: NavHostController)  {
//    navController.navigate(Screen.Login.route)
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // User creation successful
                navController.navigate(Screen.Login.route)

                // Get the current user
                val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

                // If user is not null, save additional data to Firestore
                user?.let {
                    saveUserData(it.uid, email, namaLengkap, password)
                }
            } else {
                // User creation failed, handle the error
                val exception = task.exception
                Log.e(TAG, "Error creating user", exception)
                navController.navigate(Screen.RegistrasiError.route)
                // Handle the error, display a message to the user, etc.
            }
        }
}
private fun saveUserData(userId: String, email: String, namaLengkap: String, password: String) {
    val db = FirebaseFirestore.getInstance()
    val user = hashMapOf(
        "email" to email,
        "nama_lengkap" to namaLengkap,
        "password" to password,
        "uid" to userId,
        "imageUrl" to "",
        "bookmarkResep" to listOf<Any?>(null)
    )
    // Add a new document with a generated ID
    db.collection("users")
        .document(userId)
        .set(user)
        .addOnSuccessListener { documentReference ->
            // Data added successfully
        }
        .addOnFailureListener { e ->
            // Handle failure
        }
}
public fun signIn(email: String, password: String, navController: NavHostController) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //get UID and put it on variable session manual
                val user = FirebaseAuth.getInstance().currentUser
                user?.let {
                    id_user.id_user = it.uid
                }
                // User signed in successfully
                navController.navigate(Screen.HomePage.route)
            } else {
                // Handle sign in failure
                navController.navigate(Screen.Login.route)
            }
        }
}

public fun signOut(navController: NavHostController) {
    FirebaseAuth.getInstance().signOut()
    navController.navigate(Screen.Login.route)
    // Optionally, perform any other clean-up or navigation tasks after sign out
}

public suspend fun Profile(): List<UserLogin>{
    val firestore = FirebaseFirestore.getInstance()
    val userCollection = firestore.collection("users")
    return try{
        val querySnapshot = userCollection.whereEqualTo("uid", id_user.id_user).get().await()
        val userList = mutableListOf<UserLogin>()

        for (document in querySnapshot.documents){
            val user = document.toObject(UserLogin::class.java)
            user?.let {
                userList.add(it)
            }
        }
        userList
    } catch (e: Exception){
        emptyList()
    }
}
public fun uploadImageToFirebaseStorage(uri: Uri?) {
    uri?.let { imageUri ->
        val storageRef = Firebase.storage.reference
        val imagesRef = storageRef.child("profile/${imageUri.lastPathSegment}") // Ubah sesuai kebutuhan Anda
        val uploadTask = imagesRef.putFile(imageUri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Berhasil mengunggah gambar ke Firebase Storage
            // Handle keberhasilan di sini, seperti mendapatkan URL gambar
            Log.e("FirebaseStorage", "Berhasil")
            val db = FirebaseFirestore.getInstance()
            val collection = db.collection("users")
            val field = collection.document(id_user.id_user)
            field.update("imageUrl", "profile/${imageUri.lastPathSegment}").addOnCanceledListener {  }

        }.addOnFailureListener { exception ->
            // Gagal mengunggah gambar ke Firebase Storage
            // Handle error di sini
            Log.e("FirebaseStorage", "Gagal mengunggah gambar: ${exception.message}", exception)
        }
    }
}
// Fungsi untuk mendapatkan gambar dari Firebase Storage
public suspend fun getImageBitmapFromFirebaseStorage(): Bitmap? {
    val db = FirebaseFirestore.getInstance()
    val collection = db.collection("users").whereEqualTo("uid", id_user.id_user).get().await()
    var userList = mutableListOf<UserLogin>()
    for (document in collection.documents){
        val user = document.toObject(UserLogin::class.java)
        user?.let {
            userList.add(it)
        }
    }
    var imageUrl: String = ""
    userList.forEach {
        imageUrl = it.imageUrl
    }
    // Get a reference to the Firebase Storage instance
    val storage = Firebase.storage

    // Create a reference with an initial file path and name
    val imageRef = storage.reference.child(imageUrl)

    return try {
        // Download image byte data from Firebase Storage
        val byteArray = imageRef.getBytes(Long.MAX_VALUE).await()

        // Convert byte data to Bitmap
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
fun GetUserId(): String{
    return id_user.id_user
}
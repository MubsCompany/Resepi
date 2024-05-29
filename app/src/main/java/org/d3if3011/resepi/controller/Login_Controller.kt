package org.d3if3011.resepi.controller

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import org.d3if3011.resepi.model.resep
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

fun signOut() {
    FirebaseAuth.getInstance().signOut()
    // Optionally, perform any other clean-up or navigation tasks after sign out
}
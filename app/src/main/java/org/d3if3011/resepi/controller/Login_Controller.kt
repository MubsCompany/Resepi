package org.d3if3011.resepi.controller

import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.d3if3011.resepi.navigation.Screen


fun signUp(email: String, password: String, confirmPassword: String, navController: NavHostController)  {
    if (password == confirmPassword) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate(Screen.Login.route)
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        //handle username double with error on login screen
                    } else {
                        // Handle other sign up failures
                    }
                }
            }
    } else {
        // Passwords do not match, handle accordingly
    }
}

fun signIn(email: String, password: String, navController: NavHostController) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
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
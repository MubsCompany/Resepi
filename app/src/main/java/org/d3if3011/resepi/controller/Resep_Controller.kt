package org.d3if3011.resepi.controller

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import org.d3if3011.resepi.model.ResepMasakan
import org.d3if3011.resepi.model.UserLogin

suspend fun ambilDaftarResepDariFirestore(): List<ResepMasakan> {
    val firestore = FirebaseFirestore.getInstance()
    val resepCollection = firestore.collection("resep")

    return try {
        val querySnapshot = resepCollection.get().await() // Menunggu hasil dari Firestore
        val resepMasakanList = mutableListOf<ResepMasakan>()

        for (document in querySnapshot.documents) {
            val resepMasakan = document.toObject(ResepMasakan::class.java)
            resepMasakan?.let {
                resepMasakanList.add(it)
            }
        }
        resepMasakanList
    } catch (e: Exception) {
        // Tangani kesalahan jika ada
        Log.e("ERRORDATA", "${e.message}")
        emptyList() // Kembalikan daftar kosong jika terjadi kesalahan
    }
}
suspend fun ambilDaftarResepBookmark():List<UserLogin>{
    var id_user = GetUserId()
    val firestore = FirebaseFirestore.getInstance()
    val userCollection = firestore.collection("users")
    return try{
        val querySnapshot = userCollection.whereEqualTo("uid", id_user).get().await()
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

suspend fun ambilResepSearch(Tipe: Int):List<ResepMasakan>{
    val firestore = FirebaseFirestore.getInstance()
    val resepCollection = firestore.collection("resep")
    var querySnapshot = resepCollection.get().await()
    return try {
        if (Tipe == 1) querySnapshot = resepCollection.get().await() // Menunggu hasil dari Firestore
            else if(Tipe == 2) querySnapshot = resepCollection.whereEqualTo("kategori", "ayam").get().await()
            else if(Tipe == 3) querySnapshot = resepCollection.whereEqualTo("kategori", "daging").get().await()
            else if(Tipe == 4) querySnapshot = resepCollection.whereEqualTo("kategori", "ikan").get().await()
            else if(Tipe == 5) querySnapshot = resepCollection.whereEqualTo("kategori", "sayur").get().await()
        val resepMasakanList = mutableListOf<ResepMasakan>()

        for (document in querySnapshot.documents) {
            val resepMasakan = document.toObject(ResepMasakan::class.java)
            resepMasakan?.let {
                resepMasakanList.add(it)
            }
        }
        resepMasakanList
    } catch (e: Exception) {
        // Tangani kesalahan jika ada
        Log.e("ERRORDATA", "${e.message}")
        emptyList() // Kembalikan daftar kosong jika terjadi kesalahan
    }
}
fun SearchResep(search: String, Tipe: Int):List<ResepMasakan>{
    val firestore = FirebaseFirestore.getInstance()
    val resepCollection = firestore.collection("resep")
    var querySnapshot = resepCollection.get()
    return try {
        if (Tipe == 1) querySnapshot = resepCollection.whereEqualTo("nama_resep", search).get() // Menunggu hasil dari Firestore
        else if(Tipe == 2) querySnapshot = resepCollection.whereEqualTo("kategori", "ayam").whereEqualTo("nama_resep", search).get()
        else if(Tipe == 3) querySnapshot = resepCollection.whereEqualTo("kategori", "daging").whereEqualTo("nama_resep", search).get()
        else if(Tipe == 4) querySnapshot = resepCollection.whereEqualTo("kategori", "ikan").whereEqualTo("nama_resep", search).get()
        else if(Tipe == 5) querySnapshot = resepCollection.whereEqualTo("kategori", "sayur").whereEqualTo("nama_resep", search).get()
        val resepMasakanList = mutableListOf<ResepMasakan>()

        for (document in querySnapshot.result) {
            val resepMasakan = document.toObject(ResepMasakan::class.java)
            resepMasakan?.let {
                resepMasakanList.add(it)
            }
        }
        resepMasakanList
    } catch (e: Exception) {
        // Tangani kesalahan jika ada
        Log.e("ERRORDATA", "${e.message}")
        emptyList() // Kembalikan daftar kosong jika terjadi kesalahan
    }
}

suspend fun DetailResep(uid: String): List<ResepMasakan> {
    val firestore = FirebaseFirestore.getInstance()
    val resepCollection = firestore.collection("resep")
    return try {
        val querySnapshot = resepCollection.get().await() // Menunggu hasil dari Firestore
        var detailResep = mutableListOf<ResepMasakan>()
        for (document in querySnapshot.documents){
            val resep = document.toObject(ResepMasakan::class.java)
            resep?.let {
                detailResep.add(it)
            }
        }
        detailResep
    } catch (e: Exception) {
        // Tangani kesalahan jika ada
        Log.e("ERRORDATA", "${e.message}")
        emptyList<ResepMasakan>()
    }
}

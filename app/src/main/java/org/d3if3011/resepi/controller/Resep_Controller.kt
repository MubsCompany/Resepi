package org.d3if3011.resepi.controller

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
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

//fun ambilResepBookmark(uid:String):List<ResepMasakan>{
//
//}

suspend fun ambilResepSearch(Tipe: Int):List<ResepMasakan>{
    val firestore = FirebaseFirestore.getInstance()
    val resepCollection = firestore.collection("resep")
    var querySnapshot = resepCollection.get().await()
    return try {
        if (Tipe == 1) querySnapshot = resepCollection.get().await() // Menunggu hasil dari Firestore
            else if(Tipe == 2) querySnapshot = resepCollection.whereEqualTo("kategori", "Ayam").get().await()
            else if(Tipe == 3) querySnapshot = resepCollection.whereEqualTo("kategori", "Daging").get().await()
            else if(Tipe == 4) querySnapshot = resepCollection.whereEqualTo("kategori", "Ikan").get().await()
            else if(Tipe == 5) querySnapshot = resepCollection.whereEqualTo("kategori", "Sayuran").get().await()
        val resepMasakanList = mutableListOf<ResepMasakan>()

        for (document in querySnapshot.documents) {
            val resepMasakan = document.toObject(ResepMasakan::class.java)
            resepMasakan?.let {
                resepMasakanList.add(it)
            }
        }
        Log.d("FirebaseFirestore", "Berhasil Ambil Data")
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
        else if(Tipe == 2) querySnapshot = resepCollection.whereEqualTo("kategori", "Ayam").whereEqualTo("nama_resep", search).get()
        else if(Tipe == 3) querySnapshot = resepCollection.whereEqualTo("kategori", "Daging").whereEqualTo("nama_resep", search).get()
        else if(Tipe == 4) querySnapshot = resepCollection.whereEqualTo("kategori", "Ikan").whereEqualTo("nama_resep", search).get()
        else if(Tipe == 5) querySnapshot = resepCollection.whereEqualTo("kategori", "Sayuran").whereEqualTo("nama_resep", search).get()
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
        val querySnapshot = resepCollection.whereEqualTo("uid", uid).get().await() // Menunggu hasil dari Firestore
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

fun downloadImageFromFirebase(imagePath: String, onComplete: (Bitmap?) -> Unit) {
    val storageReference = FirebaseStorage.getInstance().reference.child(imagePath)

    storageReference.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        onComplete(bitmap)
    }.addOnFailureListener {
        onComplete(null)
    }
}

suspend fun bookmarkChecker(resepMasakan: ResepMasakan): Boolean {
    val firestore = FirebaseFirestore.getInstance()
    val id_user = GetUserId()

    // Mendapatkan data pengguna
    val userDocument = firestore.collection("users").document(id_user).get().await()

    if (userDocument.exists()) {
        val bookmarkResep = userDocument.toObject(UserLogin::class.java)?.bookmarkResep
        // Memeriksa apakah resepMasakan sudah ada di dalam daftar bookmarkResep
        return bookmarkResep?.contains(resepMasakan) ?: false
    } else {
        // Handle jika dokumen pengguna tidak ada
        return false
    }
}


fun deleteBookmark(resepMasakan: ResepMasakan) {
    val firestore = FirebaseFirestore.getInstance()
    val id_user = GetUserId()
    firestore.collection("users").document(id_user)
        .update("bookmarkResep", FieldValue.arrayRemove(resepMasakan))
        .addOnSuccessListener {
            // Handle jika berhasil
        }
        .addOnFailureListener { exception ->
            // Handle jika terjadi kesalahan
        }
}


fun addBookmark(resepMasakan: ResepMasakan) {
    val firestore = FirebaseFirestore.getInstance()
    val id_user = GetUserId()
    firestore.collection("users").document(id_user)
        .update("bookmarkResep", FieldValue.arrayUnion(resepMasakan))
        .addOnSuccessListener {
            // Handle jika berhasil
        }
        .addOnFailureListener { exception ->
            // Handle jika terjadi kesalahan
        }
}



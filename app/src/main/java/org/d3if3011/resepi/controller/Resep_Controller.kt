package org.d3if3011.resepi.controller

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.d3if3011.resepi.model.resep

suspend fun ambilDaftarResepDariFirestore(): List<resep> {
    val firestore = FirebaseFirestore.getInstance()
    val resepCollection = firestore.collection("resep")

    return try {
        val querySnapshot = resepCollection.get().await() // Menunggu hasil dari Firestore
        val resepList = mutableListOf<resep>()

        for (document in querySnapshot.documents) {
            val resep = document.toObject(resep::class.java)
            resep?.let {
                resepList.add(it)
            }
        }
        resepList
    } catch (e: Exception) {
        // Tangani kesalahan jika ada
        Log.e("ERRORDATA", "${e.message}")
        emptyList() // Kembalikan daftar kosong jika terjadi kesalahan
    }
}

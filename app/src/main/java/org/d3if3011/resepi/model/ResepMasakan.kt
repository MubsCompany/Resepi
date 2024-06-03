package org.d3if3011.resepi.model

data class ResepMasakan (
    var alat_resep: List<String>,
    var bahan_resep: List<String>,
    var caraMasak_resep:List<String>,
    var deskripsi_resep: String,
    var gambar: String,
    var nama_resep: String,
    val uid: String,
    var waktu: String
){
    // Konstruktor tanpa argumen
    constructor() : this(
        listOf(),
        listOf(),
        listOf(),
        "",
        "",
        "",
        "",
        ""
    )
}
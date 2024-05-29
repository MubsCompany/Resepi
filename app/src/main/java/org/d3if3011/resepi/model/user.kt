package org.d3if3011.resepi.model

class user (
    val uid: String,
    var password: String,
    var nama_lengkap: String,
    var email: String,
    var bookmarkResep: List<resep>
)
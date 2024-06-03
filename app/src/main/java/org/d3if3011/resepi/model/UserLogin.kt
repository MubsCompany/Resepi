package org.d3if3011.resepi.model

class UserLogin(
    var uid: String = "",
    var password: String = "",
    var nama_lengkap: String = "",
    var email: String = "",
    var bookmarkResepMasakan: List<ResepMasakan> = listOf()
)
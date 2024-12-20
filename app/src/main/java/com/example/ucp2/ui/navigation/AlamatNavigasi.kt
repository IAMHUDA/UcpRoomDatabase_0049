package com.example.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiInsert : AlamatNavigasi { // Object untuk halaman insert dokter
    override val route: String = "insertdokter"
}

object DestinasiHome : AlamatNavigasi { // Object untuk halaman home
    override val route: String = "home"
}

object DestinasiDetail : AlamatNavigasi { // Object untuk halaman detail dokter
    override val route: String = "detail"
    const val ID_DOKTER = "idDokter"
    val routesWithArg = "$route/{$ID_DOKTER}"
}

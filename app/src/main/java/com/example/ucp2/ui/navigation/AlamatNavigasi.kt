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

object DestinasiInsertJadwal : AlamatNavigasi {
    override val route: String = "insertjadwal"
}

object  DestinasiHomeJW : AlamatNavigasi {
    override val route: String = "home_jw"
}

object DestinasiDetailJW : AlamatNavigasi {
    override val route = "detail_jw"
    const val idJadwal = "idJadwal"
    val routesWithArg = "$route/{$idJadwal}"
}

object DestinasiUpdateJadwal : AlamatNavigasi {
    override val route = " update_jw"
    const val  idJadwal = " idJadwal"
    val routesWithArg = "$route/{$idJadwal}"
}

object DestinasiDetail : AlamatNavigasi {
    override val route: String = "detail"
    const val ID_DOKTER = "idDokter"
    val routesWithArg = "$route/{$ID_DOKTER}"
}

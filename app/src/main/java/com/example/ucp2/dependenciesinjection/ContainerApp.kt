package com.example.ucp2.dependenciesinjection



import com.example.ucp2.repository.RepositoryJadwal
import com.example.ucp2.repository.RepositoryDokter
import android.content.Context
import com.example.ucp2.data.database.RsDatabase
import com.example.ucp2.repository.LocalRepositoryDokter
import com.example.ucp2.repository.LocalRepositoryJadwal


interface InterfaceContainerApp {
    val dokterRepository: RepositoryDokter
    val jadwalRepository: RepositoryJadwal
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val dokterRepository: RepositoryDokter by lazy {
        LocalRepositoryDokter(RsDatabase.getDatabase(context).dokterDao())
    }

    override val jadwalRepository: RepositoryJadwal by lazy {
        LocalRepositoryJadwal(RsDatabase.getDatabase(context).jadwalDao())
    }
}
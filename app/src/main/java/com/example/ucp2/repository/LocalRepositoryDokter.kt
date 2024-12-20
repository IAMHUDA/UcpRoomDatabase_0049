package com.example.ucp2.repository


import com.example.ucp2.data.dao.DokterDao
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepositoryDokter(private val dokterDao: DokterDao) : RepositoryDokter {

    // Mengganti implementasi addDokter dari interface
    override suspend fun addDokter(dokter: Dokter) {
        withContext(Dispatchers.IO) {
            dokterDao.insertDokter(dokter)
        }
    }

    // Mengganti implementasi getAllDokter dari interface
    override suspend fun getAllDokter(): List<Dokter> {
        return withContext(Dispatchers.IO) {
            dokterDao.getAllDokter()
        }
    }
}
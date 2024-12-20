package com.example.ucp2.repository

import com.example.ucp2.data.dao.JadwalDao
import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepositoryJadwal(private val jadwalDao: JadwalDao) : RepositoryJadwal {

    // Menambahkan jadwal
    override suspend fun addJadwal(jadwal: Jadwal) {
        withContext(Dispatchers.IO) {
            jadwalDao.insertJadwal(jadwal)
        }
    }

    // Mendapatkan semua jadwal
    override suspend fun getAllJadwal(): List<Jadwal> {
        return withContext(Dispatchers.IO) {
            jadwalDao.getAllJadwal()
        }
    }

    // Memperbarui jadwal
    override suspend fun updateJadwal(jadwal: Jadwal) {
        withContext(Dispatchers.IO) {
            jadwalDao.updateJadwal(jadwal)
        }
    }

    // Menghapus jadwal
    override suspend fun deleteJadwal(jadwal: Jadwal) {
        withContext(Dispatchers.IO) {
            jadwalDao.deleteJadwal(jadwal)
        }
    }

    // Mendapatkan detail jadwal berdasarkan ID
    override suspend fun getJadwalDetail(id: Int): Jadwal {
        return withContext(Dispatchers.IO) {
            jadwalDao.getJadwalDetail(id)
        }
    }
}
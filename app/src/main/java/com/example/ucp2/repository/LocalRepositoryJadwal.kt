package com.example.ucp2.repository

import androidx.lifecycle.LiveData
import com.example.ucp2.data.dao.JadwalDao
import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalRepositoryJadwal(private val jadwalDao: JadwalDao) : RepositoryJadwal {

    // Menambahkan jadwal
    override suspend fun addJadwal(jadwal: Jadwal) {
        jadwalDao.addJadwal(jadwal)
    }

    override fun getAllJadwal(): Flow<List<Jadwal>> {
        return jadwalDao.getAllJadwal()
    }

    override fun getJadwal(idJadwal: String): Flow<Jadwal>{
        return jadwalDao.getJadwal(idJadwal)
    }

    override fun getJadwalJoin(idJadwal: String): Flow<List<Jadwal>>{
        return jadwalDao.getJadwalJoin(idJadwal)
    }

    // Memperbarui jadwal
    override suspend fun updateJadwal(jadwal: Jadwal) {
        jadwalDao.updateJadwal(jadwal)
    }

    // Menghapus jadwal
    override suspend fun deleteJadwal(jadwal: Jadwal) {
        jadwalDao.deleteJadwal(jadwal)
    }

}
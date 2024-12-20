package com.example.ucp2.repository

import com.example.ucp2.data.entity.Jadwal

interface RepositoryJadwal {
    suspend fun addJadwal(jadwal: Jadwal)
    suspend fun getAllJadwal(): List<Jadwal>
    suspend fun updateJadwal(jadwal: Jadwal)
    suspend fun deleteJadwal(jadwal: Jadwal)
    suspend fun getJadwalDetail(id: Int): Jadwal
}
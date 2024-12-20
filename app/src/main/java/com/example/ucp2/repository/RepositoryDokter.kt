package com.example.ucp2.repository

import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoryDokter {
    suspend fun addDokter(dokter: Dokter)
    fun getAllDokter(): Flow<List<Dokter>>
    fun getDokter(idDokter:String): Flow<Dokter>
}
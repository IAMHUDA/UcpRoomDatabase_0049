package com.example.ucp2.repository

interface RepositoryDokter {
    suspend fun addDokter(dokter: Dokter)
    suspend fun getAllDokter(): List<Dokter>
}
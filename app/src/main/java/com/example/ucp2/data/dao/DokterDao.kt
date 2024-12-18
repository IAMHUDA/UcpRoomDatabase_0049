package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


interface DokterDao {

    @Insert
    suspend fun insertDokter(dokter:Dokter)

    //fungsi get all data
    @Query("SELECT * FROM dokter ORDER BY nama ASC")
    fun getALLDokter() : Flow<List<Dokter>>

    //get dokter
    @Query("SELECT * FROM dokter")
}
package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Jadwal


@Dao
interface JadwalDao {
    @Insert
    suspend fun insertJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal")
    suspend fun getAllJadwal(): List<Jadwal>

    @Update
    suspend fun updateJadwal(jadwal: Jadwal)

    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal WHERE idJadwal = :id")
    suspend fun getJadwalDetail(id: Int): Jadwal

}
package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {
    @Insert
    suspend fun insertJadwal(jadwal: Jadwal)

    //fungsi get all data
    @Query("SELECT * FROM jadwal ORDER BY NamaDokter ASC")
    fun getAlljadwal() : Flow<List<Jadwal>>

    //get jadwal
    @Query("SELECT * FROM jadwal WHERE  NamaDokter= :NamaDokter")
    fun getjadwal(NamaDokter: String): Flow<Jadwal>

    //delete jadwal
    @Delete
    suspend fun  deletejadwal(jadwal: Jadwal)

    //update jadwal
    @Update
    suspend fun  updatejadwal(jadwal: Jadwal)

}
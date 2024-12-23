package com.example.ucp2.data.dao

import androidx.lifecycle.LiveData
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
    suspend fun addJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal ORDER BY namaPasien")
    fun getAllJadwal(): Flow<List<Jadwal>>

    @Query("SELECT*FROM jadwal WHERE idJadwal = :idJadwal")
    fun getJadwal(idJadwal: String): Flow<Jadwal>

    @Query("""
        SELECT jw.idJadwal, jw.namaDokter, jw.namaPasien,jw.noHp,jw.tanggalKonsul,jw.status
        FROM jadwal jw
        INNER JOIN dokter d ON jw.namaDokter = d.idDokter
        WHERE jw.idJadwal = :idJadwal
        ORDER BY jw.namaPasien ASC
    """)
    fun getJadwalJoin(idJadwal: String): Flow<List<Jadwal>>

    @Update
    suspend fun updateJadwal(jadwal: Jadwal)

    @Delete
    suspend fun deleteJadwal(jadwal: Jadwal)



}
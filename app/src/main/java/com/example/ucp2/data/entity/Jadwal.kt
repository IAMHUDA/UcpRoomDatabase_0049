package com.example.ucp2.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "jadwal",
    foreignKeys = [ForeignKey(
        entity = Dokter::class,
        parentColumns = ["idDokter"],
        childColumns = ["idDokter"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Jadwal(
    @PrimaryKey(autoGenerate = true) val idJadwal: Int = 0,
    val idDokter: String,
    val hari: String,
    val jamMulai: String,
    val jamSelesai: String
)



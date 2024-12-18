package com.example.ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jadwal")
data class Jadwal(
    @PrimaryKey
    val id: String,
    val NamaDokter: String,
    val NamaPasien: String,
    val noHp: String,
    val TanggalKonsul: String,
    val Status: String
)

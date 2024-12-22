package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryDokter
import com.example.ucp2.repository.RepositoryJadwal

import kotlinx.coroutines.launch

class InsertJadwalViewModel(
    private val repositoryJadwal: RepositoryJadwal,
    private val repositoryDokter: RepositoryDokter
) : ViewModel() {
    var jwUiState by mutableStateOf(JwUiState())

    init {
        getListDokter()
    }

    private fun getListDokter() {
        viewModelScope.launch {
            try {
                repositoryDokter.getAllDokter().collect { dokterList ->
                    jwUiState = jwUiState.copy(listDokter = dokterList)
                }
            } catch (e: Exception) {
                jwUiState = jwUiState.copy(snackBarMessage = "Gagal mengambil data dokter")
            }
        }
    }

    fun updateState(jwEvent: JwEvent) {
        jwUiState = jwUiState.copy(
            jwEvent = jwEvent)
    }

    private fun validateFields(): Boolean {
        val event = jwUiState.jwEvent
        val errorState = FormErrorStateJW(
            idJadwal = if (event.idJadwal.isNotEmpty()) null else "ID tidak boleh kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Tidak boleh kosong",
            tanggalKonsul = if (event.tanggalKonsul.isNotEmpty()) null else "Tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Tidak boleh kosong"
        )
        jwUiState = jwUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = jwUiState.jwEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwal.addJadwal(currentEvent.toJadwalEntity())
                    jwUiState = jwUiState.copy(
                        snackBarMessage = "Data berhasil dikirim",
                        jwEvent = JwEvent(),
                        isEntryValid = FormErrorStateJW()
                    )
                } catch (e: Exception) {
                    jwUiState = jwUiState.copy(
                        snackBarMessage = "Gagal mengirim data: ${e.message}"
                    )
                }
            }
        } else {
            jwUiState = jwUiState.copy(
                snackBarMessage = "Input tidak valid, periksa kembali"
            )
        }
    }

    fun resetSnackBarMessage() {
        jwUiState = jwUiState.copy(snackBarMessage = null)
    }
}

data class JwUiState(
    val jwEvent: JwEvent = JwEvent(),
    val isEntryValid: FormErrorStateJW = FormErrorStateJW(),
    val snackBarMessage: String? = null,
    val listDokter: List<Dokter> = emptyList()
)

data class FormErrorStateJW(
    val idJadwal: String? = null,
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val noHp: String? = null,
    val tanggalKonsul: String? = null,
    val status: String? = null,
) {
    fun isValid(): Boolean {
        return idJadwal == null && namaDokter == null && namaPasien == null &&
                noHp == null && tanggalKonsul == null && status == null
    }
}

// Fungsi ekstensi untuk mengonversi JwEvent ke Jadwal
fun JwEvent.toJadwalEntity(): Jadwal = Jadwal(
    idJadwal = idJadwal,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noHp = noHp,
    tanggalKonsul = tanggalKonsul,
    status = status
)

data class JwEvent(
    val idJadwal: String = "",
    val namaDokter: String = "",
    val namaPasien: String = "",
    val noHp: String = "",
    val tanggalKonsul: String = "",
    val status: String = "",
)



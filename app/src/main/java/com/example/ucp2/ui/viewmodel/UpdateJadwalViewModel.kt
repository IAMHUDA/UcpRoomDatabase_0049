package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryDokter
import com.example.ucp2.repository.RepositoryJadwal
import com.example.ucp2.ui.navigation.DestinasiUpdateJadwal
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private  val repositoryJadwal: RepositoryJadwal
): ViewModel()
{
    var updateUiState by mutableStateOf(JwUiState())
        private set

    private val _idJadwal: String = checkNotNull(savedStateHandle[DestinasiUpdateJadwal.idJadwal])

    init {
        viewModelScope.launch {
            updateUiState = repositoryJadwal.getJadwal(_idJadwal)
                .filterNotNull()
                .first()
                .toUIStateJadwal()
        }
    }

    fun updateState(jwEvent: JwEvent){
        updateUiState = updateUiState.copy(
            jwEvent = jwEvent
        )
    }

    fun validateFields(): Boolean {
        val event = updateUiState.jwEvent
        val errorState = FormErrorStateJW(
            idJadwal = if (event.idJadwal.isNotEmpty()) null else "ID tidak boleh kosong",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Tidak boleh kosong",
            tanggalKonsul = if (event.tanggalKonsul.isNotEmpty()) null else "Tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Tidak boleh kosong"
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(namaDokter: String) {
        val currentEvent = updateUiState.jwEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {

                    repositoryJadwal.updateJadwal(currentEvent.toJadwalEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        jwEvent = JwEvent(), // Reset event setelah update
                        isEntryValid = FormErrorStateJW()
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data gagal di update"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage(){
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }

}

fun Jadwal.toUIStateJadwal(): JwUiState = JwUiState(
    jwEvent = this.toJadwalUiEvent()
)
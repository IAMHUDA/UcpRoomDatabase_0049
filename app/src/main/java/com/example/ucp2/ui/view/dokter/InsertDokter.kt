package com.example.ucp2.ui.view.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.components.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.viewmodel.DokterEvent
import com.example.ucp2.viewmodel.DokterUIState
import com.example.ucp2.viewmodel.DokterViewModel
import com.example.ucp2.viewmodel.FormErrorState
import kotlinx.coroutines.launch


@Composable
fun InsertDokter(
    onBack:() -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val snackbarHostState = remember{SnackbarHostState()}
    val coroutineScope = rememberCoroutineScope()

    //Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) //Tampilkan snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)} //Tempatkan snackbar di scaffold
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(horizontal = 16.dp),
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah dokter",
            )
            //isi body
            InsertBodyDokter(
                uiState = uiState,
                onValueChange = {updatedEvent ->
                    viewModel.updateState(updatedEvent) //update state di ViewModel
                },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyDokter(
    modifier: Modifier = Modifier,
    onValueChange: (DokterEvent) -> Unit,
    uiState: DokterUIState,
    onClick:() -> Unit
){
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        FormDokter(
            dokterEvent = uiState.dokterEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormDokter(
    dokterEvent: DokterEvent = DokterEvent(),
    onValueChange: (DokterEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {

    val specializations = listOf(
        "Umum" to Color(0xFFE57373),      // Red
        "Anak" to Color(0xFF64B5F6),       // Blue
        "Kandungan" to Color(0xFF81C784),  // Green
        "Gigi" to Color(0xFFFFF176),       // Yellow
        "Bedah" to Color(0xFFBA68C8)       // Purple
    )

    var selectedSpecialization by remember { mutableStateOf(dokterEvent.spesialis) }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ID Dokter
        TextField(
            value = dokterEvent.idDokter,
            onValueChange = { onValueChange(dokterEvent.copy(idDokter = it)) },
            label = { Text("ID Dokter") },
            isError = errorState.idDokter != null
        )

        // Nama Dokter
        TextField(
            value = dokterEvent.nama,
            onValueChange = { onValueChange(dokterEvent.copy(nama = it)) },
            label = { Text("Nama Dokter") },
            isError = errorState.nama != null
        )

        // Spesialis
        Text(
            text = "Spesialis",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            specializations.forEach { (spesialis, color) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedSpecialization == spesialis,
                        onClick = {
                            selectedSpecialization = spesialis
                            onValueChange(dokterEvent.copy(spesialis = spesialis))
                        },
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = color,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text(
                        text = spesialis,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selectedSpecialization == spesialis) color else Color.Gray
                    )
                }
            }
        }

        // Klinik
        TextField(
            value = dokterEvent.klinik,
            onValueChange = { onValueChange(dokterEvent.copy(klinik = it)) },
            label = { Text("Klinik") },
            isError = errorState.klinik != null
        )

        // No HP
        TextField(
            value = dokterEvent.noHp,
            onValueChange = { onValueChange(dokterEvent.copy(noHp = it)) },
            label = { Text("No. HP") },
            isError = errorState.noHp != null
        )

        // Jam Kerja
        TextField(
            value = dokterEvent.jamKerja,
            onValueChange = { onValueChange(dokterEvent.copy(jamKerja = it)) },
            label = { Text("Jam Kerja") },
            isError = errorState.jamKerja != null
        )
    }
}






package com.example.ucp2.ui.view.jadwal



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.ui.utils.DynamicSelectedTextField
import com.example.ucp2.ui.viewmodel.FormErrorStateJW
import com.example.ucp2.ui.viewmodel.HomeScreenViewModel
import com.example.ucp2.ui.viewmodel.InsertJadwalViewModel
import com.example.ucp2.ui.viewmodel.JwEvent
import com.example.ucp2.ui.viewmodel.JwUiState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertJadwal(
    insertJadwalViewModel: InsertJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    homeScreenViewModel: HomeScreenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    onJadwal:() -> Unit,
    onDokter:() -> Unit,
    modifier: Modifier = Modifier,
) {

    val jwUiState = insertJadwalViewModel.jwUiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val homeUiState by homeScreenViewModel.homeUIState.collectAsState()
    val listDokter = homeUiState.listDokter

    LaunchedEffect(jwUiState.snackBarMessage) {
        jwUiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                insertJadwalViewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            com.example.ucp2.ui.components.TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah jadwal",
            )
            InsertBodyjadwal(
                uiState = jwUiState,
                onValueChange = { updatedEvent ->
                    insertJadwalViewModel.updateState(updatedEvent)
                },
                onClick = {
                    insertJadwalViewModel.saveData()

                },
                listDokter = listDokter
            )
        }
    }

}

@Composable
fun InsertBodyjadwal(
    modifier: Modifier = Modifier,
    onValueChange: (JwEvent) -> Unit = {},
    uiState: JwUiState = JwUiState(),
    onClick: () -> Unit,
    listDokter: List<Dokter> = emptyList(),

    ) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormJadwal(
            jwEvent = uiState.jwEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            listDokter = listDokter,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        ) {
            Text(text = "Simpan", fontSize = 16.sp)
        }
    }
}


@Composable
fun FormJadwal(
    jwEvent: JwEvent = JwEvent(),
    onValueChange: (JwEvent) -> Unit = {},
    errorState: FormErrorStateJW = FormErrorStateJW(),
    listDokter: List<Dokter> = emptyList(),
    modifier: Modifier = Modifier
){
    Box() {
        Column(modifier = modifier.fillMaxWidth().padding(top = 20.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jwEvent.idJadwal,
                onValueChange = { onValueChange(jwEvent.copy(idJadwal = it)) },
                label = { Text("idJadwal") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.idJadwal != null,
                placeholder = { Text("Masukkan id Jadwal") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.idJadwal ?: "", color = Color.Red)

            DynamicSelectedTextField(
                selectedValue = jwEvent.namaDokter,
                listDokter = listDokter,
                label = "Pilih Dokter",
                onValueChangedEvent = { selectedDokter ->
                    onValueChange(jwEvent.copy(namaDokter = selectedDokter))
                },
                onCLick = {},
                modifier = Modifier.fillMaxWidth(),
            )


            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jwEvent.namaPasien,
                onValueChange = { onValueChange(jwEvent.copy(namaPasien = it)) },
                label = { Text("nama Pasien") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.namaPasien != null,
                placeholder = { Text("Masukkan nama Pasien") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.namaPasien ?: "", color = Color.Red)


            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jwEvent.noHp,
                onValueChange = { onValueChange(jwEvent.copy(noHp = it)) },
                label = { Text("NO Hape") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.noHp != null,
                placeholder = { Text("Masukkan no hape") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.noHp ?: "", color = Color.Red)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jwEvent.tanggalKonsul,
                onValueChange = { onValueChange(jwEvent.copy(tanggalKonsul = it)) },
                label = { Text("tanggal konsul") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.tanggalKonsul != null,
                placeholder = { Text("Masukkan tanggal") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.tanggalKonsul ?: "", color = Color.Red)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = jwEvent.status,
                onValueChange = { onValueChange(jwEvent.copy(status = it)) },
                label = { Text("Ststud") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = ""
                    )
                },
                isError = errorState.status != null,
                placeholder = { Text("Masukkan status pasien") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Text(text = errorState.status ?: "", color = Color.Red)


        }
    }
}
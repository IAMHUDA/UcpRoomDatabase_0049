package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.ui.components.TopBarWithSearch
import com.example.ucp2.ui.viewmodel.HomeJWUiState
import com.example.ucp2.ui.viewmodel.HomeJWViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeJWView(
    viewModel: HomeJWViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAddJadwal: () -> Unit = {},
    onEditJadwal: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val homeUiState by viewModel.homeJwUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBarWithSearch(
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                title = "Halaman Jadwal"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddJadwal,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Jadwal"
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(onAddJadwal = onAddJadwal)
        }
    ) { innerPadding ->
        BodyHomeJWView(
            homeJWUiState = homeUiState,
            onEditJadwal = { onEditJadwal(it) }, // Pass the correct id here
            modifier = Modifier.padding(innerPadding),
            snackbarHostState = snackbarHostState,
            viewModel = viewModel
        )
    }
}

@Composable
fun BodyHomeJWView(
    homeJWUiState: HomeJWUiState,
    onClick: (String) -> Unit = {},
    onEditJadwal: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: HomeJWViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var selectedJadwal: Jadwal? by remember { mutableStateOf(null) }

    when {
        homeJWUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        homeJWUiState.isError -> {
            LaunchedEffect(homeJWUiState.errorMessage) {
                homeJWUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        homeJWUiState.listJw.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada jadwal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(homeJWUiState.listJw) { jw ->
                    JadwalCard(
                        jw = jw,
                        onClick = { onClick(jw.idJadwal) },
                        onDelete = {
                            selectedJadwal = jw
                            deleteConfirmationRequired = true
                        },
                        onEditJadwal = { onEditJadwal(jw.idJadwal) } // Correctly pass the id of the jadwal
                    )
                }
            }
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                selectedJadwal?.let { jadwal ->
                    coroutineScope.launch {
                        viewModel.deleteJW(jadwal)
                        snackbarHostState.showSnackbar("Jadwal berhasil dihapus")
                    }
                }
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false
            }
        )
    }
}

@Composable
fun JadwalCard(
    jw: Jadwal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onEditJadwal: (String) -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp)), // Border hitam
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE7FBB4)) // Warna latar belakang
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nama Dokter: ${jw.namaDokter}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black // Ubah warna teks menjadi hitam
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Nama Pasien: ${jw.namaPasien}",
                    fontSize = 14.sp,
                    color = Color.Black // Ubah warna teks menjadi hitam
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tanggal Konsul: ${jw.tanggalKonsul}",
                    fontSize = 14.sp,
                    color = Color.Black // Ubah warna teks menjadi hitam
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Nomer Telp: ${jw.noHp}",
                    fontSize = 14.sp,
                    color = Color.Black // Ubah warna teks menjadi hitam
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Status: ${jw.status}",
                    fontSize = 14.sp,
                    color = Color.Black // Ubah warna teks menjadi hitam
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onEditJadwal(jw.idJadwal) }, // Use the correct id here
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFFFF574),RoundedCornerShape(38.dp)) // Warna latar belakang tombol
                        .border(1.dp, Color.Black, RoundedCornerShape(38.dp))
                        .clip(RoundedCornerShape(8.dp))// Border hitam tombol
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Jadwal",
                        tint = Color.Black // Ubah tint menjadi hitam
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFFF8383),RoundedCornerShape(38.dp)) // Warna latar belakang tombol hapus
                        .border(1.dp, Color.Black, RoundedCornerShape(38.dp)) // Border hitam tombol
                        .clip(RoundedCornerShape(8.dp)) // Menentukan bentuk dengan sudut membulat
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Jadwal",
                        tint = Color.Black // Ubah tint menjadi hitam
                    )
                }

            }
        }
    }
}


@Composable
fun BottomNavigationBar(
    onAddJadwal: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle Home click */ }) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
            }
            IconButton(onClick = { /* Handle Calendar click */ }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
            }
            IconButton(onClick = onAddJadwal) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Jadwal")
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDeleteCancel,
        title = { Text(text = "Konfirmasi Hapus") },
        text = { Text(text = "Apakah Anda yakin ingin menghapus jadwal ini?") },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Hapus")
            }
        },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeJWView() {
    HomeJWView(onBackClick = {}, onSearchClick = {})
}

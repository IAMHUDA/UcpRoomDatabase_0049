package com.example.ucp2.ui.view.dokter

import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.viewmodel.DokterViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import com.example.ucp2.ui.viewmodel.HomeScreenViewModel
import com.example.ucp2.ui.viewmodel.HomeUiState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Person
import com.example.ucp2.ui.components.Dashboard


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onDetailClick: (String) -> Unit,
    onAddDokter: () -> Unit = {},
    onAddJadwal: () -> Unit = {},
    onDetailJW: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopHeader()
        },
        bottomBar = {
            BottomNavigationBar(onAddDokter = onAddDokter,onDetailJW= onDetailJW) // Passing the navigation action
        }
    ) { padding ->

        val homeUiState by viewModel.homeUIState.collectAsState()
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SearchBar()
            SchedulesSection(onDetailJW=onDetailJW, onAddJadwal = onAddJadwal) // Memanggil bagian jadwal
            TopDoctorsSection(
                listDokter = homeUiState.listDokter,
                onCLick = {
                    println(it)
                },
                modifier = Modifier.padding(top = 10.dp),
                onAddDokter = onAddDokter
            )
        }
    }
}

@Composable
fun TopHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
            .background(Color(0xFF6D6D6B))
            .padding(16.dp)
    ) {
        // Kartu utama
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)

                .padding(16.dp)
        ) {

            // Konten di dalam kartu
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = "BPJS Kesehatan",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = "NAMA PESERTA: MIFTAHUL HUDA ",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White
                            , fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "PEKERJAAN: MARINIR",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White
                            , fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Nomor: 1234 5678 9012 3456",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                // Foto profil di sisi kanan
                Image(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCBD5E1)) // Warna abu-abu pastel
                        .border(2.dp, Color.White, CircleShape) // Border putih
                )
            }
        }
    }
}



@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE9E3F3)),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Gray),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box { innerTextField() }
                }
            }
        )
    }
}
@Composable
fun SchedulesSection(onDetailJW: () -> Unit,onAddJadwal:() -> Unit) {
    Row (horizontalArrangement = Arrangement.spacedBy(60.dp)) {

        Button(
            onClick = onAddJadwal,
            modifier = Modifier,
            shape = RoundedCornerShape(topEnd = 50.dp, bottomStart = 50.dp)
        ) {
            Text("Create Schedule")
        }
        Button(
            onClick = onDetailJW,
            modifier = Modifier,
            shape = RoundedCornerShape(topStart = 50.dp, bottomEnd = 50.dp)
        ) {
            Text("View Schedules")
        }
    }
}


@Composable
fun BottomNavigationBar(onAddDokter: () -> Unit,onDetailJW: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .offset(y = (-10).dp)
            .clip(RoundedCornerShape(16.dp))

            .border(2.dp, Color.Gray, RoundedCornerShape(20.dp))
            .shadow(19.dp, RoundedCornerShape(13.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Memberi padding internal agar isi tidak terlalu sempit
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Navigate to Home */ }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = Color.Black
                )
            }
            IconButton(onClick = onDetailJW) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Schedules List",
                    tint = Color.Black
                )
            }
            IconButton(onClick = onAddDokter) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Doctor",
                    tint = Color.Black
                )
            }
        }
    }
}


@Composable
fun BodyHomeDokterView(
    homeUiState: HomeUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier,
    onAddDokter: () -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } //Snackbar stste
    when {
        homeUiState.isLoading -> {
            // Menampilkan indikator loading
            Box (modifier= modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                CircularProgressIndicator()
            }
        }

        homeUiState.isError -> {
            //menampilkan pesan error
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let{message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)  //menampilkan snackbar
                    }
                }
            }
        }

        homeUiState.listDokter.isEmpty() -> {
            //menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data mahasiswa.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            //Menampilkan daftar mahasiswa
            TopDoctorsSection(
                listDokter = homeUiState.listDokter,
                onCLick = {
                    onClick(it)
                    println(it)
                },
                modifier = Modifier.padding(top = 10.dp),
                onAddDokter = onAddDokter
            )
        }
    }
}

@Composable
fun TopDoctorsSection(
    listDokter: List<Dokter>,
    onCLick: (String) -> Unit = { },
    modifier: Modifier = Modifier,
    onAddDokter: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Top Doctors",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = onAddDokter,
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Doctor",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // LazyColumn untuk menampilkan daftar dokter
        LazyColumn {
            items(items = listDokter, itemContent = { dokter ->
                // Dapatkan warna spesialisasi untuk dokter ini
                val spesialisColor = when (dokter.spesialis) {
                    "Umum" -> Color(0xFFE57373) // Red
                    "Anak" -> Color(0xFF64B5F6) // Blue
                    "Kandungan" -> Color(0xFF81C784) // Green
                    "Gigi" -> Color(0xFFBDB703) // Yellow
                    "Bedah" -> Color(0xFFBA68C8) // Purple
                    else -> Color.Gray // Default color
                }

                DoctorCard(
                    name = dokter.nama,
                    speciality = dokter.spesialis,
                    location = dokter.klinik,
                    workHours = dokter.jamKerja, // Menggunakan jam kerja
                    onClick = { onCLick(dokter.idDokter) },
                    color = spesialisColor // Menggunakan warna spesialisasi
                )
            })
        }
    }
}


@Composable
fun DoctorCard(
    name: String,
    speciality: String,
    location: String,
    workHours: String, // Menambahkan parameter untuk jam kerja
    onClick: () -> Unit,
    color: Color // Menambahkan parameter untuk warna spesialis
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable(onClick = onClick)
            .background(Color.White)
            .border(
                width = 1.dp,
                brush = Brush.radialGradient(
                    colors = listOf(Color.Black, Color.Gray),
                    center = Offset(50f, 50f),
                    radius = 100f // Radius gradien
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .offset(x = 18.dp, y = 1.dp)
    ) {
        Image(
            imageVector = Icons.Filled.Person,
            contentDescription = name,
            modifier = Modifier
                .padding(top =5.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(color = color)


        )
        Spacer(modifier = Modifier.width(25.dp))
        Column(
            modifier = Modifier.weight(1f).padding(3.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = speciality,
                style = TextStyle(fontSize = 14.sp, color = color) // Menggunakan warna spesialis
            )
            Text(
                text = "Jam Kerja: $workHours",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
            Text(
                text = "Klinik: $location",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
            Spacer(modifier = Modifier.width(17.dp))
        }
    }
}









package com.example.ucp2.ui.components



import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBarWithSearch(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical =38.dp,),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tombol Back
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF6200EA) // Warna ungu
            )
        }

        // Spacer untuk menempatkan judul di tengah
        Spacer(modifier = Modifier.weight(1f))

        // Judul Halaman
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.weight(3f)
        )

        // Spacer untuk menjaga keseimbangan
        Spacer(modifier = Modifier.weight(1f))

        // Tombol Search
        IconButton(
            onClick = onSearchClick,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF6200EA) // Warna ungu
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TopBarWithSearchPreview() {
    TopBarWithSearch(
        onBackClick = {},
        onSearchClick = {},
        title = "Halaman Jadwal"
    )
}

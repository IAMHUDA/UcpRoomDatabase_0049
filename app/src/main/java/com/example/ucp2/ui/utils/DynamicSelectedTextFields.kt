package com.example.ucp2.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectedTextField(
    selectedValue: String,
    listDokter: List<Dokter>,
    label: String,
    onCLick: () -> Unit,
    onValueChangedEvent:(String) -> Unit,
    modifier: Modifier = Modifier
    ) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded},
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier.menuAnchor().fillMaxWidth(),

        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
            listDokter.forEach {
                dokter ->
                DropdownMenuItem(
                    text = { Text(text = dokter.nama) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(dokter.nama)
                    }
                )
            }
        }
    }
}
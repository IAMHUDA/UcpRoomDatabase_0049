package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.RumahSakitApp
import com.example.ucp2.repository.RepositoryDokter
import com.example.ucp2.viewmodel.DokterViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                RumahSakitApp().containerApp.dokterRepository
            )
        }
        initializer {
            HomeScreenViewModel(
                RumahSakitApp().containerApp.dokterRepository
            )
        }
        // Tambahkan ViewModel lain sesuai kebutuhan
    }
}

fun CreationExtras.RumahSakitApp(): RumahSakitApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RumahSakitApp)

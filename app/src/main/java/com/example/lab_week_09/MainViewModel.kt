package com.example.lab_week_09

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // Menyimpan daftar nama
    private val _names = mutableStateListOf("Tanu", "Tina", "Tono")
    val names: List<String> get() = _names

    // Menambahkan nama baru
    fun addName(name: String) {
        if (name.isNotBlank()) {
            _names.add(name)
        }
    }
}
package com.example.midterm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class User(
    val email: String,
    val password: String,
    val name: String
)

data class Item(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val quantity: Int,
    val isChecked: Boolean = false
)

class AppViewModel : ViewModel() {
    private val registeredUsers = mutableListOf<User>()

    var currentUser by mutableStateOf<User?>(null)
        private set

    var items by mutableStateOf(emptyList<Item>())
        private set

    fun registerUser(email: String, pass: String, name: String): Boolean {
        // Cek apakah email sudah ada?
        val isExist = registeredUsers.any { it.email == email }
        if (isExist) return false

        // Jika belum, simpan user baru
        registeredUsers.add(User(email, pass, name))
        return true
    }
    fun loginUser(email: String, pass: String): Boolean {
        val user = registeredUsers.find { it.email == email && it.password == pass }
        return if (user != null) {
            currentUser = user
            true
        } else {
            false
        }
    }

    fun addItem(name: String, quantityString: String) {
        val quantity = quantityString.toIntOrNull() ?: 0
        if (name.isNotBlank() && quantity > 0) {
            val newItem = Item(name = name, quantity = quantity)
            items = items + newItem
        }
    }

    fun removeItem(item: Item) {
        items = items.filter { it.id != item.id }
    }

    fun toggleItemChecked(item: Item) {
        items = items.map {
            if (it.id == item.id) it.copy(isChecked = !it.isChecked) else it
        }
    }
}
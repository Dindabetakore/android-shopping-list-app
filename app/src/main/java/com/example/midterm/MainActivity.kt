package com.example.midterm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.midterm.ui.theme.MidTermTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MidTermTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    val viewModel: AppViewModel = viewModel()
                    val context = LocalContext.current

                    var currentScreen by remember { mutableStateOf("login") }

                    when (currentScreen) {
                        "login" -> LoginScreen(
                            onLoginSubmit = { email, password ->
                                val success = viewModel.loginUser(email, password)
                                if (success) {
                                    Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                                }
                                success
                            },
                            onLoginSuccess = {
                                currentScreen = "dashboard"
                            },
                            onSignUpClick = {
                                currentScreen = "signup"
                            }
                        )

                        "signup" -> SignUpScreen(
                            onSignUpSubmit = { fullName, email, password ->
                                val success = viewModel.registerUser(email, password, fullName)
                                if (success) {
                                    Toast.makeText(context, "Akun berhasil dibuat! Silakan Login.", Toast.LENGTH_LONG).show()
                                    currentScreen = "login"
                                } else {
                                    Toast.makeText(context, "Gagal: Email sudah terdaftar.", Toast.LENGTH_SHORT).show()
                                }
                                success
                            },
                            onLoginClick = {
                                currentScreen = "login"
                            }
                        )

                        "dashboard" -> DashboardScreen(
                            viewModel = viewModel,
                            onAddItemClick = {
                                currentScreen = "add"
                            }
                        )

                        "add" -> AddItemScreen(
                            onSave = { name, quantity ->
                                viewModel.addItem(name, quantity)
                                currentScreen = "dashboard"
                            },
                            onCancel = {
                                currentScreen = "dashboard"
                            }
                        )
                    }
                }
            }
        }
    }
}
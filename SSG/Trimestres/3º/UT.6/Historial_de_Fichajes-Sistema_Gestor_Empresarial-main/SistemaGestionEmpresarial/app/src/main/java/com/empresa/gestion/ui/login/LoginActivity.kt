package com.empresa.gestion.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.empresa.gestion.databinding.ActivityLoginBinding
import com.empresa.gestion.ui.punch.PunchActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var currentPin: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // Configurar listeners
        setupListeners()

        // Observar estados del ViewModel
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val employeeCode = binding.etEmployeeCode.text.toString().trim()
            val pin = binding.etPin.text.toString().trim()

            // Validaciones básicas
            if (employeeCode.isEmpty()) {
                binding.tilEmployeeCode.error = "Ingrese código de empleado"
                return@setOnClickListener
            }

            if (pin.isEmpty()) {
                binding.tilPin.error = "Ingrese PIN"
                return@setOnClickListener
            }

            if (pin.length < 4) {
                binding.tilPin.error = "PIN debe tener al menos 4 dígitos"
                return@setOnClickListener
            }

            // Limpiar errores
            binding.tilEmployeeCode.error = null
            binding.tilPin.error = null

            // Guardar el PIN para pasarlo a la siguiente pantalla
            currentPin = pin

            // Intentar login
            viewModel.login(employeeCode, pin)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Idle -> {
                        hideLoading()
                        hideError()
                    }

                    is LoginState.Loading -> {
                        showLoading()
                        hideError()
                    }

                    is LoginState.Success -> {
                        hideLoading()
                        hideError()

                        Toast.makeText(
                            this@LoginActivity,
                            "Bienvenido ${state.employee.name}",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Ir a la pantalla de fichaje
                        navigateToPunchScreen(
                            state.employee.employeeCode,
                            state.employee.name,
                            state.employee.role // 👈 NUEVA LÍNEA
                        )
                    }

                    is LoginState.Error -> {
                        hideLoading()
                        showError(state.message)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.btnLogin.isEnabled = true
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
    }

    private fun navigateToPunchScreen(employeeCode: String, employeeName: String, employeeRole: String) {
        val intent = Intent(this, PunchActivity::class.java).apply {
            putExtra("EMPLOYEE_CODE", employeeCode)
            putExtra("EMPLOYEE_NAME", employeeName)
            putExtra("EMPLOYEE_PIN", currentPin)
            putExtra("EMPLOYEE_ROLE", employeeRole) // 👈 NUEVA LÍNEA
        }
        startActivity(intent)
        finish()
    }
}
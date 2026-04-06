package com.empresa.gestion.ui.punch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.empresa.gestion.databinding.ActivityPunchBinding
import com.empresa.gestion.ui.employees.EmployeesActivity
import com.empresa.gestion.ui.history.PunchHistoryActivity
import kotlinx.coroutines.launch

class PunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPunchBinding
    private lateinit var viewModel: PunchViewModel

    private var employeeCode: String = ""
    private var employeeName: String = ""
    private var employeePin: String = ""
    private var employeeRole: String = "" // 👈 NUEVA VARIABLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityPunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del Intent
        employeeCode = intent.getStringExtra("EMPLOYEE_CODE") ?: ""
        employeeName = intent.getStringExtra("EMPLOYEE_NAME") ?: ""
        employeePin = intent.getStringExtra("EMPLOYEE_PIN") ?: ""
        employeeRole = intent.getStringExtra("EMPLOYEE_ROLE") ?: "employee" // 👈 NUEVA LÍNEA

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this)[PunchViewModel::class.java]

        // Configurar UI
        setupUI()

        // Configurar listeners
        setupListeners()

        // Observar estados
        observeViewModel()
    }

    private fun setupUI() {
        binding.tvWelcome.text = "Bienvenido $employeeName"
        binding.tvEmployeeCode.text = "Código: $employeeCode"

        // Mostrar botón de gestión solo si es admin o manager
        if (employeeRole == "admin" || employeeRole == "manager") {
            binding.btnManageEmployees.visibility = View.VISIBLE
        }
    }

    private fun setupListeners() {
        // Botón Fichar Entrada
        binding.btnPunchIn.setOnClickListener {
            viewModel.registerPunch(employeeCode, "IN", employeePin)
        }

        // Botón Fichar Salida
        binding.btnPunchOut.setOnClickListener {
            viewModel.registerPunch(employeeCode, "OUT", employeePin)
        }

        // Botón Ver Historial
        binding.btnViewHistory.setOnClickListener {
            val intent = Intent(this, PunchHistoryActivity::class.java)
            startActivity(intent)
        }

        // Botón Gestión de Empleados
        binding.btnManageEmployees.setOnClickListener {
            val intent = Intent(this, EmployeesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.punchState.collect { state ->
                when (state) {
                    is PunchState.Idle -> {
                        hideLoading()
                        hideMessage()
                    }

                    is PunchState.Loading -> {
                        showLoading()
                        hideMessage()
                    }

                    is PunchState.Success -> {
                        hideLoading()

                        val typeText = if (state.punch.punchType == "IN") "ENTRADA" else "SALIDA"
                        showMessage("✓ Fichaje de $typeText registrado correctamente", true)

                        Toast.makeText(
                            this@PunchActivity,
                            "Fichaje registrado: $typeText",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is PunchState.Error -> {
                        hideLoading()
                        showMessage("✗ Error: ${state.message}", false)

                        Toast.makeText(
                            this@PunchActivity,
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnPunchIn.isEnabled = false
        binding.btnPunchOut.isEnabled = false
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.btnPunchIn.isEnabled = true
        binding.btnPunchOut.isEnabled = true
    }

    private fun showMessage(message: String, isSuccess: Boolean) {
        binding.tvMessage.text = message
        binding.tvMessage.setTextColor(
            if (isSuccess)
                resources.getColor(android.R.color.holo_green_dark, null)
            else
                resources.getColor(android.R.color.holo_red_dark, null)
        )
        binding.tvMessage.visibility = View.VISIBLE
    }

    private fun hideMessage() {
        binding.tvMessage.visibility = View.GONE
    }
}
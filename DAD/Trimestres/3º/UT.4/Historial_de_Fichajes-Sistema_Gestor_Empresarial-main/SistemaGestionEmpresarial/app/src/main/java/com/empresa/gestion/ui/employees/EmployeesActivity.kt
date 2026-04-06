package com.empresa.gestion.ui.employees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.empresa.gestion.R
import com.empresa.gestion.databinding.ActivityEmployeesBinding
import com.empresa.gestion.databinding.DialogAddEmployeeBinding
import com.empresa.gestion.domain.model.Employee
import kotlinx.coroutines.launch

class EmployeesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeesBinding
    private lateinit var viewModel: EmployeesViewModel
    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityEmployeesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this)[EmployeesViewModel::class.java]

        // Configurar RecyclerView
        setupRecyclerView()

        // Configurar listeners
        setupListeners()

        // Observar estados
        observeViewModel()

        // Cargar empleados
        viewModel.loadEmployees()
    }

    private fun setupRecyclerView() {
        adapter = EmployeeAdapter { employee ->
            showDeleteConfirmation(employee)
        }

        binding.rvEmployees.apply {
            layoutManager = LinearLayoutManager(this@EmployeesActivity)
            adapter = this@EmployeesActivity.adapter
        }
    }

    private fun setupListeners() {
        // FloatingActionButton para añadir empleado
        binding.fabAddEmployee.setOnClickListener {
            showAddEmployeeDialog()
        }
    }

    private fun observeViewModel() {
        // Observar estado de la lista
        lifecycleScope.launch {
            viewModel.employeesState.collect { state ->
                when (state) {
                    is EmployeesState.Idle -> {
                        hideLoading()
                    }

                    is EmployeesState.Loading -> {
                        showLoading()
                    }

                    is EmployeesState.Success -> {
                        hideLoading()

                        if (state.employees.isEmpty()) {
                            showEmptyMessage()
                        } else {
                            hideEmptyMessage()
                            adapter.submitList(state.employees)
                        }
                    }

                    is EmployeesState.Error -> {
                        hideLoading()
                        showEmptyMessage()

                        Toast.makeText(
                            this@EmployeesActivity,
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        // Observar estado de acciones (crear/eliminar)
        lifecycleScope.launch {
            viewModel.actionState.collect { state ->
                when (state) {
                    is EmployeeActionState.Idle -> {
                        // No hacer nada
                    }

                    is EmployeeActionState.Loading -> {
                        // Mostrar loading si es necesario
                    }

                    is EmployeeActionState.Success -> {
                        Toast.makeText(
                            this@EmployeesActivity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.resetActionState()
                    }

                    is EmployeeActionState.Error -> {
                        Toast.makeText(
                            this@EmployeesActivity,
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.resetActionState()
                    }
                }
            }
        }
    }

    private fun showAddEmployeeDialog() {
        val dialogBinding = DialogAddEmployeeBinding.inflate(LayoutInflater.from(this))

        // Configurar dropdown de roles
        val roles = arrayOf("employee", "manager", "admin")
        val rolesAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roles)
        dialogBinding.actvRole.setAdapter(rolesAdapter)
        dialogBinding.actvRole.setText("employee", false)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        // Botón Cancelar
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Botón Crear
        dialogBinding.btnCreate.setOnClickListener {
            val employeeCode = dialogBinding.etEmployeeCode.text.toString().trim()
            val name = dialogBinding.etName.text.toString().trim()
            val email = dialogBinding.etEmail.text.toString().trim()
            val pin = dialogBinding.etPin.text.toString().trim()
            val role = dialogBinding.actvRole.text.toString().trim()

            // Validaciones
            var hasError = false

            if (employeeCode.isEmpty()) {
                dialogBinding.tilEmployeeCode.error = "Ingrese código de empleado"
                hasError = true
            } else {
                dialogBinding.tilEmployeeCode.error = null
            }

            if (name.isEmpty()) {
                dialogBinding.tilName.error = "Ingrese nombre"
                hasError = true
            } else {
                dialogBinding.tilName.error = null
            }

            if (email.isEmpty()) {
                dialogBinding.tilEmail.error = "Ingrese email"
                hasError = true
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                dialogBinding.tilEmail.error = "Email inválido"
                hasError = true
            } else {
                dialogBinding.tilEmail.error = null
            }

            if (pin.isEmpty()) {
                dialogBinding.tilPin.error = "Ingrese PIN"
                hasError = true
            } else if (pin.length < 4) {
                dialogBinding.tilPin.error = "PIN debe tener al menos 4 dígitos"
                hasError = true
            } else {
                dialogBinding.tilPin.error = null
            }

            if (!hasError) {
                // Crear empleado
                viewModel.createEmployee(employeeCode, name, email, pin, role)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun showDeleteConfirmation(employee: Employee) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Empleado")
            .setMessage("¿Está seguro de que desea eliminar a ${employee.name}?")
            .setPositiveButton("Eliminar") { _, _ ->
                viewModel.deleteEmployee(employee.id)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvEmployees.visibility = View.GONE
        binding.fabAddEmployee.isEnabled = false
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.rvEmployees.visibility = View.VISIBLE
        binding.fabAddEmployee.isEnabled = true
    }

    private fun showEmptyMessage() {
        binding.tvEmptyMessage.visibility = View.VISIBLE
        binding.rvEmployees.visibility = View.GONE
    }

    private fun hideEmptyMessage() {
        binding.tvEmptyMessage.visibility = View.GONE
    }
}
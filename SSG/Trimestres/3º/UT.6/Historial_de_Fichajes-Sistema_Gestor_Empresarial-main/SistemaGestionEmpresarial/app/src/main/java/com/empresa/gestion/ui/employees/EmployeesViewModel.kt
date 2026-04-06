package com.empresa.gestion.ui.employees

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.empresa.gestion.data.repository.EmployeeRepository
import com.empresa.gestion.domain.model.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados posibles de la lista de empleados
 */
sealed class EmployeesState {
    object Idle : EmployeesState()
    object Loading : EmployeesState()
    data class Success(val employees: List<Employee>) : EmployeesState()
    data class Error(val message: String) : EmployeesState()
}

/**
 * Estados posibles de crear/eliminar empleado
 */
sealed class EmployeeActionState {
    object Idle : EmployeeActionState()
    object Loading : EmployeeActionState()
    data class Success(val message: String) : EmployeeActionState()
    data class Error(val message: String) : EmployeeActionState()
}

class EmployeesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EmployeeRepository(application.applicationContext)

    // Estado de la lista de empleados
    private val _employeesState = MutableStateFlow<EmployeesState>(EmployeesState.Idle)
    val employeesState: StateFlow<EmployeesState> = _employeesState

    // Estado de acciones (crear/eliminar)
    private val _actionState = MutableStateFlow<EmployeeActionState>(EmployeeActionState.Idle)
    val actionState: StateFlow<EmployeeActionState> = _actionState

    /**
     * Carga la lista de empleados
     */
    fun loadEmployees() {
        viewModelScope.launch {
            _employeesState.value = EmployeesState.Loading

            val result = repository.getEmployees(activeOnly = true)

            result.fold(
                onSuccess = { employees ->
                    _employeesState.value = EmployeesState.Success(employees)
                },
                onFailure = { error ->
                    _employeesState.value = EmployeesState.Error(
                        error.message ?: "Error al cargar empleados"
                    )
                }
            )
        }
    }

    /**
     * Crea un nuevo empleado
     */
    fun createEmployee(
        employeeCode: String,
        name: String,
        email: String,
        pin: String,
        role: String
    ) {
        viewModelScope.launch {
            _actionState.value = EmployeeActionState.Loading

            val result = repository.createEmployee(employeeCode, name, email, pin, role)

            result.fold(
                onSuccess = { employee ->
                    _actionState.value = EmployeeActionState.Success("Empleado creado correctamente")
                    // Recargar lista
                    loadEmployees()
                },
                onFailure = { error ->
                    _actionState.value = EmployeeActionState.Error(
                        error.message ?: "Error al crear empleado"
                    )
                }
            )
        }
    }

    /**
     * Elimina un empleado
     */
    fun deleteEmployee(employeeId: Int) {
        viewModelScope.launch {
            _actionState.value = EmployeeActionState.Loading

            val result = repository.deleteEmployee(employeeId)

            result.fold(
                onSuccess = {
                    _actionState.value = EmployeeActionState.Success("Empleado eliminado correctamente")
                    // Recargar lista
                    loadEmployees()
                },
                onFailure = { error ->
                    _actionState.value = EmployeeActionState.Error(
                        error.message ?: "Error al eliminar empleado"
                    )
                }
            )
        }
    }

    /**
     * Reinicia el estado de acciones a Idle
     */
    fun resetActionState() {
        _actionState.value = EmployeeActionState.Idle
    }
}
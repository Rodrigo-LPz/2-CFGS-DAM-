package com.empresa.gestion.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.empresa.gestion.data.repository.AuthRepository
import com.empresa.gestion.domain.model.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados posibles del login
 */
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val employee: Employee) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository(application.applicationContext)

    // Estado del login (observable desde la UI)
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    /**
     * Intenta hacer login con las credenciales proporcionadas
     */
    fun login(employeeCode: String, pin: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val result = repository.login(employeeCode, pin)

            result.fold(
                onSuccess = { employee ->
                    _loginState.value = LoginState.Success(employee)
                },
                onFailure = { error ->
                    _loginState.value = LoginState.Error(
                        error.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    /**
     * Reinicia el estado a Idle
     */
    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}
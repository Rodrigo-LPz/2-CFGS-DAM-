package com.empresa.gestion.ui.punch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.empresa.gestion.data.repository.PunchRepository
import com.empresa.gestion.domain.model.Punch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados posibles del fichaje
 */
sealed class PunchState {
    object Idle : PunchState()
    object Loading : PunchState()
    data class Success(val punch: Punch) : PunchState()
    data class Error(val message: String) : PunchState()
}

class PunchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PunchRepository(application.applicationContext)

    // Estado del fichaje (observable desde la UI)
    private val _punchState = MutableStateFlow<PunchState>(PunchState.Idle)
    val punchState: StateFlow<PunchState> = _punchState

    /**
     * Registra un fichaje (entrada o salida)
     * Ahora recibe el PIN del empleado que hizo login
     */
    fun registerPunch(employeeCode: String, punchType: String, pin: String) {
        viewModelScope.launch {
            _punchState.value = PunchState.Loading

            val result = repository.createPunch(
                employeeCode = employeeCode,
                pin = pin,
                punchType = punchType,
                location = null,
                notes = null
            )

            result.fold(
                onSuccess = { punch ->
                    _punchState.value = PunchState.Success(punch)
                    // Volver a Idle después de 3 segundos
                    kotlinx.coroutines.delay(3000)
                    _punchState.value = PunchState.Idle
                },
                onFailure = { error ->
                    _punchState.value = PunchState.Error(
                        error.message ?: "Error al registrar fichaje"
                    )
                    // Volver a Idle después de 5 segundos
                    kotlinx.coroutines.delay(5000)
                    _punchState.value = PunchState.Idle
                }
            )
        }
    }

    /**
     * Reinicia el estado a Idle
     */
    fun resetState() {
        _punchState.value = PunchState.Idle
    }
}
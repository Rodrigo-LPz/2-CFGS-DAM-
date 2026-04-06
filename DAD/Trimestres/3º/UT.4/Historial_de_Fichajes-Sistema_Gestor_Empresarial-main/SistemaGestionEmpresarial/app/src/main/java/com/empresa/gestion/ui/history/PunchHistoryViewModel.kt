package com.empresa.gestion.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.empresa.gestion.data.repository.PunchRepository
import com.empresa.gestion.domain.model.Punch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estados posibles del historial
 */
sealed class PunchHistoryState {
    object Idle : PunchHistoryState()
    object Loading : PunchHistoryState()
    data class Success(val punches: List<Punch>) : PunchHistoryState()
    data class Error(val message: String) : PunchHistoryState()
}

class PunchHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PunchRepository(application.applicationContext)

    // Estado del historial (observable desde la UI)
    private val _historyState = MutableStateFlow<PunchHistoryState>(PunchHistoryState.Idle)
    val historyState: StateFlow<PunchHistoryState> = _historyState

    /**
     * Carga el historial de fichajes
     * @param employeeId Si se proporciona, filtra por empleado. Si es null, muestra todos
     */
    fun loadPunches(employeeId: Int? = null) {
        viewModelScope.launch {
            _historyState.value = PunchHistoryState.Loading

            val result = repository.getPunches(employeeId = employeeId, limit = 100)

            result.fold(
                onSuccess = { punches ->
                    _historyState.value = PunchHistoryState.Success(punches)
                },
                onFailure = { error ->
                    _historyState.value = PunchHistoryState.Error(
                        error.message ?: "Error al cargar fichajes"
                    )
                }
            )
        }
    }

    /**
     * Reinicia el estado a Idle
     */
    fun resetState() {
        _historyState.value = PunchHistoryState.Idle
    }
}
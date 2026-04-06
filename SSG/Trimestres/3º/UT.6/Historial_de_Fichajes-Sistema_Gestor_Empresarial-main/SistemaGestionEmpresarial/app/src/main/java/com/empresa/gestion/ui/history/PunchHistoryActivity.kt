package com.empresa.gestion.ui.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.empresa.gestion.databinding.ActivityPunchHistoryBinding
import kotlinx.coroutines.launch

class PunchHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPunchHistoryBinding
    private lateinit var viewModel: PunchHistoryViewModel
    private lateinit var adapter: PunchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityPunchHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this)[PunchHistoryViewModel::class.java]

        // Configurar RecyclerView
        setupRecyclerView()

        // Observar estados
        observeViewModel()

        // Cargar fichajes
        viewModel.loadPunches()
    }

    private fun setupRecyclerView() {
        adapter = PunchAdapter()
        binding.rvPunches.apply {
            layoutManager = LinearLayoutManager(this@PunchHistoryActivity)
            adapter = this@PunchHistoryActivity.adapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.historyState.collect { state ->
                when (state) {
                    is PunchHistoryState.Idle -> {
                        hideLoading()
                        hideEmptyMessage()
                    }

                    is PunchHistoryState.Loading -> {
                        showLoading()
                        hideEmptyMessage()
                    }

                    is PunchHistoryState.Success -> {
                        hideLoading()

                        if (state.punches.isEmpty()) {
                            showEmptyMessage()
                        } else {
                            hideEmptyMessage()
                            adapter.submitList(state.punches)
                        }
                    }

                    is PunchHistoryState.Error -> {
                        hideLoading()
                        showEmptyMessage()

                        Toast.makeText(
                            this@PunchHistoryActivity,
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
        binding.rvPunches.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.rvPunches.visibility = View.VISIBLE
    }

    private fun showEmptyMessage() {
        binding.tvEmptyMessage.visibility = View.VISIBLE
        binding.rvPunches.visibility = View.GONE
    }

    private fun hideEmptyMessage() {
        binding.tvEmptyMessage.visibility = View.GONE
    }
}
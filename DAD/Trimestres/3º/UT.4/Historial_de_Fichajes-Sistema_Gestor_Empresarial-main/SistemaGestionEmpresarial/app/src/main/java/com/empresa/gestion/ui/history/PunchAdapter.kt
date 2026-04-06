package com.empresa.gestion.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empresa.gestion.R
import com.empresa.gestion.databinding.ItemPunchBinding
import com.empresa.gestion.domain.model.Punch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PunchAdapter : RecyclerView.Adapter<PunchAdapter.PunchViewHolder>() {

    private var punches: List<Punch> = emptyList()

    // Formato de fecha y hora
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    /**
     * Actualiza la lista de fichajes
     */
    fun submitList(newPunches: List<Punch>) {
        punches = newPunches
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PunchViewHolder {
        val binding = ItemPunchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PunchViewHolder, position: Int) {
        holder.bind(punches[position])
    }

    override fun getItemCount(): Int = punches.size

    inner class PunchViewHolder(
        private val binding: ItemPunchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(punch: Punch) {
            // Nombre del empleado (Confiamos en lo que venga de la API)
            binding.tvEmployeeName.text = punch.employeeName

            // Código del empleado
            binding.tvEmployeeCode.text = punch.employeeCode

            // Tipo de fichaje (ENTRADA o SALIDA)
            val typeText = if (punch.punchType == "IN") "ENTRADA" else "SALIDA"
            binding.tvPunchType.text = typeText

            // Color según el tipo
            if (punch.punchType == "IN") {
                // Verde para entrada
                binding.tvPunchType.setBackgroundColor(
                    binding.root.context.getColor(android.R.color.holo_green_light)
                )
                binding.ivPunchIcon.setImageResource(android.R.drawable.ic_menu_upload)
                binding.ivPunchIcon.setColorFilter(
                    binding.root.context.getColor(android.R.color.holo_green_dark)
                )
            } else {
                // Rojo para salida
                binding.tvPunchType.setBackgroundColor(
                    binding.root.context.getColor(android.R.color.holo_red_light)
                )
                binding.ivPunchIcon.setImageResource(android.R.drawable.ic_menu_revert)
                binding.ivPunchIcon.setColorFilter(
                    binding.root.context.getColor(android.R.color.holo_red_dark)
                )
            }

            // Fecha y hora (convertir timestamp a formato legible)
            val date = Date(punch.timestamp * 1000) // timestamp está en segundos
            binding.tvDateTime.text = dateFormat.format(date)

            // Notas (mostrar solo si hay)
            if (!punch.notes.isNullOrBlank()) {
                binding.tvNotes.visibility = View.VISIBLE
                binding.tvNotes.text = "Nota: ${punch.notes}"
            } else {
                binding.tvNotes.visibility = View.GONE
            }
        }
    }
}
package com.empresa.gestion.ui.employees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empresa.gestion.databinding.ItemEmployeeBinding
import com.empresa.gestion.domain.model.Employee

class EmployeeAdapter(
    private val onDeleteClick: (Employee) -> Unit
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    private var employees: List<Employee> = emptyList()

    /**
     * Actualiza la lista de empleados
     */
    fun submitList(newEmployees: List<Employee>) {
        employees = newEmployees
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ItemEmployeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employees[position])
    }

    override fun getItemCount(): Int = employees.size

    inner class EmployeeViewHolder(
        private val binding: ItemEmployeeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(employee: Employee) {
            // Nombre del empleado
            binding.tvEmployeeName.text = employee.name

            // Código del empleado
            binding.tvEmployeeCode.text = "Código: ${employee.employeeCode}"

            // Email
            binding.tvEmployeeEmail.text = employee.email

            // Rol
            val roleText = when (employee.role) {
                "admin" -> "ADMIN"
                "manager" -> "MANAGER"
                else -> "EMPLEADO"
            }
            binding.tvEmployeeRole.text = roleText

            // Color según el rol
            val roleColor = when (employee.role) {
                "admin" -> android.R.color.holo_red_light
                "manager" -> android.R.color.holo_orange_light
                else -> android.R.color.holo_blue_light
            }
            binding.tvEmployeeRole.setBackgroundColor(
                binding.root.context.getColor(roleColor)
            )

            // Icono según el rol
            val iconTint = when (employee.role) {
                "admin" -> android.R.color.holo_red_dark
                "manager" -> android.R.color.holo_orange_dark
                else -> android.R.color.holo_blue_dark
            }
            binding.ivUserIcon.setColorFilter(
                binding.root.context.getColor(iconTint)
            )

            // Botón eliminar
            binding.btnDelete.setOnClickListener {
                onDeleteClick(employee)
            }
        }
    }
}
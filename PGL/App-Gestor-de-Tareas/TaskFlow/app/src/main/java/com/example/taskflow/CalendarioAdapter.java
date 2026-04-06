package com.example.taskflow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;

public class CalendarioAdapter extends RecyclerView.Adapter<CalendarioAdapter.HoraViewHolder> {

    private final List<Tarea> listaTareasDelDia;
    private final OnItemClickListener listener;

    // Interfaz para comunicar acciones a la Activity
    public interface OnItemClickListener {
        void onEditClick(Tarea tarea);
        void onDeleteClick(Tarea tarea);
        void onDuplicateClick(Tarea tarea);
    }

    public CalendarioAdapter(List<Tarea> listaTareasDelDia, OnItemClickListener listener) {
        this.listaTareasDelDia = listaTareasDelDia;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HoraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendario_hora, parent, false);
        return new HoraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoraViewHolder holder, int position) {
        // 1. Formatear hora
        holder.tvHora.setText(formatearHora(position));

        // 2. Buscar si hay tarea en esta hora
        Tarea tareaEncontrada = null;
        boolean esHoraInicio = false;
        boolean esUltimaHora = false;

        for (Tarea t : listaTareasDelDia) {
            // Métodos de conversión de Tarea
            int horaInicio24 = t.getHoraInicio24();
            int horaFin24 = t.getHoraFin24();

            if (horaFin24 < horaInicio24) horaFin24 = 24;

            if (position >= horaInicio24 && position < horaFin24) {
                tareaEncontrada = t;
                esHoraInicio = (position == horaInicio24);
                esUltimaHora = (position == horaFin24 - 1);
                break;
            }
        }

        // 3. Mostrar/Ocultar tarjeta
        if (tareaEncontrada != null) {
            holder.cardTarea.setVisibility(View.VISIBLE);

            // Obtenemos los LayoutParams para modificar márgenes dinámicamente
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.cardTarea.getLayoutParams();
            final Tarea finalTarea = tareaEncontrada;

            if (esHoraInicio) {
                holder.layoutContenidoPrincipal.setVisibility(View.VISIBLE);

                // Márgenes
                params.topMargin = 4;
                params.bottomMargin = 0;

                // Estética
                holder.cardTarea.setRadius(16);
                holder.cardTarea.setCardElevation(0);

                // Ocultamos línea de fondo para fusionar con la siguiente
                holder.lineaSeparadora.setVisibility(View.GONE);

                // Datos
                holder.tvTitulo.setText(finalTarea.getTitulo());
                holder.tvUbi.setText(finalTarea.getUbicacion());
                holder.tvDesc.setText(generarTextoDescripcion(finalTarea));

                // Configurar lógica de botones y click
                configurarBotonesYExpansion(holder, finalTarea);

            } else {
                holder.layoutContenidoPrincipal.setVisibility(View.GONE);
                holder.layoutDetalles.setVisibility(View.GONE);

                // Limpiamos listeners antiguos
                holder.cardTarea.setOnClickListener(null);

                // Márgenes
                params.topMargin = 0;
                holder.cardTarea.setCardElevation(0);

                // Hacemos que esta parte "vacía" funcione igual que la principal
                holder.cardTarea.setOnClickListener(v -> {
                    finalTarea.setExpanded(!finalTarea.isExpanded());
                    notifyDataSetChanged();
                });

                if (esUltimaHora) {
                    params.bottomMargin = 4;
                    holder.cardTarea.setRadius(16);
                    holder.lineaSeparadora.setVisibility(View.VISIBLE);
                } else {
                    params.bottomMargin = 0;
                    holder.cardTarea.setRadius(0);
                    holder.lineaSeparadora.setVisibility(View.GONE);
                }
            }
            holder.cardTarea.setCardBackgroundColor(android.graphics.Color.WHITE);
            holder.cardTarea.setLayoutParams(params);
        } else {
            // Hora vacía
            holder.cardTarea.setVisibility(View.INVISIBLE);
            holder.cardTarea.setOnClickListener(null);
            holder.lineaSeparadora.setVisibility(View.VISIBLE);
        }
    }

    // Genera el texto de descripción con formato adecuado
    private String generarTextoDescripcion(Tarea tarea) {
        String minIn = tarea.getMinInicio() < 10 ? "0" + tarea.getMinInicio() : String.valueOf(tarea.getMinInicio());
        String minOut = tarea.getMinFin() < 10 ? "0" + tarea.getMinFin() : String.valueOf(tarea.getMinFin());
        String rangoHoras = tarea.getHoraInicio() + ":" + minIn + " " + tarea.getAmPmInicio() +
                " - " +
                tarea.getHoraFin() + ":" + minOut + " " + tarea.getAmPmFin();

        if (tarea.getDescripcion() != null && !tarea.getDescripcion().isEmpty()) {
            return rangoHoras + "\n\n" + tarea.getDescripcion();
        }
        return rangoHoras;
    }

    private void configurarBotonesYExpansion(HoraViewHolder holder, Tarea tarea) {
        // Controlar Expansión
        boolean isExpanded = tarea.isExpanded();
        holder.layoutDetalles.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.imgArrow.setImageResource(isExpanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down);

        // Click en tarjeta (Expansión)
        holder.cardTarea.setOnClickListener(v -> {
            tarea.setExpanded(!tarea.isExpanded());
            notifyDataSetChanged();
        });

        // Botones
        holder.btnEditar.setOnClickListener(v -> { if (listener != null) listener.onEditClick(tarea); });
        holder.btnCompartir.setOnClickListener(v -> compartirTarea(v.getContext(), tarea));
        holder.btnMenu.setOnClickListener(v -> mostrarMenuOpciones(v, tarea));
    }

    // Para no repetir código y compatir la tarea
    private void compartirTarea(Context context, Tarea tarea) {
        String asunto = "Tarea: " + tarea.getTitulo();
        String mensaje = "📅 Fecha: " + tarea.getFechaHora() + "\n" +
                "📝 Nota: " + tarea.getDescripcion() + "\n" +
                "📍 Lugar: " + tarea.getUbicacion();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        context.startActivity(Intent.createChooser(intent, "Compartir tarea..."));
    }

    // Menú de opciones
    private void mostrarMenuOpciones(View v, Tarea tarea) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.getMenu().add("Duplicar");
        popup.getMenu().add("Eliminar");
        popup.setOnMenuItemClickListener(item -> {
            if (listener == null) return false;
            if (Objects.equals(item.getTitle(), "Duplicar")) { listener.onDuplicateClick(tarea); return true; }
            else if (Objects.equals(item.getTitle(), "Eliminar")) { listener.onDeleteClick(tarea); return true; }
            return false;
        });
        popup.show();
    }

    @Override
    public int getItemCount() { return 24; }

    private String formatearHora(int hora) {
        if (hora == 0) return "12 AM";
        if (hora < 12) return hora + " AM";
        if (hora == 12) return "12 PM";
        return (hora - 12) + " PM";
    }

    public static class HoraViewHolder extends RecyclerView.ViewHolder {
        TextView tvHora, tvTitulo, tvDesc, tvUbi;
        CardView cardTarea;
        ImageView imgArrow, btnEditar, btnCompartir, btnMenu;
        LinearLayout layoutDetalles, layoutContenidoPrincipal;
        View lineaSeparadora;

        public HoraViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHora = itemView.findViewById(R.id.tvHoraSlot);
            tvTitulo = itemView.findViewById(R.id.tvTituloTareaSlot);
            tvDesc = itemView.findViewById(R.id.tvDescSlot);
            tvUbi = itemView.findViewById(R.id.tvUbiSlot);
            cardTarea = itemView.findViewById(R.id.cardTareaSlot);
            imgArrow = itemView.findViewById(R.id.imgArrowSlot);
            layoutDetalles = itemView.findViewById(R.id.layoutDetallesSlot);
            layoutContenidoPrincipal = itemView.findViewById(R.id.layoutContenedorInfo);
            lineaSeparadora = itemView.findViewById(R.id.lineaSeparadoraFondo);

            // Botones del slot
            btnEditar = itemView.findViewById(R.id.btnEditarSlot);
            btnCompartir = itemView.findViewById(R.id.btnCompartirSlot);
            btnMenu = itemView.findViewById(R.id.btnMenuSlot);
        }
    }
}
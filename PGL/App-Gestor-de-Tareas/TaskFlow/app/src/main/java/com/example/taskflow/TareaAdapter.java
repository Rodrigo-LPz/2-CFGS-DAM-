package com.example.taskflow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {

    private final List<Tarea> listaTareas;
    private final OnItemClickListener listener;
    private MediaPlayer mediaPlayer; // Reproductor de sonido

    // === INTERFAZ CORRECTA (Usa objetos Tarea para evitar errores de índice) ===
    public interface OnItemClickListener {
        void onEditClick(Tarea tarea);
        void onDeleteClick(Tarea tarea);
        void onDuplicateClick(Tarea tarea);

        void onShareClick(Tarea t);
    }

    public TareaAdapter(List<Tarea> listaTareas, OnItemClickListener listener) {
        this.listaTareas = listaTareas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Asegúrate de que item_tarea.xml tiene la estructura completa (oculta y visible)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = listaTareas.get(position);

        // 1. RELLENAR DATOS
        holder.tvTitulo.setText(tarea.getTitulo());
        holder.tvFecha.setText(tarea.getFechaHora());
        holder.tvDescripcion.setText(tarea.getDescripcion());
        holder.tvUbicacion.setText(tarea.getUbicacion());

        // Listener del checkbox
        holder.checkboxCompletar.setOnCheckedChangeListener(null); // Evitar llamadas duplicadas
        holder.checkboxCompletar.setChecked(tarea.isCompletada()); // Sincronizar estado
        holder.checkboxCompletar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tarea.setCompletada(isChecked);

            if (isChecked) {
                // ¡Tarea completada! Reproducir animación y sonido
                reproducirAnimacionCompletado(holder, tarea);
                reproducirSonidoExito(holder.itemView.getContext());

                // Aplicar tachado
                holder.tvTitulo.setPaintFlags(holder.tvTitulo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvTitulo.setAlpha(0.6f);
            } else {
                // Tarea desmarcada, quitar tachado
                reproducirSonidoDesexito(holder.itemView.getContext());
                holder.tvTitulo.setPaintFlags(holder.tvTitulo.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.tvTitulo.setAlpha(1.0f);
            }
        });

        // 2. CONTROLAR EXPANSIÓN (ACORDEÓN)
        boolean isExpanded = tarea.isExpanded();
        holder.layoutDetallesHidden.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // Cambiar flecha visualmente
        if (isExpanded) {
            holder.imgArrowIcon.setImageResource(R.drawable.ic_arrow_up);
        } else {
            holder.imgArrowIcon.setImageResource(R.drawable.ic_arrow_down);
        }

        // Listener para expandir/contraer al tocar la tarjeta
        View.OnClickListener expandListener = v -> {
            // 1. Cambiamos el estado del dato
            tarea.setExpanded(!tarea.isExpanded());

            // 2. IMPORTANTE: Notificamos al adaptador que esta posición cambió
            // Esto fuerza a que se vuelva a ejecutar onBindViewHolder para esta fila
            notifyItemChanged(holder.getBindingAdapterPosition());
        };

        holder.itemView.setOnClickListener(expandListener);
        holder.cardArrow.setOnClickListener(expandListener);


        // 3. BOTÓN EDITAR
        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(tarea);
        });

        // 4. BOTÓN COMPARTIR (Intent al sistema)
        holder.btnCompartir.setOnClickListener(v -> {
            String asunto = "Tarea: " + tarea.getTitulo();
            String mensaje = "📅 Fecha: " + tarea.getFechaHora() + "\n" +
                    "📝 Nota: " + tarea.getDescripcion() + "\n" +
                    "📍 Lugar: " + tarea.getUbicacion();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
            intent.putExtra(Intent.EXTRA_TEXT, mensaje);

            // Iniciar selector de apps (Gmail, WhatsApp...)
            v.getContext().startActivity(Intent.createChooser(intent, "Compartir tarea..."));
        });

        // 5. BOTÓN MENÚ (3 PUNTOS) -> BORRAR / DUPLICAR
        holder.btnMenuOpciones.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), holder.btnMenuOpciones);
            popup.getMenu().add("Duplicar");
            popup.getMenu().add("Eliminar");

            popup.setOnMenuItemClickListener(item -> {
                if (listener == null) return false;

                if (Objects.equals(item.getTitle(), "Duplicar")) {
                    listener.onDuplicateClick(tarea);
                    return true;
                } else if (Objects.equals(item.getTitle(), "Eliminar")) {
                    listener.onDeleteClick(tarea);
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    /**
     * Reproduce una animación visual cuando se completa una tarea
     * Combina: escala, rotación y fade para dar feedback visual satisfactorio
     */
    private void reproducirAnimacionCompletado(TareaViewHolder holder, Tarea tarea) {
        // Animación del checkbox: Escala con efecto "bounce"
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(holder.checkboxCompletar, "scaleX", 1f, 1.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(holder.checkboxCompletar, "scaleY", 1f, 1.3f, 1f);

        AnimatorSet checkboxAnim = new AnimatorSet();
        checkboxAnim.playTogether(scaleX, scaleY);
        checkboxAnim.setDuration(400);
        checkboxAnim.setInterpolator(new OvershootInterpolator());

        // Animación de la tarjeta completa: Brillo y escala sutil
        ObjectAnimator cardScale = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1f, 1.02f, 1f);
        ObjectAnimator cardScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 1f, 1.02f, 1f);
        ObjectAnimator cardAlpha = ObjectAnimator.ofFloat(holder.itemView, "alpha", 1f, 0.8f, 1f);

        AnimatorSet cardAnim = new AnimatorSet();
        cardAnim.playTogether(cardScale, cardScaleY, cardAlpha);
        cardAnim.setDuration(500);
        cardAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        // Ejecutar ambas animaciones
        checkboxAnim.start();
        cardAnim.start();

        // Animación adicional del icono de check (si existe)
        ObjectAnimator rotation = ObjectAnimator.ofFloat(holder.checkboxCompletar, "rotation", 0f, 360f);
        rotation.setDuration(600);
        rotation.setStartDelay(100);
        rotation.start();
    }

    /**
     * Reproduce un sonido de éxito al completar una tarea
     * El archivo de sonido debe estar en la ruta: "res/raw/task_completed.wav"
     */
    private void reproducirSonidoExito(android.content.Context context) {
        try {
            // Liberar MediaPlayer anterior si existe
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            // Crear nuevo MediaPlayer con el sonido de éxito
            mediaPlayer = MediaPlayer.create(context, R.raw.task_completed);

            if (mediaPlayer != null) {
                mediaPlayer.setOnCompletionListener(mp -> {
                    mp.release(); // Liberar recursos cuando termine
                });
                mediaPlayer.start();
            }
        } catch (Exception e) {
            // Si no se encuentra el archivo de sonido, no hacer nada
            // (La animación visual seguirá funcionando)
            e.printStackTrace();
        }
    }

    /**
     * Reproduce un sonido de "deshacer" al desmarcar una tarea
     * El archivo debe estar en "res/raw/task_uncompleted.wav"
     */
    private void reproducirSonidoDesexito(android.content.Context context) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(context, R.raw.task_uncompleted); // Sonido de "deshacer"
            if (mediaPlayer != null) {
                mediaPlayer.setOnCompletionListener(mp -> mp.release());
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    /**
     * Liberar recursos del MediaPlayer cuando el adaptador sea destruido
     */
    public void liberarRecursos() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // === VIEWHOLDER: CONECTA CON EL XML ===
    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha, tvDescripcion, tvUbicacion;
        LinearLayout layoutDetallesHidden;
        View cardArrow;
        ImageView imgArrowIcon, btnEditar, btnCompartir, btnMenuOpciones;
        CheckBox checkboxCompletar; // Checkbox para marcar completada

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTareaTitulo);
            tvFecha = itemView.findViewById(R.id.tvTareaFecha);

            // Parte oculta
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionBody);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacionBody);
            layoutDetallesHidden = itemView.findViewById(R.id.layoutDetallesHidden);

            // Botones e iconos
            cardArrow = itemView.findViewById(R.id.cardArrow);
            imgArrowIcon = itemView.findViewById(R.id.imgArrowIcon);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnCompartir = itemView.findViewById(R.id.btnCompartir);
            btnMenuOpciones = itemView.findViewById(R.id.btnMenuOpciones);

            // Checkbox
            checkboxCompletar = itemView.findViewById(R.id.checkboxCompletar);
        }
    }
}

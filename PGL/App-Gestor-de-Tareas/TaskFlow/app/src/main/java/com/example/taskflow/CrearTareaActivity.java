package com.example.taskflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.util.Log;

public class CrearTareaActivity extends AppCompatActivity {

    // Vistas principales
    private EditText etNombre, etDesc, etUbicacion;
    private TextView tvTituloPantalla;

    // Spinners de Fecha
    private Spinner spDiaInicio, spMesInicio, spAnoInicio, spHoraInicio, spMinInicio, spAmPmInicio;
    private Spinner spDiaFin, spMesFin, spAnoFin, spHoraFin, spMinFin, spAmPmFin;

    // Contenedores
    private LinearLayout containerNotificaciones, containerColaboradores;

    // Variables de control
    private int posicionOriginal = -1;
    private final String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
    private final String[] anos = {"2025", "2026", "2027", "2028", "2029", "2030"};
    //private final String[] mins = {"00", "15", "30", "45"};
    private final String[] ampm = {"AM", "PM"};

    private final String[] unidadesTiempo = {"Minutos antes", "Horas antes", "Días antes", "Segundos antes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        vincularVistas();
        configurarSpinners(); // Aquí rellenamos los datos
        configurarListeners();

        // Modo Edición
        if (getIntent().hasExtra("TAREA_A_EDITAR")) {
            Tarea tarea = (Tarea) getIntent().getSerializableExtra("TAREA_A_EDITAR");
            posicionOriginal = getIntent().getIntExtra("POSICION_ORIGINAL", -1);
            if (tarea != null) cargarDatosTarea(tarea);
        } else {
            // Si no es edición carga la fecha actual
            cargarFechaHoraActual();
        }

        findViewById(R.id.btnGuardar).setOnClickListener(v -> guardarTarea());
    }

    // --- 1. VINCULAR VISTAS ---
    private void vincularVistas() {
        tvTituloPantalla = findViewById(R.id.tvTituloPantalla);
        etNombre = findViewById(R.id.etNombre);
        etDesc = findViewById(R.id.etDesc);
        etUbicacion = findViewById(R.id.etUbicacion);

        spDiaInicio = findViewById(R.id.spDiaInicio);
        spMesInicio = findViewById(R.id.spMesInicio);
        spAnoInicio = findViewById(R.id.spAnoInicio);
        spHoraInicio = findViewById(R.id.spHoraInicio);
        spMinInicio = findViewById(R.id.spMinInicio);
        spAmPmInicio = findViewById(R.id.spAmPmInicio);

        spDiaFin = findViewById(R.id.spDiaFin);
        spMesFin = findViewById(R.id.spMesFin);
        spAnoFin = findViewById(R.id.spAnoFin);
        spHoraFin = findViewById(R.id.spHoraFin);
        spMinFin = findViewById(R.id.spMinFin);
        spAmPmFin = findViewById(R.id.spAmPmFin);

        containerNotificaciones = findViewById(R.id.containerNotificaciones);
        containerColaboradores = findViewById(R.id.containerColaboradores);
    }

    // Método mejorado para generar números (ej: 0..59 o 1..12)
    private String[] generarNumeros(int inicio, int fin) {
        List<String> l = new ArrayList<>();
        for (int k = inicio; k <= fin; k++) {
            l.add(k < 10 ? "0" + k : String.valueOf(k));
        }
        return l.toArray(new String[0]);
    }

    // --- 2. CONFIGURAR SPINNERS ---
    private void configurarSpinners() {
        // Fechas
        setupSpinner(spMesInicio, meses);
        setupSpinner(spAnoInicio, anos);
        setupSpinner(spHoraInicio, generarNumeros(1, 12));
        setupSpinner(spMinInicio, generarNumeros(0, 59));
        setupSpinner(spAmPmInicio, ampm);
        setupSpinner(spMesFin, meses);
        setupSpinner(spAnoFin, anos);
        setupSpinner(spHoraFin, generarNumeros(1, 12));
        setupSpinner(spMinFin, generarNumeros(0, 59));
        setupSpinner(spAmPmFin, ampm);

        // --- ARREGLO DE NOTIFICACIÓN ---
        // Buscamos la primera fila que ya existe en el XML (la que tiene el include)
        if (containerNotificaciones.getChildCount() > 0) {
            View primeraFila = containerNotificaciones.getChildAt(0);
            Spinner spUnidad = primeraFila.findViewById(R.id.spUnidad);
            // ¡Rellenamos el Spinner inmediatamente!
            if (spUnidad != null) {
                setupSpinner(spUnidad, unidadesTiempo);
            }
        }

        actualizarDiasDelMes(spMesInicio, spAnoInicio, spDiaInicio);
        actualizarDiasDelMes(spMesFin, spAnoFin, spDiaFin);
    }

    // --- 3. LISTENERS ---
    private void configurarListeners() {
        configurarListenerFechas(spMesInicio, spAnoInicio, spDiaInicio);
        configurarListenerFechas(spMesFin, spAnoFin, spDiaFin);

        // Al pulsar + Notificación
        findViewById(R.id.btnAddNotif).setOnClickListener(v -> agregarFila(R.layout.item_notificacion, containerNotificaciones));

        // Al pulsar + Colaborador
        findViewById(R.id.btnAddColab).setOnClickListener(v -> agregarFila(R.layout.item_colaborador, containerColaboradores));
    }

    // Método genérico para añadir filas dinámicas
    private void agregarFila(int layoutId, LinearLayout container) {
        View view = getLayoutInflater().inflate(layoutId, container, false);

        // Si estamos añadiendo una notificación, hay que rellenar su spinner también
        if (layoutId == R.layout.item_notificacion) {
            Spinner spUnidad = view.findViewById(R.id.spUnidad);
            setupSpinner(spUnidad, unidadesTiempo);
        }

        container.addView(view);
    }

    // --- GUARDAR ---
    private void guardarTarea() {
        String nombre = etNombre.getText().toString();
        if (nombre.isEmpty()) nombre = "Nueva Tarea";
        String desc = etDesc.getText().toString();
        String ubi = etUbicacion.getText().toString();

        int dia = 1, mes = 0, anio = 2025, horaIn = 0, minIn = 0, horaOut = 0, minOut = 0;
        String apIn = "AM", apOut = "AM";
        String notifCant = "30", notifUni = "Minutos antes";

        try {
            String diaStrSpinner = spDiaInicio.getSelectedItem().toString(); // "05", "10", etc.
            dia = Integer.parseInt(diaStrSpinner);
            mes = spMesInicio.getSelectedItemPosition();
            anio = Integer.parseInt(spAnoInicio.getSelectedItem().toString());
            horaIn = Integer.parseInt(spHoraInicio.getSelectedItem().toString());
            minIn = Integer.parseInt(spMinInicio.getSelectedItem().toString());
            apIn = spAmPmInicio.getSelectedItem().toString();
            horaOut = Integer.parseInt(spHoraFin.getSelectedItem().toString());
            minOut = Integer.parseInt(spMinFin.getSelectedItem().toString());
            apOut = spAmPmFin.getSelectedItem().toString();

            // Recoger datos de la primera notificación
            if (containerNotificaciones.getChildCount() > 0) {
                View fila = containerNotificaciones.getChildAt(0);
                EditText etCant = fila.findViewById(R.id.etCantidad);
                Spinner spUni = fila.findViewById(R.id.spUnidad);
                notifCant = etCant.getText().toString();
                notifUni = spUni.getSelectedItem().toString();
            }
        } catch (Exception e) {
            Log.e("CrearTarea", "Error al parsear datos de los spinners", e);
        }

        String fechaStr = dia + " " + meses[mes] + " " + anio + " · " +
                horaIn + ":" + (minIn < 10 ? "0" + minIn : minIn) + " " + apIn + " - " +
                horaOut + ":" + (minOut < 10 ? "0" + minOut : minOut) + " " + apOut;

        Tarea nuevaTarea = new Tarea(nombre, fechaStr, desc, ubi, dia, mes, anio,
                horaIn, minIn, apIn, horaOut, minOut, apOut,
                notifCant, notifUni);

        if (posicionOriginal != -1) {
            // Actualizar la lista global en la posición específica
            if (posicionOriginal >= 0 && posicionOriginal < Repositorio.tareasGlobales.size()) {
                Repositorio.tareasGlobales.set(posicionOriginal, nuevaTarea);
            }
        } else {
            Repositorio.tareasGlobales.add(nuevaTarea);
        }

        Intent resultado = new Intent();
        resultado.putExtra("TAREA_OBJETO", nuevaTarea);
        resultado.putExtra("POSICION_EDITADA", posicionOriginal);
        setResult(Activity.RESULT_OK, resultado);
        finish();
    }

    // --- CARGAR DATOS ---
    private void cargarDatosTarea(Tarea t) {
        tvTituloPantalla.setText("EDITAR TAREA");
        etNombre.setText(t.getTitulo());
        etDesc.setText(t.getDescripcion());
        etUbicacion.setText(t.getUbicacion());

        seleccionarSpinner(spMesInicio, meses[t.getMes()]);
        seleccionarSpinner(spAnoInicio, String.valueOf(t.getAnio()));
        seleccionarSpinner(spHoraInicio, t.getHoraInicio() < 10 ? "0" + t.getHoraInicio() : String.valueOf(t.getHoraInicio()));
        seleccionarSpinner(spMinInicio, t.getMinInicio() < 10 ? "0" + t.getMinInicio() : String.valueOf(t.getMinInicio()));
        seleccionarSpinner(spAmPmInicio, t.getAmPmInicio());

        seleccionarSpinner(spHoraFin, t.getHoraFin() < 10 ? "0" + t.getHoraFin() : String.valueOf(t.getHoraFin()));
        seleccionarSpinner(spMinFin, t.getMinFin() < 10 ? "0" + t.getMinFin() : String.valueOf(t.getMinFin()));
        seleccionarSpinner(spAmPmFin, t.getAmPmFin());

        // Rellenar Notificación
        if (containerNotificaciones.getChildCount() > 0) {
            View fila = containerNotificaciones.getChildAt(0);
            ((EditText) fila.findViewById(R.id.etCantidad)).setText(t.getNotifCantidad());
            seleccionarSpinner(fila.findViewById(R.id.spUnidad), t.getNotifUnidad());
        }

        spDiaInicio.post(() -> seleccionarSpinner(spDiaInicio, t.getDia() < 10 ? "0" + t.getDia() : String.valueOf(t.getDia())));
        spDiaFin.post(() -> seleccionarSpinner(spDiaFin, t.getDia() < 10 ? "0" + t.getDia() : String.valueOf(t.getDia())));
    }

    private void cargarFechaHoraActual() {
        Calendar hoy = Calendar.getInstance();

        // 1. Obtener datos actuales
        int dia = hoy.get(Calendar.DAY_OF_MONTH);
        int mes = hoy.get(Calendar.MONTH);
        int anio = hoy.get(Calendar.YEAR);

        int hora24 = hoy.get(Calendar.HOUR_OF_DAY);
        int minuto = hoy.get(Calendar.MINUTE);

        // Convertir 24h a 12h + AM/PM
        String amPmStr = (hora24 >= 12) ? "PM" : "AM";
        int hora12 = (hora24 > 12) ? hora24 - 12 : hora24;
        if (hora12 == 0) hora12 = 12; // Las 00:00 son las 12 AM

        // 2. Seleccionar en los Spinners de INICIO
        seleccionarSpinner(spMesInicio, meses[mes]);
        seleccionarSpinner(spAnoInicio, String.valueOf(anio));

        // El día necesita actualizarse tras cambiar mes/año
        spDiaInicio.post(() -> seleccionarSpinner(spDiaInicio, dia < 10 ? "0" + dia : String.valueOf(dia)));

        seleccionarSpinner(spHoraInicio, hora12 < 10 ? "0" + hora12 : String.valueOf(hora12));
        seleccionarSpinner(spMinInicio, minuto < 10 ? "0" + minuto : String.valueOf(minuto));
        seleccionarSpinner(spAmPmInicio, amPmStr);

        // 3. Seleccionar en los Spinners de FIN (Por defecto, ponemos 1 hora después)
        Calendar fin = (Calendar) hoy.clone();
        fin.add(Calendar.HOUR_OF_DAY, 1);

        int diaFin = fin.get(Calendar.DAY_OF_MONTH);
        int mesFin = fin.get(Calendar.MONTH);
        int anioFin = fin.get(Calendar.YEAR);
        int horaFin24 = fin.get(Calendar.HOUR_OF_DAY);
        int minFin = fin.get(Calendar.MINUTE);

        String amPmFinStr = (horaFin24 >= 12) ? "PM" : "AM";
        int horaFin12 = (horaFin24 > 12) ? horaFin24 - 12 : horaFin24;
        if (horaFin12 == 0) horaFin12 = 12;

        seleccionarSpinner(spMesFin, meses[mesFin]);
        seleccionarSpinner(spAnoFin, String.valueOf(anioFin));

        spDiaFin.post(() -> seleccionarSpinner(spDiaFin, diaFin < 10 ? "0" + diaFin : String.valueOf(diaFin)));

        seleccionarSpinner(spHoraFin, horaFin12 < 10 ? "0" + horaFin12 : String.valueOf(horaFin12));
        seleccionarSpinner(spMinFin, minFin < 10 ? "0" + minFin : String.valueOf(minFin));
        seleccionarSpinner(spAmPmFin, amPmFinStr);
    }

    // --- HELPERS ---
    private void configurarListenerFechas(Spinner spMes, Spinner spAno, Spinner spDiaDestino) {
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                actualizarDiasDelMes(spMes, spAno, spDiaDestino);
            }

            @Override
            public void onNothingSelected(AdapterView<?> p) {
            }
        };
        spMes.setOnItemSelectedListener(listener);
        spAno.setOnItemSelectedListener(listener);
    }

    private void actualizarDiasDelMes(Spinner spMes, Spinner spAno, Spinner spDia) {
        int mesIndex = spMes.getSelectedItemPosition();
        int anio = Integer.parseInt(spAno.getSelectedItem().toString());
        int dias = 31;
        if (mesIndex == 3 || mesIndex == 5 || mesIndex == 8 || mesIndex == 10) dias = 30;
        else if (mesIndex == 1)
            dias = ((anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0)) ? 29 : 28;

        String[] diasArr = generarNumeros(dias);
        int prev = spDia.getSelectedItemPosition();
        setupSpinner(spDia, diasArr);
        spDia.setSelection(Math.min(prev, diasArr.length - 1));
    }

    private String[] generarNumeros(int f) {
        List<String> l = new ArrayList<>();
        for (int k = 1; k <= f; k++) l.add(k < 10 ? "0" + k : String.valueOf(k));
        return l.toArray(new String[0]);
    }

    private void setupSpinner(Spinner s, String[] d) {
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, d);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(a);
    }

    // Suprimimos la advertencia porque sabemos que el adapter es <String>
    @SuppressWarnings("unchecked")
    private void seleccionarSpinner(Spinner s, String v) {
        // Obtenemos el adaptador genérico
        android.widget.SpinnerAdapter adapter = s.getAdapter();

        // Verificamos si es instancia de ArrayAdapter antes de castear
        if (adapter instanceof ArrayAdapter) {
            ArrayAdapter<String> a = (ArrayAdapter<String>) adapter;

            for (int i = 0; i < a.getCount(); i++) {
                String item = a.getItem(i);
                if (item != null && item.equals(v)) {
                    s.setSelection(i);
                    break;
                }
            }
        }
    }
}
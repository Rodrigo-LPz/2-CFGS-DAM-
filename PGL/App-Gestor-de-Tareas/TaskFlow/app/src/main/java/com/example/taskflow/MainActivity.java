package com.example.taskflow;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private TareaAdapter adapterHoy;
    private List<Tarea> listaHoy;
    private RecyclerView rvTareasManana;
    private TareaAdapter adapterManana;
    private List<Tarea> listaManana;
    private TextView tvTituloManana;

    // --- VARIABLES BLUETOOTH (Paso 2 y 3) ---
    private BluetoothAdapter bluetoothAdapter;
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    ActivityResultLauncher<Intent> launcherCrearTarea = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    cargarListasSeparadas();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. INICIALIZAR BLUETOOTH
        configurarBluetooth();

        // 2. VINCULAR VISTAS Y ADAPTADORES
        RecyclerView rvTareasHoy = findViewById(R.id.rvTareasHoy);
        rvTareasHoy.setLayoutManager(new LinearLayoutManager(this));
        listaHoy = new ArrayList<>();
        adapterHoy = new TareaAdapter(listaHoy, crearListener());
        rvTareasHoy.setAdapter(adapterHoy);

        rvTareasManana = findViewById(R.id.rvTareasManana);
        rvTareasManana.setLayoutManager(new LinearLayoutManager(this));
        listaManana = new ArrayList<>();
        adapterManana = new TareaAdapter(listaManana, crearListener());
        rvTareasManana.setAdapter(adapterManana);

        tvTituloManana = findViewById(R.id.tvTituloManana);

        // 3. DATOS INICIALES
        if (Repositorio.tareasGlobales.isEmpty()) {
            generarDatosPrueba();
        }

        cargarListasSeparadas();

        // 4. BOTONES Y NAVEGACIÓN
        Button btnAnadir = findViewById(R.id.btnAnadir);
        btnAnadir.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CrearTareaActivity.class);
            intent.putExtra("POSICION_ORIGINAL", -1);
            launcherCrearTarea.launch(intent);
        });

        View btnCal = findViewById(R.id.btnCalendario);
        if(btnCal != null) btnCal.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CalendarioActivity.class)));

        FloatingActionButton fab = findViewById(R.id.fabExpand);
        if(fab != null) {
            fab.setOnClickListener(v -> {
                int visibility = (rvTareasManana.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE;
                rvTareasManana.setVisibility(visibility);
                tvTituloManana.setVisibility(visibility);
            });
        }
    }

    // --- LÓGICA DE BLUETOOTH ---

    private void configurarBluetooth() {
        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth no disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 101);
        }
    }

    public void compartirTarea(Tarea t) {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Activa el Bluetooth para compartir", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.isEmpty()) {
            Toast.makeText(this, "Vincula un dispositivo primero", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> nombres = new ArrayList<>();
        List<BluetoothDevice> dispositivos = new ArrayList<>();
        for (BluetoothDevice d : pairedDevices) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            nombres.add(d.getName());
            dispositivos.add(d);
        }

        new AlertDialog.Builder(this)
                .setTitle("Enviar tarea a...")
                .setItems(nombres.toArray(new String[0]), (dialog, which) -> {
                    new ClientThread(dispositivos.get(which), t).start();
                }).show();
    }

    // Hilo de envío (Paso 3)
    private class ClientThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final Tarea tareaAEnviar;

        public ClientThread(BluetoothDevice device, Tarea tarea) {
            BluetoothSocket tmp = null;
            tareaAEnviar = tarea;
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { Log.e("BT", "Error socket", e); }
            mmSocket = tmp;
        }

        public void run() {
            try {
                mmSocket.connect();
                ObjectOutputStream oos = new ObjectOutputStream(mmSocket.getOutputStream());
                oos.writeObject(tareaAEnviar);
                oos.flush();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Tarea enviada con éxito", Toast.LENGTH_SHORT).show());
                mmSocket.close();
            } catch (IOException e) {
                Log.e("BT", "Error conexión", e);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error al conectar", Toast.LENGTH_SHORT).show());
            }
        }
    }

    // --- GESTIÓN DE TAREAS ---

    private void cargarListasSeparadas() {
        listaHoy.clear();
        listaManana.clear();
        Calendar cal = Calendar.getInstance();
        int d = cal.get(Calendar.DAY_OF_MONTH);
        int m = cal.get(Calendar.MONTH);
        int a = cal.get(Calendar.YEAR);

        for (Tarea t : Repositorio.tareasGlobales) {
            if (t.getDia() == d && t.getMes() == m && t.getAnio() == a) {
                listaHoy.add(t);
            } else {
                listaManana.add(t);
            }
        }
        adapterHoy.notifyDataSetChanged();
        adapterManana.notifyDataSetChanged();
    }

    private TareaAdapter.OnItemClickListener crearListener() {
        return new TareaAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Tarea t) {
                int index = Repositorio.tareasGlobales.indexOf(t);
                Intent intent = new Intent(MainActivity.this, CrearTareaActivity.class);
                intent.putExtra("TAREA_A_EDITAR", t);
                intent.putExtra("POSICION_ORIGINAL", index);
                launcherCrearTarea.launch(intent);
            }

            @Override
            public void onDeleteClick(Tarea t) {
                Repositorio.tareasGlobales.remove(t);
                cargarListasSeparadas();
                Toast.makeText(MainActivity.this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDuplicateClick(Tarea t) {
                Tarea copia = new Tarea(t.getTitulo() + " (Copia)", t.getFechaHora(), t.getDescripcion(), t.getUbicacion(),
                        t.getDia(), t.getMes(), t.getAnio(), t.getHoraInicio(), t.getMinInicio(), t.getAmPmInicio(),
                        t.getHoraFin(), t.getMinFin(), t.getAmPmFin(), t.getNotifCantidad(), t.getNotifUnidad());
                Repositorio.tareasGlobales.add(copia);
                cargarListasSeparadas();
            }

            @Override
            public void onShareClick(Tarea t) {
                compartirTarea(t);
            }
        };
    }

    private void generarDatosPrueba() {
        Calendar hoy = Calendar.getInstance();
        int d = hoy.get(Calendar.DAY_OF_MONTH);
        int m = hoy.get(Calendar.MONTH);
        int a = hoy.get(Calendar.YEAR);
        String fechaStr = d + " Ene " + a + " · 11:00 AM";
        Repositorio.tareasGlobales.add(new Tarea("Bombardear la ULPGC", fechaStr, "...", "Las Palmas", d, m, a, 11, 0, "AM", 12, 0, "PM", "30", "Min"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarListasSeparadas();
    }
}
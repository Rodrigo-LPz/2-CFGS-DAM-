package com.example.taskflow;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    // Esta lista es 'static', lo que significa que es la "memoria compartida"
    // accesible desde cualquier pantalla de la aplicación.
    public static List<Tarea> tareasGlobales = new ArrayList<>();
}
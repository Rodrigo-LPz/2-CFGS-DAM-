package com.empresa.gestion.utils

object Constants {

    // ============================================
    // CONFIGURACIÓN DE LA API
    // ============================================

    // Para EMULADOR de Android Studio
    const val BASE_URL = "http://10.0.2.2:8001/"

    // Para DISPOSITIVO FÍSICO conectado a la misma red WiFi
    // Descomenta la siguiente línea y pon la IP de tu ordenador
    // const val BASE_URL = "http://192.168.1.XXX:8001/"
    // Para encontrar tu IP:
    // - Windows: ipconfig (busca IPv4)
    // - Mac/Linux: ifconfig (busca inet)

    // Tiempo máximo de espera para las peticiones HTTP
    const val TIMEOUT_SECONDS = 30L

    // ============================================
    // SHARED PREFERENCES (para guardar datos)
    // ============================================

    const val PREFS_NAME = "GestionEmpresaPrefs"
    const val KEY_EMPLOYEE_ID = "employee_id"
    const val KEY_EMPLOYEE_CODE = "employee_code"
    const val KEY_EMPLOYEE_NAME = "employee_name"
    const val KEY_EMPLOYEE_ROLE = "employee_role"
    const val KEY_IS_LOGGED_IN = "is_logged_in"

    // ============================================
    // CÓDIGOS DE RESPUESTA
    // ============================================

    const val HTTP_OK = 200
    const val HTTP_CREATED = 201
    const val HTTP_BAD_REQUEST = 400
    const val HTTP_UNAUTHORIZED = 401
    const val HTTP_NOT_FOUND = 404
    const val HTTP_SERVER_ERROR = 500
}
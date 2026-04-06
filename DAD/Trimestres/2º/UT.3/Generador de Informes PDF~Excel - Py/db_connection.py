# Conexión a la DB (base de datos).
    # Conexiñon reutilizable y segura con MySQL.
import mysql.connector              # Importa el conector de MySQL.

from mysql.connector import Error   # Importa la clase "Error" para manejar excepciones.



host_name = "localhost"     # Dirección/URL del servidor de la base de datos.
db_name = "HOSPITAL"        # Nombre de la base de datos a la que se desea conectar.
user_name = "root"          # Nombre de usuario para la base de datos.
user_password = "root"      # Contraseña del usuario de la base de datos.

# Función/Método para crear una conexión a la base de datos MySQL.
    # La función espera los parámetos de conexión (host_name, user_name, user_password, db_name) y devuelve el objeto de conexión (connection).
def connection(host_name, db_name, user_name, user_password):
    print("\n\n\tConectándose a la base de datos MySQL...")

    # Inicializa la variable de conexión ("connection") con valor nulo ("None"). Para luego asignarle el objeto de conexión si la conexión es exitosa.
    connection = None

    # Al igual que en AED, utilizamos un bloque de recursos "try-except" ("try-catch") para gestionar de forma automática, segura y eficiente la conexión a la base de datos mientras aseguramos la captura de errores/excepciones durante la conexión paara que ésta no se vea interrumpida/detenida o afectada.
    try :
        # Intenta establecer la conexión a la base de datos utilizando los parámetros proporcionados/pasados en la función.
        connection = mysql.connector.connect(host = host_name, database = db_name, user = user_name, password = user_password)
        
        # Si la conexión es exitosa, muestra un mensaje de confirmación en la consola.
        if connection.is_connected():
            print(f"\n\n\tConexión a la base de datos '{db_name}' exitosa.")
    except Error as er:
        # Si ocurre un error durante el proceso de conexión, capturamos la excepción y mostramos un mensaje de error.
        #print(f"\n\n\tError inesperado al intentar conectarse a la base de datos '" + {db_name} + "': {er}")
        raise Exception(f"\n\n\tError inesperado al intentar conectarse a la base de datos '{db_name}': {er}.")

    return connection  # Devolvemos el objeto de conexión (puede ser None si la conexión falló).
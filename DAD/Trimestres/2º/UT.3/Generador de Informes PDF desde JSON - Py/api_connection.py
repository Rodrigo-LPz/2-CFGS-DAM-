# Conexión a la API de DummyJSON.
    # Módulo para consumir la API de DummyJSON. Lo que permitirá buscar productos por nombre y obtener su información.
import requests     # Importa de la biblioteca/librería para realizar peticiones HTTP.
import ctypes       # Importa la biblioteca "ctypes" para interactuar con bibliotecas de bajo nivel y funciones del sistema operativo.
import ctypes       # Importa la biblioteca "ctypes" para interactuar con bibliotecas de bajo nivel y funciones del sistema operativo.
import sys          # Importa la biblioteca "sys" para interactuar con el intérprete de Python y obtener información del sistema.
import traceback    # Importa la biblioteca "traceback" para manejar y mostrar rastros de pila



# URL base de la API de DummyJSON
BASE_URL = "https://dummyjson.com"


# Función/Método para mostrar mensajes de error al usuario.
    # Muestra un mensaje de error al usuario utilizando un cuadro de diálogo nativo en Windows o por consola en otros sistemas.
def _mostrar_error_usuario(mensaje, titulo = "Error"):
    try:
        # Si el usuario se encuentra utilizando o está en Windows, mostramos un mensaje por ventana emergente, por "MessageBox" nativo.
        if sys.platform.startswith("win"):
            ctypes.windll.user32.MessageBoxW(0, mensaje, titulo, 0x10)
        # Si el usuario se encuentra utilizando o está en otro sistema operativo distinto de Windows, mostramos el mensaje por consola.
        else:
            # En otros sistemas mostramos por consola (la GUI puede usar messagebox desde main si lo desea).
            print(f"[{titulo}] {mensaje}")
    except Exception:
        # Si ocurre un error al intentar mostrar el mensaje de error al usuario, lo mostramos por consola. De esta manera intentemaos nunca corromper o detener la ejecución por intentar mostrar el error en forma de mensaje.
        print(f"[{titulo}] {mensaje}")
        traceback.print_exc()   # Muestra el rastro de la pila para depuración.


# Función/Método para crear una conexión, hacer una llamada a la API de DummyJSON.
    # La función recibe como parámetro el término de búsqueda (nombre del producto) y devuelve los datos del producto en formato JSON.
def conectar_api(termino_busqueda = ""):
    print("\n\n\tConectándose a la API de DummyJSON...")
    
    # Construye la "URL" completa para la búsqueda de productos, "products".
    #url = f"{BASE_URL}/products/search?q={termino_busqueda}"
    # Si hay término de búsqueda, usa el "endpoint" de búsqueda.
        # "endpoint" es el punto final de acceso a un servicio web o API que permite realizar operaciones específicas, como obtener datos o enviar información.
    if termino_busqueda and termino_busqueda.strip():
        url = f"{BASE_URL}/products/search?q={termino_busqueda.strip()}"       # Usa el "endpoint" de búsqueda si hay término.
        print(f"\n\n\tBuscando productos con término: '{termino_busqueda.strip()}'...")
    else:
        # Si no hay término, obtiene todos los productos.
        url = f"{BASE_URL}/products"
        print(f"\n\n\tObteniendo todos los productos...")
    
    try:
        # Define el tiempo máximo de espera para la solicitud (en segundos).
        requestTimeout = 10
        
        # Realiza una petición de tipo GET a la API con el tiempo de espera definido.
        response = requests.get(url, timeout = requestTimeout)

        # Verifica/Comprueba si la respuesta fue exitosa (código de estado 200).
            # El código de estado HTTP 200 indica que la solicitud se ha procesado correctamente y que el servidor ha devuelto la información solicitada. Es decir, ha habido una interacción usuario/cliente-servidor exitosa.
                # Si la respuesta es exitosa, muestra un mensaje de confirmación en la consola.
        if response.status_code == 200:
            print(f"\n\n\tConexión a la API de DummyJSON exitosa. Procesando datos...")

            # Convierte la respuesta JSON a diccionario Python.
            data = response.json()

            # Extrae la lista de productos, "products" (si la lista no viene/está vacía).
            productos_raw = data.get("products", [])

            # Lista para almacenar los productos procesados. Esta lista será la que devolveremos como resultado de la función.
            productos_procesados = []

            # Procesa y filtra los datos de la lista de cada producto para sólo obtener los campos que nos interesan (solo los campos necesarios).
            for producto in productos_raw:
                productos_procesados.append({
                    "id": producto.get("id", 0),                                    # ID del producto.
                    "nombre": producto.get("title", "sin nombre"),                  # Nombre del producto.
                    "categoria": producto.get("category", "sin categoría"),         # Categoría del producto.
                    "marca": producto.get("brand", "sin marca"),                 # Marca del producto.
                    "descripción": producto.get("description", "sin descripción"),  # Descripción del producto.
                    "stock": producto.get("stock", 0),                              # Cantidad en stock del producto.
                    "precio": producto.get("price", 0.0),                           # Precio del producto.
                    "descuento": producto.get("discountPercentage", 0.0),           # Porcentaje de descuento del producto.
                    "valoracion": producto.get("rating", 0.0)                       # Valoración.
                })

            print(f"\n\n\tSe encontraron {len(productos_procesados)} productos registrados en la lista creada y asociada a la de DummyJSON.")
            return productos_procesados   # Devuelve la lista de productos procesados en formato JSON.
            # Si no se hiciese el procesamiento por lista y quisieramos devolver todos los datos tal cual vienen en la lista, sin filtar ni procesar, se usaría la siguiente línea:
            #return response.json() # Devuelve los datos del producto en formato ".json".
                # Si la respuesta no es exitosa, muestra un mensaje de error con el código de estado.
        else:
            # Cualquier otro código de estado lo tratamos como error recuperable.
            print(f"\n\n\tError al conectar con la API de DummyJSON: Código de estado {response.status_code}")
            ctypes.windll.user32.MessageBoxW(0, f"Error inesperado al intentar lanzar/ejecutar la petición a la API. Código: {response.status_code}", "Error de ejecución", 0x10)   # Muestra un mensaje de error si ocurre una excepción durante la exportación.
            return None # Devuelve "None" si la conexión no fue exitosa.
    except requests.exceptions.ConnectionError as cerex:
        #print(f"\n\n\tError inesperado al intentar conectarse a la API de DummyJSON: {rex}")
        ctypes.windll.user32.MessageBoxW(0, f"Error inesperado al intentar conectarse a la API de DummyJSON: {cerex}. Le recomendamos que compruebe/verifique su conexión a internet y vuelva a intentarlo en unos minutos.", "Error de conexión", 0x10)  # Muestra un mensaje de error si ocurre una excepción durante la exportación.
        return None
    except requests.exceptions.RequestException as rex:
        ctypes.windll.user32.MessageBoxW(0, f"Error inesperado al intentar lanzar/ejecutar la petición a la API: {rex}", "Error de ejecución", 0x10)    # Muestra un mensaje de error si ocurre una excepción durante la exportación.
        return None
    except requests.exceptions.Timeout as tex:
        ctypes.windll.user32.MessageBoxW(0, f"Error inesperado durante la ejecución de la petición a la API. La petición tardó demasiado tiempo ({requestTimeout}): {tex}", "Error de carga / tiempo de de carga", 0x10)   # Muestra un mensaje de error si ocurre una excepción durante la exportación.
        return None
    except Exception as ex:
        ctypes.windll.user32.MessageBoxW(0, f"Error inesperado durante la ejecución del programa: {ex}", "Error inesperado de ejecución", 0x10)   # Muestra un mensaje de error si ocurre una excepción durante la exportación.
        traceback.print_exc()   # Muestra el rastro de la pila para depuración.
        return None


# Función/Método para buscar productos por nombre.
    # La función recibe como parámetro el término de búsqueda (nombre del producto) y devuelve los datos del producto en formato JSON.
def buscar_productos(termino_busqueda):
    # Llama a la función de conexión a la API con el término (retornando/devolviendo) de búsqueda proporcionado.
    return conectar_api(termino_busqueda)


# Función/Método para buscar todos los productos (sin filtro).
    # La función no recibe parámetros y por tanto devuelve todos los productos en formato JSON.
def buscar_todos_productos():
    return conectar_api("")


"""
# ========== PRUEBA DE MÓDULO ==========
    # Descomentar las siguientes líneas para probar la funcionalidad del módulo individualmente, seprándolo del resto del programa.
def obtener_todos_productos():
    return conectar_api("")

if __name__ == "__main__":
    print("=" * 50)
    print("PRUEBA DEL MÓDULO api_connection.py")
    print("=" * 50)
    
    # Prueba 1: Buscar productos con término "phone"
    print("\n1. Buscando productos con 'phone':")
    resultados = buscar_productos("phone")
    if resultados:
        for i, prod in enumerate(resultados[:3], 1):  # Muestra solo los 3 primeros
            print(f"   {i}. {prod['nombre']} - {prod['categoria']} - ${prod['precio']}")
    
    # Prueba 2: Obtener todos los productos
    print("\n2. Obteniendo todos los productos:")
    todos = obtener_todos_productos()
    if todos:
        print(f"   Total de productos: {len(todos)}")
"""
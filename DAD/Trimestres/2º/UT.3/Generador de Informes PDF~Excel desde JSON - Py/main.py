# Programa buscador de productos DummyJSON con interfaz gráfica de usuario (GUI) en "Tkinter" para poder visualizar los datos de la aplicación de tablas interactivas y exportarlos mediante botones a PDF.
import tkinter as tk                                        # Importa la biblioteca Tkinter y la renombra como 'tk'.
import os                                                   # Importa la biblioteca "os" para operaciones del sistema operativo. 

from tkinter import ttk, messagebox                         # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".
from api_connection import buscar_productos                 # Importa la función de conexión "connection" a la base de datos desde el módulo/archivo "db_connection.py".
from export_pdf import export_data_to_pdf                   # Importa la función para generar informes PDF desde el módulo/archivo "export_pdf.py".
from export_excel import export_data_to_excel               # Importa la función para generar informes Excel desde el módulo/archivo "export_excel.py".


# Lista para guardar y almacenar los productos obtenidos desde la API en cada acción.
productos_actuales = []


# Función/Método para limpiar la tabla.
    # Limpia todos los elementos del "Treeview" (tabla) antes de cargar nuevos datos.
def limpiar_tabla():
    # Variable global. Será la lista para almacenar los productos obtenidos de la API. Inicialmente está vacía.
        # Su función es la de cargar todos los productos desde la API y actualizar la tabla.
    #global productos_actuales

    # Reiniciamos la lista global de productos actuales. La dejamos a cero, vacía, antes de cargar nuevos productos.
    #productos_actuales = []

    # Recorre todas las filas actuales del "Treeview" (tabla) limpiando todos los elementos de este.
    for item in tree.get_children():
        # Elimina cada elemento (fila) del "Treeview".
        tree.delete(item)


# Función/Método para cargar los productos desde la API y actualizar la tabla.
    # Llama a la función "buscar_productos()" para obtener los productos desde la API. Le implementamos un parámetro opcional " termino = "" " con un valor por defecto vacío. Si este es cambiado por otro valor, el nombre de un producto concreto éste será únicamente buscado en la API.
def cargar_productos(termino = ""):
    # Variable global. Será la lista para almacenar los productos obtenidos de la API. Inicialmente está vacía.
        # Su función es la de cargar todos los productos desde la API y actualizar la tabla.
    global productos_actuales

    # Pedimos los productos a la API.
    productos = buscar_productos(termino)
    
    # Si todo va bien, los guardamos.
    if productos is not None:
        # Actualiza la variable global con los productos obtenidos. Es decir, guarda los productos reunidos/obtenidos en la lista global "productos_actuales".
        productos_actuales = productos
        return True
    # Si hubo un error, devolvemos False.
    else:
        return False


# Función/Método para mostrar los productos en la tabla.
    # Muestra los productos almacenados en la variable global "productos_actuales" en el "Treeview" (tabla).
def mostrar_productos():
    # Llama/Inicializa el método "limpiar_tabla()" para limpiar la tabla antes de mostrar nuevos productos.
    limpiar_tabla()
    
    # Inserta cada producto en la tabla.
    for producto in productos_actuales:
        # Inserta una nueva fila en el "Treeview" con los valores del producto.
        tree.insert("", "end", values = (
            producto["id"],
            producto["nombre"],
            producto["categoria"],
            producto["marca"],
            producto["descripción"],
            producto["stock"],
            producto["precio"],
            producto["descuento"],
            producto["valoracion"]
        ))

    # Actualiza la etiqueta de resultados con el número de productos encontrados.
    label_resultados.config(text = f"\n\n\t📊 Resultado: Se han encontrado un total de {len(productos_actuales)} productos dentro de la lista.")


# Función/Método para cargar todos los productos y mostrarlos en la tabla.
    # Llama a los métodos "cargar_productos()" y "mostrar_productos()" para cargar y mostrar todos los productos.
        # Este método es quier coordinará a los anteriores métodos.
            # "limpiar_tabla" se encarga de limpiar la tabla antes de cargar nuevos datos.
            # "cargar_productos" se encarga de obtener los productos desde la API.
            # "mostrar_productos" se encarga de mostrar los productos en la tabla.
            # Finalmente, este método es el que será llamado desde el botón de la interfaz gráfica para coordinar los otros métodos y así cargar y mostrar los productos.
def cargar_todos_productos():
    # Llama/Inicializa el método "limpiar_tabla()" para limpiar la tabla antes de mostrar nuevos productos.
    limpiar_tabla()

    # Deshabilita los botones durante la carga.
    btn_buscar.config(state = "disabled")
    btn_ver_todos.config(state = "disabled")
    btn_exportar_pdf.config(state = "disabled")
    btn_exportar_excel.config(state = "disabled")

    # Muestra un mensaje temporal de carga en la tabla mientras se obtienen los datos.
    tree.insert("", "end", values =("Cargando productos desde la API...", "", "", "", "", "", "", "", ""))  # Inserta una fila temporal indicando que se están cargando los datos.
    root_windows.update()                                                                                   # Actualiza la ventana para mostrar el mensaje de carga.

    # Si la carga de productos fue exitosa, llama al método "cargar_productos()" para obtener todos los productos (sin filtro) y muestra los productos en la tabla.
    if cargar_productos(""):
        mostrar_productos()
    # Si hubo un error al cargar los productos, muestra un mensaje de error.
    else:
        # Llama/Inicializa el método "limpiar_tabla()" para limpiar la tabla antes de mostrar nuevos productos.
        limpiar_tabla()
        messagebox.showerror("Error de carga", "Error inesperado durante la carga de productos. No se pudieron cargar los productos.")
    
    # Rehabilita los botones tras la carga.
    btn_buscar.config(state = "normal")
    btn_ver_todos.config(state = "normal")
    btn_exportar_pdf.config(state = "normal")
    btn_exportar_excel.config(state = "normal")

    
# Función/Método para buscar productos por término ingresado en el campo de búsqueda.
    # La función obtiene el término de búsqueda desde el campo de entrada, llama a la función "buscar_productos()" para obtener los productos concordantes según el parámetro pasado por el usuario desde la API y muestra los resultados en la tabla.
        # Realiza la misma función que "cargar_todos_productos()", pero en este caso filtra los productos según el término de búsqueda ingresado por el usuario.
def buscar_productos_por_termino():
    # Obtiene el término de búsqueda pasado por el usuario.
    termino = entry_busqueda.get().strip()
    
    # Verifica/Comprueba que le usuario haya ingresado el término de búsqueda. Si este no ha escrito nada, avisamos mediante un mensaje emergente en pantalla.
    if not termino:
        messagebox.showwarning("Aviso de búsqueda parametriazda/filtrada", "Campo de búsqueda vacío, si desea realizar una búsqueda filtrada, por favor ingrese un término de búsqueda.")
        return
    
    # Llama/Inicializa el método "limpiar_tabla()" para limpiar la tabla antes de mostrar nuevos productos.
    limpiar_tabla()

    # Muestra un mensaje temporal de carga en la tabla mientras se obtienen los datos.
    tree.insert("", "end", values =("Cargando productos desde la API...", "", "", "", "", "", "", "", ""))  # Inserta una fila temporal indicando que se están cargando los datos.
    root_windows.update()                                                                                   # Actualiza la ventana para mostrar el mensaje de carga.
    
    # Si se registra un término de búsqueda se cargan los productos coincidentes con el parámetro de filtración. Realizamos la búsqueda.
    if cargar_productos(termino):
        mostrar_productos()
        label_resultados.config(text = f"\n\n\tBúsqueda realizada: Se han encontrado un total de {len(productos_actuales)} '{termino}', productos, encontrados.")
    # Si hubo un error al cargar los productos, muestra un mensaje de error.
    else:
        messagebox.showerror("Error de búsqueda", f"Error inesperado durante la búsqueda del/de los producto/s '{termino}'. No se pudieron obtener los datos desde la API.")


# Función/Método para exportar los productos actuales a un archivo PDF.
    # Llama a la función "generar_informe_pdf()" para generar el informe PDF con los productos actuales almacenados en la variable global "productos_actuales".
def exportar_pdf():
    # Verifica/Comprueba si la lista está vacía, en cuyo caso se le notificará al usuario de la situación, puesto que no tendría sentido exportar una lista vacía.
    if not productos_actuales:
        messagebox.showwarning("Aviso de exportación", "No hay productos para exportar. La lista de productos está vacía. Por favor, cargue o busque productos antes de intentar volver a exportar.")
        return
    
    try:
        # Obtiene el término de búsqueda pasado por el usuario. Lo utilizaremos como parte del nombre del archivo PDF generado.
        termino = entry_busqueda.get().strip()

        # Genera el PDF con los productos actuales obtenidos de la lista.
        archivo_pdf = export_data_to_pdf(productos_actuales, termino)

        # Muestra un mensaje de confirmación al usuario con la ubicación del archivo generado.
        carpeta = os.path.dirname(archivo_pdf)          # Obtiene la carpeta donde se guardó el archivo PDF.
        nombre_archivo = os.path.basename(archivo_pdf)  # Obtiene el nombre del archivo PDF generado.

        messagebox.showinfo("Exportación completada", f"El informe PDF se ha generado correctamente.\n\n\tNombre del Archivo: {nombre_archivo}\n\tUbicación del Archivo: {carpeta}\n\tFormato del Archivo: PDF\n\n¡Gracias por utilizar la aplicación 'Buscador de Productos - DummyJSON'!")

    except Exception as ex:
        messagebox.showerror("Error de exportación", f"Error inesperado al intentar generar, cargar y/o guardar el informe PDF:\n{ex}")


# Función/Método para exportar los productos actuales a un archivo Excel.
    # Llama a la función "generar_informe_excel()" para generar el informe Excel con los productos actuales almacenados en la variable global "productos_actuales".
def exportar_excel():
    # Verifica/Comprueba si la lista está vacía, en cuyo caso se le notificará al usuario de la situación, puesto que no tendría sentido exportar una lista vacía.
    if not productos_actuales:
        messagebox.showwarning("Aviso de exportación", "No hay productos para exportar. La lista de productos está vacía. Por favor, cargue o busque productos antes de intentar volver a exportar.")
        return
    
    try:
        # Obtiene el término de búsqueda pasado por el usuario. Lo utilizaremos como parte del nombre del archivo PDF generado.
        termino = entry_busqueda.get().strip()

        # Genera el Excel con los productos actuales obtenidos de la lista.
        archivo_excel = export_data_to_excel(productos_actuales, termino)

        # Muestra un mensaje de confirmación al usuario con la ubicación del archivo generado.
        carpeta = os.path.dirname(archivo_excel)          # Obtiene la carpeta donde se guardó el archivo Excel.
        nombre_archivo = os.path.basename(archivo_excel)  # Obtiene el nombre del archivo excel generado.

        messagebox.showinfo("Exportación completada", f"El informe Excel se ha generado correctamente.\n\n\tNombre del Archivo: {nombre_archivo}\n\tUbicación del Archivo: {carpeta}\n\tFormato del Archivo: PDF\n\n¡Gracias por utilizar la aplicación 'Buscador de Productos - DummyJSON'!")

    except Exception as ex:
        messagebox.showerror("Error de exportación", f"Error inesperado al intentar generar, cargar y/o guardar el informe Excel:\n{ex}")


# Configuración gráfica/visual del programa.
    # Configuración de la ventana principal de la aplicación.
        # Crea la ventana principal.
root_windows = tk.Tk()                                  # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("Buscador de Productos - DummyJSON") # Título de la ventana.
root_windows.geometry("1100x600")                       # Define el tamaño de la ventana (ancho x alto).
root_windows.configure(bg = "#2C3E50")                # Cambia el color de fondo de la ventana principal
#root_windows.resizable(False, False)                   # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).

    # Configuración de los frames y widgets (etiquetas, entradas, botones...).
        # Frame superior para los controles de búsqueda y botones.
frame_superior = tk.Frame(root_windows) # Crea un marco (frame) para contener los controles de búsqueda y botones.
frame_superior.config(bg="#2C3E50")  # Configura el color de fondo del marco (frame).
frame_superior.pack(pady = 10)          # Empaqueta/Muestra el marco (frame) con un margen vertical (arriba/abajo) de tamaño 10.

        # Etiquetas para el término de búsqueda.
label_busqueda = tk.Label(frame_superior, text = "Buscar producto:")                   # Crea una etiqueta (label) para el término de búsqueda.
label_busqueda.config( font = ("Arial", 10, "bold"), bg = "#2C3E50", fg = "white")   # Configura el estilo de la etiqueta (fuente, color de fondo y color de texto
label_busqueda.grid(row = 0, column = 0, padx = 5, pady = 5)                           # Empaqueta/Muestra la etiqueta (label) en la cuadrícula (grid).

        # Entradas o campos de entradas de texto para el término de búsqueda.
entry_busqueda = tk.Entry(frame_superior, width = 30)           # Crea un campo de entrada (entry) para el término de búsqueda.
entry_busqueda.grid(row = 0, column = 1, padx = 5, pady = 5)    # Empaqueta/Muestra el campo de entrada en la cuadrícula (grid).

        # Botones de búsqueda, cargar todos los productos y exportar a PDF.
btn_buscar = tk.Button(frame_superior, text = "🔍\nBuscar por término", command = buscar_productos_por_termino) # Crea un botón para buscar productos por término.
btn_buscar.config(bg = "#3498DB", fg = "white", font = ("Arial", 10, "bold"), relief = "raised", bd = 3)      # Configura el estilo del botón (color de fondo, color de texto, fuente, relieve y borde).
btn_buscar.grid(row = 0, column = 2, padx = 5)                                                                  # Empaqueta/Muestra el botón en la cuadrícula (grid).

        # Botón para cargar todos los productos.
btn_ver_todos = tk.Button(frame_superior, text = "⏬\nCargar todos los productos", command = cargar_todos_productos) # Crea un botón para cargar todos los productos.
btn_ver_todos.config(bg = "#A0AE27", fg = "white", font = ("Arial", 10, "bold"), relief = "raised", bd = 3)        # Configura el estilo del botón (color de fondo, color de texto, fuente, relieve y borde).
btn_ver_todos.grid(row = 0, column = 3, padx = 5)                                                                    # Empaqueta/Muestra el botón en la cuadrícula (grid).

        # Botón para exportar a PDF.
btn_exportar_pdf = tk.Button(frame_superior, text = "📄\nExportar a PDF", command = exportar_pdf)                 # Crea un botón para exportar los datos a PDF.
btn_exportar_pdf.config(bg = "#E74C3C", fg = "white", font = ("Arial", 10, "bold"), relief = "raised", bd = 3)  # Configura el estilo del botón (color de fondo, color de texto, fuente, relieve y borde).
btn_exportar_pdf.grid(row = 0, column = 4, padx = 5)                                                              # Empaqueta/Muestra el botón en la cuadrícula (grid).

        # Botón para exportar a Excel.
btn_exportar_excel = tk.Button(frame_superior, text = "📄\nExportar a Excel", command = exportar_excel)             # Crea un botón para exportar los datos a Excel.
btn_exportar_excel.config(bg = "#27AE60", fg = "white", font = ("Arial", 10, "bold"), relief = "raised", bd = 3)  # Configura el estilo del botón (color de fondo, color de texto, fuente, relieve y borde).
btn_exportar_excel.grid(row = 0, column = 5, padx = 5)                                                              # Empaqueta/Muestra el botón en la cuadrícula (grid).

    # Configuración del "Treeview" (tabla) para mostrar los productos.
label_resultados = tk.Label(root_windows, text = "\n\n\tResultado: 0 productos.")   # Etiqueta para mostrar el número de resultados encontrados. Inicialmente es 0, ya que no se han cargado productos.
label_resultados.config( font = ("Arial", 10), bg = "#2C3E50", fg = "white")      # Configura el estilo de la etiqueta (fuente, color de fondo y color de texto
label_resultados.pack()                                                             # Empaqueta/Muestra la etiqueta (label).

    # Configuración de la tabla (Treeview) para mostrar los productos.
        # Frame para la tabla.
frame_tabla = tk.Frame(root_windows)    # Crea un marco (frame) para contener la tabla (Treeview).
frame_tabla.pack(pady = 10)             # Empaqueta/Muestra el marco (frame) con un margen vertical (arriba/abajo) de tamaño 10.

        # Creación del Treeview (tabla).
tree = ttk.Treeview(frame_tabla, columns = ("ID", "Nombre", "Categoría", "Marca", "Descripción", "Stock", "Precio", "Descuento", "Valoración"), show = "headings", height = 20)

        # Definición de columnas.
tree.heading("ID", text = "ID")
tree.heading("Nombre", text = "Nombre")
tree.heading("Categoría", text = "Categoría")
tree.heading("Marca", text = "Marca")
tree.heading("Descripción", text = "Descripción")
tree.heading("Stock", text = "Stock")
tree.heading("Precio", text = "Precio ($)")
tree.heading("Descuento", text = "Descuento (%)")
tree.heading("Valoración", text = "Valoración (0-5)")

        # Tamaños de columnas.
tree.column("ID", width = 40)
tree.column("Nombre", width = 200)
tree.column("Categoría", width = 150)
tree.column("Marca", width = 150)
tree.column("Descripción", width = 300)
tree.column("Stock", width = 80, anchor = "center")
tree.column("Precio", width = 100, anchor = "center")
tree.column("Descuento", width = 120, anchor = "center")
tree.column("Valoración", width = 120, anchor = "center")

tree.pack() # Empaqueta/Muestra el "Treeview" (tabla) dentro del marco (frame).


# Crea un bloque para el "footer" (pie de página).
footer = tk.Frame(root_windows, bg = "#34495E")   # Crea un marco (frame) para el pie de página con un color de fondo específico.
footer.pack(side = "bottom", fill = "x", pady = 5)  # Empaqueta/Muestra el marco (frame) en la parte inferior de la ventana, ocupando todo el ancho disponible y con un margen vertical (arriba/abajo) de tamaño 5.

# Etiqueta del footer.
label_footer = tk.Label(footer, text = "Buscador de Productos - DummyJSON ~ RODRISTARK.GAME$17\n© 2025 | ® Marca Registrada | ™ Producto Original\nDerechos de imagen respetados.", font = ("Arial", 8), bg = "#34495E", fg = "white")  # Crea una etiqueta (label) para el pie de página.
label_footer.pack(pady = 5)                                                                                                                                                                                                               # Empaqueta/Muestra la etiqueta (label) dentro del marco (frame) con un margen vertical (arriba/abajo) de tamaño 5.


# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
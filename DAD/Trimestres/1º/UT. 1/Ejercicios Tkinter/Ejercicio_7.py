import tkinter as tk                        # Importa la biblioteca Tkinter y la renombra como 'tk'.
from tkinter import messagebox              # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".
from tkinter import filedialog              # Importa diálogos de archivo y cuadros de mensaje.
from PIL import Image, ImageTk              # Necesitamos instalar Pillow: pip install pillow. Esto para poder abrir y mostrar imágenes en Tkinter.
import os                                   # Importa la biblioteca Tkinter y la renombra como 'os'.




# Crea la ventana principal.
root_windows = tk.Tk()                      # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("Gestor de Productos")       # Título de la ventana.
root_windows.geometry("750x750")            # Define el tamaño de la ventana (ancho x alto).
root_windows.resizable(False, False)        # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).


# Crea la lista donde guardar/almacenar los productos. Cada contacto será un diccionario: {"nombre": ..., "precio": ... "imagen": ...}.
productos = []

# Índice actual de imagen. Posicionamos cualquier operación sobre la lista para que empiece por su posición inical (0). Su función será la de guardar la posición de la imagen que se está mostrando actualmente.
indice = 0

# Función para mostrar seleccionar una imagen.
def seleccionar_imagen():
    ruta = filedialog.askopenfilename(                                  # Semejante a un "Scanner" o un "JOptionPane" de Java. Abre un diálogo con el usuario para elegir un archivo (solo imágenes).
        title="Selecciona una imagen",                                  # Título de la ventana del diálogo.
        filetypes=[("Archivos de imagen", "*.jpg *.png *.jpeg *.gif")]  # Tipos de archivo permitidos.
    )
    
    if ruta:                            # Si el usuario ha seleccionado una ruta (es decir, no ha cancelado).
        entry_imagen.delete(0, tk.END)  # Borra el contenido actual del Entry de imagen.
        entry_imagen.insert(0, ruta)    # Inserta la ruta seleccionada en el Entry.

# Función para agregar un producto a la lista de productos.
def agregar_producto():
    nombre = entry_nombre.get().strip()         # Lee el nombre desde el Entry y quita espacios alrededor
    precio_texto = entry_precio.get().strip()   # Lee el precio como texto y quita espacios
    imagen = entry_imagen.get().strip()         # Lee la ruta de la imagen desde el Entry y quita espacios

    if not nombre or not precio_texto or not imagen: # Verifica/Comprueba que los campos no estén vacíos.
        messagebox.showwarning("Error:Campos incompletos", "Por favor, completa todos los campos para agregar el producto a la lista.")
        return  # Sale de la función sin agregar.
    
    try:                                # Convierte el precio a número (float).
        precio = float(precio_texto)    # Convierte texto a número; si falla, salta a except.
    except ValueError:
        messagebox.showerror("Error", "El precio debe ser un número.")
        return

    if not os.path.exists(imagen):  # Comprueba que la ruta de la imagen existe en el disco.
        messagebox.showerror("Error", "La ruta de la imagen no es válida.")
        return

    producto = {            # Crear el diccionario que representa el producto.
        "nombre": nombre,   # Campo 'nombre' con el nombre del producto.
        "precio": precio,   # Campo 'precio' con el precio convertido a float.
        "imagen": imagen    # Campo 'imagen' con la ruta al archivo de imagen.
    }

    productos.append(producto)  # Añade el producto a la lista global.

    # Limpiar los campos del formulario para nueva entrada, un nuevo producto.
    entry_nombre.delete(0, tk.END)  # Borra el text del Entry del nombre.
    entry_precio.delete(0, tk.END)  # Borra el text del Entry del precio.
    entry_imagen.delete(0, tk.END)  # Borra el text del Entry de la ruta de imagen.

    mostrar_productos()  # Actualiza la vista de productos para mostrar el nuevo producto.


# Función para mostrar el catálogo de productos incrustados en la lista.
def mostrar_productos():
    for widget in frame_lista.winfo_children(): # Borra todos los widgets que pueda tener el contenedor (si los hay), para redibujar limpio.
        widget.destroy()

    if not productos:   # Si no hay productos, mostrar un texto indicándolo.
        tk.Label(frame_lista, text = "No hay productos disponibles.", font = ("Arial", 12)).pack()
        return  # Salimos porque no hay nada más que dibujar.

    for producto in productos:  # Recorremos la lista de productos y creamos un "item" visual por cada uno.
        # Creamos un Frame que contendrá la miniatura y los textos del producto.
        frame_item = tk.Frame(frame_lista, bd = 2, relief = "groove", padx = 10, pady = 10)
        frame_item.pack(pady = 5, fill = "x")

        try:                                        # Intentamos cargar la imagen y crear una miniatura.
            img = Image.open(producto["imagen"])    # Abre la imagen desde la ruta guardada.
            img = img.resize((100, 100))            # Redimensiona la imagen a 100x100 píxeles.
            img_tk = ImageTk.PhotoImage(img)        # Convierte la imagen a un objeto compatible con Tkinter.
        except Exception:
            img_tk = None  # Si falla la carga, no habrá miniatura (puede pasar si la ruta se borra más tarde).

        if img_tk:                                              # Si la imagen se cargó correctamente, la colocamos en la parte izquierda del item.
            lbl_imagen = tk.Label(frame_item, image = img_tk)   # Label que contiene la imagen.
            lbl_imagen.image = img_tk                           # Guardar la referencia para evitar que Python la recoja como basura.
            lbl_imagen.pack(side = "left", padx = 10)           # Empaquetar a la izquierda con margen horizontal.

        # Creamos una etiqueta con el nombre y precio del producto (en texto).
        lbl_texto = tk.Label(frame_item, text = f"Producto:\n\tNombre: {producto['nombre']}\n\tPrecio: {producto['precio']} €", font = ("Arial", 14)) # Texto con salto de línea. # Texto con Fuente y tamaño definidos.

        # Posicionamos el texto a la izquierda (junto a la imagen).
        lbl_texto.pack(side="left")


# Crea y configura widgets (etiquetas, botones, etc.).
frame_form = tk.Frame(root_windows, pady=10)
frame_form.pack()

tk.Label(frame_form, text = "Nombre del producto:", font = ("Arial", 12)).grid(row = 0, column = 0, sticky = "e", padx = 5)

entry_nombre = tk.Entry(frame_form, width = 30)
entry_nombre.grid(row = 0, column = 1, padx = 5)

tk.Label(frame_form, text = "Precio (€):", font = ("Arial", 12)).grid(row = 1, column = 0, sticky = "e", padx = 5)

entry_precio = tk.Entry(frame_form, width = 30)
entry_precio.grid(row = 1, column = 1, padx = 5)

tk.Label(frame_form, text = "Imagen:", font = ("Arial", 12)).grid(row = 2, column = 0, sticky = "e", padx = 5)

entry_imagen = tk.Entry(frame_form, width = 30)
entry_imagen.grid(row = 2, column = 1, padx = 5)

btn_explorar = tk.Button(frame_form, text = "📁 Buscar", command = seleccionar_imagen)
btn_explorar.grid(row = 2, column = 2, padx = 5)

btn_agregar = tk.Button(root_windows, text = "➕\nAgregar producto", font = ("Arial", 12), bg = "lightgreen", command = agregar_producto)
btn_agregar.pack(pady=10)

frame_lista = tk.Frame(root_windows)
frame_lista.pack(pady=10, fill="both", expand=True)


# Mostrar la primera imagen al iniciar.
mostrar_productos()


# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
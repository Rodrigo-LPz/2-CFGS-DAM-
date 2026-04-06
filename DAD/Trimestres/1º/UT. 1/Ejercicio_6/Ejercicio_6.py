import tkinter as tk           # Importa la biblioteca Tkinter y la renombra como 'tk'.
from tkinter import messagebox # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".
from PIL import Image, ImageTk # Necesitamos instalar Pillow: pip install pillow. Esto para poder abrir y mostrar imágenes en Tkinter.
import os                      # Importa la biblioteca Tkinter y la renombra como 'os'.




# Crea la ventana principal.
root_windows = tk.Tk()                      # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("Visor de Imágenes")       # Título de la ventana.
root_windows.geometry("750x650")            # Define el tamaño de la ventana (ancho x alto).
root_windows.resizable(False, False)        # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).


# Crea la lista donde guardar/almacenar la ruta de imágenes. Cada contacto será un diccionario: {"nombre": ..., "telefono": ...}.
    #imagenes = [
    #    "Imagenes/Ciclismo.jpg",
    #    "Imagenes/CiclismoEnPista_Velódromo.jpg",
    #    "Imagenes/Enduro.jpg",
    #    "Imagenes/Modalidades.jpg",
    #    "Imagenes/TiposBicicleta.jpg"
    #]
    
# "os" permite trabajar con rutas de carpetas de forma compatible con cualquier sistema.
base_dir = os.path.dirname(__file__)  # obtiene la ruta completa de la carpeta donde está guardado este archivo (.py). Carpeta actual donde está el ".py".
# Crea la lista donde guardar/almacenar la ruta de imágenes. Cada contacto será un diccionario: {"nombre": ..., "telefono": ...}.
imagenes = [
    os.path.join(base_dir, "Imagenes", "Ciclismo.jpg"),
    os.path.join(base_dir, "Imagenes", "CiclismoEnPista_Velódromo.jpg"),
    os.path.join(base_dir, "Imagenes", "Enduro.jpg"),
    os.path.join(base_dir, "Imagenes", "Modalidades.jpg"),
    os.path.join(base_dir, "Imagenes", "TiposBicicleta.jpg"),
]

# Índice actual de imagen. Posicionamos cualquier operación sobre la lista para que empiece por su posición inical (0). Su función será la de guardar la posición de la imagen que se está mostrando actualmente.
indice = 0

# Función para mostrar una imagen según el índice actual.
def mostrar_imagen():
    try:
        img = Image.open(imagenes[indice])  # Abre la imagen en la posición "indice" de la lista.
        img = img.resize((500, 400))        # Ajusta el tamaño de la imagen a 500x400 píxeles
        img_tk = ImageTk.PhotoImage(img)    # Convierte la imagen a un formato compatible con Tkinter.
        label_imagen.config(image=img_tk)   # Muestra la imagen en el Label "label_imagen2.
        label_imagen.image = img_tk         # Guarda una referencia de la imagen para que Tkinter no la elimine de memoria.
        label_info.config(text = f"Imagen {indice + 1} de {len(imagenes)}") # Muestra información del índice actual y total.
    except Exception as e:
        messagebox.showerror("Error", f"El sistema no pudo abrir la imagen:\n{e}")
        img = Image.new("RGB", (600, 500), color="gray")

# Función para pasar a la siguiente imagen.
def siguiente():
    global indice                   # Semejante al ".super()" de Java. Se utiliza para que Python use y modifique variables que están definidas fuera de la función. Se indica que se va a usar la variable global 'indice'.
    if indice < len(imagenes) - 1:  # Comprueba que no se llegue al final de la lista.
        indice += 1                 # Aumenta el índice para mostrar la siguiente imagen.
        mostrar_imagen()            # Llama a la función para mostrar la nueva imagen.
    else:
        messagebox.showinfo("Fin", "Ya estás en la última imagen.")

# Función para volver a la imagen anterior
def anterior():
    global indice
    if indice > 0:          # Comprueba que no se llegue al inicio de la lista.
        indice -= 1         # Disminuye el índice para mostrar la imagen anterior.
        mostrar_imagen()    # Llama a la función para mostrar la nueva imagen.
    else:
        messagebox.showinfo("Inicio", "Ya estás en la primera imagen.")


# Crea y configura widgets (etiquetas, botones, etc.).
label_titulo = tk.Label(root_windows, text = "Visor de Imágenes\n-\nCatálogo/Recopilación 'Mundo del ciclismo'", font = ("Arial", 18))
label_imagen = tk.Label(root_windows)
label_info = tk.Label(root_windows, text = "", font = ("Arial", 12))
frame_botones = tk.Frame(root_windows)
btn_anterior = tk.Button(frame_botones, text = "⬅️\nAnterior", font = ("Arial", 12), command = anterior)  
btn_siguiente = tk.Button(frame_botones, text="➡️\nSiguiente", font = ("Arial", 12), command = siguiente) # Botón para ir a la siguiente imagen.


# Posiciona los widgets en la ventana.
label_titulo.pack(pady = 10)
label_imagen.pack(pady = 10)
label_info.pack()
frame_botones.pack(pady = 10)
btn_anterior.grid(row=0, column=0, padx=10)   # Lo coloca en la fila 0, columna 0 del Frame y con margen horizontal de 10 píxeles.
btn_siguiente.grid(row=0, column=1, padx=10)  # Lo coloca en la fila 0, columna 1 del Frame y con margen horizontal de 10 píxeles.


# Mostrar la primera imagen al iniciar.
mostrar_imagen()


# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
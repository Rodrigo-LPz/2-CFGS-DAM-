import tkinter as tk            # Importa la biblioteca Tkinter y la renombra como 'tk'.
from tkinter import messagebox  # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".
from PIL import Image, ImageTk  # Necesitamos instalar Pillow: pip install pillow. Esto para poder abrir y mostrar imágenes en Tkinter.
import os                       # Importa la biblioteca Tkinter y la renombra como 'os'.
import random                   # Importa la biblioteca random para generar números aleatorios.




# Crea la ventana principal.
root_windows = tk.Tk()                      # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("Juego Hiragana\n-\n(Juego para aprender japonés)")       # Título de la ventana.
root_windows.geometry("650x650")            # Define el tamaño de la ventana (ancho x alto).
root_windows.resizable(False, False)        # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).


# "os" permite trabajar con rutas de carpetas de forma compatible con cualquier sistema.
base_dir = os.path.dirname(__file__)                                                # Obtiene la ruta completa de la carpeta donde está guardado este archivo (.py). Carpeta actual donde está el ".py".
carpeta_imagenes = os.path.join(base_dir, "hiragana")                               # Carpeta donde están las imágenes.
todas_imagenes = [f for f in os.listdir(carpeta_imagenes) if f.endswith(".png")]    # Obtiene la lista de todos los archivos con extensión ".png" dentro de la carpeta.
imagenes_seleccionadas = random.sample(todas_imagenes, 10)                          # Mezcla las imágenes y elige 10 aleatorias sin repetirse.

indice_actual = 0   # Índice actual de imagen (comienza en 0).
aciertos = 0        # Contador de aciertos del usuario.


# Función para mostrar la imagen actual.
def mostrar_imagen():
    global img_tk   # Semejante al ".super()" de Java. Se utiliza para que Python use y modifique variables que están definidas fuera de la función. Se indica que se va a usar la variable global 'img_tk'.

    nombre_archivo = imagenes_seleccionadas[indice_actual]  # Obtiene el nombre del archivo actual.
    ruta = os.path.join(carpeta_imagenes, nombre_archivo)   # Crea la ruta completa del archivo (carpeta + nombre del archivo).

    # Abre la imagen con Pillow.
    img = Image.open(ruta)
    img = img.resize((350, 350))   # Redimensiona para ajustarla a la ventana.
    img_tk = ImageTk.PhotoImage(img)

    # Actualiza el widget Label con la nueva imagen.
    label_imagen.config(image = img_tk)
    label_imagen.image = img_tk

    entry_respuesta.delete(0, tk.END)                                                               # Limpia el cuadro de texto de respuestas anteriores.
    label_progreso.config(text = f"Imagen {indice_actual + 1} de {len(imagenes_seleccionadas)}")    # Muestra el número de imagen actual.


# Función para comprobar la respuesta.
def comprobar_respuesta():
    global aciertos, indice_actual    # Semejante al ".super()" de Java. Se utiliza para que Python use y modifique variables que están definidas fuera de la función. Se indica que se va a usar la variable global 'aciertos' y 'indice_actual'.

    respuesta = entry_respuesta.get().strip().lower()   # Obtiene la respuesta del usuario (sin espacios y en minúsculas).

    # Obtiene el nombre correcto (sin extensión).
    nombre_archivo = imagenes_seleccionadas[indice_actual]
    respuesta_correcta = os.path.splitext(nombre_archivo)[0].lower()

    if respuesta == respuesta_correcta: # Comprueba si coincide.
        aciertos += 1
        messagebox.showinfo("Correcto. Has acertado", "¡Muy bien! Respuesta correcta.")
    else:
        messagebox.showwarning("Incorrecto. Te equivocaste", f"La respuesta correcta era: '{respuesta_correcta}'.")

    # Pasa a la siguiente imagen o finaliza si ya son 10.
    indice_actual += 1
    if indice_actual < len(imagenes_seleccionadas):
        mostrar_imagen()
    else:
        mostrar_resultado_final()

# Función para mostrar el resultado final.
def mostrar_resultado_final():
    nota = aciertos # Calcula la nota de 0 a 10.

    # Determina el mensaje según la nota.
    if nota == 0:
        calificacion = "No has estudiado NADA!!!"
    if nota < 5:
        calificacion = "Insuficiente/Suspenso"
    elif nota < 6:
        calificacion = "Suficiente/Aprobado"
    elif nota < 7:
        calificacion = "Bien"
    elif nota < 9:
        if nota < 8:
            calificacion = "Notable bajo"
        else:
            calificacion = "Notable alto"
    elif nota < 10:
        calificacion = "Sobresaliente"
    elif nota == 10:
        calificacion = "Matrícula de Honor. ENHORABUENA!!!"
    else:
        calificacion = "Error del sistema para la nota de la calificación."

    # Muestra un resumen en una ventana emergente.
    messagebox.showinfo("Resultado Final", f"Has acertado {aciertos} de 10.\n" f"Tu nota/puntaje final es de: {nota}/10\n" f"Calificación: {calificacion}")

    # Desactiva los botones y la entrada.
    entry_respuesta.config(state="disabled")
    btn_comprobar.config(state="disabled")


# Título principal.
label_titulo = tk.Label( root_windows, text = "Juego de Hiragana\nTraduce el carácter al español (romaji)", font = ("Arial", 16), pady = 10)
label_titulo.pack()

# Label donde se mostrará la imagen.
label_imagen = tk.Label(root_windows)
label_imagen.pack(pady = 10)

# Label de progreso (ejemplo: Imagen 1 de 10).
label_progreso = tk.Label(root_windows, text = "", font = ("Arial", 12))
label_progreso.pack()

# Cuadro de texto para escribir la respuesta.
entry_respuesta = tk.Entry(root_windows, font = ("Arial", 14), justify = "center")
entry_respuesta.pack(pady = 10)

# Botón para comprobar la respuesta.
btn_comprobar = tk.Button(root_windows, text = "Comprobar", font = ("Arial", 14), command = comprobar_respuesta)
btn_comprobar.pack(pady = 5)

# Mostrar la primera imagen al iniciar.
mostrar_imagen()

# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
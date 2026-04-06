import tkinter as tk    # Importa la biblioteca Tkinter y la renombra como 'tk'.
from tkinter import messagebox # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".




# Crea la ventana principal.
root_windows = tk.Tk()                      # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("CALCULADORA SIMPLE")    # Título de la ventana.
root_windows.geometry("500x500")            # Define el tamaño de la ventana (ancho x alto).
root_windows.resizable(False, False)        # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).


# Declara una variable para almacenar el valor del contador.
contador = tk.IntVar(value = 0) # Crea una variable entera (Integer Variable) inicializada en cero enlazada con los widgets.


# Crea la función de sumar.
def sumar():
    try:
        # Lee el texto de los "Entry" y los convierte en 'floats'.
        num1 = float(entry_num1.get()) # Lee/Toma el valor de la variable "entry_num1".
        num2 = float(entry_num2.get())

        contador.set(num1 + num2)       # La variable "contador" toma el nuevalor de operar los valores de las variables "num1" y "num2"
    except ValueError:
        messagebox.showerror("Error", "Por favor, ingrese valores números válidos o aceptados por el sistema.")


# Crea y configura widgets (etiquetas, botones, etc.).
label_titulo = tk.Label(root_windows, text = "Calculadora de sumas", font = ("Arial", 24))          # Etiqueta con/para el título.
label_num1 = tk.Label(root_windows, text = "Número 1", font = ("Arial", 20))                        # Etiqueta de texto para el primer número.
label_num2 = tk.Label(root_windows, text = "Número 2", font = ("Arial", 20))
entry_num1 = tk.Entry(root_windows, font = ("Arial", 20))                                            # Campo de texto vacío a la espera del usuario, donde el usuario inserta/escribe.
entry_num2 = tk.Entry(root_windows, font = ("Arial", 20))
button_sumar = tk.Button(root_windows, text = "➕\nSUMAR", font = ("Arial", 20), command = sumar)   # Botón que usa el/interactua con el usuario. Llama a la función "sumar".
Label_Resultado = tk.Label(root_windows, textvariable = contador, font=("Arial", 22), fg = "blue")   # Etiqueta de texto con texto variable para mostrar el resultaddo que se da al terminar el procedimiento de la función "sumar".


# Posiciona los widgets en la ventana.
label_titulo.pack(pady = 20)                        # Empaqueta/muestra la etiqueta con un espacio vertical (pady) de tamaño 10.
label_num1.pack()                                   # Empaqueta/muestra la etiqueta "Número 1".
entry_num1.pack(pady = 10)
label_num2.pack()
entry_num2.pack(pady = 10)
button_sumar.pack(fill = 'x', padx = 15, pady = 25) # Empaqueta/muestra el botón "Sumar" estirado horizontalmente (a lo ancho). Añadiendo un margen horizontal (izquierda/derecha) y un margen vertical (arriba/abajo), ambos de tamaño 15.
Label_Resultado.pack(pady = 30)


# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
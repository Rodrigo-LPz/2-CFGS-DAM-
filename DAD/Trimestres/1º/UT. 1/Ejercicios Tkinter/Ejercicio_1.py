import tkinter as tk    # Importa la biblioteca Tkinter y la renombra como 'tk'.




# Crea la ventana principal.
root_windows = tk.Tk()              # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("CONTADOR")      # Título de la ventana.
root_windows.geometry("500x250")    # Define el tamaño de la ventana (ancho x alto).


# Declara una variable para almacenar el valor del contador.
contador = tk.IntVar(value = 0) # Crea una variable entera (Integer Variable) inicializada en cero enlazada con los widgets.


# Crea las funciones.
def incrementar():
    contador.set(contador.get() + 1)

def decrementar():
    contador.set(contador.get() - 1)


# Crea y configura widgets (etiquetas, botones, etc.).
Label_Resultado = tk.Label(root_windows, textvariable = contador, font=("Arial", 24))   # Etiqueta con/para el título.
buttonIncr = tk.Button(root_windows, text = "➕\nIncrementar", command = incrementar)   # Botón que usa el/interactua con el usuario. Llama a la función "incrementar".
buttonDecr = tk.Button(root_windows, text = "➖\nDecrementar", command = decrementar)
button = tk.Button(root_windows, text = "Cerrar", command = root_windows.destroy)


# Posiciona los widgets en la ventana.
Label_Resultado.pack(pady = 10)                 # Empaqueta/muestra la etiqueta con un espacio vertical (pady) de tamaño 10.
buttonIncr.pack(fill='x', padx=150, pady=15)    # Empaqueta/muestra el botón "Incrementar" estirado horizontalmente (a lo ancho). Añadiendo un margen horizontal (izquierda/derecha) y un margen vertical (arriba/abajo), ambos de tamaño 150.
buttonDecr.pack(fill='x', padx=150, pady=15)
button.pack(pady = 10)


# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
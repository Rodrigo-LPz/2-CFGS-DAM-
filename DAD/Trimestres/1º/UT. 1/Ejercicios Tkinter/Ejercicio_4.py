import tkinter as tk    # Importa la biblioteca Tkinter y la renombra como 'tk'.
from tkinter import messagebox # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".




# Crea la ventana principal.
root_windows = tk.Tk()                      # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("Conversor km ⇄ millas")       # Título de la ventana.
root_windows.geometry("500x300")            # Define el tamaño de la ventana (ancho x alto).
root_windows.resizable(False, False)        # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).


# Constantes de conversión.
KM_POR_MILLA = 1.609344 # Declara una variable para conservar la constante de (1 milla = 1.609344 kilómetros).


# Variables vinculadas a los Entry (como texto).
km_var = tk.StringVar()     # "StringVar" para el campo de kilómetros.
millas_var = tk.StringVar() # "StringVar" para el campo de millas.


# Variables que hará la función de 'semáforo'/booleano. Para evitar actualizaciones recursivas
actualizando = {"flag": False} # Variable para decirle al programa "estoy cambiando el otro campo, no hacer nada todavía".
                               # Diccionario con flag mutable para compartir en closures.


# Crea la función de conversión (de km a millas).
def km_a_millas(km_text):
    try:
        km = float(km_text)             # Intenta convertir el texto a float.
        millas = km / KM_POR_MILLA      # Convierte kilómetros a millas.
        return format(millas, ".6g")    # Formatea con precisión razonable (evita notación científica fea).
    except (ValueError, TypeError):
        messagebox.showerror("Error", "Por favor, ingrese valores números válidos o aceptados por el sistema.")
        return ""                       #Si no es número válido, devuelve cadena vacía.

def millas_a_km(millas_text):
    try:
        millas = float(millas_text)
        km = millas * KM_POR_MILLA
        return format(km, ".6g")
    except (ValueError, TypeError):
        messagebox.showerror("Error", "Por favor, ingrese valores números válidos o aceptados por el sistema.")
        return ""                       #Si no es número válido, devuelve cadena vacía.
    
# Crea 'Callbacks' que se ejecutan cuando cambia una variable. Funciones dependientes de qu eel uusuario interactue cambiando los valores de las anteriores variable srecogidas por las funciones creadas.
    # (Función que se pasa como argumento a otra función para que se ejecute más tarde, generalmente después de que se haya completado una tarea).
def on_km_change(*args):
    # Condicional "if" éste será el semáforo para evitar bucles infinitos:
        # Si ya estamos actualizando desde código, si cambiamos el valor de millas_var, le decimos que no haga nada evitando así un bucle infinito. Salimos para evitar bucle.
    if actualizando["flag"]:
        return
    
    actualizando["flag"] = True           # Marcamos que vamos a actualizar programáticamente. Es decir, activamos el semáforo para indicar que es factible hacer la operación.
    km_text = km_var.get()                # Tomamos el texto actual del campo km.
    millas_var.set(km_a_millas(km_text))  # Calculamos y seteamos valor en millas_var (puede quedar vacío).
    actualizando["flag"] = False          # Una vez hecho todo volvemos a la bandera/semáforo y la desactivamos.

def on_millas_change(*args):
    if actualizando["flag"]:
        return
    actualizando["flag"] = True
    millas_text = millas_var.get()
    km_var.set(millas_a_km(millas_text))
    actualizando["flag"] = False


# Asociar trace (observadores) a las StringVar. Lo que permite que cada vez que cambie el campo de 'km' o 'millas' se llame automáticamente a la función correspondiente para actualizar el otro campo.
km_var.trace_add("write", on_km_change)         # Llama a "on_km_change" cuando "km_var" cambie.
millas_var.trace_add("write", on_millas_change) # Llama "on_millas_change" cuando "millas_var" cambie.


# Crea y configura widgets (etiquetas, botones, etc.).
label_titulo = tk.Label(root_windows, text = "Conversor km ⇄ millas", font = ("Arial", 18))   # Etiqueta con/para el título.

frame = tk.Frame(root_windows, padx = 20, pady = 15)                                    # Frame contenedor con padding para ordenar mejor.
frame.pack(fill = "both", expand = True)

label_km = tk.Label(frame, text = "Kilómetros (Km):", font = ("Arial", 12))             # Etiqueta "Kilómetros".
entry_km = tk.Entry(frame, textvariable = km_var, font = ("Arial", 14))                 # Entry vinculado a "km_var".

label_millas = tk.Label(frame, text = "Millas (Mi):", font = ("Arial", 12))             # Etiqueta "Millas".
entry_millas = tk.Entry(frame, textvariable = millas_var, font = ("Arial", 14))         # Entry vinculado a "millas_var".


# Botones auxiliares para mayor interactividad con el usuario final.
def limpiar():
    actualizando["flag"] = True
    km_var.set("")
    millas_var.set("")
    actualizando["flag"] = False

btn_limpiar = tk.Button(frame, text = "Limpiar", command = limpiar)        # 31. Botón para limpiar ambos campos.

def intercambiar():
    # Tomamos valores actuales (texto).
    km = km_var.get()
    mi = millas_var.get()
    
    # Intercambiamos: ponemos las millas en el campo de los kilómetros y viceversa, pero como las unidades cambian, convertimos correctamente.
    actualizando["flag"] = True

    try:
        # Caso 1: Si ambos campos (kilómetros y millas) están vacíos. No hay nada que hacer.
        if not km and not mi:
            actualizando["flag"] = False
            messagebox.showerror("Error", "Por favor, ingrese valores números válidos o aceptados por el sistema.")
            return
        
        # Caso 2: Si sólo el campo "km" (kilómetro) no está vacío. Convirtiendo a "Mi" (millas).
        if km and not mi:
            km_val = float(km)
            millas_var.set(format(round(km_val / KM_POR_MILLA, 6), ".6g"))
            km_var.set("")
            
        # Caso 3 (Inverso/Opuesto al "Caso 2"): Si sólo el campo "Mi" (millas) no está vacío. Convirtiendo a "Km" (kilómetros).
        elif mi and not km:
            mi_val = float(mi)
            km_var.set(format(round(mi_val * KM_POR_MILLA, 6), ".6g"))
            millas_var.set("")
            
        # Caso 4: Si hay valores leídos para ambos valores, se intercambian sus valores numéricos.
        else:
            km_val = float(km)  # Convertimos el valor de km a float.
            km_var.set("")      # Primero limpiamos el campo km.
            # IMPORTANTE: Desactivamos el semáforo ANTES de resetear las millas para que el trace pueda calcular automáticamente los nuevos km.
            actualizando["flag"] = False
            # Ponemos el valor de km en el campo millas. El trace automáticamente calculará los nuevos km.
            millas_var.set(format(km_val, ".6g"))
            return  # Salimos aquí porque ya desactivamos el semáforo

    except ValueError:
        messagebox.showerror("Error", "Por favor, ingrese valores números válidos o aceptados por el sistema.")
    
    # Una vez hecho el intercambio cerramos el semáforo.
    actualizando["flag"] = False

btn_intercambiar = tk.Button(frame, text = "Convertir (Intercambiar)", command = intercambiar) # 33. Botón para intercambiar/conmutar valores.


# Posiciona los widgets en la ventana.
label_titulo.pack(pady = 25)                                                                # Empaqueta/muestra la etiqueta con un espacio vertical (pady) de tamaño 25.
label_km.grid(row = 0, column = 0, sticky = "w", padx = (0,10), pady = (0,8))               # Empaqueta/muestra la etiqueta con un espacio vertical (pady) posicionandolo al oeste ,'w' = west.
entry_km.grid(row = 0, column = 1, sticky = "ew", pady = (0,8))                                                             # 'ew', se estira horizontalmente dentro de su celda.
label_millas.grid(row = 1, column = 0, sticky = "w", padx = (0,10), pady = (0,8))
entry_millas.grid(row = 1, column = 1, sticky = "ew", pady = (0,8))
btn_limpiar.grid(row = 2, column = 0, columnspan = 1, sticky = "ew", pady = (10,0))
btn_intercambiar.grid(row = 2, column = 1, columnspan = 1, sticky = "ew", pady = (10,0))

frame.columnconfigure(1, weight = 1)                                                        # Permite que la columna 1 (entries) se estire ocupando espacio.


# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
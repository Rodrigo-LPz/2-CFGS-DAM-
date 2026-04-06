import tkinter as tk    # Importa la biblioteca Tkinter y la renombra como 'tk'.
from tkinter import messagebox # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".




# Crea la ventana principal.
root_windows = tk.Tk()                      # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("Lista de Tareas")       # Título de la ventana.
root_windows.geometry("500x500")            # Define el tamaño de la ventana (ancho x alto).
root_windows.resizable(False, False)        # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).


# Crea la función de agrgar tareas.
def agregarTarea():
    tarea = entry_tarea.get()                # Declara una variable para obtener el texto que haya insertado/escrito el usuario.
    if tarea != "":                          # Comprueba que la tarea insertada no sea o no esté vacia.
        listbox_tareas.insert(tk.END, tarea) # Inserta esta nueva tarea al final de la lista.
        entry_tarea.delete(0, tarea.END)     # Limpia el campo de entrada (solo el texto que escribiste). Esto sirve para limpiar el campo, lo vuelve a dejar vacío, y así permitir una nueva inserción de tarea.
    else:
        messagebox.showerror("Error", "No puede insertar tareas vacías.")

# Crea la función para eliminar la tarea seleccionada.
def eliminar_tarea():
    seleccion = listbox_tareas.curselection()   # Declara una variable para obtener el índice de la tarea seleccionada (si hay).
    if seleccion:                               # Comprueba que la tarea insertada exista.
        listbox_tareas.delete(seleccion)        # Elimina esa tarea de la lista.
    else:
        messagebox.showerror("Error", "Debe seleccionar una tarea existente para su eliminación/borrado.")


# Crea y configura widgets (etiquetas, botones, etc.).
label_titulo = tk.Label(root_windows, text = "Lista de Tareas", font = ("Arial", 18))                           # Etiqueta con/para el título.
entry_tarea = tk.Entry(root_windows, font = ("Arial", 14))                                                      # Etiqueta de texto para el primer número.
button_agregar = tk.Button(root_windows, text = "➕\nAgregar", font = ("Arial", 14), command = agregarTarea)    # Botón que usa el/interactua con el usuario. Llama a la función "agregarTarea".
button_eliminar = tk.Button(root_windows, text = "🗑️\nEliminar", font = ("Arial", 14), command = eliminar_tarea)
listbox_tareas = tk.Listbox(root_windows, font = ("Arial", 14), height = 10, selectmode = tk.SINGLE)            # Lista donde se muestran las tareas.
                                                                                                                # "selectmode = tk.SINGLE" → Indica/le dice a la lista la forma que tiene de permitir cómo se pueden seleccionar sus objetos. En este caso sólo se puede seleccionar una tarea a la vez, de una en una.


# Posiciona los widgets en la ventana.
label_titulo.pack(pady = 25)                                            # Empaqueta/muestra la etiqueta con un espacio vertical (pady) de tamaño 10.
entry_tarea.pack(pady = 10)                                         
button_agregar.pack(fill = 'x', padx = 100, pady = 5)                   # Empaqueta/muestra el botón "Agregar" estirado horizontalmente (a lo ancho). Añadiendo un margen horizontal (izquierda/derecha) y un margen vertical (arriba/abajo), ambos de tamaño 100.
button_eliminar.pack(fill = 'x', padx = 100, pady = 5)
listbox_tareas.pack(padx = 50, pady = 10, fill = 'both', expand = True) # Lista de tareas: se estira en ambas direcciones ['fill = both' (horizontal y verticalmente)] y ocupa el espacio disponible ('expand = True').


# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
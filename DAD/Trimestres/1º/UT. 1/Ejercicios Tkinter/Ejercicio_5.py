import tkinter as tk    # Importa la biblioteca Tkinter y la renombra como 'tk'.
from tkinter import messagebox # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".




# Crea la ventana principal.
root_windows = tk.Tk()                      # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("Gestor de Contactos")       # Título de la ventana.
root_windows.geometry("500x800")            # Define el tamaño de la ventana (ancho x alto).
root_windows.resizable(False, False)        # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).


# Crea la lista donde guardar/almacenar los contactos. Cada contacto será un diccionario: {"nombre": ..., "telefono": ...}.
contactos = []


# Funciones CRUD (Crear, Leer, Actualizar, Eliminar).
    # Crea la función de agrgar contactos (Crear).
def agregarContacto():
    nombre = entry_nombre.get().strip()      # Obtiene el nombre escrito y quita espacios.
    telefono = entry_telefono.get().strip()  # Obtiene el teléfono escrito y quita espacios.

    if nombre == "" or telefono == "":       # Comprobamos que ambos campos estén llenos.
        messagebox.showerror("Error", "Debes ingresar al menos un nombre y un teléfono de contacto.")
        return
    
    for contacto in contactos:
        if contacto["telefono"] == telefono:        # Comprobamos/Verificamos si ya existe un contacto con el mismo número de teléfono.
            messagebox.showerror("Error", f"El contacto introducido con el número de teléfono {telefono} no es válido. Éste número de teléfono {telefono} ya está registrado para otro contacto.")
            return  # Sale de la función sin agregar el contacto repetido.
    
    contacto = {"nombre": nombre, "telefono": telefono}         # Creamos un diccionario del contacto.
    contactos.append(contacto)                                  # Agregamos a la lista "contactos" el nuevo contacto (con nombre y teléfono).
    listbox_contactos.insert(tk.END, f"{nombre} - {telefono}")  # Lo mostramos en la lista.
    entry_nombre.delete(0, tk.END)                              # Limpiamos los campos.
    entry_telefono.delete(0, tk.END)

    # Crea la función de eliminar contactos (Eliminar).
def eliminarContacto():
    seleccion = listbox_contactos.curselection()
    if not seleccion:  # No hay contacto seleccionado para eliminar.
        messagebox.showerror("Error", "Debes seleccionar el contacto a eliminar")
        return

    indice = seleccion[0]
    listbox_contactos.delete(indice)  # Lo quitamos del Listbox.
    contactos.pop(indice)             # Lo quitamos de la lista interna.

    # Crea la función de actualizar contactos (Actualizar).
def actualizarContacto():
    seleccion = listbox_contactos.curselection()
    if not seleccion:
        messagebox.showerror("Error", "Debes seleccionar un contacto para actualizar")
        return

    indice = seleccion[0]
    nombre = entry_nombre.get().strip()
    telefono = entry_telefono.get().strip()

    if nombre == "" or telefono == "":
        messagebox.showerror("Error", "Debes ingresar un nombre y un teléfono")
        return

    contactos[indice] = {"nombre": nombre, "telefono": telefono}    # Actualizamos en la lista interna.
    listbox_contactos.delete(indice)                                # Actualizamos en el Listbox [eliminamos e insertamos (los nuevos datos)].
    listbox_contactos.insert(indice, f"{nombre} - {telefono}")
    entry_nombre.delete(0, tk.END)                                  # Limpiamos los campos.
    entry_telefono.delete(0, tk.END)

    # Crea la función de cargar contactos (cargar) (muestreo y lectura de datos).
def cargarContacto(event):
    seleccion = listbox_contactos.curselection()
    if seleccion:
        indice = seleccion[0]
        contacto = contactos[indice]
        entry_nombre.delete(0, tk.END)
        entry_nombre.insert(0, contacto["nombre"])
        entry_telefono.delete(0, tk.END)
        entry_telefono.insert(0, contacto["telefono"])


# Crea y configura widgets (etiquetas, botones, etc.).
label_titulo = tk.Label(root_windows, text = "Gestor de Contactos", font = ("Arial", 18))   # Etiqueta con/para el título.
label_nombre = tk.Label(root_windows, text = "Nombre: ")
entry_nombre  = tk.Entry(root_windows)
label_telefono  = tk.Label(root_windows, text = "Teléfono: ")
entry_telefono  = tk.Entry(root_windows)
entry_tarea = tk.Entry(root_windows, font = ("Arial", 14))                                                  # Etiqueta de texto para el primer número.
button_agregar = tk.Button(root_windows, text = "➕\nAgregar", font = ("Arial", 14), command = agregarContacto)     # Botón que usa el/interactua con el usuario. Llama a la función "agregarTarea".
button_eliminar = tk.Button(root_windows, text = "🗑️\nEliminar", font = ("Arial", 14), command = eliminarContacto)
button_actualizar = tk.Button(root_windows, text = "🔄\nActualizar", font = ("Arial", 14), command = actualizarContacto)
button_cargar = tk.Button(root_windows, text = "⬇️\nCargar (Imprimir-Leer)", font = ("Arial", 14), command = eliminarContacto)
listbox_contactos = tk.Listbox(root_windows, height=10)


# Posiciona los widgets en la ventana.
label_titulo.pack(pady = 10)                                            # Empaqueta/muestra la etiqueta con un espacio vertical (pady) de tamaño 10.
label_nombre.pack()
entry_nombre.pack(pady = 5)
label_telefono.pack()
entry_telefono.pack(pady = 5)
button_agregar.pack(fill = 'x', padx = 50, pady = 5)                   # Empaqueta/muestra el botón "Agregar" estirado horizontalmente (a lo ancho). Añadiendo un margen horizontal (izquierda/derecha) y un margen vertical (arriba/abajo), ambos de tamaño 100.
button_eliminar.pack(fill = 'x', padx = 50, pady = 5)
button_actualizar.pack(fill = 'x', padx = 50, pady = 5)
button_cargar.pack(fill = 'x', padx = 50, pady = 5)
listbox_contactos.pack(padx = 20, pady = 10, fill = 'both', expand = True) # Lista de tareas: se estira en ambas direcciones ['fill = both' (horizontal y verticalmente)] y ocupa el espacio disponible ('expand = True').
listbox_contactos.bind("<<ListboxSelect>>", cargarContacto)  # Cuando se selecciona un contacto


# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
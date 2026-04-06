# Interfaz gráfica de usuario (GUI) en "Tkinter" para poder visualizar los datos de la aplicación de tablas interactivas y exportarlos mediante botones.
import tkinter as tk                    # Importa la biblioteca Tkinter y la renombra como 'tk'.
import pandas as pd                     # Importa la biblioteca "Pandas" para manejar datos en estructuras de DataFrame. (Un "DataFrame" es una estructura de datos bidimensional parecida a una hoja de cálculo o tabla, con filas y columnas, donde cada columna puede contener diferentes tipos de datos (números, texto, etc.) y cada fila representa una observación)
import ctypes                           # Importa la biblioteca "ctypes" para interactuar con bibliotecas de bajo nivel y funciones del sistema operativo.

from tkinter import ttk, messagebox     # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".
from db_connection import connection    # Importa la función de conexión "connection" a la base de datos desde el módulo/archivo "db_connection.py".



#Función/Método para consultar datos de la base de datos y devolverlos en un DataFrame de "Pandas".
def consultarDatos(host, database, user, password):
    try:
        # Establece la conexión a la base de datos.
        conn = connection(host, database, user, password)
        
        # Si la conexión es exitosa, realiza la consulta.
        if conn is not None:
            # Consulta SQL para obtener los datos de las tablas deseadas ("MEDICOS" y "CITAS") de la tabla ("HOSPITAL").
            querySQL = """
                            SELECT m.COD_MEDICO, m.NOMBRE_COMPLETO, m.ESPECIALIDAD, m.TURNO, m.CONSULTAS_DISPONIBLES_LUNES, m.CONSULTAS_DISPONIBLES_MARTES, m.CONSULTAS_DISPONIBLES_MIERCOLES, m.CONSULTAS_DISPONIBLES_JUEVES, m.CONSULTAS_DISPONIBLES_VIERNES, m.ANOS_EXPERIENCIA, c.NUM_CITA, c.COD_MEDICO, c.FECHA_CITA, c.HORA_CITA, c.MODALIDAD, c.URGENTE, c.ESTADO
                            FROM MEDICOS m
                            LEFT JOIN CITAS c ON m.COD_MEDICO = c.COD_MEDICO
                            ORDER BY m.COD_MEDICO, c.NUM_CITA
                       """
            
            # Utiliza "Pandas" para ejecutar la consulta y cargar los datos en un "DataFrame".
            df = pd.read_sql(querySQL, conn)
            
            # Una vez terminada la conexión y su posterior consulta cierra la conexión a la base de datos.
            conn.close()

            # Devuelve el "DataFrame" con los datos consultados, obtenidos de la consulta.
            #return df
            
            # Limpia la tabla antes de insertar nuevos datos para que estos no se dupliquen al volver a cargar.
            for item in tree.get_children():
                tree.delete(item)
            
            # Inserta los datos del" DataFrame" en el "Treeview".
                # Por cada fila del "DataFrame", ésta se convierte en lista ("list(now)") y después se inserta en la tabla visual "Treeview".
            for _, row in df.iterrows():
                #tree.insert("", "end", values = list(row))
                tree.insert("", "end", values = [str(item) for item in row])  # Para evitar errores de tipo (de valores) convertimos cada elemento de la fila a cadena, a "String", antes de insertarlo.
        else:
            raise Exception("\n\n\tNo se pudo establecer la conexión a la base de datos.")  # Lanza una excepción si no se pudo conectar a la base de datos.
    except Exception as ex:
        # Si ocurre un error durante el proceso de ejecución de la consulta, capturamos la excepción y mostramos un mensaje de error.
        #print(f"\n\n\tError inesperado al intentar conectarse a la base de datos '" + {db_name} + "': {er}")
        #raise Exception(f"\n\n\tError inesperado al intentar conectarse a la base de datos '" + {db_name} + "': {er}")
        #messagebox.showerror("Error de ejecución", f"Error inesperado al intentar ejecutar la consulta de datos de las tablas a la base de datos: {e}.")
        ctypes.windll.user32.MessageBoxW(0, f"Error inesperado al intentar ejecutar la consulta de datos de las tablas a la base de datos: {ex}", "Error de ejecución", 0x10)      # Muestra un mensaje de error si ocurre una excepción durante la ejecución de la consulta.
        return None                                                                                                                                                                # Devuelve "None" si hubo un error durante la ejecución de la consulta.


# Función/Método para abrir la ventana emergente auxiliar/secundaria con las opciones o tipos de exportación de los datos a un archivo PDF o Excel.
#def export_data_to_pdf(host, database, user, password):
#    try:
#        from export_pdf import export_data_to_pdf           # Importa la función "export_data_to_pdf" desde el módulo/archivo "export_pdf.py".
#        export_data_to_pdf(host, database, user, password)  # Llama a la función para exportar los datos a PDF.
#    except Exception as ex:
#        ctypes.windll.user32.MessageBoxW(0, f"Error inesperado al intentar exportar los datos a PDF: {ex}", "Error de exportación", 0x10)   # Muestra un mensaje de error si ocurre una excepción durante la exportación.
#        return None                                                                                                                         # Devuelve "None" si hubo un error durante la exportación de datos a PDF.
# Función/Método para abrir la ventana emergente auxiliar/secundaria con las opciones o tipos de exportación de los datos a un archivo PDF o Excel.
def exportations_windows():
    # Crea una nueva ventana ("Toplevel").
    windows_export = tk.Toplevel(root_windows)      # Crea una ventana secundaria (emergente) encima de la ventana principal.
    windows_export.title("Opciones de Exportación") # Título de la ventana.
    windows_export.geometry("500x550")              # Define el tamaño de la ventana (ancho x alto).
    windows_export.configure(bg = "#34495E")      # Cambia el color de fondo de la ventana secundaria.
    windows_export.resizable(False, False)          # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).
    
    # Centra la ventana en la pantalla.
    windows_export.transient(root_windows)  # Hace que la ventana secundaria esté siempre encima de la ventana principal.
    windows_export.grab_set()               # Hace que la ventana secundaria capture todos los eventos (impide interactuar con la ventana principal hasta que se cierre la secundaria).
    root_windows.update_idletasks()         # Actualiza la ventana principal para obtener sus dimensiones.
    
    # Título de la ventana.
    titulo = tk.Label(windows_export, text = "Seleccione el tipo de exportación", font = ("Arial", 14, "bold"), bg = "#34495E", fg = "white")   # Crea una etiqueta (label) como título de la ventana secundaria.
    titulo.pack(pady = 20)                                                                                                                        # Empaqueta/Muestra la etiqueta (label) con un margen vertical (arriba/abajo) de tamaño 20.
    
    # Frame para el botón de exportación completa.
    frame_completo = tk.Frame(windows_export, bg = "#34495E")             # Crea un marco (frame) para contener el botón de exportación completa.
    frame_completo.pack(fill = "x", padx = 20, pady = 10, expand = True)    # Empaqueta/Muestra el marco (frame) con un margen horizontal (izquierda/derecha) de tamaño 20 y un margen vertical (arriba/abajo) de tamaño 10.
    
    # Botón de exportar todo.
    btn_exportar_todo = tk.Button(frame_completo, text = "📄\nExportar Todos los Médicos y Citas", font = ("Arial", 12), bg = "#3498DB", fg = "white", activebackground = "#229954", activeforeground = "white", relief = "raised", bd = 3, command = lambda: complete_export(windows_export))    # Crea el botón para exportar todos los médicos y citas.
    btn_exportar_todo.pack(fill = "x", ipady = 10)                                                                                                                                                                                                                                                       # Empaqueta/Muestra el botón estirado horizontalmente (a lo ancho) dentro del marco (frame).
    
    # Separador
    separador = tk.Label(windows_export, text = "───────────────────", font = ("Arial", 10), bg = "#34495E", fg = "white")  # Crea una etiqueta (label) como separador visual.
    separador.pack(pady = 10)                                                                                                 # Empaqueta/Muestra la etiqueta (label) con un margen vertical (arriba/abajo) de tamaño 10.

    # Frame para exportación filtrada.
    frame_filtrado = tk.LabelFrame(windows_export, text = " Exportar Médico(s) Específico(s) ", font = ("Arial", 11, "bold"), bg = "#34495E", fg = "white", relief = "groove", bd = 2)  # Crea un marco con borde y título para la sección de exportación filtrada.
    frame_filtrado.pack(fill = "both", padx = 20, pady = 10, expand = True)                                                                                                               # Empaqueta/Muestra el marco (frame) con un margen horizontal (izquierda/derecha) de tamaño 20 y un margen vertical (arriba/abajo) de tamaño 10.
    
    # Campos de entrada.
    frame_inputs = tk.Frame(frame_filtrado, bg = "#34495E")
    frame_inputs.pack(pady = 15, padx = 10)
    
    # Campo para código de médico inicial.
    tk.Label(frame_inputs, text = "Código Médico Inicial:", font = ("Arial", 10), bg = "#34495E", fg="white").grid(row = 0, column = 0, sticky = "w", pady = 5) # Etiqueta (label) para el código de médico inicial.
    entry_inicio = tk.Entry(frame_inputs, font = ("Arial", 10), width = 15)                                                                                       # Campo de entrada (entry) para el código de médico inicial.
    entry_inicio.grid(row = 0, column = 1, padx = 10, pady = 5)                                                                                                   # Empaqueta/Muestra el campo de entrada en la cuadrícula (grid).
    
    # Campo para código de médico final (opcional).
    tk.Label(frame_inputs, text = "Código Médico Final:", font = ("Arial", 10), bg = "#34495E", fg = "white").grid(row = 1, column = 0, sticky = "w", pady = 5) # Etiqueta (label) para el código de médico final.
    entry_fin = tk.Entry(frame_inputs, font = ("Arial", 10), width = 15)                                                                                          # Campo de entrada (entry) para el código de médico final.
    entry_fin.grid(row = 1, column = 1, padx = 10, pady = 5)                                                                                                      # Empaqueta/Muestra el campo de entrada en la cuadrícula (grid).

    # Etiqueta informativa para el campo opcional.
    tk.Label(frame_inputs, text = "(opcional - dejar vacío para exportar hasta el final del listado)", font = ("Arial", 8, "italic"), bg = "#34495E", fg = "#BDC3C7").grid(row = 2, column = 1, sticky = "w")
    
    # Frame para botones de formato.
    frame_formato = tk.Frame(frame_filtrado, bg = "#34495E")
    frame_formato.pack(fill = "x", padx = 10, pady = 5)

    # Botón de exportar filtrado.
    #btn_exportar_filtrado = tk.Button(frame_filtrado, text = "📊\nExportar Selección", font = ("Arial", 11), bg = "#3498DB", fg = "white", activebackground = "#2E86C1", activeforeground = "white", relief = "raised", bd = 3, command = lambda: filtered_export(windows_export, entry_inicio.get(), entry_fin.get()))    # Crea el botón de exportar filtrado.
    #btn_exportar_filtrado.pack(fill = "x", padx = 10, pady = 10, ipady = 8)                                                                                                                                                                                                                                                       # Empaqueta/Muestra el botón estirado horizontalmente (a lo ancho) dentro del marco (frame).
    
    # Botón de exportar a PDF filtrado.
    btn_exportar_pdf = tk.Button(frame_formato, text = "📄\nExportar a PDF", font = ("Arial", 10), bg = "#E74C3C", fg = "white", activebackground = "#C0392B", activeforeground = "white", relief = "raised", bd = 3, command = lambda: filtered_export(windows_export, entry_inicio.get(), entry_fin.get(), "PDF"))
    btn_exportar_pdf.pack(side = "left", fill = "x", expand = True, padx = (0, 5), ipady = 8)
    
    # Botón de exportar a Excel filtrado.
    btn_exportar_excel = tk.Button(frame_formato, text = "📊\nExportar a Excel", font = ("Arial", 10), bg = "#27AE60", fg = "white", activebackground = "#229954", activeforeground = "white", relief = "raised", bd = 3, command = lambda: filtered_export(windows_export, entry_inicio.get(), entry_fin.get(), "EXCEL"))
    btn_exportar_excel.pack(side = "left", fill = "x", expand = True, padx = (5, 0), ipady = 8)

    # Botón de cancelar.
    btn_cancelar = tk.Button(windows_export, text = "❌\nCancelar", font = ("Arial", 10), bg = "#95A5A6", fg = "white", activebackground = "#7F8C8D", activeforeground = "white", command = windows_export.destroy) # Crea el botón de cancelar para cerrar la ventana secundaria.
    btn_cancelar.pack(fill = "x", padx = 20, pady = 10, ipady = 5)                                                                                                                                                       # Empaqueta/Muestra el botón estirado horizontalmente (a lo ancho) dentro de la ventana secundaria.


# Función/Método para exportar todos los datos a un archivo PDF.
#def complete_export(windows):
#    try:
#        from export_pdf import export_data_to_pdf                                   # Importa la función "export_data_to_pdf" desde el módulo/archivo "export_pdf.py".
#        archive_pdf = export_data_to_pdf ("localhost", "HOSPITAL", "root", "root")  # Llama a la función para exportar los datos a PDF.
#        
#        # Cierra la ventana secundaria después de la exportación.
#        windows.destroy()
#        
#        # Muestra un mensaje de confirmación al usuario.
#        messagebox.showinfo("Exportación Completa/Exitosa", f"Los datos han sido exportados exitosamente al archivo: '{archive_pdf}'")
#    except Exception as ex:
#        ctypes.windll.user32.MessageBoxW(0, f"Error inesperado al intentar exportar los datos a PDF: {ex}", "Error de exportación", 0x10)   # Muestra un mensaje de error si ocurre una excepción durante la exportación.
#        return None                                                                                                                         # Devuelve "None" si hubo un error durante la exportación de datos a PDF.
# Función/Método para exportar todos los datos a un archivo PDF o Excel.
def complete_export(windows):
    # Crear ventana de selección de formato
    format_window = tk.Toplevel(windows)
    format_window.title("Seleccionar Formato")  # Título de la ventana.
    format_window.geometry("400x350")           # Define el tamaño de la ventana (ancho x alto).
    format_window.configure(bg = "#34495E")   # Cambia el color de fondo de la ventana secundaria.
    format_window.resizable(False, False)       # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).
    
    # Centrar la ventana en la pantalla.
    format_window.transient(windows)    # Hace que la ventana secundaria esté siempre encima de la ventana principal.
    format_window.grab_set()            # Hace que la ventana secundaria capture todos los eventos (impide interactuar con la ventana principal hasta que se cierre la secundaria).
    
    # Título de la ventana.
    tk.Label(format_window, text = "Seleccione el formato de exportación:", font = ("Arial", 12, "bold"), bg = "#34495E", fg = "white").pack(pady = 20)
    
    # Frame para botones.
    frame_btns = tk.Frame(format_window, bg = "#34495E")    # Crea un marco (frame) para contener los botones de formato.
    frame_btns.pack(pady = 20)                                # Empaqueta/Muestra el marco (frame) con un margen vertical (arriba/abajo) de tamaño 20.
    
    # Botón PDF.
    btn_pdf = tk.Button(frame_btns, text = "📄\nExportar a PDF", font = ("Arial", 12), bg = "#E74C3C", fg = "white", activebackground = "#C0392B", activeforeground = "white", relief = "raised", bd = 3, width = 15, command = lambda: execute_complete_export(format_window, windows, "PDF"))  # Crea el botón para exportar a PDF.
    btn_pdf.pack(pady = 10, ipady = 10)                                                                                                                                                                                                                                                               # Empaqueta/Muestra el botón con un margen vertical (arriba/abajo) de tamaño 10.
    
    # Botón Excel.
    btn_excel = tk.Button(frame_btns, text = "📊\nExportar a Excel", font = ("Arial", 12), bg = "#27AE60", fg = "white", activebackground = "#229954", activeforeground = "white", relief = "raised", bd = 3, width = 15, command = lambda: execute_complete_export(format_window, windows, "EXCEL"))  # 
    btn_excel.pack(pady = 10, ipady = 10)                                                                                                                                                                                                                                                                   # Empaqueta/Muestra el botón con un margen vertical (arriba/abajo) de tamaño 10.
    
    # Botón Cancelar.
    btn_cancel = tk.Button(frame_btns, text = "Cancelar", font = ("Arial", 10), bg = "#95A5A6", fg = "white", activebackground = "#7F8C8D", activeforeground = "white", command = format_window.destroy)
    btn_cancel.pack(pady = 10)  # Empaqueta/Muestra el botón con un margen vertical (arriba/abajo) de tamaño 10.


# Función/Método para ejecutar la exportación completa según el formato seleccionado.
def execute_complete_export(format_window, windows, formato):
    try:
        if formato == "PDF":
            from export_pdf import export_data_to_pdf
            archive = export_data_to_pdf("localhost", "HOSPITAL", "root", "root")
        else:  # EXCEL
            from export_excel import export_data_to_excel
            archive = export_data_to_excel("localhost", "HOSPITAL", "root", "root")
        
        # Cierra ambas ventanas
        format_window.destroy()
        windows.destroy()
        
        # Muestra un mensaje de confirmación al usuario.
        messagebox.showinfo("Exportación Completa/Exitosa", f"Los datos han sido exportados exitosamente al archivo:\n\n'{archive}'")
    except Exception as ex:
        ctypes.windll.user32.MessageBoxW(0, f"Error inesperado al intentar exportar los datos: {ex}", "Error de exportación", 0x10)
        return None


# Función/Método para exportar todos los datos filtrados a un archivo PDF.
def filtered_export(windows, cod_inicio, cod_fin, formato):
    try:
        # Valida/Comprueba que al menos se ingresó el código inicial, el médico inicial. Si no es así, muestra una advertencia y no continúa con la exportación.
        if not cod_inicio.strip():
            messagebox.showwarning("Advertencia", "Debe ingresar al menos el código del médico inicial.")
            return
        
        # Convierte los códigos ingresados a valores adecuados, a los tipos correctos.
        cod_inicio_val = cod_inicio.strip()                         # Convierte el código inicial a valor adecuado (elimina espacios en blanco).
        cod_fin_val = cod_fin.strip() if cod_fin.strip() else None  # Convierte el código final a valor adecuado (elimina espacios en blanco) o lo establece como "None" si está vacío.
        
        #from export_pdf import export_data_to_pdf                                                               # Importa la función "export_data_to_pdf" desde el módulo/archivo "export_pdf.py".
        #archive_pdf = export_data_to_pdf ("localhost", "HOSPITAL", "root", "root", cod_inicio_val, cod_fin_val) # Llama a la función para exportar los datos a PDF junto a/con los códigos filtrados.

        # Comprueba/Valida que la selección del usuario del formato de exportación sea PDF. En cuyo caso llama a la función de exportación a PDF, "export_data_to_pdf", del archivo "export_pdf.py".
        if formato == "PDF":
            from export_pdf import export_data_to_pdf
            archive_pdf = export_data_to_pdf("localhost", "HOSPITAL", "root", "root", cod_inicio_val, cod_fin_val)
        # En caso contrario, si el formato seleccionado es Excel, llama a la función de exportación a Excel, "export_data_to_excel", del archivo "export_excel.py".
        else:
            from export_excel import export_data_to_excel
            archive_pdf = export_data_to_excel("localhost", "HOSPITAL", "root", "root", cod_inicio_val, cod_fin_val)
        
        # Cierra la ventana secundaria después de la exportación.
        windows.destroy()

        # Mensaje personalizado según el tipo de exportación realizada.
        if cod_fin_val: mensaje = f"Médicos desde {cod_inicio_val} hasta {cod_fin_val}."
        else: mensaje = f"Médico {cod_inicio_val} en adelante."
 
        # Muestra un mensaje de confirmación al usuario.
        messagebox.showinfo("Exportación Completa/Exitosa", f"\t{mensaje}\n\nExportado al archivo: '{archive_pdf}'")
    except Exception as ex:
        ctypes.windll.user32.MessageBoxW(0, f"Error inesperado al intentar exportar los datos: {ex}", "Error de exportación", 0x10)   # Muestra un mensaje de error si ocurre una excepción durante la exportación.
        return None                                                                                                                         # Devuelve "None" si hubo un error durante la exportación de datos a PDF.


# Configuración de la ventana principal de la aplicación.
    # Crea la ventana principal.
root_windows = tk.Tk()                      # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("Gestor de Informes")    # Título de la ventana.
root_windows.geometry("750x450")            # Define el tamaño de la ventana (ancho x alto).
root_windows.configure(bg = "#2C3E50")    # Cambia el color de fondo de la ventana principal
#root_windows.resizable(False, False)       # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).

# Frame para colocar los botones lado a lado.
frame_botones = tk.Frame(root_windows, bg = "#2C3E50")  # Crea un marco (frame) para contener los botones.
frame_botones.pack(fill = 'x', padx = 20, pady = 15)      # Empaqueta/Muestra el marco (frame) con un margen vertical (arriba/abajo) de tamaño 15.

# Crea y configura widgets (etiquetas, botones, etc.).
button_cargar = tk.Button(frame_botones, text = "🔄️\nCargar Datos", font = ("Arial", 20), command = lambda:consultarDatos("localhost", "HOSPITAL", "root", "root")) # Botón que usa el/interactua con el usuario. Llama a la función "consultarDatos".
button_cargar.pack(side = "left", fill = "x", expand = True, padx = (0, 20))                                                                                         # Empaqueta/Muestra el botón "consultarDatos" estirado horizontalmente (a lo ancho). Añadiendo un margen horizontal (izquierda/derecha) con tamaño 50 y un margen vertical (arriba/abajo) con tamaño 15.

button_exportar = tk.Button(frame_botones, text = "⏬\nExportar Datos", font = ("Arial", 20), command = lambda:exportations_windows()) # Botón que usa el/interactua con el usuario. Llama a la función "consultarDatos".
button_exportar.pack(side = "left")                                                                                                   # Empaqueta/Muestra el botón "consultarDatos" estirado horizontalmente (a lo ancho). Añadiendo un margen horizontal (izquierda/derecha) con tamaño 50 y un margen vertical (arriba/abajo) con tamaño 15.

# Crea un marco (frame) para contener la tabla y las barras de desplazamiento.
table_frame = ttk.Frame(root_windows, padding = (10, 10, 10, 10))       # Margen interno (izq, sup, der, inf).
table_frame.pack(fill = "both", padx = 20, pady = 10, expand = True)    # "padx" y "pady" crean el margen externo (borde) azul.

# Empaqueta/Muestra el "Treeview" dentro del marco (frame).
scrollbar_x = ttk.Scrollbar(table_frame, orient = "horizontal") # Crea la barra de desplazamiento horizontal.
scrollbar_x.pack(side = "bottom", fill = "x")                   # Empaqueta/Muestra la barra horizontal en el lado derecho.

# Crea el "Treeview" para mostrar los datos en forma de tabla.
tree = ttk.Treeview(table_frame)                                                                                                                                                                                                                                                                                                                # Crea el widget "Treeview" dentro de la ventana principal.
tree["columns"] = ("COD_MEDICO", "NOMBRE_COMPLETO", "ESPECIALIDAD", "TURNO", "CONSULTAS_DISPONIBLES_LUNES", "CONSULTAS_DISPONIBLES_MARTES", "CONSULTAS_DISPONIBLES_MIERCOLES", "CONSULTAS_DISPONIBLES_JUEVES", "CONSULTAS_DISPONIBLES_VIERNES", "ANOS_EXPERIENCIA", "NUM_CITA", "FECHA_CITA", "HORA_CITA", "MODALIDAD", "URGENTE", "ESTADO")    # Define las columnas del "Treeview".

# Configura los encabezados de las columnas.
for col in tree["columns"]:
    tree.heading(col, text = col, anchor = "w")                     # Configura los encabezados de las columnas.
    tree.column(col, width = 150, minwidth = 50, stretch = tk.YES)  # Configura el ancho de las columnas.

# La columna 'fantasma' que crea el espacio a la izquierda es la columna "#0" (el identificador de fila). Eliminamos ese espacio:
tree.column("#0", width = 0, stretch = tk.NO)   # Ancho 0, no redimensionable.
tree.heading("#0", text = "")                   # Columna vacía para el identificador del ítem. Sin encabezado.

# Para que la primera columna visible ("COD_MEDICO") se vea bien alineada a la izquierda y sin espacio extra.
tree.column("COD_MEDICO", anchor = "w", width = 80, minwidth = 60, stretch = tk.NO)

# Vincula/Añade el scrollbar horizontal al "Treeview".
tree.pack(fill = "both", side = "left", expand = True)  # Empaqueta/Muestra el "Treeview" para que ocupe todo el espacio disponible en la ventana principal.
scrollbar_x.config(command = tree.xview)                # Configura la barra horizontal para controlar la vista del "Treeview".
tree.config(xscrollcommand = scrollbar_x.set)           # Configura el "Treeview" para usar la barra horizontal.


# Crea un bloque para el "footer" (pie de página).
footer = tk.Frame(root_windows, bg = "#34495E")
footer.pack(side = "bottom", fill = "x", pady = 5)

# Etiqueta del footer.
label_footer = tk.Label(footer, text = "Gestor de Informes PDF/Excel ~ RODRISTARK.GAME$17\n© 2025 | ® Marca Registrada | ™ Producto Original\nDerechos de imagen respetados.", font = ("Arial", 8), bg = "#34495E", fg = "white")   # Crea una etiqueta (label) para el pie de página.
label_footer.pack(pady = 5)                                                                                                                                                                                                       # Empaqueta/Muestra la etiqueta (label) con un margen vertical (arriba/abajo) de tamaño 5.


# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
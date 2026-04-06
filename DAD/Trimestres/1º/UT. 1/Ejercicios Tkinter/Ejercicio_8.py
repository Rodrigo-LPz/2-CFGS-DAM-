import tkinter as tk           # Importa la biblioteca Tkinter y la renombra como 'tk'.
from tkinter import ttk, messagebox # El paquete "messagebox" crea ventanas emergentes, similar a "JOptionPane".




# Crea la ventana principal.
root_windows = tk.Tk()                      # Crea la ventana principal (el contenedor de toda la app).
root_windows.title("Conversor de Unidades")       # Título de la ventana.
root_windows.geometry("400x400")            # Define el tamaño de la ventana (ancho x alto).
root_windows.resizable(False, False)        # Evita que el usuario cambie el tamaño de la ventana (ancho, alto).


# Función para la conversión de monedas.
def convertir_monedas(valor, origen, destino):
    
    tasas = {"Euro": 1.0, "Dólar": 1.07, "Libra": 0.85} # Tasas (de conversión) de cambio aproximadas (1 unidad = ? euros).
    valor_en_euros = valor / tasas[origen]              # Convertir primero el valor a euros.
    return valor_en_euros * tasas[destino]              # Luego convertir de euros a la moneda de destino.

# Función para la conversión de longitudes.
def convertir_longitud(valor, origen, destino):
    # Todo se basa en metros como unidad intermedia.
    factores = {
        "Milímetro": 0.001,
        "Centímetro": 0.01,
        "Decímetro": 0.01,
        "Metro": 1.0,
        "Decametro": 1.0,
        "Heptometro": 1.0,
        "Kilómetro": 1000.0,
        "Pulgada": 0.0254,
        "Yarda": 0.9144,
        "Milla": 1609.34
    }

    valor_en_metros = valor * factores[origen]     # Pasar todo a metros como SI (unidad del Sistema Internacional)
    return valor_en_metros / factores[destino]     # Paso 2: de metros a la unidad deseada

# Función para la conversión de temperatura.
def convertir_temperatura(valor, origen, destino):
    if origen == destino:       # Si origen y destino son iguales, no hay conversión.
        return valor

    if origen == "Fahrenheit":  # Convierte/Pasa todo primero a Celsius.
        valor = (valor - 32) * 5 / 9
    elif origen == "Kelvin":
        valor = valor - 273.15

    if destino == "Fahrenheit": # De Celsius a la unidad de destino.
        return (valor * 9 / 5) + 32
    elif destino == "Kelvin":
        return valor + 273.15
    else:                       # Si no, devuelve al destino original, a Celsius.
        return valor



# VENTANA SECUNDARIA.
# Plantilla base para crear una ventana de conversión.
def crear_ventana_conversion(titulo, unidades, funcion_conversion):
    # Crear nueva ventana secundaria (Toplevel).
    ventana = tk.Toplevel()
    ventana.title(f"Conversor de {titulo}")
    ventana.geometry("400x300")
    ventana.resizable(False, False)

    # Etiqueta principal (título de la ventana).
    tk.Label(ventana, text=f"Conversor de {titulo}", font=("Arial", 16, "bold")).pack(pady=10)

    # Frame para organizar las entradas.
    frame = tk.Frame(ventana)
    frame.pack(pady=10)

    # Entrada de texto (valor numérico).
    tk.Label(frame, text="Cantidad:", font=("Arial", 12)).grid(row=0, column=0, padx=5, pady=5)
    entry_valor = tk.Entry(frame, width=10)
    entry_valor.grid(row=0, column=1, padx=5, pady=5)

    # ComboBox (menú desplegable) para unidad de origen.
    tk.Label(frame, text="De:", font=("Arial", 12)).grid(row=1, column=0, padx=5, pady=5)
    combo_origen = ttk.Combobox(frame, values=unidades, state="readonly", width=15)
    combo_origen.current(0)  # Selecciona la primera por defecto.
    combo_origen.grid(row=1, column=1, padx=5, pady=5)

    # ComboBox para unidad de destino.
    tk.Label(frame, text="A:", font=("Arial", 12)).grid(row=2, column=0, padx=5, pady=5)
    combo_destino = ttk.Combobox(frame, values=unidades, state="readonly", width=15)
    combo_destino.current(1)  # Segunda por defecto.
    combo_destino.grid(row=2, column=1, padx=5, pady=5)

    # Etiqueta donde se mostrará el resultado.
    lbl_resultado = tk.Label(ventana, text="", font=("Arial", 14))
    lbl_resultado.pack(pady=10)

    # Función interna que hace la conversión cuando se pulsa el botón.
    def convertir():
        try:
            valor = float(entry_valor.get())  # Convierte el texto a número.
            origen = combo_origen.get()
            destino = combo_destino.get()
            resultado = funcion_conversion(valor, origen, destino)
            lbl_resultado.config(text = f"{valor:.2f} {origen} = {resultado:.2f} {destino}")
        except ValueError:
            messagebox.showerror("Error", "Introduce un valor numérico válido.")

    # Botón que ejecuta la conversión.
    tk.Button(ventana, text="Convertir", font = ("Arial", 12), bg = "lightblue", command = convertir).pack(pady = 10)


# Título principal
tk.Label(root_windows, text = "Conversor de Unidades", font = ("Arial", 18, "bold")).pack(pady = 20)
tk.Label(root_windows, text = "Selecciona una categoría para convertir:", font = ("Arial", 12)).pack(pady = 10)

# Botones para abrir cada tipo de conversor
tk.Button(root_windows, text = "💶\nMonetarias", font = ("Arial", 14), width = 20, command = lambda: crear_ventana_conversion("Monedas", ["Euro", "Dólar", "Libra"], convertir_monedas)).pack(pady=10)
tk.Button(root_windows, text = "📏\nLongitud", font = ("Arial", 14), width = 20, command = lambda: crear_ventana_conversion("Longitud", ["Milímetro", "Centímetro", "Metro", "Kilómetro", "Pulgada", "Yarda", "Milla"], convertir_longitud)).pack(pady = 10)
tk.Button(root_windows, text = "🌡️\nTemperatura", font = ("Arial", 14), width = 20, command = lambda: crear_ventana_conversion("Temperatura", ["Celsius", "Fahrenheit", "Kelvin"], convertir_temperatura)).pack(pady=10)

# Inicia el bucle principal de la aplicación (se llama a la ventana).
root_windows.mainloop() # Inicia el bucle principal que mantiene la ventana abierta y responde a eventos.
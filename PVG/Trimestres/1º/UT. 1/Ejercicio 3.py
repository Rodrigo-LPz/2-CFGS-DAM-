# Ejercicio 3: "Escribir un programa que pregunte al usuario una cantidad a invertir, el interés anual y el número de años, y muestre por pantalla el capital obtenido en la inversión."

# Bienvenida al usuario y solicitud de datos (nombre , cantidad a invertir, duración de la inversión e interés anual).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de interés compuesto', por favor ingrese su nombre: ")
# Solicitar la cantidad a invertir, la duración de la inversión y el interés anual.
cantidad = float(input(f"\nBienvenido a la aplicación {nombre}, ahora introduzca cuánta será la canidad a invertir (€): "))
duracion = float(input(f"\nValor guardado/almacenado. De nuevo, ahora introduzca la duración (años), por cuánto tiempo mantendrá activa la inversión: "))
interes = int(input(f"\nValor guardado/almacenado. De nuevo, ahora introduzca cuál será el ratio de interés anual (depende del país fiscal en el que tributes): "))

# Calcular el capital final con interés compuesto.
capital_final = (cantidad * (1 + interes / 100)) ** duracion

# Muestreo del resultado final al usuario.
print(f"\n\nSr./Sra. {nombre} tras {duracion} años de inversión, con un intereés compuesto del {interes}% obtendrá un beneficio neto final (capital) de {capital_final:.2f}€.")
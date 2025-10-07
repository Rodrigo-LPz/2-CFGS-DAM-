# Ejercicio 6: "Imagina que acabas de abrir una nueva cuenta de ahorros que te ofrece el 4% de interés al año. Estos ahorros debido a intereses, que no se cobran hasta finales de año, se te añaden al balance final de tu cuenta de ahorros. Escribir un programa que comience leyendo la cantidad de dinero depositada en la cuenta de ahorros, introducida por el usuario. Después el programa debe calcular y mostrar por pantalla la cantidad de ahorros tras el primer, segundo y tercer años. Redondear cada cantidad a dos decimales."

# Bienvenida al usuario y solicitud de datos (nombre y cantidad de dinero depositado).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de interés compuesto', por favor ingrese su nombre: ")
# Solicitar la cantidad de dinero a depositar en la cuenta.
capital_inicial = float(input("\nBienvenido a la aplicación {nombre}, ahora introduzca la cantidad inicial a invertir: "))

interes_anual = 0.04

# Calcular el precio total de la compra.
capital_1año = capital_inicial * (interes_anual + 1)
capital_2año = capital_1año * (interes_anual + 1)
capital_3año = capital_2año * (interes_anual + 1)

# Muestreo del resultado final al usuario.
print(f"\n\nDado un capital inical, {capital_inicial}€, con un interés anual del 4%. Tras el primer año consigues {capital_1año}€, tras el segundo año {capital_2año}€ y tras el tercero {capital_3año}€.")
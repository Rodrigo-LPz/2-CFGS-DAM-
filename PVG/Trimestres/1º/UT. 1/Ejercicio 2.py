# Ejercicio 2: "Escribir un programa que pida al usuario dos números enteros y muestre por pantalla la <n> entre <m> da un cociente <c> y un resto <r> donde <n> y <m> son los números introducidos por el usuario, y <c> y <r> son el cociente y el resto de la división entera respectivamente."

# Bienvenida al usuario y solicitud de datos (nombre y números <n> y <m>).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de cociente y resto', por favor ingrese su nombre: ")
# Solicitar los números <n> y <m>.
numeroN = int(input(f"\nBienvenido a la aplicación {nombre}, ahora introduzca el número dividendo: "))
numeroM = int(input(f"\nValor guardado/almacenado. De nuevo, ahora introduzca el número divisor: "))

# Calcular el cociente y resto.
if numeroM == 0:
    print("\n\n⚠️ Error: No se puede dividir entre cero. Operación/División con resultado indefinido.")
    exit()
else:
    cociente = numeroN / numeroM
    resto = numeroN % numeroM

# Muestreo del resultado final al usuario.
print(f"\n\nEl resultado de la división es:\n\tCociente: {cociente}\n\tResto: {resto}.")
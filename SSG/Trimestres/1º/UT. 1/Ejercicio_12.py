# Ejercicio 12: "Calcular para cualquier n el sumatorio, donde (n - 1) * n dividido entre el factorial de n."


# Librería para imporatar la raíz cuadrada.
import math

# Bienvenida al usuario y solicitud de datos (nombre y cadena de sucesion de numeros a sumar)
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de Números pares', por favor ingrese su nombre: ")
# Solicitar el número correspondiente a la cadena del número n.
numeroN = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca el número de la cadena hasta el que desea que llegue el cálculo de la suma: "))

# Inicializarla variable en 0, pues es el elemento neutro de la suma.
sumatorio = 0

# Crear un bucle donde se recorre 
for i in range(1, numeroN + 1):
    factorial = 1

    for j in range (1, i + 1):
        factorial *= j

    sumatorio += (i * (i - 1)) / factorial

# Muestreo del resultado final.
print(f"\n\nEl resultado final del sumatorio es {sumatorio} -->Sumatorio: {sumatorio} = {numeroN * (numeroN - 1)} / {factorial}.")

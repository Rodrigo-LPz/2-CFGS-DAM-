# Ejercicio 13: "Calcular para cualquier n el sumatorio, donde ((n - 1) * n) dividido entre el factorial de n."


# Librería para imporatar la raíz cuadrada.
import math

# Bienvenida al usuario y solicitud de datos (nombre y cadena de sucesion de numeros a sumar).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de Sumatorios donde ((n - 1) * n) * n dividido entre el factorial de n.', por favor ingrese su nombre: ")
# Solicitar el número correspondiente a la cadena del número n.
numeroN = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca el número de la cadena hasta el que desea que llegue el cálculo de la suma: "))

# Inicializa variable en 0, pues es el elemento neutro de la suma.
sumatorio = 0

# Inicializa variable en 1, pues es el elemento neutro de la multiplicación.
factorial = 1

# Crear un bucle donde obtener el factorial recorriendo desde 1 hasta 'n'. 
for i in range(1, numeroN + 1):
    factorial *= i      # Se multiplica el número leído de la cadena en el bucle por el último valor registrado de la variable "factorial".

    sumatorio += (i * (i - 1)) / factorial

# Muestreo del resultado final.
print(f"\n\nEl resultado final del sumatorio es {sumatorio} -->Sumatorio: {sumatorio} = {numeroN * (numeroN - 1)} / {factorial}.")
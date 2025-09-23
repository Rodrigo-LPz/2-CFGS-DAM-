# Ejercicio 12: "Calcular para cualquier n el sumatorio, donde (n - 1) * n dividido entre el factorial de n."


# Librería para imporatar la raíz cuadrada.
import math

# Bienvenida al usuario y solicitud de datos (nombre y cadena de sucesion de numeros a sumar)
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de Números pares', por favor ingrese su nombre: ")
# Solicitar el número correspondiente a la cadena del número n.
numeroN = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca el número de la cadena hasta el que desea que llegue el cálculo de la suma: "))

# Solución con programación 1.
sumatorio = 0

for i in range(1, numeroN + 1):
    factorial = 1

    for j in range (1, i + 1):
        factorial *= j

    sumatorio += (i * (i - 1)) / factorial

# Solución con programación 2.
    #suma = sum(range(1, numeroN + 1)) # La función range genera una secuencia de números desde 1 hasta numero (inclusive), y sum los suma. No crea una lista fija, con objetos en memeoria, sino que genera los números sobre la marcha, lo que es más eficiente en términos de memoria.


# Solución matemática.
    #suma = (numeroN * (numeroN + 1)) // 2 # Se utiliza "//" en vez de "/" ya que el uso de "/" devuelve un valor float [con decimales (28.0)] y en este caso no es necesario, ya que el resultado siempre será un número entero.
    #suma = (numeroN * (numeroN - 1)) // 2



# Muestreo del resultado final.
print(f"\n\nEl resultado final del sumatorio es {sumatorio} -->Sumatorio: {sumatorio} = {numeroN * (numeroN - 1)} / {factorial}.")
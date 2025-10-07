# Ejercicio 14: "Haz un programa que calcule el productorio, donde ((n-1) * n) dividido entre el factorial de n."


# Librería para imporatar la raíz cuadrada.
import math

# Bienvenida al usuario y solicitud de datos (nombre y cadena de sucesion de numeros a sumar).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de Productorios donde ((n - 1) * n) dividido entre el factorial de n.', por favor ingrese su nombre: ")
# Solicitar el número correspondiente a la cadena del número n.
numeroN = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca un número entero positivo 'n' [esto debido a que dentro del factorial (n!) está definido/sólo se puede realizar para números enteros positivos.]: "))

# Comprobar que el número 'n' introducido es superior o igual a 0.
if numeroN < 0:
    print("\n\nError. El número escogido para la incógnita 'n' debe ser igual o superior a cero (0).")
else:
    # Inicializa variable en 1, pues es el elemento neutro de la multiplicación.
    factorial = 1
    
    # Calcula el factorial de 'n' (n!). Bucle donde obtener el factorial recorriendo desde 1 hasta 'n'.
    for i in range(1, numeroN + 1):     # Crea un bucle de tipo "for" que recorra la cadena de 'n', desde 1 hasta 'n'.
        factorial *= i      # Se multiplica el número leído de la cadena en el bucle por el último valor registrado de la variable "factorial".

    # Calcular el productorio.
    productorio = (i * (i - 1)) / factorial

# Muestreo del resultado final.
print(f"\n\nEl resultado final del sumatorio es {productorio} -->Sumatorio: {productorio} = {numeroN * (numeroN - 1)} / {factorial}.")
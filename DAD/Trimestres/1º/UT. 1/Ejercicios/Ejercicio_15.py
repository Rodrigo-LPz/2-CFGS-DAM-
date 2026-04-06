# Ejercicio 15: "Haz un programa que cuenta los n primero primos."


# Librería para imporatar la raíz cuadrada.
import math

# Bienvenida al usuario y solicitud de datos (nombre y número para la cadena de primos).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Contador de primos', por favor ingrese su nombre: ")
# Solicitar el número correspondiente a la cadena del número n.
numero = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca un número a obtener esa cantidad como cadena de primos: "))

# Comprobar que el número introducido es positivo, es superior o igual a 0.
if numero < 0:
    print("\n\nError. El número introducido para la cadena de números primos debe ser positivo, es decir, superior o igual a 0.")
else:
    # crea una lista para almacenar los primeros números primos encontrados.
    primos = []

    # Declara una variable, esta tendrá un valor de 2, pues será el primer número de la cadena de primos a contabilizar, pues tanto el 1 como el 0 no se consideran primos.
    primer_numero = 2

    # Crea un bucle de tipo "while" con el que leer la lista e ir haciendo las comprobaciones idóneas para saber si  el número leído en ese momento es o no primo. 
    while len(primos) < numero:
        # Declara una variable de tipo lógixa, booleana.
        es_primo = True
        
        # Crea un bucle de tipo "for" para comprobar si el número que se está leyendo en ese momento es primo o no.
        for i in range(2, int(math.sqrt(primer_numero)) + 1):   # "range(2, int(math.sqrt(2)) + 1)" funciona de la siguiente manera: Recorre todos los números desde 2 hasta la raíz cuadrada del número actual (primer_numero), inclusive.
                                                                # Se usa la raíz cuadrada porque un número compuesto (número natural mayor que 1 que tiene más de dos divisores. Ejemplo, el número 4 es divisible por/entre 1, 2 y 4) siempre tendrá un factor menor o igual a su raíz cuadrada, 
                                                                # por lo que no hace falta comprobar divisores mayores. La función int() se usa para convertir el resultado de ('math.sqrt') en un entero, ya que range() no acepta números decimales.
                                                                # Finalmente, se suma 1 para incluir la raíz cuadrada en el rango. Por ejemplo, si primer_numero = 2, math.sqrt(2) ≈ 1.414 → int(1.414) = 1 → range(2, 2) → el bucle no se ejecuta porque no hay números entre 2 y 1. Por tanto, 2 no tiene divisores entre 2 y √2, por lo que es primo.
            
            # Crea un condicional de tipo "if" donde se toma el valor de la variable 'primer_numero', es decir, 2 y se divide por el número obtenido tras haber sido operado por la raíz cuadrada.
            if primer_numero % i == 0:
                es_primo = False    # Convierte la variable con un valor 'false'.
                break               # Si encuentra un divisor, ya no es primo y se termina/sale del bucle.
        
        # Si es primo, añadirlo a la lista.
        if es_primo:
            primos.append(primer_numero)
        # Pasa al siguiente número a comprobar.
        primer_numero += 1

# Muestreo del resultado final al usuario.
print(f"\n\nDado el número introducido {numero} como cantidad de primos a la cadena. Los {numero} primeros números primos son: {primos}.")
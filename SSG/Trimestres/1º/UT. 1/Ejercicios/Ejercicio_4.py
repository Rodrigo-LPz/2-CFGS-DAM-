# Ejercicio 4: "Programa que sume los n primeros números pares."


# Bienvenida al usuario y solicitud de datos (nombre y cadena de numeros a sumar).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de 'n' Primeros Números Pares', por favor ingrese su nombre: ")
# Solicitar el número correspondiente a la cadena.
numero = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca el número de la cadena de los primeros pares hasta los que quiere llegar (Ejemplo: n primeros numeros → n = 5 es igual a 2 + 4 + 6 + 8 + 10 = 30): "))

# Solución con programación 1.
suma = 0
pares = []

for i in range(1, numero + 1): # El rango se recorre de uno en uno hasta el número indicado
    suma += 2 * i # Multiplicamos por 2 para que el numero i sólo sea par
    pares.append(2 * i)


# Solución con programación 2.
    # pares = [2 * i for i in range(1, numero + 1)]
    # suma = sum(pares)

# Muestreo del resultado final.
print(f"\n\nEl resultado final de suma de los n {numero} primeros números pares ({' + '.join(map(str, pares))}) es de: {suma}.")
    #print(f"\n\nEl resultado final de suma de los números pares menores que {numero} ({pares}) es de: {Suma}.")
    #print(f"\n\nEl resultado final de suma de los números pares menores que {numero} ({', '.join(map(str, pares))}) es de: {Suma}.") # La función "map(str, pares)" convierte cada número en la lista "pares" a una cadena de texto, lo que permite que la función "join" los una correctamente con comas y espacios entre ellos (", "). Sin esta conversión, "join" no podría procesar los números directamente, no funcionaría, ya que espera cadenas de texto.
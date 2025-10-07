# Ejercicio 4: "Suma de números pares menores que n."


# Bienvenida al usuario y solicitud de datos (nombre y cadena de numeros a sumar).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de Números Pares menores que 'n'', por favor ingrese su nombre: ")
# Solicitar el número correspondiente a la cadena.
numero = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca el número de la cadena hasta el que desea que llegue el cálculo de la suma (Recuerde que sólo se sumaran los números pares menores al introducido): "))

# Solución con programación 1.
suma = 0
pares = []

for i in range(2, numero, 2): # "range(2, numero, 2)" funciona de la siguiente manera: "range(start(empieza desde el nº 2), stop(termina en el nº introducido, sin incluirlo), step(aumenta/avanza la lectura de la lista de 2 en 2))". De esta manera, se generan sólo los números pares menores que el número introducido.
    suma += i
    pares.append(i)

# Solución con programación 2.
    #suma = sum(range(2, numero, 2))

# Muestreo del resultado final.
print(f"\n\nEl resultado final de suma de los números pares menores que {numero} ({pares}) es de: {suma}.")
    #print(f"\n\nEl resultado final de suma de los números pares menores que {numero} ({', '.join(map(str, pares))}) es de: {Suma}.") # La función "map(str, pares)" convierte cada número en la lista "pares" a una cadena de texto, lo que permite que la función "join" los una correctamente con comas y espacios entre ellos (", "). Sin esta conversión, "join" no podría procesar los números directamente, no funcionaría, ya que espera cadenas de texto.
    #print(f"\n\nEl resultado final de suma de los números pares menores que {numero} ({' + '.join(map(str, pares))}) es de: {Suma}.")
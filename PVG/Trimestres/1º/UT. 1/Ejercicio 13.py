# Ejercicio 13: "Escribir un programa en el que se pregunte al usuario por un frase y una letra, y muestre por pantalla el número de veces que aparece el número de veces la letra."

# Bienvenida al usuario y solicitud de datos (nombre , frase, letra).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de letras', por favor ingrese su nombre: ")
# Solicitar la frase y la letra en cuestión.
frase = input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca su frase, oración o texto a buscar su letra: ")
letra = input(f"\nValor guardado/almacenado. De nuevo, ahora introduzca la letra a buscar: ")

# Calcular la cantidad de veces que se repite la letra dicha dentro de la frase.
contador_total = 0
contador_letra = 0
contador_caracter = {letra:0}

for caracter in frase:
    contador_total += 1
    if caracter.lower() in contador_caracter:
        contador_letra += 1
        contador_caracter[caracter.lower()] += 1    

# Muestreo del resultado final al usuario.
print(f"\n\nDentro de la frase, oración o texto dicho, la letra '{letra}' escogida aparece un total de {contador_letra} veces de las {contador_total} que podían haber sido.")
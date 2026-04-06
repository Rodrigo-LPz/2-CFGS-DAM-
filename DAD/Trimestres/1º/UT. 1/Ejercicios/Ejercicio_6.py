# Ejercicio 6: "Introduce un texto y cuenta el número de vocales totales y el número de vocales de cada tipo."


# Bienvenida al usuario y solicitud de datos (nombre y texto).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Contador de vocales', por favor ingrese su nombre: ")
# Solicitar el texto a contabilizar los caracteres.
texto = input(f"\n\tBienvenido a la aplicacion {nombre}, ahora introduzca el texto a contabilizar carácter por carácter: ")

contador_total = 0
    #vocales = "aAeEiIoOuU"
contador_vocales = 0
contador_por_vocal = {"a": 0, "e": 0, "i": 0, "o": 0, "u": 0}

for caracter in texto:
    contador_total += 1
    if caracter.lower() in contador_por_vocal:
        contador_vocales += 1
        contador_por_vocal[caracter.lower()] += 1 # Incrementa el contador específico de la vocal.

# Muestreo del resultado final al usuario.
print(f"\n\nEl texto (\"{texto}\")\n\t ingresado tiene {contador_total} caracteres de los cuales {contador_vocales} son vocales, en concreto:\n\t\t\ta: {contador_por_vocal['a']}\t\te: {contador_por_vocal['e']}\t\ti: {contador_por_vocal['i']}\t\to: {contador_por_vocal['o']}\t\tu: {contador_por_vocal['u']}.")
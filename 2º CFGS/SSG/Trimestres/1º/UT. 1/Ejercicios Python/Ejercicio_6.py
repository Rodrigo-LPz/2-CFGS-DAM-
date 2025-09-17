# Ejercicio 6: "Introducir texto y contar el número de vocales."


# Bienvenida al usuario y solicitud de datos (nombre y texto)
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Contador de caracteres', por favor ingrese su nombre: ")
texto = input(f"\n\tBienvenido a la aplicacion {nombre}, ahora introduzca el texto a contabilizar carácter por carácter: ")

contador_total = 0
vocales = "aAeEiIoOuU"
contador_vocales = 0

for caracter in texto:
    contador_total += 1
    if caracter in vocales:
        contador_vocales += 1

print(f"\n\nEl texto (\"{texto}\")\n\t ingresado tiene {contador_total} caracteres de los cuales {contador_vocales} son vocales.")
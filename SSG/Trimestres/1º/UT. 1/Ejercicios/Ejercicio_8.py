# Ejercicio 8: "Programa que al introducir un cadena y contar las veces que se repite una letra."


# Bienvenida al usuario y solicitud de datos (nombre y texto).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Contador de caracteres', por favor ingrese su nombre: ")
# Solicitar el texto a contabilizar el carácter concreto.
texto = input(f"\n\tBienvenido a la aplicacion {nombre}, ahora introduzca el texto a contabilizar el carácter específico que desee: ")
# Solicitar el carácter concreto a contabilizar.
letra = input(f"\n\tFinalmente, especifique cuál es el carácter concreto que desea buscar y contabilizar dentro del texto introducido: ")

contador_total = 0
contador_letra = 0

for caracter in texto:
    contador_total += 1
    if caracter.lower() in letra.lower():
        contador_letra += 1

# Muestreo del resultado final al usuario.
print(f"\n\nDentro del texto (\"{texto}\")\n\t ingresado tiene un total de {contador_total} caracteres de los cuales {contador_letra} son el carácter '{letra}' solicitado a buscar.")
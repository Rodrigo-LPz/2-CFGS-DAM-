# Ejercicio 5: "Calculadora."


# Bienvenida al usuario y solicitud de datos (nombre, valores numéricos y operación matemática a realizar).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora', por favor ingrese su nombre: ")
# Solicitar los numeros a operar.
numero1 = int(input(f"\n\tBienvenido a la aplicacion {nombre}, ahora introduzca los dos números con los que desea operar: "))
numero2 = int(input(f"\n\tNúmero almacenado/guardado. Escriba su segundo número: "))
# Solicitar el tipo de operación a realizar.
operación = input("¿Qué operación matematica desea realizar? Escoga entre suma (+), resta (-), multiplicación (*) y división (/): ")

# Realizar la operación matemática escogida por el usuario.
if operación == "+":
    resultado = numero1 + numero2
elif operación == "-":
    resultado = numero1 - numero2
elif operación == "*":
    resultado = numero1 * numero2
elif operación == "/":
    if numero2 != 0:
        resultado = numero1 / numero2
    else:
        print("\n\nError: No se puede dividir entre cero. Operación/División con resultado indefinido.")
        exit()
else:
    print("\n\nError: Operación no válida. Por favor [Recuerde escoger entre suma (+), resta (-), multiplicación (*) y división (/)].")
    exit()

# Muestreo del resultado final en función de la operación escogida.
print(f"\n\nEl resultado final de la operación escogida ({operación}) entre los números {numero1} y {numero2} es de: {resultado}.")
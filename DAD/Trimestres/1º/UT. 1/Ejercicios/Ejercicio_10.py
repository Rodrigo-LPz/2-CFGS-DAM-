# Ejercicio 10: "Hacer una calculadora, cuyas operaciones sean funciones (suma, resta, multiplicación, división, etc.)."


# Librería para imporatar la raíz cuadrada.
import math

# Bienvenida al usuario y solicitud de datos (nombre, valores numéricos y operación matemática a realizar).
# Solicitar el nombre al usuario y la cantidad de números con los que desee operar.
nombre = input("Bienvenido al programa 'Calculadora', por favor ingrese su nombre: ")
numeros = int(input(f"\n\tBienvenido a la aplicacion {nombre}, ahora introduzca cuántos números son los que deseas operar: "))

lista_operaciones = []
for i in range(numeros):
    numero = float(input(f"\n\tPor favor, ahora introduzca el número con el que desea operar {i + 1}: "))
    lista_operaciones.append(numero)

# Crear funciones matemáticas para cada tipo de operación.
def sumar (lista):
    resultado = lista[0]    # Empieza con el primer número del array.
    for i in lista[1:]:     # Recorre el resto de números del array. "[1:]" --> lista[inicio:fin:paso]. En este caso equivaldría a empezar desde el segundo número hasa el resto de estos.
        resultado += i      # Suma cada número al resultado acumulado.
    return resultado        # Devuelve el resultado final

# def sumar(lista):
#     return sum(lista)

def restar(lista):
    resultado = lista[0]    # Empieza con el primer número del array.
    for i in lista[1:]:     # Recorre el resto de números del array. "[1:]" --> lista[inicio:fin:paso]. En este caso equivaldría a empezar desde el segundo número hasa el resto de estos.
        resultado -= i      # Resta cada número al resultado acumulado.
    return resultado        # Devuelve el resultado final

def multiplicar(lista):
    resultado = 1           # Empieza por 1 (porque multiplicar por 0 da 0).
    for num in lista:       # Recorre todos los números.
        resultado *= num    # Multiplica cada número al resultado acumulado.
    return resultado        # Devuelve el resultado final.

def dividir(lista):
    resultado = lista[0]    # Empieza con el primer número.
    for num in lista[1:]:   # Recorre el resto.
        if num == 0:        # Si alguno es cero, error.
            print("\n\nError: No se puede dividir entre cero. Operación/División con resultado indefinido.")
            exit()
        resultado /= num    # Divide cada número al resultado acumulado.
    return resultado        # Devuelve el resultado final.

def potencia(lista):
    resultado = lista[0]          # Empieza con el primer número.
    for num in lista[1:]:         # Recorre el resto.
        resultado **= num         # Eleva el resultado acumulado a cada exponente.
    return resultado              # Devuelve el resultado final.

def raiz_cuadrada(lista):
    return [math.sqrt(num) for num in lista]    # Devuelve una lista con la raíz cuadrada de cada número.

# Crea un bucle para repetir operaciones hasta que el usuario decida salir.
while True:
    operación = input("\n\n¿Qué operación matematica desea realizar? Escoga entre sumar, restar, multiplicación, división, potencia y raíz cuadrada: ")

    # Realizar la operación matemática escogida por el usuario.
    if operación.lower() == "sumar":
        resultado = sumar(lista_operaciones)
    elif operación.lower() == "restar":
        resultado = restar(lista_operaciones)
    elif operación.lower() == "multiplicación":
        resultado = multiplicar(lista_operaciones)
    elif operación.lower() == "división":
        resultado = dividir(lista_operaciones)
    elif operación.lower() == "potencia":
        resultado = potencia(lista_operaciones)
    elif operación.lower() == "raíz cuadrada":
        resultado = raiz_cuadrada(lista_operaciones)
    else:
        print("\n\nError: Operación no válida. Por favor [Recuerde escoger entre sumar (+), restar (-), multiplicación (*), división (/), potencia (^) o raíz cuadrada (√)].")
        continue    # Pedir nuevamente el tipo de operación.

    # Muestreo del resultado final en función de la operación escogida.
    print(f"\n\nEl resultado final de la operación escogida ({operación}) entre los números {numeros} es de: {resultado}.")

    # preguntar al usuario si desea continuar/seguir.
    continuar = input("\n¿Desea realizar otra operación con estos mismos números? (Sí/No): ")
    #if continuar.lower() != "sí" and continuar.lower() != "si":
    if continuar not in ["si", "sí"]:
        print("\nGracias por usar la calculadora.")
        break
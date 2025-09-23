# Ejercicio 10: "Calculadora geométrica. Usar funciones para calcular el área de figuras tales como triángulo, rectángulo y círculo. Además del volumen de un prisma y una esfera. El programa principal debe tener un menú para elegir que cálculo realizar."


# Librería para imporatar la raíz cuadrada.
import math

# Bienvenida al usuario y solicitud de datos (nombre, valores numéricos y operación matemática a realizar)
# Solicitar el nombre al usuario y la operación que desee realizar.
nombre = input("Bienvenido al programa 'Calculadora Geométrica', por favor ingrese su nombre: ")
eleccion = input(f"\n\tBienvenido a la aplicacion {nombre}, ahora introduzca qué operación desea realizar (Área o Volumen): ")

# Partición entre las opciones para las figuras con área a calcular y con volumen a calcular.
if eleccion.lower() == "área" or eleccion.lower() == "area":
    seleccion = input("\n\tAhora escoga entre las figuras (Triángulo, Rectángulo y Círculo): ")

    # Partición entre figuras con área a calcular (Triángulo, Rectángulo y Círculo), junto con sus operaciones.
    if seleccion.lower() == "triángulo" or seleccion.lower() == "triangulo":
        base = int(input(f"\n\tHas seleccionado calcular el área del {seleccion}. Por favor, indique su base (m): "))
        altura = int(input(f"\n\tNuevamente, por favor, indique la altura (m) del {seleccion}: "))

        if base < 0 or altura < 0:
            print("\n\nError. La longitud no puede ser una distancia negativa, inferior a cero.")
            exit()
        else:
            operacion = (base * altura) / 2

    elif seleccion.lower() == "rectángulo" or seleccion.lower() == "rectangulo":
        base = int(input(f"\n\tHas seleccionado calcular el área del {seleccion}. Por favor, indique su base (m): "))
        altura = int(input(f"\n\tNuevamente, por favor, indique la altura (m) del {seleccion}: "))

        if base < 0 or altura < 0:
            print("\n\nError. La longitud no puede ser una distancia negativa, inferior a cero.")
            exit()
        else:
            operacion = base * altura

    else:
        radio = int(input(f"\n\tHas seleccionado calcular el área del {seleccion}. Por favor, indique su radio (m): "))

        if radio < 0:
            print("\n\nError. La longitud no puede ser una distancia negativa, inferior a cero.")
            exit()
        else:
            operacion = math.pi * (radio**2)

    # Muestreo del resultado final en función de la operación escogida.
    print(f"\n\nEl resultado final de la operación escogida según su figura ({seleccion}) es de: {operacion} metros.")

elif eleccion.lower() == "volumen":
    seleccion = input("\n\tAhora escoga entre las figuras (Prisma y Esfera): ")

    # Partición entre figuras con área a calcular (Prisma y Esfera), junto con sus operaciones.
    if seleccion.lower() == "prisma":
        base = int(input(f"\n\tHas seleccionado calcular el volumen del {seleccion}. Por favor, indique su base (m): "))
        altura = int(input(f"\n\tNuevamente, por favor, indique la altura (m) del {seleccion}: "))

        if base < 0 or altura < 0:
            print("\n\nError. La longitud no puede ser una distancia negativa, inferior a cero.")
            exit()
        else:
            operacion = base * altura

    else:
        radio = int(input(f"\n\tHas seleccionado calcular el volumen del {seleccion}. Por favor, indique su radio (m): "))

        if radio < 0:
            print("\n\nError. La longitud no puede ser una distancia negativa, inferior a cero.")
            exit()
        else:
            operacion = (4 * math.pi * radio**3) / 3

    # Muestreo del resultado final en función de la operación escogida.
    print(f"\n\nEl resultado final de la operación escogida según su figura ({seleccion}) es de: {operacion} metros.")

else:
    print("\n\nError. La elección escogida no está o no la reconoce el sistema.")
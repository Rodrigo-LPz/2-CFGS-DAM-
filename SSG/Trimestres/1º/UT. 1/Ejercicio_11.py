# Ejercicio 11: "Programa en Python una función que solucione una ecuación de segundo grado. Muestra sus dos soluciones o muestra que no tiene solución en los números reales (R)."


# Librería para imporatar la raíz cuadrada.
import math

# Bienvenida al usuario y solicitud de datos (nombre, valores numéricos).
# Solicitar el nombre al usuario y la cantidad de números con los que desee operar.
nombre = input("Bienvenido al programa 'Calculadora', por favor ingrese su nombre: ")
print("\n\n\t\t\t\t\tEcuación: (ax^2 ± bx ± c = 0)")
a = int(input(f"\n\tBienvenido a la aplicacion {nombre}, ahora introduzca el valor de la incógnita 'a': "))
b = int(input(f"\n\tNuevamente, por favor, introduzca el valor de la incógnita 'b': "))
c = int(input(f"\n\tNuevamente, por favor, introduzca el valor de la incógnita 'c': "))

# Sacar la excepción de la operación
discriminante = (b ** 2 - 4 * a * c)

if discriminante < 0:
    print("\nError. La ecuación no tiene soluciones reales dado que no es posible realizar raices cuadradas con un discriminante negativo.")
else:
    # Calcular la ecuación con sus dos posibles soluciones
    solucion_ecuaion_1 = (- b + math.sqrt(b ** 2 - 4 * a * c)) / (2 * a)
    solucion_ecuaion_2 = (- b - math.sqrt(b ** 2 - 4 * a * c)) / (2 * a)

# Muestreo del resultado final en función de la operación escogida.
print(f"\n\nEl resultado final de la ecuación tiene dos posibles resultados.\n\n\t\t\t\t\tEcuación: (ax^2 ± bx ± c = 0)\n\t\t\t\t\tEcuación: ({a}x^2 ± {b}x ± {c} = 0)\n\n\tEcuación 1: (- {b} + √({b} ^ 2 - 4 * {a} * {c})) / (2 * {a})\t\tResultado 1 --> X = ({solucion_ecuaion_1})\n\n\tEcuación 2: (- {b} - √({b} ^ 2 - 4 * {a} * {c})) / (2 * {a})\t\tResultado 2: ({solucion_ecuaion_1}).")
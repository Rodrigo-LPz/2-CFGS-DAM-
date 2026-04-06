# Ejercicio 7: "Pedir al suario que introduzca un array por consola para luego ser impreso de a uno sus elementos. Y después haz que la consola pregunte al usuario por un elemento concreto y luego lo imprima."


# Bienvenida al usuario y solicitud de datos (nombre y array).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Impresión de arrays', por favor ingrese su nombre: ")
# Solicitar el tamaño del array.
tamaño = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora especifique de cuánto será el tamaño del array: "))

# Crear un array donde almacenar los distintos elementos.
array = []

# Recorrer un bucle de tipo "for" para ir añadiendo los elementos al array.
for i in range (tamaño):
    elemento = input(f"\n\tIntroduce un elemento {i + 1}: ")
    array.append(elemento)

# Seleccionar el elemento a imprimir.
eleccion = int(input(f"\n¿Qué elemento concreto de los almacenados dentro del array desea imprimir?: "))

# Validar y mostrar el elemento
if 1 <= eleccion <= tamaño:
    elemento = array[eleccion - 1] # Toma la elección del usuario, resta 1 porque los índices en Python empiezan en 0 ([posición 0, posición 1, posición 2]), y guardamos el elemento correspondiente del array en la variable 'elemento'. Si queremos el 2, tendremos que coger el número leído por python 2, que sería la posición tres y restarle 1 para que sea la posición 2.

    # Muestreo del resultado final en función de la operación escogida.
    print(f"\n\nHas creado un array con un tamaño de {tamaño} espacios: ({array})\n\tEl elemento selecionado a tomar del array fue {elemento}, con un orden de inserción de {eleccion}.")

else:
    print("\n\nError. El número escogido no es un elemento válido o se encuentra fuera de rango.")
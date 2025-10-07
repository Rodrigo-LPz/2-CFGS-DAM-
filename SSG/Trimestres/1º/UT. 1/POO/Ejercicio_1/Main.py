# Ejercicio 1: "Realizar un programa que conste de una clase llamada 'Estudiante', que tenga como atributos el nombre y la nota del alumno. Definir los métodos para iniciar sus atributos, imprimirlos y mostrar un mensaje con el resultado de la nota y si ha aprobado o no."


# Importa tu clase "Estudiante" del archivo "Clases.py".
from Clases import Estudiante

# Bienvenida al usuario y solicitud de datos (nombre, edad).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido/a al programa 'Calificadora', por favor ingrese su nombre: ")

alumnos = []

# Solicitar la cantidad de alumnos a añadir.
alumno = int(input(f"\nBienvenido/a a la aplicacion profesor/a {nombre}, ahora introduzca cuántos alumnos desea ingresar: "))

# Crea un bucle de tipo "for" donde ir añadiendo los alumnos al array "alumnos"
for i in range(alumno):
    # Solicitar el nombre del alumno, apellido, curso y su nota por trimestre al usuario.
    nombreAlumno = input(f"\nAhora introduzca el nombre del alumno a calcular la nota: ")
    apellido = input(f"\nAhora su apellido: ")
    curso = input(f"\nY el curso: ")
    notaPrimerTrimestre = float(input(f"\nEl alumno/a {nombreAlumno} {apellido} del curso {curso} ha sido encontrado. Por favor, ahora introduzca la nota obtenida en el primer trimestre por el alumno: "))
    notaSegundoTrimestre = float(input(f"\nValor guardado/almacenado. De nuevo, ahora introduzca la nota obtenida para el segundo trimestre: "))
    notaTercerTrimestre = float(input(f"\nValor guardado/almacenado. Y, finalmente, la nota obtenida para el tercer trimestre: "))

    pupilo = Estudiante(nombreAlumno, apellido, curso, notaPrimerTrimestre, notaSegundoTrimestre, notaTercerTrimestre)  # Crea una instancia de la clase "Estudiante".
    alumnos.append(pupilo)  # Añade la instancia "pupilo" al array "alumnos".

# Muestreo del resultado final al usuario.
for alumno in alumnos:
    alumno.imprimir()  # Llama al método "imprimir" de la clase "Estudiante".
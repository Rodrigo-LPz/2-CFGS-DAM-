# Ejercicio 2: "Calcula la calificación en base a la nota (suspenso, bien, notable y sobresaliente)."


# Bienvenida al usuario y solicitud de datos (nombre del profesor, nombre del alumno y las notas obtenidas por trimestre del alumno)
# Solicitar el nombre al usuario (usuario profesor/a).
nombreP = input("Bienvenido al programa 'Calculadora de Notas/Calificaciones', por favor ingrese su nombre: ")
# Solicitar el nombre del alumno a evaluar/calificar.
nombreA = input(f"\nBienvenido a la aplicacion profesor/a {nombreP}, ahora introduzca el nombre del alumno a evaluar/calificar: ")

# Solicitar las notas del primer, segundo y tercer trimestre respectivamente.
    #nota1 = int(input("\n\tAhora introduzca la nota obtenida en el primer trimestre (0-10): "))
    #nota2 = int(input("\n\tAhora introduzca la nota obtenida en el segundo trimestre (0-10): "))
    #nota3 = int(input("\n\tAhora introduzca la nota obtenida en el tercer trimestre (0-10): "))

# Comprobar que las notas introducidas son válidas (entre 0 y 10).
    #if nota1 < 0 or nota1 > 10:
        #print("\n\nError: El valor introducido para la nota del primer trimestre no es válido para nuesto sistema (Recuerde que el valor debe corresponderse entre 0 y 10).")
        #exit()
    #elif nota2 < 0 or nota2 > 10:
        #print("\n\nError: El valor introducido para la nota del segundo trimestre no es válido para nuesto sistema (Recuerde que el valor debe corresponderse entre 0 y 10).")
        #exit()
    #elif nota3 < 0 or nota3 > 10:
        #print("\n\nError: El valor introducido para la nota del tercer trimestre no es válido para nuesto sistema (Recuerde que el valor debe corresponderse entre 0 y 10).")
        #exit()
    #else:
        # Cálculo de la nota final.
            #nota_final = (nota1 + nota2 + nota3) / 3
        # Clasificación final de la nota del alumno.
        #if nota_final < 5:
            #calificacion = "Suspenso o Insuficiente"
        #elif nota_final < 6:
            #calificacion = "Aprobado o Suficiente"
        #elif nota_final < 7:
            #calificacion = "Bien"
        #elif nota_final < 9:
            #calificacion = "Notable"
            #if nota_final < 8:
                #calificacion = "Notable bajo"
            #elif nota_final < 9:
                #calificacion = "Notable alto"
        #elif nota_final < 10:
            #calificacion = "Sobresaliente"
        #else:
            #calificacion = "Matrícula de Honor"

notas = []
for i in range(1, 4):
    nota = int(input(f"\n\tAhora introduzca la nota obtenida en el {i}º trimestre (0-10): "))
    if nota < 0 or nota > 10:
        print(f"\n\nError: La nota introducida para el {i}º trimestre no es válida para nuestro sistema (Recuerde que el valor debe corresponderse entre 0 y 10).")
        exit()
    notas.append(nota)

nota_final = sum(notas) / len(notas)

if nota_final < 5:
    calificacion = "Suspenso o Insuficiente"
elif nota_final < 6:
    calificacion = "Aprobado o Suficiente"
elif nota_final < 7:
    calificacion = "Bien"
elif nota_final < 9:
    calificacion = "Notable"
    if nota_final < 8:
        calificacion = "Notable bajo"
    elif nota_final < 9:
        calificacion = "Notable alto"
elif nota_final < 10:
    calificacion = "Sobresaliente"
else:
    calificacion = "Matrícula de Honor"

# Muestreo del resultado final al usuario (profesor/a).
print(f"\n\nEl alumno/a {nombreA} ha obtenido una calificación final correspondiente a un \"{calificacion}\" ({nota_final:.2f}).")
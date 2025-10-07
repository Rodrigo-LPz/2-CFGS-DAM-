# Ejercicio 1: "Realizar un programa que conste de una clase llamada 'Estudiante', que tenga como atributos el nombre y la nota del alumno. Definir los métodos para iniciar sus atributos, imprimirlos y mostrar un mensaje con el resultado de la nota y si ha aprobado o no."


# Crea la clase 'Nota' del programa.
class Nota:
    # Crea el construcctor.
    def __init__(self, notaPrimerTrimestre, notaSegundoTrimestre, notaTercerTrimestre):
        self.notaPrimerTrimestre = notaPrimerTrimestre
        self.notaSegundoTrimestre = notaSegundoTrimestre
        self.notaTercerTrimestre = notaTercerTrimestre

    # Crea el método/función 'notaFinal'.
    def notaFinal(self):
        return (self.notaPrimerTrimestre + self.notaSegundoTrimestre + self.notaTercerTrimestre) / 3
    
    # Crea el método/función 'clasificacionNota'.
    def clasificacionNota(self):
        nota = self.notaFinal()
        if nota < 5:
            return f"\n\nEl alumno ha suspendido con una nota final 'insuficiente' de {nota:.2f} puntos."
        elif nota < 6:
            return f"\n\nEl alumno ha aprobado con una nota final 'suficiente' de {nota:.2f} puntos."
        elif nota < 7:
            return f"\n\nEl alumno ha aprobado con una nota final 'bien' de {nota:.2f} puntos."
        elif nota < 8:
            return f"\n\nEl alumno ha aprobado con una nota final 'notable bajo' de {nota:.2f} puntos."
        elif nota < 9:
            return f"\n\nEl alumno ha aprobado con una nota final 'notable alto' de {nota:.2f} puntos."
        elif nota < 10:
            return f"\n\nEl alumno ha aprobado con una nota final 'sobresaliente' de {nota:.2f} puntos."
        elif nota == 10:
            return f"\n\nEl alumno ha aprobado con una nota final 'matrícula de honor' de {nota:.2f} puntos."
        else:
            return f"\n\nError. En las notas introducidas o en su cálculo."

# Crea la clase 'Estudiante' del programa que hereda/reutiliza la clase padre "Main".
class Estudiante:
    def __init__(self, nombre, apellido, curso, notaPrimerTrimestre, notaSegundoTrimestre, notaTercerTrimestre):
        self.nombre = nombre
        self.apellido = apellido
        self.curso = curso
        self.notas = Nota(notaPrimerTrimestre, notaSegundoTrimestre, notaTercerTrimestre)

    # Crea el método/función 'imprimir' como muestreo del resultado final al usuario.
    def imprimir (self):
        print(f"\n\nEl alumno {self.nombre} {self.apellido}, del curso {self.curso}, ha obtenido las siguientes notas:\n\tPrimer trimestre: {self.notas.notaPrimerTrimestre} puntos.\n\tSegundo trimestre: {self.notas.notaSegundoTrimestre} puntos.\n\tTercer trimestre: {self.notas.notaTercerTrimestre} puntos.\n\tObteniendo una calificación final de {self.notas.clasificacionNota()} puntos.")
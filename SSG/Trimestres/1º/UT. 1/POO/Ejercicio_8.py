# Ejercicio 8: "Desarrollar un programa con tres clases: La primera debe ser Universidad, con atributos nombre (Donde se almacena el nombre de la Universidad). La segunda llamada Carrera, con los atributos especialidad (En donde me guarda la especialidad de un estudiante). Y por último, una llamada Estudiante, que tenga como atributos su nombre y edad. El programa debe imprimir la especialidad, edad, nombre y universidad de dicho estudiante con un objeto llamado persona."


# Clase "Universidad".
class Universidad:
    def __init__(self, nombre):
        self.nombre = nombre

# Clase "Carrera".
class Carrera:
    def __init__(self, especialidad):
        self.especialidad = especialidad

# Clase "Estudiante".
class Estudiante:
    def __init__(self, nombre, edad, universidad, carrera):
        self.nombre = nombre
        self.edad = edad
        self.universidad = universidad
        self.carrera = carrera

    # Método para mostrar la información del estudiante.
    def mostrar_informacion(self):
        print(f"Nombre: {self.nombre}")
        print(f"Edad: {self.edad}")
        print(f"Universidad: {self.universidad.nombre}")
        print(f"Carrera: {self.carrera.especialidad}")


# Programa principal.
if __name__ == "__main__":
    # Creamos objetos.
    uni = Universidad("Universidad de La Laguna")
    carrera = Carrera("Ingeniería Informática")
    persona = Estudiante("Rodrigo", 20, uni, carrera)

    # Mostramos los datos.
    persona.mostrar_informacion()
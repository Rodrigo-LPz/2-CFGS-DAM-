# Ejercicio 5: "Crear una clase "Persona" que sea la clase Padre de otra clase "Estudiante". Por tanto: En la clase "Persona" su método __init__() debe de estar preparado para recibir nombre y apellido. Además, esta clase, debe tener un método para mostrar el nombre acompañado del apellido. La otra clase "Estudiante", debe de poder heredar de "Persona", y además recibir los argumentos nombre y edad. También la clase "Estudiante", recibe el valor "carrera", y además contar con un método mostrar_carrera(). Las dos clases son obligatorias."


# Clase Padre.
class Persona:
    def __init__(self, nombre, apellido):
        self.nombre = nombre
        self.apellido = apellido

    def mostrar_nombre_completo(self):
        return f"{self.nombre} {self.apellido}"


# Clase Hija.
class Estudiante(Persona):
    def __init__(self, nombre, apellido, edad, carrera):
        super().__init__(nombre, apellido)  # Llamamos al __init__ de la clase Persona
        self.edad = edad
        self.carrera = carrera

    def mostrar_carrera(self):
        return self.carrera

# Bienvenida al usuario y solicitud de datos (nombre, apellido, edad y carrera).
# Solicitar el nombre al usuario.
nombre = input("\nBienvenido/a al programa 'Personas', por favor ingrese su nombre: ")

# Solicitar el nombre de la persona, su apellido, la edad y la carrera.
nombrePersona = input(f"Bienvenido/a a la aplicacion profesor/a {nombre}, ahora el nombre de la persona: ")
apellido = input(f"\nAhora introduzca el apellido de {nombrePersona}: ")
edad = int(input(f"\nAhora introduzca su edad: "))
carrera = input(f"\nY, por último, especifique el nombre de la carrera que está cursando {nombrePersona} {apellido}: ")

# Almacena los valores/datos introducidos u obtenidos en una instancia de la clase "Estudiante".
estudiante = Estudiante(nombrePersona, apellido, edad, carrera)

# Muestreo del resultado final al usuario.
#print ("\n\nLos datos introducidos son los siguientes:\n\tNombre completo:", estudiante.mostrar_nombre_completo(), "\n\tEdad: ", estudiante.edad, "\n\tCarrera cursada: ", estudiante.carrera, "\n\n")
print (f"\n\nLos datos introducidos son los siguientes:\n\tNombre completo: {estudiante.mostrar_nombre_completo()}\n\tEdad: {estudiante.edad}\n\tCarrera cursada: {estudiante.carrera}\n\n")
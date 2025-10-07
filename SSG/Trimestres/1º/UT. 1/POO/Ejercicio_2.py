# Ejercicio 2: "Crea una clase “Persona”. Con atributos nombre y edad. Además, hay que crear un método “cumpleaños”, que aumente en 1 la edad de la persona cuando se invoque sobre un objeto creado con “Persona”. Tendríamos que lograr ejecutar el siguiente código con la clase creada:."


# Bienvenida al usuario y solicitud de datos (nombre, edad).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido/a al programa 'Gestión de Personas', por favor ingrese su nombre: ")
# Solicitar la edad al usuario.
nombrePersona = input(f"\nBienvenido/a a la aplicacion {nombre}, ahora introduzca el nombre de la persona a registrar: ")
edad = int(input(f"\nValor guardado/almacenado. Por último, introduzca la edad de la persona a registrar: "))

# Crea la clase "Persona" del programa.
class Persona:
    def __init__(self, nombrePersona, edad):
        self.nombrePersona = nombrePersona
        self.edad = edad

    # Método cumpleaños: aumenta la edad en 1.
    def cumpleaños(self):
        self.edad += 1
        print(f"¡Feliz cumpleaños, {self.nombrePersona}! Ahora tiene {self.edad} años.")


# Crear una iteración, un objeto, de la clase "Persona".
persona1 = Persona(nombre, edad)


# Muestreo de los datos iniciales al usuario.
print(f"\nDatos iniciales de la persona:\nNombre: {persona1.nombrePersona}\nEdad: {persona1.edad}")

# Invocar el método cumpleaños.
persona1.cumpleaños()

# Muestreo del resultado final al usuario.
print(f"\nDatos actualizados de la persona:\nNombre: {persona1.nombrePersona}\nEdad: {persona1.edad}")
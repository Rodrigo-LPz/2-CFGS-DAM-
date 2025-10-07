# Ejercicio 7: "Crear una clase llamada Marino(), con un método que sea hablar, en donde muestre un mensaje que diga «Hola, soy un animal marino!». Luego, crear una clase Pulpo() que herede Marino, pero modificar el mensaje de hablar por «Hola soy un Pulpo!». Por último, crear una clase Foca(), heredada de Marino, pero que tenga un atributo nuevo llamado mensaje y que muestre ese mensaje como parámetro."


# Clase Padre.
class Marino:
    def hablar(self):
        print("Hola, soy un animal marino!")

# Clase Hija "Pulpo" que hereda de "Marino".
class Pulpo(Marino):
    def hablar(self):
        print("Hola, soy un Pulpo!")

# Clase Hija "Foca" que hereda de "Marino".
class Foca(Marino):
    def __init__(self, mensaje):
        self.mensaje = mensaje

    def hablar(self):
        print(self.mensaje)

# Ejemplo de uso.
print("Ejemplos de animales marinos:\n")

# Crear objeto "Marino".
animal1 = Marino()
animal1.hablar()

# Crear objeto "Pulpo".
animal2 = Pulpo()
animal2.hablar()

# Crear objeto Foca con mensaje personalizado.
mensaje_foca = input("\nIngrese un mensaje para la foca: ")
animal3 = Foca(mensaje_foca)
animal3.hablar()
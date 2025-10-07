# Ejercicio 6: "Crear una clase "Fábrica" que tenga los atributos de "Llantas", "Color" y "Precio"; luego crear dos clases más que hereden de "Fábrica", las cuales son "Moto" y "Carro". Crear métodos que muestren la cantidad de llantas, color y precio de ambos transportes. Por último, crear objetos para cada clase y mostrar por pantalla los atributos de cada uno."


# Clase padre.
class Fabrica:
    def __init__(self, llantas, color, precio):
        self.llantas = llantas
        self.color = color
        self.precio = precio

    def mostrar_atributos(self):
        return f"Llantas: {self.llantas}, Color: {self.color}, Precio: ${self.precio}"


# Clase hija Moto.
class Moto(Fabrica):
    def __init__(self, llantas, color, precio):
        super().__init__(llantas, color, precio)


# Clase hija Carro.
class Carro(Fabrica):
    def __init__(self, llantas, color, precio):
        super().__init__(llantas, color, precio)

# Bienvenida al usuario y solicitud de datos (nombre, moto, coche, llantas, color y precio).
# Solicitar el nombre al usuario.
nombre = input("\nBienvenido/a al programa 'Fábrica', por favor ingrese su nombre: ")

# Solicitar el tipo de vehículo.
eleccion = input(f"\nBienvenido/a a la aplicacion {nombre}, ahora seleccione el tipo de vehículo a fabricar (moto) y/o (coche): ")

if eleccion.strip().lower() == "moto":
    llantas = 2
    color = input("\nValor guardado/almacenado. Por favor, ahora ingrese el color de la moto: ")
    precio = float(input("\npor último, ahora ingrese el precio de la moto: "))
    vehiculo = Moto(llantas, color, precio)
elif eleccion.strip().lower() == "coche":
    llantas = 4
    color = input("\nValor guardado/almacenado. Por favor, ahora ingrese el color del coche: ")
    precio = float(input("\nPor último, ahora ingrese el precio del coche: "))
    vehiculo = Carro(llantas, color, precio)
else:
    print("\n\nError. Tipo de vehículo no válido o no lo reconoce el sistema.")
    vehiculo = None

# Muestreo del resultado final al usuario.
if vehiculo is not None:
    print (f"\n\nLos datos introducidos son los siguientes:\n\tTipo de vehículo pedido a fabricar: {eleccion}\n\tTotal llantas: {llantas}\n\tColor solicitado {color}\n\tPrecio a pagar {precio}\n\n")
    print (vehiculo)
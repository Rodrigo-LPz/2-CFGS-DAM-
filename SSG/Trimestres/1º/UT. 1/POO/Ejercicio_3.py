# Ejercicio 3: "Crear una la Clase “Banco”, iniciando la variable “saldo” y la cuenta en la que aparece. Además, de si ha sacado o ingresado dinero, que se pueda consultar el dinero de cada cuenta. Se debe mostrar por pantalla, a que cuenta quiere entrar (“ahorros” o “ocio”), luego si quiere sacar dinero, ingresar, o consultar. Que no se pueda sacar dinero para quedarse en negativo. Y luego que muestra que operación se ha hecho y que salgo queda."


# Bienvenida al usuario y solicitud de datos (nombre, edad).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido/a al programa 'Gestión Bancaria', por favor ingrese su nombre: ")
# Solicitar el nombre del alumno y su nota al usuario.
opcion = input(f"\nBienvenido/a a la aplicacion {nombre}, ahora introduzca a qué tipo de cuenta desea ingresar (ahorros) o (socio): ")

# Validar que la cuenta sea correcta.
while opcion.strip().lower() not in ["ahorros", "ocio"]:
    opcion = input("Error. el tipo de cuenta seleccionado es erróneo o no lo reconoce el sistema.")

# Definición de la clase "Banco"
class Banco:
    def __init__(self, saldo = 0):
        self.saldo = saldo

    # Método para ingresar dinero.
    def ingresar(self, cantidad):
        self.saldo += cantidad
        print(f"Se han ingresado {cantidad}€. Saldo actual: {self.saldo}€.")

    # Método para sacar dinero.
    def sacar(self, cantidad):
        if cantidad > self.saldo:
            print("Erro. No se puede realizar la operación. Saldo insuficiente.")
        else:
            self.saldo -= cantidad
            print(f"Se han retirado {cantidad}€. Saldo actual: {self.saldo}€.")

    # Método para consultar saldo
    def consultar_saldo(self):
        print(f"Saldo actual: {self.saldo}€.")

# Crear objeto Banco para la cuenta elegida
mi_cuenta = Banco(saldo=1000)  # Por ejemplo, saldo inicial de 1000€.

# Solicitar la operación a realizar
opcion = input("\n¿Qué operación desea realizar?\n\tIngresar dinero [1].\n\tSacar dinero [2].\n\tConsultar saldo [3].")

# Realizar la operación solicitada según la opción escogida.
if opcion == "1":
    cantidad = float(input("Introduzca la cantidad a ingresar: "))
    mi_cuenta.ingresar(cantidad)
elif opcion == "2":
    cantidad = float(input("Introduzca la cantidad a retirar: "))
    mi_cuenta.sacar(cantidad)
elif opcion == "3":
    mi_cuenta.consultar_saldo()
else:
    print("Error. Opción no válida.")
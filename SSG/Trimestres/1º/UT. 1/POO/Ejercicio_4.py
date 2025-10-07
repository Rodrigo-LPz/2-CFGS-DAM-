# Ejercicio 4: "Realizar un programa en el cual se declaren dos valores enteros por teclado utilizando el método __init__. Calcular después la suma, resta, multiplicación y división. Utilizar un método para cada una e imprimir los resultados obtenidos. Llamar a la clase Calculadora."


# Clase Calculadora.
class Calculadora:
    def __init__(self, numero1, numero2):
        self.numero1 = numero1
        self.numero2 = numero2

    def sumar(self):
        return self.numero1 + self.numero2

    def restar(self):
        return self.numero1 - self.numero2

    def multiplicar(self):
        return self.numero1 * self.numero2

    def dividir(self):
        if self.numero2 == 0:
            return "Error. No se puede dividir entre 0. Operación con resultado indefinido."
        return self.numero1 / self.numero2

# Bienvenida al usuario y solicitud de datos (nombre, numeros y tipo de operación).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido/a al programa 'Calculadora', por favor ingrese su nombre: ")

# Solicitar los numeros y el tipo de operación.
numero1 = float(input(f"\nBienvenido/a a la aplicacion {nombre}, ahora introduzca el primero de los dos números a operar: "))
numero2 = float(input("\nValor guardado/almacenado. De nuevo, ahora introduzca el segundo de los dos números a operar: "))
eleccion = input("\n\nPor último, ahora introduzca que tipo de operación desea realizar (sumar), (restar), (multiplicar) y/o (dividir): ")

# Crea un objeto de "calculadora".
calc = Calculadora(numero1, numero2)

# Ejecuta operación seleccionada.
if eleccion.strip().lower() == "sumar":
    print(f"\n\nEl resultado de la suma entre los números {numero1} y {numero2} es de: {calc.sumar()}.")
elif eleccion.strip().lower() == "restar":
    print(f"\n\nEl resultado de la resta entre los números {numero1} y {numero2} es de: {calc.restar()}.")
if eleccion.strip().lower() == "multiplicar":
    print(f"\n\nEl resultado de la suma entre los números {numero1} y {numero2} es de: {calc.multiplicar()}.")
elif eleccion.strip().lower() == "dividir":
    print(f"\n\nEl resultado de la resta entre los números {numero1} y {numero2} es de: {calc.dividir()}.")
else:
    print(f"\n\nError. El tipo de operación introducida no es correcta o reconocida por nuestro sistema.")
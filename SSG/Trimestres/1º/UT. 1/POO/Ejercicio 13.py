# Ejercicio 13: "Crear en Python un programa que registre en un archivo .txt las veces que un alumno vaya al baño. Deberá contener la siguiente información: Nombre del alumno, fecha, hora (puede introducirla por pantalla o cogerla del sistema), asignatura en ese momento y profesor/a. El programa debe estar pendiente de que le introduzca un nuevo valor. y que salga del programa con una tecla en concreto."


import datetime

# Clase principal del programa.
class RegistroBano:
    # Método para inicializar los atributos de "RegistroBano".
    def __init__(self, archivo="registro_bano.txt"):
        self.archivo = archivo

    # Método para registrar una nueva salida al baño.
    def registrar(self, nombre, asignatura, profesor):
        # Fecha y hora actuales del sistema.
        fecha = datetime.datetime.now().strftime("%Y-%m-%d")
        hora = datetime.datetime.now().strftime("%H:%M:%S")

        # Crear línea de registro.
        linea = f"{fecha} {hora} | Alumno: {nombre} | Asignatura: {asignatura} | Profesor: {profesor}\n"

        # Guardar en archivo.
        with open(self.archivo, "a", encoding="utf-8") as f:
            f.write(linea)

        print("Registro guardado correctamente.")

    # Método para mostrar el historial de registros.
    def mostrar_historial(self):
        print("\n=== HISTORIAL DE REGISTROS ===")
        try:
            with open(self.archivo, "r", encoding="utf-8") as f:
                print(f.read())
        except FileNotFoundError:
            print("Error. No hay registros todavía.")


# ---------------- PROGRAMA PRINCIPAL ----------------
def menu():
    registro = RegistroBano()

    # Bucle de tipo "while" para el menú interactivo.
    while True:
        print("\n===== REGISTRO DE BAÑOS =====")
        print("1. Registrar nueva salida al baño")
        print("2. Mostrar historial")
        print("0. Salir")

        opcion = input("Selecciona una opción: ")

        # Validación de la opción introducida por el usuario.
        if opcion == "1":
            nombre = input("Nombre del alumno: ")
            asignatura = input("Asignatura: ")
            profesor = input("Profesor/a: ")
            registro.registrar(nombre, asignatura, profesor)
        elif opcion == "2":
            registro.mostrar_historial()
        elif opcion == "0":
            print("Cerrando/Saliendo del sistema de registro.")
            break
        else:
            print("Error. La opción escogida no es válida o no la reconoce el sistema.")

# Ejecutar el programa principal.
if __name__ == "__main__":
    menu()
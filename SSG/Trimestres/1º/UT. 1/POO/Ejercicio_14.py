# Ejercicio 14: "Crea un programa en Python que simule un sistema de compra de entradas de cine donde el usuario pueda seleccionar entre 5 películas diferentes, elegir entre 3 sesiones horarias disponibles, y seleccionar entre 4 tipos de combos de palomitas o la opción de no comida. El programa debe mostrar los precios de cada opción, calcular el total automáticamente, validar que las selecciones sean correctas, y finalmente mostrar un resumen detallado de la compra con el desglose de costos, así como llevar el control de asientos disponibles."


# Clase principal del programa.
class Cine:
    # Método para inicializar los atributos de "Cine".
    def __init__(self):
        # Lista de películas disponibles con sesiones y precios base.
        self.peliculas = {
            "1": {"titulo": "Avengers", "precio": 6.0},
            "2": {"titulo": "Batman", "precio": 5.5},
            "3": {"titulo": "Frozen 2", "precio": 4.5},
            "4": {"titulo": "Interstellar", "precio": 7.0},
            "5": {"titulo": "Mario Bros", "precio": 5.0},
        }
        # Asientos disponibles por sesión.
        self.asientos = {peli: 20 for peli in self.peliculas.keys()}  # 20 asientos por película
        # Combos de palomitas.
        self.combos = {
            "0": {"nombre": "Sin comida", "precio": 0},
            "1": {"nombre": "Pequeño", "precio": 3},
            "2": {"nombre": "Mediano", "precio": 5},
            "3": {"nombre": "Grande", "precio": 7},
            "4": {"nombre": "Familiar", "precio": 10},
        }
        # Sesiones disponibles.
        self.sesiones = ["16:00", "18:30", "21:00"]

    # Método para mostrar películas.
    def mostrar_peliculas(self):
        print("\n=== CARTELERA ===")
        for codigo, info in self.peliculas.items():
            print(f"{codigo}. {info['titulo']} - {info['precio']}€")
    
    # Método para mostrar combos.
    def mostrar_combos(self):
        print("\n=== COMBOS DISPONIBLES ===")
        for codigo, combo in self.combos.items():
            print(f"{codigo}. {combo['nombre']} - {combo['precio']}€")

    # Método para mostrar sesiones.
    def mostrar_sesiones(self):
        print("\n=== SESIONES DISPONIBLES ===")
        for i, hora in enumerate(self.sesiones, 1):
            print(f"{i}. {hora}")

    # Método para comprar una entrada.
    def comprar_entrada(self):
        self.mostrar_peliculas()
        pelicula = input("Selecciona una película (1-5): ")
        # Validar película, si no es válida, salir del método.
        if pelicula not in self.peliculas:
            print("Error. La opción escogida no es válida o no la reconoce el sistema.")
            return
        
        # Validar asientos disponibles.
        if self.asientos[pelicula] <= 0:
            print("Error. No quedan asientos disponibles para esta película.")
            return

        self.mostrar_sesiones()

        # Validar sesión, si no es válida, salir del método.
        try:
            sesion = int(input("Selecciona una sesión (1-3): "))
            if sesion < 1 or sesion > 3:
                print("Error. la sesión escogida no es válida o no la reconoce el sistema.")
                return
        except ValueError:
            print("Error. recuerda que debes introducir un número.")
            return

        self.mostrar_combos()
        combo = input("Selecciona un combo (0-4): ")

        # Validar combo, si no es válido, salir del método.
        if combo not in self.combos:
            print("Error. La opción de 'combo' no es válida o no la reconoce el sistema..")
            return

        # Calcular el precio.
        precio_base = self.peliculas[pelicula]["precio"]
        precio_combo = self.combos[combo]["precio"]
        total = precio_base + precio_combo

        # Reservar asiento.
        self.asientos[pelicula] -= 1

        # Mostrar resumen de la compra.
        print("\nCOMPRA REALIZADA")
        print(f"Pelicula: {self.peliculas[pelicula]['titulo']}")
        print(f"Sesión: {self.sesiones[sesion-1]}")
        print(f"Combo: {self.combos[combo]['nombre']}")
        print(f"Total a pagar: {total}€")
        print(f"Asientos restantes para {self.peliculas[pelicula]['titulo']}: {self.asientos[pelicula]}")


# ---------------- PROGRAMA PRINCIPAL ----------------
def menu():
    cine = Cine()

    # Bucle de tipo "while" para el menú interactivo.
    while True:
        print("\n===== SISTEMA DE COMPRA DE ENTRADAS DE CINE =====")
        print("1. Ver cartelera")
        print("2. Comprar entrada")
        print("0. Salir")

        opcion = input("Selecciona una opción: ")

        # Validación de la opción introducida por el usuario.
        if opcion == "1":
            cine.mostrar_peliculas()
        elif opcion == "2":
            cine.comprar_entrada()
        elif opcion == "0":
            print("Gracias por usar el sistema de cine.")
            break
        else:
            print("Error. La opción escogida no es válida o no la reconoce el sistema.")

# ---------------- EJECUCIÓN DEL PROGRAMA ----------------
if __name__ == "__main__":
    menu()
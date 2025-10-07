# Ejercicio 15: "Crear un programa en Python de una pasarela de compra de un billete de avión. Este programa debe tener la posibilidad de comprar 5 posibles destinos desde Lanzarote. En dos clases posibles (Business y Turista) y dos precios diferenciados (incluso con descuento de residente canario). Que permita compra de servicios como añadir una maleta más (más dinero), seguro del viaje o elegir asiento (Se deben definir los asientos de un avión al inicio, por ejemplo, un Boeing 737). Debe tener un contador de asientos, para evitar el overbooking."


# Clase padre.
class Vuelo:
    # Método para inicializar los atributos de "Vuelo".
    def __init__(self, destino, precio_turista, precio_business):
        self.destino = destino
        self.precio_turista = precio_turista
        self.precio_business = precio_business
        self.asientos = {  # 10 asientos turista, 5 business.
            "Turista": 10,
            "Business": 5
        }

    # Método para reservar un asiento.
    def reservar_asiento(self, clase):
        if self.asientos[clase] > 0:
            self.asientos[clase] -= 1
            return True
        return False

# Clase principal del sistema de pasarela de vuelos.
class PasarelaVuelos:
    # Método para inicializar los atributos de "PasarelaVuelos".
    def __init__(self):
        self.vuelos = [
            Vuelo("Madrid", 80, 150),
            Vuelo("Barcelona", 75, 140),
            Vuelo("Gran Canaria", 60, 120),
            Vuelo("Tenerife", 65, 130),
            Vuelo("Sevilla", 70, 135),
        ]

    # Método para mostrar destinos.
    def mostrar_destinos(self):
        print("\n=== DESTINOS DISPONIBLES ===")
        for i, vuelo in enumerate(self.vuelos, 1):
            print(f"{i}. {vuelo.destino} | Turista: {vuelo.precio_turista}€ | Business: {vuelo.precio_business}€")
            print(f"   Asientos disponibles -> Turista: {vuelo.asientos['Turista']} | Business: {vuelo.asientos['Business']}")

    # Método para comprar un billete.
    def comprar_billete(self):
        self.mostrar_destinos()
        try:
            opcion = int(input("Selecciona un destino (1-5): "))
            vuelo = self.vuelos[opcion - 1]
        except (ValueError, IndexError):
            print("Error. La opción escogida no es válida o no la reconoce el sistema.")
            return
        
        # Selección de clase.
        clase = input("Clase (Turista/Business): ").capitalize()
        if clase not in vuelo.asientos:
            print("Error. La clase seleccionada no es válida o no la reconoce el sistema.")
            return
        
        # Verificar disponibilidad de asientos.
        if vuelo.asientos[clase] <= 0:
            print("Error. No quedan asientos disponibles en esa clase.")
            return

        # Precio base.
        precio = vuelo.precio_turista if clase == "Turista" else vuelo.precio_business

        # Descuento residente canario.
        residente = input("¿Eres residente canario? (s/n): ").lower()
        if residente == "s":
            precio *= 0.75  # 25% descuento.

        # Servicios extra.
        print("\n=== SERVICIOS EXTRA ===")
        print("1. Maleta adicional (+20€)")
        print("2. Seguro de viaje (+15€)")
        print("3. Elegir asiento (+10€)")
        print("0. Ninguno")
        extras = 0

        # Bucle para seleccionar múltiples extras.
        while True:
            opcion = input("Selecciona un extra (0 para finalizar): ")
            if opcion == "1":
                extras += 20
            elif opcion == "2":
                extras += 15
            elif opcion == "3":
                extras += 10
            elif opcion == "0":
                break
            else:
                print("Error. La opción escogida no es válida o no la reconoce el sistema.")

        total = precio + extras

        # Reservar asiento
        vuelo.reservar_asiento(clase)

        # Mostrar resumen de la compra.
        print("\nCOMPRA REALIZADA")
        print(f"Destino: {vuelo.destino}")
        print(f"Clase: {clase}")
        print(f"Precio base: {precio:.2f}€")
        print(f"Extras: {extras}€")
        print(f"TOTAL: {total:.2f}€")
        print(f"Asientos restantes -> Turista: {vuelo.asientos['Turista']} | Business: {vuelo.asientos['Business']}")


# ---------------- PROGRAMA PRINCIPAL ----------------
def menu():
    sistema = PasarelaVuelos()

    # Bucle de tipo "while" para el menú interactivo.
    while True:
        print("\n===== PASARELA DE COMPRA DE VUELOS =====")
        print("1. Ver destinos")
        print("2. Comprar billete")
        print("0. Salir")

        opcion = input("Selecciona una opción: ")

        # Validación de la opción introducida por el usuario.
        if opcion == "1":
            sistema.mostrar_destinos()
        elif opcion == "2":
            sistema.comprar_billete()
        elif opcion == "0":
            print("Gracias por usar la pasarela de vuelos.")
            break
        else:
            print("Error. La opción escogida no es válida o no la reconoce el sistema.")

# ---------------- FIN PROGRAMA PRINCIPAL ----------------
if __name__ == "__main__":
    menu()
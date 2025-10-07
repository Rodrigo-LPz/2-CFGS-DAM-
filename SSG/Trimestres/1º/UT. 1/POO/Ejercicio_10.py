# Ejercicio 10: "Debes crear un programa que simule el juego de Piedra, Papel o Tijera entre un jugador humano y la computadora. El sistema debe generar elecciones aleatorias para la computadora, validar las entradas del usuario, determinar el ganador de cada ronda según las reglas clásicas, y mantener un historial de partidas."


import random

# Clase principal del juego.
class PiedraPapelTijera:
    # Método para inicializar el juego.
    def __init__(self):
        self.opciones = ["piedra", "papel", "tijera"]
        self.historial = []

    # Método para que la computadora elija aleatoriamente entre las opciones.
    def eleccion_computadora(self):
        return random.choice(self.opciones)
    
    # Método para determinar el ganador de la ronda.
    def determinar_ganador(self, jugador, computadora):
        if jugador == computadora:
            return "Empate"
        elif (jugador == "piedra" and computadora == "tijera") or \
             (jugador == "papel" and computadora == "piedra") or \
             (jugador == "tijera" and computadora == "papel"):
            return "Jugador"
        else:
            return "Computadora"
        
    # Método principal para iniciar/jugar el juego.
    def jugar(self):
        print("¡Bienvenido a Piedra, Papel o Tijera!")
        while True:
            jugador = input("Elige piedra, papel o tijera (o 'salir' para terminar): ").lower()

            if jugador == "salir":
                print("Juego terminado. Gracias por jugar.")
                break

            if jugador not in self.opciones:
                print("Opción no válida, intenta de nuevo.")
                continue

            computadora = self.eleccion_computadora()
            print(f"La computadora eligió: {computadora}")

            resultado = self.determinar_ganador(jugador, computadora)
            print(f"Resultado: {resultado}\n")

            # Guardar en historial.
            self.historial.append((jugador, computadora, resultado))

        # Mostrar historial final.
        print("\nHistorial de partidas:")
        for i, partida in enumerate(self.historial, 1):
            print(f"Partida {i}: Jugador={partida[0]}, Computadora={partida[1]}, Resultado={partida[2]}")


# Ejecutar el juego.
if __name__ == "__main__":
    juego = PiedraPapelTijera()
    juego.jugar()

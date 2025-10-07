# Ejercicio 9: "Debes crear un programa que permita a dos jugadores jugar al Tic Tac Toe. El juego debe mostrar un tablero 3x3, alternar turnos entre los jugadores (X y O), validar movimientos, detectar cuando hay un ganador o empate, y proporcionar una interfaz clara para los jugadores."


# Clase padre/principal.
class TicTacToe:
    # Método para inicializar el juego.
    def __init__(self):
        self.tablero = [[" " for _ in range(3)] for _ in range(3)]
        self.jugador_actual = "X"

    def mostrar_tablero(self):
        print("\n")
        for fila in self.tablero:
            print("|".join(fila))
            print("-" * 5)
        print("\n")

    # Método para validar movimientos dentro del rango y que la celda esté vacía.
    def movimiento_valido(self, fila, col):
        return 0 <= fila < 3 and 0 <= col < 3 and self.tablero[fila][col] == " "

    def hacer_movimiento(self, fila, col):
        if self.movimiento_valido(fila, col):
            self.tablero[fila][col] = self.jugador_actual
            return True
        else:
            print("Movimiento inválido, intenta de nuevo.")
            return False

    def hay_ganador(self):
        # Filas y columnas
        for i in range(3):
            if self.tablero[i][0] == self.tablero[i][1] == self.tablero[i][2] != " ":
                return True
            if self.tablero[0][i] == self.tablero[1][i] == self.tablero[2][i] != " ":
                return True
        # Diagonales
        if self.tablero[0][0] == self.tablero[1][1] == self.tablero[2][2] != " ":
            return True
        if self.tablero[0][2] == self.tablero[1][1] == self.tablero[2][0] != " ":
            return True
        return False
    
    # Método para verificar si el tablero está lleno (empate).
    def tablero_lleno(self):
        for fila in self.tablero:
            if " " in fila:
                return False
        return True

    def cambiar_turno(self):
        self.jugador_actual = "O" if self.jugador_actual == "X" else "X"

    # Método de bienvenida y control del juego.
    def jugar(self):
        print("¡Bienvenido a Tic Tac Toe!")
        self.mostrar_tablero()

        # Bucle principal del juego, se repite hasta que haya un ganador o empate.
        while True:
            try:
                fila = int(input(f"Jugador {self.jugador_actual}, ingresa fila (0-2): "))
                col = int(input(f"Jugador {self.jugador_actual}, ingresa columna (0-2): "))
            except ValueError:
                print("Por favor, ingresa números válidos.")
                continue

            # Realizar el movimiento y actualizar el tablero.
            if self.hacer_movimiento(fila, col):
                self.mostrar_tablero()

                if self.hay_ganador():
                    print(f"¡Jugador {self.jugador_actual} ha ganado!")
                    break
                elif self.tablero_lleno():
                    print("¡Empate!")
                    break

                self.cambiar_turno()


# Ejecutar el juego
if __name__ == "__main__":
    juego = TicTacToe()
    juego.jugar()
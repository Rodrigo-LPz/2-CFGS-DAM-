# ------------------------------------------------------------
# EJERCICIO 10 - JUEGO MEMORY (Concentration) con Tkinter y Pillow
# ------------------------------------------------------------
# Usa imágenes de la carpeta "hiragana/" para formar las parejas.
# Panel de 4x3 (12 cartas). Temporizador, contador de intentos,
# reinicio y mensaje al completar.
# ------------------------------------------------------------

import tkinter as tk                      # Interfaz gráfica
from tkinter import messagebox            # Mensajes emergentes
from PIL import Image, ImageTk            # Para cargar y convertir imágenes
import os                                 # Trabajar con rutas y archivos
import random                             # Barajar cartas
import time                               # Medir tiempo transcurrido

# ------------------------------------------------------------
# CONFIGURACIÓN: rutas y parámetros
# ------------------------------------------------------------

# Carpeta que contiene imágenes (debe existir en el mismo directorio que este script)
import os
CARPETA_IMAGENES = os.path.join(os.path.dirname(__file__), "hiragana")

# Número de pares que queremos en el juego (6 pares -> 12 cartas, tablero 4x3)
PARES_DESEADOS = 6

# Tamaño en píxeles de las miniaturas de las cartas
TAM_CARTA = (120, 120)

# Retardo (ms) para voltear cartas no coincidentes
RETRASO_VOLTEO = 900

# ------------------------------------------------------------
# CLASE PRINCIPAL DEL JUEGO
# ------------------------------------------------------------
class MemoryGame:
    def __init__(self, root):
        # Ventana principal que se pasa desde fuera
        self.root = root

        # Título de la ventana
        self.root.title("Memory - Juego de Parejas")

        # Frame principal para contener todo
        self.frame = tk.Frame(self.root, padx=10, pady=10)
        self.frame.pack()

        # Cargar rutas de imágenes disponibles en la carpeta
        # lista_archivos contendrá nombres de archivo (png/jpg/jpeg)
        if not os.path.exists(CARPETA_IMAGENES):
            messagebox.showerror("Error", f"No se encontró la carpeta '{CARPETA_IMAGENES}'.")
            root.destroy()
            return

        lista_archivos = [f for f in os.listdir(CARPETA_IMAGENES)
                          if f.lower().endswith((".png", ".jpg", ".jpeg", ".gif"))]

        # Si no hay imágenes, mostramos error y salimos
        if not lista_archivos:
            messagebox.showerror("Error", f"No hay imágenes en la carpeta '{CARPETA_IMAGENES}'.")
            root.destroy()
            return

        # Elegir PARES_DESEADOS imágenes únicas. Si hay menos, reutilizamos aleatoriamente.
        # Primero mezclamos para elegir aleatoriamente
        random.shuffle(lista_archivos)
        elecciones = []

        # Si hay suficientes imágenes, tomar las primeras PARES_DESEADOS
        if len(lista_archivos) >= PARES_DESEADOS:
            elecciones = lista_archivos[:PARES_DESEADOS]
        else:
            # Si hay menos, tomar todas las disponibles y completar repitiendo aleatoriamente
            elecciones = lista_archivos[:]  # copia
            while len(elecciones) < PARES_DESEADOS:
                elecciones.append(random.choice(lista_archivos))

        # Construir la lista de cartas duplicando cada imagen (cada par)
        cartas = []
        for nombre in elecciones:
            ruta = os.path.join(CARPETA_IMAGENES, nombre)
            cartas.append(ruta)
            cartas.append(ruta)  # segunda copia para la pareja

        # Barajar las cartas
        random.shuffle(cartas)
        self.cartas = cartas  # lista de rutas en orden aleatorio (long = PARES_DESEADOS * 2)

        # Estados del juego
        self.botones = []             # lista de widgets Button
        self.imagenes_tk = []         # referencias PhotoImage para cada carta (para que no se recojan)
        self.reveladas = [False] * len(self.cartas)   # si la carta está permanentemente emparejada
        self.mostradas_idx = []       # índices de las dos cartas volteadas en el turno actual
        self.intentos = 0             # número de intentos (pares intentados)
        self.pares_encontrados = 0    # pares que ya se han emparejado

        # Temporizador
        self.tiempo_inicio = None
        self.tiempo_label = tk.Label(self.frame, text="Tiempo: 00:00")
        self.tiempo_label.grid(row=0, column=0, columnspan=4, pady=(0, 8), sticky="w")

        # Intentos label
        self.intentos_label = tk.Label(self.frame, text="Intentos: 0")
        self.intentos_label.grid(row=0, column=2, columnspan=2, pady=(0, 8), sticky="e")

        # Crear botonera (tablero) 4 columnas x 3 filas
        # Determinamos filas y columnas según el número total de cartas
        total_cartas = len(self.cartas)
        cols = 4
        rows = (total_cartas + cols - 1) // cols  # ceil division

        # Crear imagen "reverso" para mostrar cuando la carta está boca abajo
        # Creamos una imagen simple con PIL (fondo azul marino con texto "?" opcional)
        reverso_img = Image.new("RGB", TAM_CARTA, color=(20, 60, 120))  # fondo azul oscuro
        # convertimos a PhotoImage
        self.reverso_tk = ImageTk.PhotoImage(reverso_img)

        # Cargar miniaturas de todas las cartas (para mostrarlas cuando se revelen)
        for ruta in self.cartas:
            try:
                img = Image.open(ruta)
                img = img.resize(TAM_CARTA)
                img_tk = ImageTk.PhotoImage(img)
            except Exception:
                # Si falla cargar una imagen, creamos una imagen de error visual
                err = Image.new("RGB", TAM_CARTA, color=(100, 100, 100))
                img_tk = ImageTk.PhotoImage(err)
            self.imagenes_tk.append(img_tk)

        # Crear los botones del tablero y colocarlos en grid
        idx = 0
        for r in range(rows):
            for c in range(cols):
                if idx >= total_cartas:
                    break
                # Cada botón muestra el reverso por defecto
                btn = tk.Button(self.frame,
                                image=self.reverso_tk,
                                command=lambda i=idx: self.voltear_carta(i),
                                width=TAM_CARTA[0],
                                height=TAM_CARTA[1],
                                relief="raised")
                btn.grid(row=r + 1, column=c, padx=6, pady=6)  # +1 porque la fila 0 contiene etiquetas
                self.botones.append(btn)
                idx += 1

        # Controles inferiores: Reiniciar y Salir
        self.btn_reiniciar = tk.Button(self.frame, text="🔁 Reiniciar", command=self.reiniciar, width=12)
        self.btn_reiniciar.grid(row=rows + 2, column=0, columnspan=2, pady=(10, 0))

        self.btn_salir = tk.Button(self.frame, text="⛔ Salir", command=self.root.quit, width=12)
        self.btn_salir.grid(row=rows + 2, column=2, columnspan=2, pady=(10, 0))

        # Empezar temporizador
        self.tiempo_inicio = time.time()
        self.actualizar_tiempo()

    # ------------------------------------------------------------
    # Lógica para voltear carta al pulsar
    # ------------------------------------------------------------
    def voltear_carta(self, indice):
        # Si la carta ya está emparejada o ya está mostrada en el turno actual, ignorar
        if self.reveladas[indice] or indice in self.mostradas_idx:
            return

        # Mostrar la imagen correspondiente en el botón
        self.botones[indice].config(image=self.imagenes_tk[indice], relief="sunken")

        # Añadir a las cartas mostradas en este turno
        self.mostradas_idx.append(indice)

        # Si ya hay dos cartas mostradas, comprobar si coinciden
        if len(self.mostradas_idx) == 2:
            # Incrementar intentos (porque se han intentado emparejar dos cartas)
            self.intentos += 1
            self.intentos_label.config(text=f"Intentos: {self.intentos}")

            i1, i2 = self.mostradas_idx

            # Si las rutas de imagen coinciden -> pareja encontrada
            if self.cartas[i1] == self.cartas[i2]:
                # Marcamos como reveladas (permanentes)
                self.reveladas[i1] = True
                self.reveladas[i2] = True

                # Opcional: desactivar botones para que no puedan ser pulsados
                self.botones[i1].config(state="disabled")
                self.botones[i2].config(state="disabled")

                # Limpiar lista de mostradas para el siguiente turno
                self.mostradas_idx = []

                # Incrementar contador de pares encontrados
                self.pares_encontrados += 1

                # Si todos los pares encontrados -> finalizar juego
                if self.pares_encontrados == (len(self.cartas) // 2):
                    self.juego_completo()
            else:
                # No coinciden: después de un retraso, volteamos de nuevo
                # Guardamos los índices locales para cerrarlos en el callback
                self.root.after(RETRASO_VOLTEO, lambda i1=i1, i2=i2: self.voltear_atras(i1, i2))

    # ------------------------------------------------------------
    # Voltear cartas hacia atrás (mostrar reverso)
    # ------------------------------------------------------------
    def voltear_atras(self, i1, i2):
        # Restaurar imagen de reverso y estilo "raised"
        if not self.reveladas[i1]:
            self.botones[i1].config(image=self.reverso_tk, relief="raised")
        if not self.reveladas[i2]:
            self.botones[i2].config(image=self.reverso_tk, relief="raised")
        # Limpiar la lista de mostradas para continuar el juego
        self.mostradas_idx = []

    # ------------------------------------------------------------
    # Actualizar temporizador (cada 1 segundo)
    # ------------------------------------------------------------
    def actualizar_tiempo(self):
        if self.tiempo_inicio is None:
            return
        transcurrido = int(time.time() - self.tiempo_inicio)
        minutos = transcurrido // 60
        segundos = transcurrido % 60
        self.tiempo_label.config(text=f"Tiempo: {minutos:02d}:{segundos:02d}")
        # Llamar de nuevo dentro de 1 segundo
        self.root.after(1000, self.actualizar_tiempo)

    # ------------------------------------------------------------
    # Finalizar el juego y mostrar resultados
    # ------------------------------------------------------------
    def juego_completo(self):
        # Parar temporizador poniendo tiempo_inicio a None momentáneamente
        tiempo_total = int(time.time() - self.tiempo_inicio) if self.tiempo_inicio else 0
        minutos = tiempo_total // 60
        segundos = tiempo_total % 60
        # Mostrar mensaje de felicitación con estadísticas
        messagebox.showinfo(
            "¡Completado! 🎉",
            f"Has completado el juego.\n\nIntentos: {self.intentos}\nTiempo: {minutos:02d}:{segundos:02d}"
        )
        # Opcional: desactivar todos los botones (ya están desactivados los emparejados)
        for i, btn in enumerate(self.botones):
            btn.config(state="disabled")

    # ------------------------------------------------------------
    # Reiniciar el juego (reorganizar cartas y resetear todo)
    # ------------------------------------------------------------
    def reiniciar(self):
        # Reiniciamos variables
        self.intentos = 0
        self.pares_encontrados = 0
        self.intentos_label.config(text="Intentos: 0")
        self.mostradas_idx = []
        self.reveladas = [False] * len(self.cartas)

        # Rebarajar cartas (manteniendo las mismas rutas, pero en otro orden)
        random.shuffle(self.cartas)

        # Recargar miniaturas en el nuevo orden (reconstruir imagenes_tk)
        self.imagenes_tk = []
        for ruta in self.cartas:
            try:
                img = Image.open(ruta)
                img = img.resize(TAM_CARTA)
                img_tk = ImageTk.PhotoImage(img)
            except Exception:
                err = Image.new("RGB", TAM_CARTA, color=(100, 100, 100))
                img_tk = ImageTk.PhotoImage(err)
            self.imagenes_tk.append(img_tk)

        # Restaurar todos los botones al estado reverso y habilitados
        for i, btn in enumerate(self.botones):
            btn.config(image=self.reverso_tk, state="normal", relief="raised")

        # Reiniciar temporizador
        self.tiempo_inicio = time.time()

# ------------------------------------------------------------
# FUNCION PRINCIPAL: crear ventana root y lanzar juego
# ------------------------------------------------------------
def main():
    root = tk.Tk()
    # Creamos la instancia del juego (esto inicializa la UI y el juego)
    game = MemoryGame(root)
    # Ejecutar la app
    root.mainloop()

# ------------------------------------------------------------
# Ejecutar si se lanza directamente este archivo
# ------------------------------------------------------------
if __name__ == "__main__":
    main()
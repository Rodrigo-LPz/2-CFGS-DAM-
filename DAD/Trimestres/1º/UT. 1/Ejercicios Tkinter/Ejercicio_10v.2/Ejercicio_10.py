import tkinter as tk                        # Importa la biblioteca Tkinter y la renombra como 'tk'.
from tkinter import messagebox              # Permite mostrar mensajes emergentes (como JOptionPane en Java).
from PIL import Image, ImageTk, ImageOps    # Importa Pillow para abrir, redimensionar y mostrar imágenes.
import os                                   # Biblioteca para manejar rutas de archivos de forma compatible con todos los sistemas operativos.
import random                               # Para mezclar aleatoriamente las cartas del juego.
import time                                 # Biblioteca para manejar temporizadores y retrasos.
import difflib                              # Biblioteca para comparar secuencias y encontrar similitudes.
import unicodedata                          # Biblioteca para normalizar cadenas de texto (quitar acentos, etc.).




# Crea la clase principal del programa.
class MathMemoryGame:
    # Constructor de la clase. Inicializa la ventana principal y las variables del juego.
    def __init__(self, root_windows):
        self.root_windows = root_windows                                # Crea la ventana principal (el contenedor de toda la app).
        self.root_windows.title("🧮 Juego de Memoria Matemática")       # Título de la ventana.
        self.root_windows.geometry("1600x1250")                         # Define el tamaño de la ventana (ancho x alto).
        self.root_windows.configure(bg = "#1a1a2e")                   # Define el color de fondo de la ventana.
        self.root_windows.resizable(True, True)                         # Permite que el usuario cambie el tamaño de la ventana (ancho, alto).
        
        # Obtener la ruta absoluta del directorio donde está el script.
        self.script_dir = os.path.dirname(os.path.abspath(__file__))    # Esta línea obtiene el directorio del script actual.
        self.images_dir = os.path.join(self.script_dir, "Imagenes")     # Esta línea construye la ruta al subdirectorio "Imagenes".

        # Configuración de tamaño para las cartas (con proporción 3:4). Reduciendo/Ajustando para evitar pixelado.
        self.card_width = 130   # Ancho de la carta (unidad en píxeles).
        self.card_height = 170  # Alto de la carta (unidad en píxeles).

        # Configuración de la cuadrícula: 10 columnas x 2 filas (10x2). 20 cartas en total, 10 parejas.
        self.columns = 10   # Número de columnas en el tablero.
        self.rows = 2       # Número de filas en el tablero.
        
        # Define las 10 parejas con sus imágenes y explicaciones respectivas.
        self.pairs = [
            {"id": 1,   "card1": "Logaritmo básico (1).png",                        "card2": "Potencia básica (1).png",                        "explanation": "Explicación (1).png"},
            {"id": 2,   "card1": "Potencia negativa básica (2).png",                "card2": "Fracción básica (2).png",                        "explanation": "Explicación (2).png"},
            {"id": 3,   "card1": "Límite a infinito básico (3).png",                "card2": "Resultado del límite (3).png",                   "explanation": "Explicación (3).png"},
            {"id": 4,   "card1": "Función con derivación de X básica (4).png",      "card2": "Resultado de la función derivada (4).png",       "explanation": "Explicación (4).png"},
            {"id": 5,   "card1": "Teorema de Pitágoras_ Representación (5).png",    "card2": "Teorema de Pitágoras_ Fórmula (5).png",          "explanation": None},
            {"id": 6,   "card1": "Multiplicación básica (6).png",                   "card2": "Resultado de la multiplicación (6).png",         "explanation": "Explicación (6).png"},
            {"id": 7,   "card1": "División (7).png",                                "card2": "División invertida (7).png",                     "explanation": None},
            {"id": 8,   "card1": "Operación de signos (8).png",                     "card2": "Resultado de la operación de signos (8).png",    "explanation": None},
            {"id": 9,   "card1": "Indeterminación (9).png",                         "card2": "Indeterminación_Expresión (9).png",              "explanation": None},
            {"id": 10,  "card1": "Despeje de X (10).png",                           "card2": "Resultado del despeje de X (10).png",            "explanation": "Explicación (10).png"}
        ]
        
        # Variables del juego (como atributos de clase en Java).
        self.cards = []             # Lista de todas las cartas en el tablero.
        self.card_buttons = []      # Lista de botones (widgets) de las cartas.
        self.flipped_cards = []     # Lista de cartas actualmente volteadas.
        self.matched_pairs = []     # Lista de IDs de parejas ya encontradas.
        self.attempts = 0           # Contador de intentos.
        self.timer_seconds = 0      # Temporizador (en segundos).
        self.timer_running = False  # Estado del temporizador. Se inicializa en "False" para evitar que comience automáticamente.
        self.game_state = "initial" # Estados del juego/partida: initial (inicio), preview (previsualización), shuffling (barajeando/mezclando), playing (jugando), finished (terminado).
        self.is_checking = False    # Bandera para evitar clicks durante verificación. De esta forma no se pueden voltear más cartas mientras se verifica una pareja.
        
        # Cargar imagen del reverso de las cartas.
        self.back_image = None  # Variable/Atributo para almacenar la imagen del reverso de las cartas.
        self.load_back_image()  # Método para cargar la imagen del reverso de las cartas.
        
        # Crear interfaz gráfica.
        self.create_widgets()   # Método para crear los widgets de la interfaz gráfica.

    # Método para cargar la imagen del reverso de las cartas.
    def load_back_image(self):
        # Bloque "try-except", su función es intentar cargar la imagen y capturar y manejar los errores/excepciones en caso de no encontrarse (la imagen).
        try:
            image_path = os.path.join(self.images_dir, "Reverso-Dorso cartas.png")  # Construye la ruta absoluta completa a la imagen, partiendo del directorio "Imagenes" 
            print(f"Intentando cargar: {image_path}")  # Debug.                     # Imprime por consola la ruta que intenta cargar.
            
            img = Image.open(image_path)                                                            # Abre la imagen usando Pillow.
            img = ImageOps.fit(img, (self.card_width, self.card_height), Image.Resampling.LANCZOS)  # Usa "ImageOps.fit" para un redimensionado de las cartas de alta calidad (manteniendo proporción y calidad).
            self.back_image = ImageTk.PhotoImage(img)                                               # Convierte la imagen a un formato que Tkinter puede usar (formato compatible con Tkinter).

        # Captura cualquier excepción/error que ocurra durante la carga de la imagen.
        except Exception as ex:
            messagebox.showerror("Error", f"Error al cargar imagen de reverso: {ex}")   # Muestra un mensaje emergente de error.
            print(f"\n\nError al cargar imagen de reverso: {ex}")                       # Imprime el mismo mensaje de error pero por consola para depuración.
            print(f"\n\nDirectorio de imágenes: {self.images_dir}")                     # Imprime por consola la ruta del directorio de imágenes para depuración.
            print(f"\n¿Existe el directorio? {os.path.exists(self.images_dir)}")        # Imprime por consola si el directorio existe o no.
            
            if os.path.exists(self.images_dir): # Si el directorio existe, imprime la lista de archivos que contiene.
                print(f"\nArchivos en el directorio: {os.listdir(self.images_dir)}")

    # Método para resolver la ruta de una imagen, tolerando errores menores en el nombre (acentos, typos, etc.).
        # Su funcionamiento será el siguiente:
            # 1. Intenta construir la ruta de la imagen directamente.
            # 2. Si no se encuentra, busca un nombre similar en el directorio de imágenes. Utilizando difflib para encontrar el mejor match.
            # 3. Si no hay matches cercanos, intenta una búsqueda parcial (subcadena).
            # 4. Si aún no se encuentra, retorna None.
    def resolve_image_path(self, filename):
        if not filename:    # Si no se proporciona un nombre de archivo, retorna None.
            return None     # No hay imagen que resolver.
        
        candidate = os.path.join(self.images_dir, filename) # Construye la ruta absoluta al archivo.
        
        if os.path.exists(candidate):   # Si dada la ruta el archivo existe, retorna esa ruta.
            return candidate

        # Función interna para normalizar cadenas (quitar acentos y pasar a minúsculas) para comparar.
        def norm(s):
            s = s.lower()                                                   # Convierte a minúsculas.
            s = unicodedata.normalize('NFKD', s)                            # Normaliza la cadena (descompone caracteres acentuados). Es decir, ejemplo: "á" -> "a" + "´". 
            s = ''.join(ch for ch in s if not unicodedata.combining(ch))    # Quita los caracteres de acento/combinación. Elimina los acentos que antes habíamos separado.
            return s                                                        # Retorna la cadena normalizada.
        
        # Bloque "try-except", su función es intentar buscar el archivo dentro del directorio de imágenes.
        try:
            files = os.listdir(self.images_dir) # Lista todos los archivos en el directorio.
        
        # Captura cualquier excepción/error que ocurra durante la lectura del directorio.
        except Exception:
            return None # Error al listar el directorio, retorna None.

        # Crear un mapa de nombres normalizados a nombres originales.
        normalized_map = {f: norm(f) for f in files}    # Crea un diccionario con los nombres originales y sus versiones normalizadas.
        target_norm = norm(filename)                    # Normaliza el nombre del archivo objetivo. Es decir, normaliza el nombre del archivo que queremos encontrar.

        # Usar difflib para obtener el mejor match. "difflib" es una biblioteca que permite comparar secuencias y encontrar similitudes entre ellas.
        matches = difflib.get_close_matches(target_norm, normalized_map.values(), n = 1, cutoff = 0.6)  # Busca el mejor match cercano con un umbral de similitud del 60%.
        
        if matches: # Si hay matches cercanos, retorna la ruta del archivo correspondiente.
            for orig, nval in normalized_map.items(): # Itera sobre el mapa de nombres normalizados hasta encontrar la clave original que tiene este valor normalizado.
                if nval == matches[0]:  # Si el valor normalizado coincide con el mejor match encontrado.
                    resolved = os.path.join(self.images_dir, orig)  # Construye la ruta completa al archivo original.
                    print(f"Resolviendo '{filename}' -> '{orig}'")  # Imprime la resolución encontrada.
                    return resolved                                 # Retorna la ruta resuelta.

        for orig, nval in normalized_map.items():   # Si no hay match cercano, intentar una búsqueda parcial (subcadena)
            if target_norm in nval or nval in target_norm:  # Si la cadena normalizada del objetivo es subcadena del nombre normalizado o viceversa.
                resolved = os.path.join(self.images_dir, orig)                  # Construye la ruta completa al archivo original.
                print(f"Resolviendo por subcadena '{filename}' -> '{orig}'")    # Imprime la resolución encontrada.
                return resolved                                                 # Retorna la ruta resuelta.

        print(f"No se encontró imagen para: {filename}")    # Imprime un mensaje argumentando que no se encontró la imagen.
        return None
    
    # Método para crear la imagen de una carta a partir de la ruta de la imagen.
    def create_card_photo(self, image_path):
        # Bloque "try-except", su función es intentar cargar y preparar la imagen de la carta.
        try:
            if not image_path or not os.path.exists(image_path):                            # Verificar que la ruta de la imagen es válida.
                messagebox.showerror("Error", f"Ruta de imagen no válida: {image_path}")    # Muestra un mensaje emergente de error.
                raise FileNotFoundError(image_path)                                         # Lanzar excepción si la ruta no es válida.
            
            img = Image.open(image_path).convert("RGBA")    # Abre la imagen y la convierte a formato RGBA (con canal alfa para transparencia).

            # Crea una copia en miniatura de la imagen, una copia que quepa dentro de la carta dejando 7px de padding.
            max_w = max(10, self.card_width - 14)                   # Calcula el ancho máximo permitido para la miniatura. Ancho de la carta - 14 píxeles (7px de padding a cada lado).
            max_h = max(10, self.card_height - 14)                  # Calcula el alto máximo permitido para la miniatura. Alto de la carta - 14 píxeles (7px de padding a cada lado).
            img.thumbnail((max_w, max_h), Image.Resampling.LANCZOS) # Redimensiona la imagen manteniendo proporción, usando un filtro de alta calidad.

            bg = Image.new("RGBA", (self.card_width, self.card_height), (58, 58, 94, 255)) # Crea una imagen de fondo del tamaño de la carta con un color específico (RGBA).

            # Pegar la miniatura centrada
            x = (self.card_width - img.width) // 2      # Calcula la posición X para centrar la imagen.
            y = (self.card_height - img.height) // 2    # Calcula la posición Y para centrar la imagen.
            bg.paste(img, (x, y), img)                  # Pega la miniatura sobre el fondo, usando la miniatura como máscara para preservar transparencia. Es decir, solo pega las partes no transparentes de la miniatura.

            return ImageTk.PhotoImage(bg)   # Convierte la imagen final a un formato que Tkinter puede usar y retornar(formato compatible con Tkinter).
        
        # Captura cualquier excepción/error que ocurra durante la carga y preparación de la imagen.
        except Exception as e:
            messagebox.showerror("Error", f"Error al cargar la imagen de carta: {e}")           # Muestra un mensaje emergente de error.
            print(f"Warning: no se pudo preparar imagen de carta '{image_path}': {e}")          # Imprime el mismo mensaje de error pero por consola para depuración.
            bg = Image.new("RGBA", (self.card_width, self.card_height), (100, 100, 120, 255))   # Crea una imagen de fondo gris como placeholder. "placeholder" es un término usado en programación para referirse a un valor temporal o de reserva.
            return ImageTk.PhotoImage(bg)                                                       # Convierte la imagen de fondo a un formato que Tkinter puede usar y retornar (formato compatible con Tkinter).
    
    # Método para crear los widgets de la interfaz gráfica.
    def create_widgets(self):
        top_frame = tk.Frame(self.root_windows, bg = "#1a1a2e") # Crea un frame (contenedor) para la parte superior de la ventana.
        top_frame.pack(pady = 20)                                 # Empaqueta el frame con un padding vertical de 20 píxeles.
        
        title_label = tk.Label(top_frame, text = "🧮 MEMORIA MATEMÁTICA", font = ("Arial", 26, "bold"), bg = "#1a1a2e", fg = "white")   # Etiqueta para el título del juego.
        title_label.pack(pady = 5)                                                                                                         # Empaqueta la etiqueta con un padding vertical de 5 píxeles.
        
        stats_frame = tk.Frame(top_frame, bg = "#1a1a2e") # Crea un frame (contenedor) para las estadísticas del juego.
        stats_frame.pack(pady = 5)                          # Empaqueta el frame con un padding vertical de 5 píxeles.
        
        self.time_label = tk.Label(stats_frame, text = "⏱️ Tiempo: 00:00", font = ("Arial", 14, "bold"), bg = "#2a2a4e", fg = "white", padx = 20, pady = 8, relief = tk.RAISED)  # Crea la etiqueta para mostrar el tiempo transcurrido.
        self.time_label.grid(row = 0, column = 0, padx = 10)                                                                                                                        # Coloca la etiqueta en la cuadrícula del frame de estadísticas.
        
        self.attempts_label = tk.Label(stats_frame, text = "🎯 Intentos: 0", font = ("Arial", 14, "bold"), bg = "#2a2a4e", fg = "white", padx = 20, pady = 8, relief = tk.RAISED)   # Crea la etiqueta para mostrar los intentos realizados.
        self.attempts_label.grid(row = 0, column = 1, padx = 10)                                                                                                                       # Coloca la etiqueta en la cuadrícula del frame de estadísticas.
        
        self.pairs_label = tk.Label(stats_frame, text = "✅ Parejas: 0/10", font = ("Arial", 14, "bold"), bg = "#2a2a4e", fg = "white", padx = 20, pady = 8, relief = tk.RAISED)    # Crea la etiqueta para mostrar las parejas encontradas.
        self.pairs_label.grid(row = 0, column = 2, padx = 10)                                                                                                                          # Coloca la etiqueta en la cuadrícula del frame de estadísticas.
        
        self.start_button = tk.Button(top_frame, text = "🎮 INICIAR JUEGO", font = ("Arial", 16, "bold"), bg = "#4CAF50", fg = "white", padx = 300, pady = 12, command = self.start_game, cursor = "hand2") # Crea el botón para iniciar el juego.
        self.start_button.pack(pady = 20)                                                                                                                                                                      # Empaqueta el botón con un padding vertical de 20 píxeles.
        
        self.board_frame = tk.Frame(self.root_windows, bg = "#1a1a2e")  # Crea un frame (contenedor) para el tablero de juego.
        self.board_frame.pack(pady = 20)                                  # Empaqueta el frame con un padding vertical de 20 píxeles.
        
        self.initialize_board() # Llama al método para inicializar/crear el tablero de juego (inicialmente vacío).

    # Método para inicializar/crear el tablero de juego.
    def initialize_board(self):
        for widget in self.board_frame.winfo_children():    # Limpia el frame del tablero eliminando cualquier widget existente. Limpiar el tablero anterior si existe.
            widget.destroy()                                # Destruye cada widget hijo del frame del tablero.

        self.cards = [] # Crear lista de cartas. Reinicia la lista de cartas.


        for pair in self.pairs: # Itera sobre cada pareja definida.
                                    # 1. Añade la primera carta de la pareja (construye la ruta absoluta). Resuelve las rutas tolerando pequeños errores en nombres (acentos/typos)
            resolved1 = self.resolve_image_path(pair['card1'])  # Intenta resolver la ruta de la primera carta.
            
            if not resolved1:   # Si no se encuentra, usa la ruta directa.
                resolved1 = os.path.join(self.images_dir, pair['card1'])    # Construye la ruta al archivo de la primera carta.
            self.cards.append({                                             # Añadie primera carta de la pareja.
                "pair_id": pair["id"],
                "image_path": resolved1,
                "explanation": pair["explanation"],
                "button": None,
                "photo": None
            })

                                    # 2. Añade la segunda carta de la pareja (construye la ruta absoluta).
            resolved2 = self.resolve_image_path(pair['card2'])

            if not resolved2:   # Si no se encuentra, usa la ruta directa.
                resolved2 = os.path.join(self.images_dir, pair['card2'])    # Construye la ruta al archivo de la segunda carta.
            self.cards.append({                                             # Añadie segunda carta de la pareja.
                "pair_id": pair["id"],
                "image_path": resolved2,
                "explanation": pair["explanation"],
                "button": None,
                "photo": None
            })
        
        for i, card in enumerate(self.cards):   # Itera sobre cada carta creada. Creando los botones para cada carta en el tablero, en el en grid (formato 10x2).
            row = i // self.columns             # Calcula la fila en la que se ubicará la carta.
            col = i % self.columns              # Calcula la columna en la que se ubicará la carta.
            
            btn = tk.Button(self.board_frame, image = self.back_image, width = self.card_width, height=self.card_height, bg = "#3a3a5e", relief = tk.RAISED, borderwidth = 3, cursor = "hand2", command = lambda c = card: self.card_clicked_by_ref(c)) # Crea el botón con la imagen del reverso. Ajusta el tamaño del botón al tamaño de la carta. Añade margen y estilo visual. Y Pone el cursor de mano al pasar sobre el botón.
            btn.grid(row = row, column = col, padx = 7, pady = 7)                                                                                                                                                                                         # Coloca el botón en la cuadrícula del frame del tablero con un padding de 7 píxeles.
            
            card["button"] = btn    # Guarda la referencia del botón en el diccionario de la carta.

        # Reiniciar/Reseta las variables del juego, a fin de comenzar una nueva partida, limpiando el estado anterior si así lo desea el usuario, el jugador.
        self.flipped_cards = []
        self.matched_pairs = []
        self.attempts = 0
        self.timer_seconds = 0
        self.timer_running = False
        self.game_state = "initial"
        self.is_checking = False
        
        self.update_stats() # Actualiza las etiquetas de estadísticas (tiempo, intentos, parejas).
        
    # Método para iniciar el juego.
    def start_game(self):
        if self.game_state == "finished":   # Valida estado actual del juego. Si el juego ya terminó, reiniciar.
            self.initialize_board()         # Reinicia el tablero para una nueva partida.
            return                          # Sale del método para evitar continuar con el inicio del juego.
        
        self.game_state = "preview"                                              # Cambia el estado del juego a "previsualización".
        self.start_button.config(state = tk.DISABLED, text = "👀 Memoriza...")  # Desactiva el botón de inicio y cambia el texto.
        
        self.show_all_cards()   # Muestra todas las cartas (volteadas, de cara) durante 10 segundos.
        
        # Después de 10 segundos, barajar y comenzar
        self.root_windows.after(10000, self.shuffle_and_start)  # Usa "after" para esperar 10 segundos. Después de esperar esos 10 segundos llama al método "shuffle_and_start" para barajar y comenzar la partida.

    # Método para mostrar todas las cartas (volteadas, de cara).    
    def show_all_cards(self):
        for card in self.cards: # Itera sobre cada carta. Volteando todas las cartas para mostrarlas.
            # Bloque "try-except", su función es intentar voltear cada carta mostrando su imagen. 
            try:
                photo = self.create_card_photo(card["image_path"])  # Crea la imagen de la carta.
                card["photo"] = photo                               # Guarda la referencia de la foto en el diccionario de la carta.
                card["button"].config(image = photo)                # Actualiza el botón para mostrar la imagen de la carta.
                card["button"].image = photo                        # Mantiene la referencia de la imagen en el botón para evitar que Python la elimine por recolección de basura.
            
            # Captura cualquier excepción/error que ocurra durante la carga de la imagen.
            except Exception as e:
                messagebox.showerror("Error", f"Error al cargar la imagen de carta: {e}")   # Muestra un mensaje emergente de error.
                print(f"Error al cargar imagen: {e}")                                       # Imprime el mismo mensaje de error pero por consola para depuración.
                
    # Método para barajar las cartas y comenzar la partida.
    def shuffle_and_start(self):
        self.game_state = "shuffling"                        # Cambia el estado del juego a "barajeando".
        self.start_button.config(text = "🔀 Barajando...")  # Cambia el texto del botón de inicio a "Barajando...".
        
        for card in self.cards: # Itera sobre cada carta. Volteando todas las cartas para mostrar el reverso (Efecto de barajado).
            card["button"].config(image = self.back_image)  # Actualiza el botón para mostrar la imagen del reverso de la carta.
        
        self.animate_shuffle(0, 15)  # Animación de barajado (intercambiar posiciones visualmente). 15 intercambios animados. 
        
    # Método para animar el barajado de las cartas.
    def animate_shuffle(self, iteration, max_iterations):
        if iteration < max_iterations:  # Si no se ha alcanzado el número máximo de iteraciones. Selecciona dos cartas aleatorias para intercambiar.
            idx1, idx2 = random.sample(range(20), 2)    # Selecciona dos índices aleatorios distintos entre 0 y 19 (20 cartas en total).
            
            row1, col1 = idx1 // self.columns, idx1 % self.columns  # Intercambia visualmente (solo los botones en el grid). Calcula la fila y la olumna del primer botón.
            row2, col2 = idx2 // self.columns, idx2 % self.columns  # Intercambia visualmente (solo los botones en el grid). Calcula la fila y la columna del segundo botón.
            
            btn1 = self.cards[idx1]["button"]   # Obtiene la referencia del primer botón.
            btn2 = self.cards[idx2]["button"]   # Obtiene la referencia del segundo botón.
            
            btn1.grid(row = row2, column = col2, padx = 7, pady = 7) # Intercambia en el grid con nuevo espaciado. Coloca el primer botón en la posición del segundo.
            btn2.grid(row = row1, column = col1, padx = 7, pady = 7) # Intercambia en el grid con nuevo espaciado. Coloca el segundo botón en la posición del primero.
            
            self.cards[idx1], self.cards[idx2] = self.cards[idx2], self.cards[idx1] # Intercambia las cartas en la lista interna para mantener el estado correcto.
            
            self.root_windows.after(100, lambda: self.animate_shuffle(iteration + 1, max_iterations))   # Llama a sí mismo después de 100 ms para la siguiente iteración.
            
        else:   # Si no se ha alcanzado el número máximo de iteraciones, finalizar el barajado.
            random.shuffle(self.cards)  # Baraja la lista de cartas internamente.
            self.redraw_board()         # Redibuja el tablero para reflejar el nuevo orden de las cartas.
            
            # Comenzar a jugar
            self.game_state = "playing"                          # Cambia el estado del juego a "jugando".
            self.timer_running = True                            # Inicia el temporizador.
            self.start_button.config(text = "🎮 Jugando...")     # Cambia el texto del botón de inicio a "Jugando...".
            self.update_timer()                                  # Inicia la actualización del temporizador.
    
    # Método para redibujar el tablero después de barajar.
    def redraw_board(self):
        for i, card in enumerate(self.cards):  # Itera sobre cada carta. Reubicando los botones de las cartas según el nuevo orden, después de barajar.
            row = i // self.columns                                             # Calcula la fila en la que se ubicará la carta.
            col = i % self.columns                                              # Calcula la columna en la que se ubicará la carta.
            card["button"].grid(row = row, column = col, padx = 7, pady = 7)    # Coloca el botón en la cuadrícula del frame del tablero con un padding de 7 píxeles.
    
    # Método para manejar el clic en una carta.
    def card_clicked(self, card_index):
        # Verificaciones/Validaciones antes de voltear la carta.
        if self.game_state != "playing":                            # Solo permitir voltear cartas si el juego está en estado "jugando".
            return                                                  # Ignorar clics si no se está jugando.
        if self.is_checking:                                        # Evitar voltear cartas mientras se está verificando una pareja.
            return                                                  # Ignorar clics durante la verificación.
        if card_index in self.flipped_cards:                        # La carta ya está volteada.
            return                                                  # Ignorar clics si la carta ya está volteada.
        if self.cards[card_index]["pair_id"] in self.matched_pairs: # La carta ya fue emparejada.
            return                                                  # Ignorar clics si la carta ya fue emparejada.
        if len(self.flipped_cards) >= 2:                            # Ya hay 2 cartas volteadas.
            return                                                  # Ignorar clics si ya hay 2 cartas volteadas.

        card = self.cards[card_index]   # Voltea/Obtiene la carta correspondiente al índice clickeado (mostrar imagen).

        # Bloque "try-except", su función es intentar voltear la carta mostrando su imagen.
        try:
            photo = self.create_card_photo(card["image_path"])  # Crea la imagen de la carta.
            card["photo"] = photo                               # Guarda la referencia de la foto en el diccionario de la carta.
            card["button"].config(image = photo)                # Actualiza el botón para mostrar la imagen de la carta.
            card["button"].image = photo                        # Mantiene la referencia de la imagen en el botón para evitar que Python la elimine por recolección de basura.
        
        # Captura cualquier excepción/error que ocurra durante la carga de la imagen.
        except Exception as e:
            messagebox.showerror("Error", f"Error al cargar la imagen de carta: {e}")   # Muestra un mensaje emergente de error.
            print(f"Error: {e}")                                                        # Imprime el mismo mensaje de error pero por consola para depuración.
        
        self.flipped_cards.append(card_index)   # Añade el índice de la carta volteada a la lista de cartas volteadas.
        
        if len(self.flipped_cards) == 2:    # Si ya hay 2 cartas volteadas, verifica si son pareja.
            self.attempts += 1                              # Incrementa el contador de intentos.
            self.update_stats()                             # Incrementa el contador de intentos y actualiza las estadísticas.
            self.is_checking = True                         # Establece la bandera para evitar más clics durante la verificación.
            self.root_windows.after(1000, self.check_match) # Espera 1 segundo antes de verificar la pareja.

    # Método para manejar el clic en una carta usando la referencia de la carta.
    def card_clicked_by_ref(self, card):
        # Bloque "try-except", su función es intentar encontrar/buscar el índice de la carta en la lista de cartas.
        try:
            idx = self.cards.index(card)    # Busca el índice de la carta en la lista de cartas.

        # Captura cualquier excepción/error que ocurra durante la búsqueda del índice.
        except ValueError:
            messagebox.showerror("Error", "Error inesperado.")  # Muestra un mensaje emergente de error.
            return                                              # Si la carta no se encuentra (no debería ocurrir), simplemente retorna sin hacer nada.
        
        return self.card_clicked(idx)   # Llama al método original pasando el índice encontrado.
    
    # Método para verificar si las dos cartas volteadas son pareja.
    def check_match(self):
        idx1, idx2 = self.flipped_cards[0], self.flipped_cards[1]   # Obtiene los índices de las dos cartas volteadas.
        card1 = self.cards[idx1]                                    # Obtiene el índice correspondiente a/de la primera carta.
        card2 = self.cards[idx2]                                    # Obtiene el índice correspondiente a/de la segunda carta.

        if card1["pair_id"] == card2["pair_id"]:    # Si las dos cartas son pareja.
            self.matched_pairs.append(card1["pair_id"]) # Añade el ID de la pareja a la lista de parejas encontradas.
            self.update_stats()                         # Actualiza las estadísticas (parejas encontradas).
            
            if card1["explanation"]:    # Si hay una explicación asociada a la pareja.
                self.show_explanation(card1["explanation"]) # Muestra la explicación en una ventana auxiliar.
            else:   # La pareja no tiene explicación. Por lo que simplemente se limpia.
                self.flipped_cards = []     # Limpia la lista de cartas volteadas.
                self.is_checking = False    # Limpia la bandera de verificación.
            
            if len(self.matched_pairs) == 10:   # Verifica si el usuario ha ganado. Si se ha/n encontrado todas las parejas.
                self.game_finished()            # Llama al método para finalizar el juego.
        else:   # Si no son pareja.
            self.root_windows.after(500, self.flip_back_cards)  # Espera 0.5 segundos antes de voltear las cartas de nuevo.
    
    # Método para voltear de nuevo las cartas que no son pareja.
    def flip_back_cards(self):
        for idx in self.flipped_cards:  # Itera sobre los índices de las cartas volteadas. Volteando de nuevo las cartas que no son pareja.
            self.cards[idx]["button"].config(image = self.back_image)   # Actualiza el botón para mostrar la imagen del reverso de la carta.
        
        self.flipped_cards = []     # Limpia la lista de cartas volteadas.
        self.is_checking = False    # Limpia la bandera de verificación.
    
    # Método para mostrar la explicación de una pareja en una ventana auxiliar.
    def show_explanation(self, explanation_file):
        self.timer_running = False  # Pausa el temporizador mientras se muestra la explicación.
        
        explanation_window = tk.Toplevel(self.root_windows) # Crea una nueva ventana secundaria, una ventana auxiliar, para la ecplicación.
        explanation_window.title("💡 ¿Por qué?")            # Título de la ventana.
        explanation_window.configure(bg = "white")          # Fondo blanco.
        explanation_window.transient(self.root_windows)     # Posicionamiento (siempre) encima de la ventana principal.
        explanation_window.grab_set()                       # Modal (bloquea la ventana principal).
        explanation_window.resizable(False, False)          # No redimensionable.
        
        title = tk.Label(explanation_window, text = "💡 ¿Por qué?", font = ("Arial", 24, "bold"), bg = "white", fg = "#6B46C1") # Crear el título de la ventana de explicación.
        title.pack(pady = 10)                                                                                                      # Empaqueta el título con un padding vertical de 10 píxeles.

        # Bloque "try-except", su función es intentar cargar y mostrar la imagen de explicación (y ajustar tamaño de la ventana al contenido).
        try:
            explanation_path = self.resolve_image_path(explanation_file) or os.path.join(self.images_dir, explanation_file) # Construye la ruta absoluta a la imagen de explicación.
            img = Image.open(explanation_path).convert("RGBA")                                                              # Abre la imagen y la convierte a formato RGBA (con canal alfa para transparencia).

            screen_w = self.root_windows.winfo_screenwidth()    # Obtiene el ancho de la pantalla.
            screen_h = self.root_windows.winfo_screenheight()   # Obtiene el alto de la pantalla.
            max_w = min(900, screen_w - 200)                    # Calcula el ancho máximo permitido para la imagen (900px o pantalla - 200px).
            max_h = min(700, screen_h - 200)                    # Calcula el alto máximo permitido para la imagen (700px o pantalla - 200px).

            img.thumbnail((max_w, max_h), Image.Resampling.LANCZOS) # Crear una copia a tamaño razonable, manteniendo proporción. Redimensiona la imagen manteniendo proporción, usando un filtro de alta calidad.
            photo = ImageTk.PhotoImage(img)                         # Convierte la imagen a un formato que Tkinter puede usar (formato compatible con Tkinter).

            img_label = tk.Label(explanation_window, image = photo, bg = "white")   # Crea una etiqueta para mostrar la imagen de explicación.
            img_label.image = photo                                                 # Mantiene la referencia de la imagen en el botón para evitar que Python la elimine por recolección de basura.
            img_label.pack(pady = 8, padx = 8)                                      # Empaqueta la etiqueta con un padding de 8 píxeles.

            win_w = img.width + 40      # Ancho de la ventana = ancho de la imagen + padding
            win_h = img.height + 140    # Alto de la ventana = alto de la imagen + espacio para título y botones

            win_w = min(win_w, screen_w - 80)   # Ajusta el ancho de la ventana si excede el ancho de la pantalla.
            win_h = min(win_h, screen_h - 80)   # Ajusta el alto de la ventana si excede el alto de la pantalla.

            explanation_window.geometry(f"{win_w}x{win_h}") # Establece el tamaño de la ventana.
        
        # Captura cualquier excepción/error que ocurra durante la carga de la imagen.
        except Exception as e:
            error_label = tk.Label(explanation_window, text = f"Error al cargar explicación: {e}", font = ("Arial", 12), bg = "white", fg = "red")  # Crea una etiqueta para mostrar el mensaje de error.
            error_label.pack(pady = 20, padx = 20)                                                                                                  # Empaqueta la etiqueta con un padding de 20 píxeles.
            explanation_window.geometry("480x220")                                                                                                  # Establece un tamaño fijo para la ventana de error.

        close_btn = tk.Button(explanation_window, text = "CERRAR", font = ("Arial", 16, "bold"), bg = "#E91E63", fg = "white", padx = 50, pady = 15, cursor = "hand2", command = lambda: self.close_explanation(explanation_window))    # Crea el botón para cerrar la ventana de explicación.
        close_btn.pack(pady = 12)                                                                                                                                                                                                          # Empaqueta el botón con un padding vertical de 12 píxeles.
    
    # Método para cerrar la ventana de explicación.
    def close_explanation(self, window):
        window.destroy()            # Cierra la ventana de explicación.
        self.flipped_cards = []     # Limpia la lista de cartas volteadas.
        self.is_checking = False    # Limpia la bandera de verificación.
        
        if len(self.matched_pairs) < 10:    # Si el juego no ha terminado, reanuda el temporizador.
            self.timer_running = True   # Reanuda el temporizador.
        else:   # Si el juego ha terminado, llama al método "game_finished()".
            self.game_finished() # Llama al método para finalizar el juego.
    
    # Método para finalizar el juego.
    def game_finished(self):
        self.game_state = "finished"                                             # Cambia el estado del juego a "terminado".
        self.timer_running = False                                               # Detiene el temporizador para que deje de contabilizar tiempo.
        self.start_button.config(state = tk.NORMAL, text = "🔄 JUGAR DE NUEVO") # Reactiva y renombra el botón para permitir iniciar una nueva partida.
        
        minutes = self.timer_seconds // 60          # Calcula los minutos transcurridos a partir de los segundos totales.
        seconds = self.timer_seconds % 60           # Calcula los segundos restantes (parte modular).
        time_str = f"{minutes:02d}:{seconds:02d}"   # Formatea el tiempo como "MM:SS" para mostrarlo al usuario.
        
        messagebox.showinfo("🎉 ¡FELICITACIONES! 🎉", f"¡Has completado el juego!\n\n" f"⏱️ Tiempo: {time_str}\n" f"🎯 Intentos: {self.attempts}\n\n" f"¡Excelente trabajo! 🌟") # Muestra un cuadro de diálogo resumen con tiempo e intentos.
    
    # Método para actualizar el temporizador cada segundo mientras esté activo.
    def update_timer(self):
        if self.timer_running:  # Solo ejecutar la actualización si el temporizador está en marcha.
            self.timer_seconds += 1                                                   # Incrementa en 1 el contador de segundos.
            minutes = self.timer_seconds // 60                                        # Recalcula los minutos transcurridos.
            seconds = self.timer_seconds % 60                                         # Recalcula los segundos restantes.
            self.time_label.config(text = f"⏱️ Tiempo: {minutes:02d}:{seconds:02d}")    # Actualiza la etiqueta del tiempo en la interfaz.
            self.root_windows.after(1000, self.update_timer)                          # Programa la próxima llamada a este método dentro de 1 segundo.
    
    # Método para actualizar las etiquetas de estadísticas (intentos y parejas).
    def update_stats(self):
        self.attempts_label.config(text = f"🎯 Intentos: {self.attempts}")          # Actualiza la etiqueta que muestra los intentos realizados.
        self.pairs_label.config(text = f"✅ Parejas: {len(self.matched_pairs)}/10") # Actualiza la etiqueta que muestra las parejas encontradas sobre el total.


# Punto de entrada del programa (similar al main en Java)
if __name__ == "__main__":              # Si el archivo se ejecuta como script principal, iniciar la aplicación.
    root_windows = tk.Tk()              # Crea la ventana principal de la aplicación Tkinter.
    game = MathMemoryGame(root_windows) # Instancia la clase del juego pasando la ventana principal.
    root_windows.mainloop()             # Inicia el bucle principal de eventos de Tkinter (mantiene la app ejecutándose).
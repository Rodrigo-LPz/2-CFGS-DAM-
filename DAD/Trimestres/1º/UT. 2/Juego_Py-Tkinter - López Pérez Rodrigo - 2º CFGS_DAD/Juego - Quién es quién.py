import tkinter as tk            # Importa de la biblioteca/librería el paquete "Tkinter" (biblioteca gráfica).
from tkinter import simpledialog, messagebox
from PIL import Image, ImageTk  # Biblioteca para manejar imágenes (para que Tkinter pueda trabajar con imágenes).
import random                   # Importa de la biblioteca/librería el paquete "random" (para selección aleatoria).
import os                       # Importa de la biblioteca/librería el paquete "os" (para manejar rutas de archivos).


# ======================================== RUTAS DE IMÁGENES ========================================
    # Aquí se definen las rutas de las imágenes de los personajes.
ruta_base = os.path.dirname(os.path.abspath(__file__))          # Obtiene la ruta base del archivo actual.


# ======================================== PERSONAJES ========================================
    # Lista para guardar/almacenar los personajes.
personajes = [
    {
        "nombre": "Mariano Rajoy",
        "genero": "Hombre",
        "nacionalidad": "Española",
        "pelo": "Castaño oscuro",
        "ojos": "Marrones",
        "gafas": True,
        "sombrero": False,
        "barba": True,
        "bigote": False,
        "imagen": "Rajoy.png"
    },
    {
        "nombre": "Pedro Sánchez",
        "genero": "Hombre",
        "nacionalidad": "Española",
        "pelo": "Castaño oscuro",
        "ojos": "Marrones oscuros",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Sánchez.png"
    },
    {
        "nombre": "Lionel Andrés Messi",
        "genero": "Hombre",
        "nacionalidad": "Argentina",
        "pelo": "Castaño claro",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Messi.png"
    },
    {
        "nombre": "Cristiano Ronaldo",
        "genero": "Hombre",
        "nacionalidad": "Portuguesa",
        "pelo": "Negro",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Cristiano.png"
    },
    {
        "nombre": "Adolf Hitler",
        "genero": "Hombre",
        "nacionalidad": "Alemana",
        "pelo": "Negro",
        "ojos": "Azules",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": True,
        "imagen": "Hitler.png"
    },
    {
        "nombre": "Francisco Franco",
        "genero": "Hombre",
        "nacionalidad": "Española",
        "pelo": "Castaño oscuro",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": True,
        "imagen": "Franco.png"
    },
    {
        "nombre": "Benito Mussolini",
        "genero": "Hombre",
        "nacionalidad": "Italiana",
        "pelo": "Calvo",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Mussolini.png"
    },
    {
        "nombre": "Mortadelo",
        "genero": "Hombre",
        "nacionalidad": "Española",
        "pelo": "Calvo",
        "ojos": "Negros",
        "gafas": True,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Mortadelo.png"
    },
    {
        "nombre": "Filemon",
        "genero": "Hombre",
        "nacionalidad": "Española",
        "pelo": "Negro",
        "ojos": "Negros",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Filemon.png"
    },
    {
        "nombre": "Marie Curie",
        "genero": "Mujer",
        "nacionalidad": "Polaca",
        "pelo": "Castaño oscuro",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Curie.png"
    },
    {
        "nombre": "Albert Einstein",
        "genero": "Hombre",
        "nacionalidad": "Alemana",
        "pelo": "Blanco",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": True,
        "imagen": "Einstein.png"
    },
    {
        "nombre": "Giorgia Meloni",
        "genero": "Mujer",
        "nacionalidad": "Italiana",
        "pelo": "Rubio",
        "ojos": "Azul verdosos",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Meloni.png"
    },
    {
        "nombre": "Yayoi Kusama",
        "genero": "Mujer",
        "nacionalidad": "Japonesa",
        "pelo": "Rojo",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Kusama.png"
    },
    {
        "nombre": "Diego Armando Maradona",
        "genero": "Hombre",
        "nacionalidad": "Argentina",
        "pelo": "Negro",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Maradona.png"
    },
    {
        "nombre": "Luis Figo",
        "genero": "Hombre",
        "nacionalidad": "Portuguesa",
        "pelo": "Castaño oscuro",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Figo.png"
    },
    {
        "nombre": "Shizuka Minamoto",
        "genero": "Mujer",
        "nacionalidad": "Japonesa",
        "pelo": "Negro",
        "ojos": "Negros",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Shizuka.png"
    },
    {
        "nombre": "Frida Kahlo",
        "genero": "Mujer",
        "nacionalidad": "Mexicana",
        "pelo": "Negro",
        "ojos": "Marrones",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Kahlo.png"
    },
    {
        "nombre": "Sara Garcia Alonso",
        "genero": "Mujer",
        "nacionalidad": "Española",
        "pelo": "Rojo",
        "ojos": "Avellana",
        "gafas": False,
        "sombrero": False,
        "barba": False,
        "bigote": False,
        "imagen": "Sara.png"
    },
    {
        "nombre": "Chapulín Colorado",
        "genero": "Hombre",
        "nacionalidad": "Mexicana",
        "pelo": "Negro",
        "ojos": "Azul verdosos",
        "gafas": False,
        "sombrero": True,
        "barba": False,
        "bigote": False,
        "imagen": "Chapulín.png"
    },
    {
        "nombre": "Juan Pablo II",
        "genero": "Hombre",
        "nacionalidad": "Polaca",
        "pelo": "Blanco",
        "ojos": "Azules",
        "gafas": False,
        "sombrero": True,
        "barba": False,
        "bigote": False,
        "imagen": "PabloII.png"
    }
]

# Añadimos estado visual inicial (no tachado) a las imágemes de los personajes.
for p in personajes:
    p["tachado"] = False

    # Lista para guardar/almacenar las preguntas cerradas del juego.
categoriaPreguntas = {
    "Género": [
        ("¿Es hombre?", ("genero", "Hombre")),
        ("¿Es mujer?", ("genero", "Mujer")),
    ],
    "Pelo": [
        ("¿Tiene el pelo castaño oscuro?", ("pelo", "Castaño oscuro")),
        ("¿Tiene el pelo castaño claro?", ("pelo", "Castaño claro")),
        ("¿Tiene el pelo negro?", ("pelo", "Negro")),
        ("¿Es calvo?", ("pelo", "Calvo")),
        ("¿Tiene el pelo blanco?", ("pelo", "Blanco")),
        ("¿Tiene el pelo rubio?", ("pelo", "Rubio")),
        ("¿Tiene el pelo rojo?", ("pelo", "Rojo")),
    ],
    "Ojos": [
        ("¿Sus ojos son marrones?", ("ojos", "Marrones")),
        ("¿Sus ojos son marrones oscuros?", ("ojos", "Marrones oscuros")),
        ("¿Sus ojos son negros?", ("ojos", "Negros")),
        ("¿Sus ojos son azules?", ("ojos", "Azules")),
        ("¿Sus ojos son azul verdosos?", ("ojos", "Azul verdosos")),
        ("¿Sus ojos son avellana?", ("ojos", "Avellana")),
    ],
    "Nacionalidad": [
        ("¿Es de nacionalidad española?", ("nacionalidad", "Española")),
        ("¿Es de nacionalidad argentina?", ("nacionalidad", "Argentina")),
        ("¿Es de nacionalidad portuguesa?", ("nacionalidad", "Portuguesa")),
        ("¿Es de nacionalidad alemana?", ("nacionalidad", "Alemana")),
        ("¿Es de nacionalidad italiana?", ("nacionalidad", "Italiana")),
        ("¿Es de nacionalidad polaca?", ("nacionalidad", "Polaca")),
        ("¿Es de nacionalidad japonesa?", ("nacionalidad", "Japonesa")),
        ("¿Es de nacionalidad mexicana?", ("nacionalidad", "Mexicana")),
    ],
    "Accesorios": [
        ("¿Tiene gafas?", "gafas"),
        ("¿Tiene sombrero o algo similar?", "sombrero"),
    ],
    "Vello facial": [
        ("¿Tiene barba?", "barba"),
        ("¿Tiene bigote?", "bigote"),
    ]
}


# ======================================== CLASE PRINCIPAL DEL JUEGO ========================================
    # Aquí se define la clase principal del juego. Su función es gestionar la lógica y la interfaz del juego.
class QuienEsQuien:
    # Constructor de la clase.
    def __init__(self, root):
        self.root = root                    # Ventana principal de Tkinter.
        self.root.title("¿Quién es quién?") # Título de la ventana.

        # Aquí se almacenará el personaje oculto.
        self.personaje_secreto = None

        # Lista para guardar las imágenes y evitar que Python las borre.
        self.imagenes_tk = []

        # Contador de preguntas realizadas.
        self.preguntas_realizadas = 0   # Contador de preguntas realizadas.
        self.max_preguntas = 5          # Número máximo de preguntas permitidas.

        # Pantalla inicial.
        self.pantalla_inicio()

    # ==================== PANTALLA DE INICIO ====================
        # Método/Función para mostrar la pantalla de inicio.
    def pantalla_inicio(self):
        # Limpia la ventana.
        self.limpiar_ventana()

        # Título del juego.
        tk.Label(self.root, text = "¿Quién es quién?", font = ("Arial", 24)).pack(pady = 20)

        # Botón para iniciar el juego.
        tk.Button(self.root, text = "Jugar", font = ("Arial", 16), command = self.iniciar_juego).pack(pady = 15)

    # ==================== INICIO DEL JUEGO ====================
        # Método/Función para iniciar el juego.
    def iniciar_juego(self):
        # Reinicia/Resetea el estado de tachado de los personajes.
        for p in personajes:
            p["tachado"] = False

        # Reinicia/Resetea el contador de preguntas realizadas.
        self.preguntas_realizadas = 0

        # Selecciona un personaje secreto aleatoriamente.
        self.personaje_secreto = random.choice(personajes)

        # Muestra la pantalla principal del juego.
        self.pantalla_juego()

    # ==================== CREAR CONTENEDOR DE MENÚS ====================
    def crear_contenedor_menus(self):
        cont = tk.Frame(self.root)  # Contenedor de menús.
        cont.pack(pady = 10)        # Muestra el contenedor.
        cont.configure(width = 300) # Ancho fijo del contenedor.
        cont.pack_propagate(False)  # Evita que el frame se reduzca al tamaño del contenido
        return cont                 # Devuelve el contenedor creado.

    # ==================== PANTALLA PRINCIPAL ====================
        # Método/Función para mostrar la pantalla principal del juego.
    def pantalla_juego(self):
        # Limpia la ventana.
        self.limpiar_ventana()

        # ========== HEADER SUPERIOR ==========
        header = tk.Frame(self.root, bg = "#2C3E50", height = 100)  # Contenedor del header superior.
        header.pack(fill = "x", side = "top")                         # Muestra el header.

        # Título de la sección de preguntas.
        tk.Label(header, text = "¿QUIÉN ES QUIÉN?\n(con personajes históricos)", font = ("Helvetica", 24, "bold"), bg = "#2C3E50", fg = "white").pack(pady = 10)

        # Contador de preguntas
        tk.Label(header, text = f"Preguntas realizadas: {self.preguntas_realizadas}/{self.max_preguntas}", font = ("Helvetica", 14, "bold"), bg = "#34495E", fg = "#ECF0F1").pack(padx = 20, pady = 15)
    
        # ========== CONTENEDOR PRINCIPAL HORIZONTAL ==========
        contenedor_principal = tk.Frame(self.root, bg = "#ECF0F1")                    # Contenedor principal horizontal.
        contenedor_principal.pack(fill = "both", expand = True, padx = 10, pady = 10)   # Muestra el contenedor.

        # ========== PANEL IZQUIERDO: PERSONAJES ==========
        panel_izquierdo = tk.Frame(contenedor_principal, bg = "#ECF0F1")              # Panel izquierdo: personajes.
        panel_izquierdo.pack(side = "left", fill = "both", expand = True, padx = 10)    # Muestra el panel.
        
        # Título del panel de personajes.
        tk.Label(panel_izquierdo, text = "PERSONAJES", font = ("Helvetica", 16, "bold"), bg = "#ECF0F1", fg = "#2C3E50").pack(pady = (0, 10))
        
        # ========== SCROLLBAR EN PANEL IZQUIERDO ==========
            # Crear un Canvas con scrollbar en el panel izquierdo (antes de la rejilla).
        canvas = tk.Canvas(panel_izquierdo, bg = "#ECF0F1", highlightthickness = 0)           # Crea el canvas.
        scrollbar = tk.Scrollbar(panel_izquierdo, orient = "vertical", command = canvas.yview)  # Crea la scrollbar.
        scrollable_frame = tk.Frame(canvas, bg = "#ECF0F1")                                   # Crea el frame scrollable.

        # Configurar el scrollable frame
        scrollable_frame.bind("<Configure>", lambda e: canvas.configure(scrollregion = canvas.bbox("all")))

        # Crear la ventana del canvas y configurar la scrollbar
        canvas.create_window((0, 0), window = scrollable_frame, anchor = "nw")  # Crea la ventana del canvas.
        canvas.configure(yscrollcommand = scrollbar.set)                        # Configura la scrollbar.

        canvas.pack(side = "left", fill = "both", expand = True)    # Muestra el canvas.
        scrollbar.pack(side = "right", fill = "y")                  # Muestra la scrollbar.
        
        # ========== PANEL DERECHO: PREGUNTAS ==========
        panel_derecho = tk.Frame(contenedor_principal, bg = "#ECF0F1", width = 350) # Panel derecho: preguntas.
        panel_derecho.pack(side = "right", fill = "y", padx = 10)                     # Muestra el panel.
        panel_derecho.pack_propagate(False)                                           # Evita que el frame se reduzca al tamaño del contenido

        # Título para la sección de preguntas
        tk.Label(panel_derecho, text = "CATEGORÍAS", font = ("Helvetica", 16, "bold"), bg = "#ECF0F1", fg = "#2C3E50").pack(pady = (0, 15))

        # ========== BOTONES DE PREGUNTAS ==========
        frame_preguntas = tk.Frame(panel_derecho, bg="#ECF0F1") # Contenedor de botones de preguntas.
        frame_preguntas.pack(pady = 5)                            # Muestra el contenedor.

        # Crea un botón para cada categoría de preguntas.
        for categoriaSubPreguntas in categoriaPreguntas.keys():
            # Crea el botón y asigna la función de respuesta.
            btn = tk.Button(frame_preguntas, text = categoriaSubPreguntas, font = ("Helvetica", 11, "bold"), width = 20, fg = "white", bg = "#3498DB", activebackground = "#2980B9", activeforeground = "white", relief = "flat", cursor = "hand2", pady = 8, command = lambda c = categoriaSubPreguntas: self.mostrar_subpreguntas(c)) # Crea el botón.
            btn.pack(pady = 5, anchor = "center")                                                                                                                                                                                                                                                                                           # Muestra el botón.

            # Efecto hover
            btn.bind("<Enter>", lambda e, b = btn: b.config(bg = "#2980B9"))    # Cambia el color al pasar el ratón por encima.
            btn.bind("<Leave>", lambda e, b = btn: b.config(bg = "#3498DB"))    # Restaura el color al quitar el ratón.

        # Zona de respuesta “Sí/No”.
        self.label_respuesta = tk.Label(panel_derecho, text = "", font = ("Helvetica", 32, "bold"), bg = "#ECF0F1", fg = "#27AE60", width = 10, height = 2) # Etiqueta para mostrar la respuesta.
        self.label_respuesta.pack(pady = 5)                                                                                                                     # Muestra la etiqueta.

        # Separador visual
        tk.Frame(panel_derecho, height = 2, bg = "#BDC3C7").pack(fill = "x", pady = 5)

        # Botón para adivinar el personaje.
        btn_adivinar = tk.Button(panel_derecho, text = "Adivinar Personaje", font = ("Helvetica", 12, "bold"), bg = "#27AE60", fg = "white", activebackground = "#229954", activeforeground = "white", relief = "flat", cursor = "hand2", pady = 12, command = self.preguntar_adivinar) # Crea el botón.
        btn_adivinar.pack(fill = "x", pady = 10, padx = 20, )                                                                                                                                                                                                                           # Muestra el botón.

        # Efecto hover para botón adivinar
        btn_adivinar.bind("<Enter>", lambda e: btn_adivinar.config(bg = "#229954")) # Cambia el color al pasar el ratón por encima.
        btn_adivinar.bind("<Leave>", lambda e: btn_adivinar.config(bg = "#27AE60")) # Restaura el color al quitar el ratón.

        # ========== FOOTER ==========
            # Crea un bloque para el "footer".
        footer = tk.Frame(panel_derecho, bg = "#ECF0F1")
        footer.pack(pady = 10)

            # Primera línea: texto normal + nombre de la empresa en negrita.
        line1 = tk.Frame(footer, bg = "#ECF0F1")
        line1.pack(anchor = "center")
        
            # Texto informativo, título del juego.
        tk.Label(line1, text = "¿Quién es quién? Personajes históricos ~ ", font = ("Helvetica", 8), bg = "#ECF0F1", fg = "#7F8C8D").pack(side = "left")

            # Texto informativo, nombre de la empresa creadora/desarrolladora del producto, del juego.
        tk.Label(line1, text = "RODRISTARK.GAME$17", font = ("Helvetica", 8, "bold"), bg = "#ECF0F1", fg = "#7F8C8D").pack(side = "left")

            # Texto informativo, legalidad del juego (líneas siguientes).
        tk.Label(footer, text = "© 2025 | ® Marca Registrada | ™ Producto Original\nDerechos de imagen respetados.", font = ("Helvetica", 8), bg = "#ECF0F1", fg = "#7F8C8D", wraplength = 300, justify = "center").pack()
        
        # ========== REJILLA DE IMÁGENES DE PERSONAJES ==========
            # Método/Función para crear la rejilla de imágenes de personajes.
        frame_personajes = tk.Frame(scrollable_frame, bg = "#ECF0F1")    # Contenedor de la rejilla de personajes.
        frame_personajes.pack(pady = 10, padx = 40, anchor = "center",)                                  # Muestra el contenedor.

            # Rejilla de 4 columnas y filas automáticas.
        columnas = 5
        fila = 0
        columna = 0

        # Crea la rejilla de personajes.
        for p in personajes:
            # Carga la imagen desde el archivo.
            ruta_img = os.path.join(ruta_base, "Personajes", p["imagen"])   # Construye la ruta completa a la imagen del personaje.
            img = Image.open(ruta_img)                                      # Abre la imagen desde la ruta "Personajes/".
            img = img.resize((120, 120))                                    # Tamaño fijo.
            img = img.convert("RGBA")                                       # Convierte la imagen a formato RGBA (para transparencia).
            img_tk = ImageTk.PhotoImage(img)                                # Convierte la imagen para Tkinter (para que Tkinter la pueda tratar).

            # Guarda la referencia para evitar que Python las elimine.
            self.imagenes_tk.append(img_tk)

            # Frame individual de cada personaje.
            cont = tk.Frame(frame_personajes, bg = "#BDC3C7", relief = "solid", borderwidth = 1)    # Crea el frame del personaje.
            cont.grid(row = fila, column = columna, padx = 8, pady = 8)                                # Posiciona el frame en la rejilla.

            # Subframe para la imagen.
            img_frame = tk.Frame(cont, bg = "white")    # Subframe para la imagen.
            img_frame.pack(padx = 2, pady = 2)          # Muestra el subframe.

            # Imagen del personaje.
            lbl_img = tk.Label(img_frame, image = img_tk, bg = "white") # Crea la etiqueta de la imagen.
            lbl_img.pack()                                              # Muestra la imagen.

            # Nombre debajo de la imagen con fondo.
            nombre_frame = tk.Frame(cont, bg = "#34495E")   # Subframe para el nombre.
            nombre_frame.pack(fill = "x")                     # Muestra el subframe.
            
            # Nombre del personaje.
            tk.Label(nombre_frame, text = p["nombre"], font = ("Helvetica", 9, "bold"), bg = "#34495E", fg = "white", wraplength = 120).pack(pady = 3)

            # Controlar (columnas / filas) de la rejilla.
            columna += 1
            if columna == columnas:
                columna = 0
                fila += 1
                
        # ========== HABILITAR SCROLL EN PANEL IZQUIERDO ==========
            # Habilitar scroll con el mouse.
        def _on_mousewheel(event):
            canvas.yview_scroll(int(-1*(event.delta/120)), "units") # Desplaza el canvas con la rueda del ratón.
        
        canvas.bind_all("<MouseWheel>", _on_mousewheel) # Vincula el evento de la rueda del ratón al canvas.
        
    # ==================== RESPONDER LA PREGUNTA ====================
        # Método/Función para responder la pregunta seleccionada.
    def responder(self, atributo, categoria):
        # Incrementa el contador de preguntas
        self.preguntas_realizadas += 1

        # Determina la respuesta según el atributo preguntado (atributo simple o atributo compuesto).
        if isinstance(atributo, str):
            # Atributo booleano (atributo simple).
            valor = self.personaje_secreto[atributo]    # Obtiene el valor del atributo.
            respuesta = "Sí" if valor else "No"         # Respuesta según el valor.
        else:
            # Atributo compuesto (clave, valor).
            clave, valor = atributo                                                 # Desempaqueta el atributo.
            respuesta = "Sí" if self.personaje_secreto[clave] == valor else "No"    # Respuesta según el valor.

        # Muestra la respuesta en la etiqueta correspondiente.
        self.label_respuesta.config(text = respuesta)

        # Actualiza los personajes tachados según la respuesta.
        self.actualizar_tachados(atributo, respuesta)
        
        # Muestra las subpreguntas de la categoría actualizada.
        #self.mostrar_subpreguntas(categoria, respuesta)

        # Verifica si se ha alcanzado el límite de preguntas.
        if self.preguntas_realizadas >= self.max_preguntas:
            self.pantalla_fin_preguntas()
        else:
            # Muestra las subpreguntas de la categoría actualizada.
            self.mostrar_subpreguntas(categoria, respuesta)

    # ==================== ACTUALIZAR TACHADOS ====================
        # Método/Función para actualizar los personajes tachados según la respuesta obtenida por la pregunta escogida.
    def actualizar_tachados(self, atributo, respuesta):
        # Recorre todos los personajes para actualizar su estado de tachado.
        for p in personajes:
            if isinstance(atributo, str):
                # Atributo booleano (atributo simple).
                valor_personaje = p[atributo]   # Obtiene el valor del atributo.

                # Si la respuesta es "Sí" o "No", actualiza el estado de tachado.
                if respuesta == "Sí":
                    # Si la respuesta es "Sí", tacha a los que NO cumplen
                    if valor_personaje == False:
                        p["tachado"] = True # Tacha al personaje.
                elif respuesta == "No":
                    # Si la respuesta es "No", tacha a los que sí cumplen
                    if valor_personaje == True:
                        p["tachado"] = True # Tacha al personaje.
            else:
                # Atributo compuesto (clave, valor).
                clave, valor = atributo # Desempaqueta el atributo.
                valor_personaje = p[clave]

                # Si la respuesta es "Sí" o "No", actualiza el estado de tachado.
                if respuesta == "Sí":
                    # Si la respuesta es "Sí", tacha a los que NO cumplen
                    if valor_personaje != valor:
                        p["tachado"] = True # Tacha al personaje.
                elif respuesta == "No":
                    # Si la respuesta es "No", tacha a los que sí cumplen
                    if valor_personaje == valor:
                        p["tachado"] = True # Tacha al personaje.

    # ==================== MOSTRAR SUBPREGUNTAS ====================
        # Método/Función para mostrar las subpreguntas de una categoría.
    def mostrar_subpreguntas(self, categoria, respuesta = ""):
        # Limpia la ventana para mostrar nuevas preguntas.
        self.limpiar_ventana()

        # Título.
        tk.Label(self.root, text = categoria, font = ("Arial", 20)).pack(pady = 10)  # Muestra el título de la categoría.

        # Contador de preguntas
        tk.Label(self.root, text = f"Preguntas realizadas: {self.preguntas_realizadas}/{self.max_preguntas}", font = ("Arial", 16), fg = "blue").pack(pady = 5)
        
        # Contenedor principal horizontal.
        contenedor_principal = tk.Frame(self.root)                          # Contenedor principal horizontal.
        contenedor_principal.pack(fill = "both", expand = True, padx = 20)  # Muestra el contenedor.

        # Panel izquierdo: personajes.
        panel_izquierdo = tk.Frame(contenedor_principal)                    # Panel izquierdo: personajes.
        panel_izquierdo.pack(side = "left", fill = "both", expand = True)   # Muestra el panel.

        # Muestra la rejilla de personajes en el panel izquierdo.
        self.mostrar_rejilla_personajes_en_panel(panel_izquierdo)

        # Panel derecho: preguntas.
        panel_derecho = tk.Frame(contenedor_principal)              # Panel derecho: preguntas.
        panel_derecho.pack(side = "right", fill  ="y", padx = 20)   # Muestra el panel.

        # Zona de respuesta “Sí/No”.
        self.label_respuesta = tk.Label(panel_derecho, text = respuesta, font = ("Arial", 22)) # Etiqueta para mostrar la respuesta.
        self.label_respuesta.pack(pady = 20)                                            # Muestra la etiqueta.

        # Contenedor de botones de subpreguntas.
        frame = tk.Frame(panel_derecho) # Contenedor de botones de subpreguntas.
        frame.pack(pady = 10)       # Muestra el contenedor.

        # Generar botones de subpreguntas
        for texto, atributo in categoriaPreguntas[categoria]:
            btn = tk.Button(frame, text = texto, font = ("Arial", 12), width = 35, command = lambda a = atributo, c = categoria: self.responder(a, c))  # Crea el botón.
            btn.pack(pady = 4, anchor = "center")                                                                                                       # Muestra el botón.

        # Botón para volver atrás.
        tk.Button(panel_derecho, text = "Volver", font = ("Arial", 14), command = self.pantalla_juego).pack(pady = 20)

    # ==================== PANTALLA FIN DE PREGUNTAS ====================
        # Método/Función para mostrar la pantalla cuando se acaban las preguntas (a modo de notificación).
    def pantalla_fin_preguntas(self):
        # Limpia la ventana.
        self.limpiar_ventana()

        # Mensaje de límite alcanzado
        tk.Label(self.root, text = "¡Se acabaron las preguntas!\nLímite de preguntas alcanzado.", font = ("Arial", 22)).pack(pady = 20)
        tk.Label(self.root, text = f"Has realizado {self.max_preguntas} preguntas.", font = ("Arial", 16)).pack(pady = 10)
        tk.Label(self.root, text = "Ahora debes adivinar el personaje.", font = ("Arial", 16)).pack(pady = 10)

        # Botón para adivinar el personaje.
        tk.Button(self.root, text = "Adivinar personaje", font = ("Arial", 14), command = self.preguntar_adivinar).pack(pady = 20)
        
        # Botón para volver al inicio
        tk.Button(self.root, text = "Volver al inicio", font = ("Arial", 14), command = self.pantalla_inicio).pack(pady = 10)


    # ==================== MOSTRAR REJILLA DE PERSONAJES EN PANEL ====================
        # Método/Función para mostrar la rejilla de personajes en un panel específico.
    def mostrar_rejilla_personajes_en_panel(self, panel):
        # Rejilla de imágenes de personajes.
        frame_personajes = tk.Frame(panel)  # Contenedor de la rejilla de personajes.
        frame_personajes.pack(pady = 10)    # Muestra el contenedor.

        # Rejilla de 5 columnas y filas automáticas.
        columnas = 5
        fila = 0
        columna = 0

        # Crea la rejilla de personajes.
        for p in personajes:
            # Carga la imagen desde el archivo.
            ruta_img = os.path.join(ruta_base, "Personajes", p["imagen"])   # Construye la ruta completa a la imagen del personaje.
            img = Image.open(ruta_img)                                      # Abre la imagen desde la ruta "Personajes/".
            img = img.resize((120, 120))                                    # Tamaño fijo.
            img_tk = ImageTk.PhotoImage(img)                                # Convierte la imagen para Tkinter (para que Tkinter la pueda tratar).

            # Guarda la referencia para evitar que Python las elimine.
            self.imagenes_tk.append(img_tk)

            # Frame individual de cada personaje.
            cont = tk.Frame(frame_personajes)                               # Crea el frame del personaje.
            cont.grid(row = fila, column = columna, padx = 10, pady = 10)   # Posiciona el frame en la rejilla.

            # Canvas para dibujar
            canvas = tk.Canvas(cont, width = 120, height = 120) # Crea un canvas para dibujar.
            canvas.pack()                                       # Muestra el canvas.

            # Dibuja la imagen
            canvas.create_image(0, 0, anchor = "nw", image = img_tk)

            # Dibuja la línea roja si está tachado.
            if p["tachado"]:
                self.animar_tachado(canvas, p)  # Dibuja la línea roja si está tachado.

            # Nombre debajo de la imagen.
            tk.Label(cont, text = p["nombre"], font = ("Arial", 12)).pack()

            # Controlar (columnas / filas) de la rejilla.
            columna += 1
            if columna == columnas:
                columna = 0
                fila += 1

    # ==================== MOSTRAR REJILLA DE PERSONAJES ====================
        # Método/Función para mostrar la rejilla de personajes.
    def mostrar_rejilla_personajes(self):
        # Elimina la rejilla de personajes anterior si existe.
        if hasattr(self, "frame_personajes"):   # Si el atributo "frame_personajes" existe,
            self.frame_personajes.destroy()     # elimina el frame anterior.


        # Rejilla de imágenes de personajes.
        self.frame_personajes = tk.Frame(self.root)  # Contenedor de la rejilla de personajes.
        self.frame_personajes.pack(pady = 10)        # Muestra el contenedor.

        # Rejilla de 4 columnas y filas automáticas.
        columnas = 5
        fila = 0
        columna = 0

        # Crea la rejilla de personajes.
        for p in personajes:
            # Carga la imagen desde el archivo.
            ruta_img = os.path.join(ruta_base, "Personajes", p["imagen"])   # Construye la ruta completa a la imagen del personaje.
            # img = Image.open(ruta_img).resize((120,120)).convert("RGBA")
            img = Image.open(ruta_img)                                      # Abre la imagen desde la ruta "Personajes/".
            img = img.resize((120, 120))                                    # Tamaño fijo.
            img_tk = ImageTk.PhotoImage(img)                                # Convierte la imagen para Tkinter (para que Tkinter la pueda tratar).

            # Guarda la referencia para evitar que Python las elimine.
            self.imagenes_tk.append(img_tk)

            # Frame individual de cada personaje.
            cont = tk.Frame(self.frame_personajes)                            # Crea el frame del personaje.
            cont.grid(row = fila, column = columna, padx = 10, pady = 10)  # Posiciona el frame en la rejilla.

            # Si el personaje está tachado, dibuja una línea roja sobre la imagen.
            canvas = tk.Canvas(cont, width = 120, height = 120) # Crea un canvas para dibujar.
            canvas.pack()                                       # Muestra el canvas.

            # Dibuja la línea roja si está tachado.
            canvas.create_image(0, 0, anchor = "nw", image = img_tk)

            # Dibuja la línea roja si está tachado.
            if p["tachado"]:
                # Sin animación.
                #canvas.create_line(0, 0, 120, 120, width = 7, fill = "red") # Dibuja la línea roja diagonal.
                #canvas.create_line(120, 0, 0, 120, width = 7, fill = "red") # Dibuja la otra línea roja diagonal.
                # Con animación.
                self.animar_tachado(canvas, p)

            # Imagen del personaje.
            #tk.Label(cont, image = img_tk).pack()                           # Muestra la imagen.
            tk.Label(cont, text = p["nombre"], font = ("Arial", 12)).pack() # Nombre debajo de la imagen.

            # Controlar (columnas / filas) de la rejilla.
            columna += 1
            if columna == columnas: # Controlar cambio de fila al llenar columnas. Si se alcanza el número de columnas, pasa a la siguiente fila.
                columna = 0
                fila += 1

    # ==================== ANIMAR TACHADO ====================
        # Método/Función para animar el tachado de un personaje.
    def animar_tachado(self, canvas, p, paso = 0):
        # Elimina la rejilla de personajes anterior si existe.
        if paso == 0:
            # Crear versión reducida de brillo.
            ruta_img = os.path.join(ruta_base, "Personajes", p["imagen"])   # Construye la ruta completa a la imagen del personaje.
            img = Image.open(ruta_img).convert("RGBA")                      # Abre la imagen desde la ruta "Personajes/".
            factor = 0.4  # (más bajo = más oscuro)                         # Factor de reducción de brillo.
            pixels = img.load()                                             # Carga los píxeles de la imagen.

            # Aplica el factor de reducción de brillo.
            for x in range(img.width):
                # Recorre cada píxel y aplica el factor.
                for y in range(img.height):
                    r,g,b,a = pixels[x,y]                                                   # Obtiene el valor RGBA del píxel.
                    pixels[x,y] = (int(r * factor), int(g * factor), int(b * factor), a)    # Aplica el factor.
            
            # Guarda la imagen modificada.
            self.fade_img = ImageTk.PhotoImage(img)                         # Convierte la imagen para Tkinter (para que Tkinter la pueda tratar).
            canvas.create_image(0,0, anchor = "nw", image = self.fade_img)  # Muestra la imagen.

        # Animación de línea.
        largo = int(120 * paso / 10)                                                                # Calcula el largo de la línea según el paso.
        canvas.delete("tachado_tmp")                                                                # Elimina la línea temporal anterior.
        canvas.create_line(0, 0, largo, largo, width = 5, fill = "red", tags = "tachado_tmp")       # Dibuja la línea roja diagonal.
        canvas.create_line(120, 0, 120-largo, largo, width = 5, fill = "red", tags = "tachado_tmp") # Dibuja la otra línea roja diagonal.

        # Continúa la animación si no ha terminado.
        if paso < 10:
            canvas.after(30, lambda: self.animar_tachado(canvas, p, paso+1))    # Llama a sí mismo después de 30 ms para el siguiente paso.

    # ==================== PREGUNTAR/ADIVINAR PERSONAJE ====================
        # Método/Función para preguntar al usuario por su suposición del personaje secreto.
    def preguntar_adivinar(self):
        # Pide al usuario que introduzca su suposición y muestra si ha acertado o no.
            # Usamos "simpledialog" para obtener una cadena de entrada.
        respuesta = simpledialog.askstring("Adivinar personaje", "¿Quién crees que es el personaje secreto?", parent = self.root)   # Pide al usuario que introduzca su suposición.

        # Si el usuario cancela el diálogo, no hacemos nada.
        if respuesta is None:
            return
        
        guess = respuesta.strip()   # Elimina espacios en blanco al inicio y al final.

        # Comprueba si la suposición es correcta.
            # Si es correcta, muestra mensaje de acierto.
        if guess.lower() == self.personaje_secreto["nombre"].lower():
            messagebox.showinfo("Resultado", "¡Correcto! Has adivinado el personaje secreto.")  # Muestra mensaje de acierto.

            # Si no es correcta, muestra mensaje de error.
        else:
            messagebox.showinfo("Resultado", f"No es correcto. Has dicho: {guess}")             # Muestra mensaje de error.
            
        # Después de mostrar el resultado, mostrar la pantalla final con el personaje secreto.
        self.pantalla_final()

    # ==================== PANTALLA FINAL ====================
        # Método/Función para mostrar la pantalla final del juego.
    def pantalla_final(self):
        # Limpia la ventana.
        self.limpiar_ventana()

        # Muestra el personaje secreto.
        texto = f"¡El personaje secreto era: {self.personaje_secreto['nombre']}!"   # Texto con el nombre del personaje secreto.
        tk.Label(self.root, text = texto, font = ("Arial", 22)).pack(pady = 20)     # Muestra el texto.

        # Muestra la imagen del personaje secreto.
        ruta_img = os.path.join(ruta_base, "Personajes", self.personaje_secreto["imagen"])   # Construye la ruta completa a la imagen del personaje.
        img = Image.open(ruta_img)                                      # Abre la imagen.
        img = img.resize((200, 200))                                    # Tamaño fijo.
        img_tk = ImageTk.PhotoImage(img)                                # Convierte la imagen para Tkinter (para que Tkinter la pueda tratar).

        # Guarda la referencia para evitar que Python las elimine.
        self.imagenes_tk.append(img_tk)

        # Muestra la imagen.
        tk.Label(self.root, image = img_tk).pack(pady = 10)

        # Botón para volver al inicio.
        tk.Button(self.root, text = "Volver al inicio", font = ("Arial", 14), command = self.pantalla_inicio).pack(pady = 20)

    # ==================== LIMPIAR VENTANA ====================
        # Método/Función para limpiar la ventana en caso de reinicio o cambio de pantalla.
    def limpiar_ventana(self):
        # Elimina todos los widgets de la ventana.
        for widget in self.root.winfo_children():
            widget.destroy()


# ======================================== EJECUCIÓN DEL PROGRAMA ========================================
    # Aquí se ejecuta el programa llamando a crear la ventana principal y la instancia del juego.
root = tk.Tk()              # Crea la ventana principal.
app = QuienEsQuien(root)    # Crea una instancia de la clase del juego.
root.mainloop()             # Inicia el bucle principal de la interfaz gráfica.
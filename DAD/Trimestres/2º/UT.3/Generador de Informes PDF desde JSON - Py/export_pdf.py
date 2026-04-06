# Exportación de los datos a PDF utilizando "ReportLab" y "MySQL Connector/MySQL Python".
    # Módulo para exportar datos de la base de datos MySQL a un archivo PDF.
import os                               # Importa la biblioteca "os" para operaciones del sistema operativo. 

from reportlab.lib.pagesizes import A4  # Importa el tamaño de página A4.
from reportlab.pdfgen import canvas     # Importa la clase "canvas" para crear el PDF.
from datetime import datetime           # Importa la clase "datetime" para manejar fechas y horas.



# Función/Método para exportar los datos a un archivo PDF.
    # La función recibe como parámetros la lista de productos y el término de búsqueda (si lo hay) y genera un archivo PDF con los datos.
def export_data_to_pdf(productos, termino = ""):
    # Crea una carpeta/Directorio "Informes PDF impresos" en el directorio actual.
    ruta_carpeta = os.path.join(os.getcwd(), "Informes PDF impresos")   # Define la ruta de la carpeta.

    # Compreuba/Verifica previo a crear la carpeta si esta existe. Si no existe crea la careta.
    if not os.path.exists(ruta_carpeta):
        os.makedirs(ruta_carpeta)                       # Crea la carpeta si no existe.
        print(f"\n\n\tCarpeta creada: {ruta_carpeta}")  # Mensaje de confirmación de creación de la carpeta por consola.
    # Si la carpeta ya existe, muestra un mensaje indicando/informando al usuario de su existencia.
    else:
        print(f"\n\n\tLa carpeta ya existe: {ruta_carpeta}")  # Mensaje de confirmación de que la carpeta ya existe por consola.

    # Obtiene la fecha y hora actual para usarla en el nombre del archivo.
    fecha_actual = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")

    # Si hay un término de búsqueda, lo usamos en el nombre del archivo.
    if termino:
        nombre_archivo = f"Informe_productos_DummyJSON_{termino}_{fecha_actual}.pdf"
        titulo = f"Informe de productos - DummyJSON - Búsqueda: '{termino}'"
    # Si no hay término de búsqueda, indicamos que es un informe de todos los productos.
    else:
        nombre_archivo = f"Informe_todos_los_productos_DummyJSON_{fecha_actual}.pdf"
        titulo = "Informe de productos - DummyJSON - Listado completo"

    # Ruta completa del archivo (se guardará en la misma carpeta del proyecto).
    #ruta_archivo = os.path.join(os.getcwd(), nombre_archivo)
    ruta_archivo = os.path.join(ruta_carpeta, nombre_archivo)

    # Crea el PDF.
    pdf = canvas.Canvas(ruta_archivo, pagesize = A4)

    # Tamaño de la página.
    ancho, alto = A4

    # Nombre/Título del informe que recibe el PDF.
    pdf.setFont("Helvetica-Bold", 16)                                   # Fuente del título.
    #pdf.drawString(50, alto - 50, "Informe de productos - DummyJSON")  # Título del informe.
    pdf.drawString(50, alto - 50, titulo)                               # Título del informe.

    # Subtítulo dependiendo de si es búsqueda o listado completo.
    pdf.setFont("Helvetica", 12)

    # Si hay término de búsqueda, lo indicamos en el subtítulo.
    if termino:
        pdf.drawString(50, alto - 70, f"Búsqueda realizada: '{termino}'")
    # Si no hay término, indicamos que es un listado completo.
    else:
        pdf.drawString(50, alto - 70, "Listado completo de productos:")

    # Fecha y hora de generación.
    pdf.drawString(50, alto - 90, f"Fecha de generación: {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}")

    # Línea separadora.
    pdf.line(50, alto - 100, ancho - 50, alto - 100)

    # Posición inicial del contenido.
    y = alto - 130
    y -= 10
    
    # Recorre la lista de productos y lo imprime en el PDF.
    for i, producto in enumerate(productos, start = 1):
        # Si la posición baja mucho, crea una nueva página.
        if y <= 120:
            pdf.showPage()
            pdf.setFont("Helvetica", 14)
            y = alto - 80

        tab = " " * 7

        # Escribe cada campo del producto en el PDF
        pdf.setFont("Helvetica-Bold", 12)
        pdf.drawString(50, y, f"Producto {i}")
        y -= 10

        pdf.setFont("Helvetica", 11)
        pdf.drawString(70, y - 15, f"ID: {producto['id']}")
        pdf.drawString(200, y - 15, f"Nombre: {producto['nombre']}")

        pdf.drawString(70, y - 30, f"Categoría: {producto['categoria']}")
        pdf.drawString(200, y - 30, f"Marca: {producto['marca']}")

        pdf.drawString(70, y - 45, f"Precio $ (USD): {producto['precio']}$")
        pdf.drawString(200, y - 45, f"Descuento (%): {producto['descuento']}%{tab}({producto['precio']/(1 - producto['descuento']/100):.2f}$ sin descuento)")

        pdf.drawString(70, y - 60, f"Stock: {producto['stock']}")
        pdf.drawString(200, y - 60, f"Valoración (0-5): {producto['valoracion']}")

        # Descripción (por si es larga).
        pdf.drawString(70, y - 75, f"Descripción:")
        texto_descripcion = producto['descripción']
        y -= 10

        # Control básico de longitud de la descripción. Si es muy larga, la cortamos.
        if len(texto_descripcion) > 80:
            pdf.drawString(90, y - 90, texto_descripcion[:80] + "...")
            y -= 115
        # Si no es muy larga, la escribimos completa.
        else:
            pdf.drawString(90, y - 90, texto_descripcion)
            y -= 115

        # Línea separadora entre productos.
        pdf.line(50, y, ancho - 50, y)
        y -= 30

    # Mensaje final en el PDF.
    pdf.setFont("Helvetica-Oblique", 9)
    pdf.drawString(50, 40, f"Total de productos incluidos en el informe: {len(productos)}")

    # Guarda el documento.
    pdf.save()

    # Devuelve la ruta del archivo creado (para mostrarla en el main).
    return ruta_archivo
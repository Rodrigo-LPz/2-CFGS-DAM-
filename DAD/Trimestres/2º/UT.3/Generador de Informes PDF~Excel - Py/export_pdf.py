# Exportación de los datos a PDF utilizando "ReportLab" y "MySQL Connector/MySQL Python".
    # Módulo para exportar datos de la base de datos MySQL a un archivo PDF.
import os                               # Importa la biblioteca "os" para operaciones del sistema operativo. 

from reportlab.lib.pagesizes import A4  # Importa el tamaño de página A4.
from reportlab.pdfgen import canvas     # Importa la clase "canvas" para crear el PDF.
from db_connection import connection    # Importa la función de conexión a la base de datos desde el módulo "db_connection".




# Función/Método para exportar los datos a un archivo PDF.
def export_data_to_pdf(host, database, user, password, cod_medico_inicio = None, cod_medico_fin = None):
    # Crea una carpeta/Directorio "Informes PDF impresos" en el directorio actual.
    ruta_carpeta = os.path.join(os.getcwd(), "Informes PDF impresos")   # Define la ruta de la carpeta.

    # Compreuba/Verifica previo a crear la carpeta si esta existe. Si no existe crea la careta.
    if not os.path.exists(ruta_carpeta):
        os.makedirs(ruta_carpeta)                       # Crea la carpeta si no existe.
        print(f"\n\n\tCarpeta creada: {ruta_carpeta}")  # Mensaje de confirmación de creación de la carpeta por consola.
    # Si la carpeta ya existe, muestra un mensaje indicando/informando al usuario de su existencia.
    else:
        print(f"\n\n\tLa carpeta ya existe: {ruta_carpeta}")  # Mensaje de confirmación de que la carpeta ya existe por consola.
    
    # Establece la conexión a la base de datos.
    conn = connection(host, database, user, password)

    # Crea un cursor para ejecutar consultas SQL.
    curSQL = conn.cursor()

    # Ejecuta una consulta SQL para obtener los datos de ambas tabla "MEDICOS" y "CITAS".
    #curSQL.execute("""
    #                SELECT m.COD_MEDICO, m.NOMBRE_COMPLETO, m.ESPECIALIDAD, m.TURNO, m.CONSULTAS_DISPONIBLES_LUNES, m.CONSULTAS_DISPONIBLES_MARTES, m.CONSULTAS_DISPONIBLES_MIERCOLES, m.CONSULTAS_DISPONIBLES_JUEVES, m.CONSULTAS_DISPONIBLES_VIERNES, m.ANOS_EXPERIENCIA, c.NUM_CITA, c.COD_MEDICO, c.FECHA_CITA, c.HORA_CITA, c.MODALIDAD, c.URGENTE, c.ESTADO
    #                FROM MEDICOS m
    #                LEFT JOIN CITAS c ON m.COD_MEDICO = c.COD_MEDICO
    #                ORDER BY m.COD_MEDICO, c.NUM_CITA
    #               """)
    querySQL = ("""
                    SELECT m.COD_MEDICO, m.NOMBRE_COMPLETO, m.ESPECIALIDAD, m.TURNO, m.CONSULTAS_DISPONIBLES_LUNES, m.CONSULTAS_DISPONIBLES_MARTES, m.CONSULTAS_DISPONIBLES_MIERCOLES, m.CONSULTAS_DISPONIBLES_JUEVES, m.CONSULTAS_DISPONIBLES_VIERNES, m.ANOS_EXPERIENCIA, c.NUM_CITA, c.COD_MEDICO, c.FECHA_CITA, c.HORA_CITA, c.MODALIDAD, c.URGENTE, c.ESTADO
                    FROM MEDICOS m
                    LEFT JOIN CITAS c ON m.COD_MEDICO = c.COD_MEDICO
                """)

    # Añade condiciones "WHERE" según los parámetros.
    condiciones = []
    parametros = []

    # Filtrado por código de médico.
    if cod_medico_inicio is not None:
        # Si se proporciona un código de médico de inicio.
        if cod_medico_fin is not None:
            # Rango de médicos (Parámetros Médico-inicio - Médico-fin).
            condiciones.append("m.COD_MEDICO BETWEEN %s AND %s")    # Añade la condición al listado de condiciones.
            parametros.extend([cod_medico_inicio, cod_medico_fin])  # Añade los parámetros correspondientes al listado de parámetros.
        # Si no se proporciona un código de médico de fin.
        else:
            # Rango de médicos (Parámetros Médico-inicio - x).
                # "x" = Parámetro Médico-no-específico. Por tanto se imprimirá (Parámetros Médico-inicio - hasta el final de la lista).
            condiciones.append("m.COD_MEDICO >= %s")    # Añade la condición al listado de condiciones.
            parametros.append(cod_medico_inicio)        # Añade el parámetro correspondiente al listado de parámetros.

    # Si se han añadido condiciones (parámetros de filtración de búsqueda), las agrega a la consulta SQL. Construye la consulta final con las condiciones.
    if condiciones:
        querySQL += " WHERE " + " AND ".join(condiciones)  # Añade las condiciones a la consulta SQL.
    
    querySQL += " ORDER BY m.COD_MEDICO, c.NUM_CITA"   # Añade el ordenamiento a la consulta SQL. La sentencia final quedará de la siguiente forma:
                                                            # SELECT ...
                                                            # FROM ...
                                                            # LEFT JOIN ...
                                                            # WHERE ... (si hay condiciones)
                                                            # ORDER BY ...
                                                    

    # Ejecuta la consulta SQL añadiendo la sentendia, los parámetros y condiciones.
    curSQL.execute(querySQL, parametros)


    # Obtiene todos los datos resultantes de la consulta.
    datos = curSQL.fetchall()

    # Una vez terminada la conexión y su posterior consulta de ejecución cierra la conexión a la base de datos.
    conn.close()

    # Crea un archivo PDF.
        # Crea un objeto "canvas" para el PDF con: 
            # Nombre del archivo PDF.
            # Tamaño de página definido (A4).
            # Establece la fuente y el tamaño de letra
            # Título del informe en el PDF.
    #pdf_file = "informe_medicos_citas.pdf"
        # Define el nombre del archivo según el tipo de exportación.
            # Si no se especifica ningún código de médico, se exporta el informe completo.
    if cod_medico_inicio is None:
        pdf_fileName = "informe_medicos_citas_completo.pdf" # Nombre del archivo PDF completo.
        titulo = "Informe Completo de Médicos y Citas"  # Título del informe completo.
            # Si se especifica un rango de códigos de médico, se exporta el informe para ese rango.
    elif cod_medico_fin is not None:
        pdf_fileName = f"informe_medicos_{cod_medico_inicio}_a_{cod_medico_fin}.pdf"    # Nombre del archivo PDF por rango de médicos.
        titulo = f"Informe de Médicos {cod_medico_inicio} a {cod_medico_fin}"       # Título del informe por rango de médicos.
            # Si se especifica solo el código de médico de inicio, se exporta el informe para ese médico en adelante, hasta el final.
    else:
        pdf_fileName = f"informe_medico_{cod_medico_inicio}.pdf"    # Nombre del archivo PDF por médico específico.
        titulo = f"Informe del Médico {cod_medico_inicio}"      # Título del informe por médico específico.
    
    # Define la ruta completa del archivo PDF.
    pdf_file = os.path.join(ruta_carpeta, pdf_fileName)

    # Crea el objeto "canvas" para el PDF y escribe el título.
    c = canvas.Canvas(pdf_file, pagesize = A4)  # Crea el objeto "canvas" para el PDF.
    c.setFont("Helvetica-Bold", 18)             # Establece la fuente y el tamaño de letra.
    c.drawString(200, 800, titulo)              # Escribe el título del informe en el PDF.

    # Define las posiciones iniciales para escribir los datos en el PDF.
    x = 50   # Posición horizontal inicial para escribir los datos.
    y = 750  # Posición vertical inicial para escribir los datos en el PDF.

    c.setFont("Helvetica", 14)  # Establece la fuente y el tamaño de letra para los datos.
    # ========== Formato 1 ==========
    # Escribe los datos en el PDF.
    #for row in datos:
    #    texto = ", ".join(str(item) for item in row)    # Para evitar errores de tipo (de valores) convierte cada elemento de la fila a cadena, a "String", y cada elemento de la cadena separado por comas, antes de insertarlo.
    #    c.drawString(x, y, texto)                       # Escribe la cadena en la posición (x, y) del PDF.
    #    y -= 20                                         # Mueve la posición vertical hacia abajo para la siguiente fila.

    #    # Si la posición vertical llega al final de la página, crea una nueva página.
    #    if y < 50:
    #        c.showPage()      # Crea una nueva página en el PDF.
    #        c.setFont("Helvetica", 12)  # Restablece la fuente y el tamaño de letra.
    #        y = 750           # Reinicia la posición vertical para la nueva página.

    # ========== Formato 2 ==========
    # Si no hay datos, escribe un mensaje indicando que no se encontraron datos.
    if not datos:
            c.drawString(x, y, "No existen o no se encontraron datos para los criterios especificados.")
    else:
        # Escribe los datos en el PDF.
        for row in datos:
            cod_medico, nombre, especialidad, turno, lun, mar, mie, jue, vie, exp, num_cita, cod_medico_cita, fecha, hora, modalidad, urgente, estado = row

            tab = " " * 7

            rows = [
                f"{tab}{tab}{tab}Código del Médico: {cod_medico}" + f"{tab}Número de Cita: {num_cita if num_cita else "Sin citas médicas asignadas"}",
                "",
                "MÉDICO:",
                f"{tab}Médico: {cod_medico}",
                f"{tab}Nombre: {nombre}",
                f"{tab}Especialidad: {especialidad}",
                f"{tab}Turno: {turno}",
                f"{tab}Consultas Semanales Disponibles (L-V): {lun}, {mar}, {mie}, {jue}, {vie}",
                f"{tab}Años de experiencia: {exp}",
            ]

            # Mostrar datos sólo si existe una cita asociada al médico, añade los detalles de la cita.
            if num_cita:  
                rows.extend([
                    "",
                    "CITA:",
                    f"{tab}Fecha: {fecha}",
                    f"{tab}Hora: {hora}",
                    f"{tab}Modalidad: {modalidad}",
                    f"{tab}Urgente: {urgente}",
                    f"{tab}Estado: {estado}",
                    "-" * 100,
                    "",
                    "",
                    "",
                    ""
                ])
            
            for row in rows:
                c.drawString(x, y, row) # Escribe la cadena en la posición (x, y) del PDF.
                y -= 18                 # Mueve la posición vertical hacia abajo para la siguiente fila.

                # Si la posición vertical llega al final de la página, crea una nueva página.
                if y < 60:
                    c.showPage()                # Crea una nueva página en el PDF.
                    c.setFont("Helvetica", 14)  # Restablece la fuente y el tamaño de letra.
                    y = 800                     # Reinicia la posición vertical para la nueva página.

    # Guarda el archivo PDF.
    c.save()

    # Mensaje de confirmación de la ejecución por consola.
    print(f"\n\n\tLos datos han sido exportados exitosamente a {pdf_file}")
    
    # Devuelve el nombre del archivo PDF generado.
    return pdf_file
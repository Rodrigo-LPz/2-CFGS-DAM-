🏥 Generador de Informes PDF - Sistema de Gestión Hospitalaria



📋 Descripción del Proyecto
"Generador de Informes PDF/Excel - Py" es una aplicación de escritorio desarrollada en total e integramente en Python que permite consultar, gestionar y exportar información de médicos y citas de un hospital a documentos PDF y Excel. El sistema ofrece una interfaz gráfica intuitiva para visualizar datos almacenados en una base de datos MySQL y generar informes personalizados e interactivos para el usuario según las necesidades del mismo.


✨ Características Principales del Proyecto
  ◈ Visualización de datos  (🔍): Interfaz gráfica con tabla interactiva que muestra médicos y sus citas asociadas/correspondientes.
  ◈ Exportación a PDF       (📄): Generación de informes profesionales en formato "PDF" con diseño elegante.
  ◈ Exportación a Excel     (📊): Generación de hojas de cálculo, "Excel", con formato profesional, filas alternadas, paneles congelados y estilos avanzados
  ◈ Filtrado flexible       (🎯): Opciones de exportación para exportar todos y cada uno de los datos o médicos específicos.
  ◈ Organización automática (📁): Reconocimiento del sistema operativo o SO y creación automática de carpeta para almacenar informes.
  ◈ Diseño moderno          (🎨): Interfaz gráfica con "Tkinter" y diseño "responsive".
  ◈ Consultas optimizadas   (🔄): Uso de sentencias "(LEFT) JOIN" para relacionar información de 'MEDICOS' y 'CITAS' y uso de "ORDER BY" para ordenar los datos obtenidos recuperados de la "DB", de la base de datos.


✨ Estructura del Proyecto
  ◊ Antes de Ejecutar (previo a la ejecución):
      Generador de Informes PDF - Py/
      │
      ├── Legal information/
      │   ├── READ.md           # Información sobre el proyecto (de que trata, como usarlo, licencia mejoras, etc.).
      │   └── LICENSE           # Información y advertencia de usos legales sobre el producto.
      │
      ├── Script DB - SQL/
      │   └── hospitalCitas.sql # Script de creación de base de datos.
      │
      ├── venv/                 # Entorno virtual de Python (opcional).
      │   ├── .../
      │   └── ...
      │
      ├── db_connection.py      # Módulo de conexión a MySQL.
      ├── export_pdf.py         # Módulo de exportación a PDF.
      ├── export_excel.py       # Módulo de exportación a Excel.
      └── main.py               # Interfaz gráfica principal.

  ◊ Antes de Ejecutar (previo a la ejecución):
      Generador de Informes PDF - Py/
      │
      ├── __pycache__/                              # Archivos compilados de Python auto-generados (autogeneración).
      │   ├── db_connection.cpython-313.pyc
      │   └── export_pdf.cpython-313.pyc
      │
      ├── Informes PDF impresos/                    # Carpeta de informes PDF exportados.
      │   ├── informe_medicos_citas_completo.pdf
      │   ├── informe_medico_MED-010.pdf
      │   └── informe_medicos_MED-001_a_MED-005.pdf
      │
      ├── Informes Excel generados/                 # Carpeta de informes Excel exportados.
      │   ├── informe_medicos_citas_completo.xlsx
      │   ├── informe_medico_MED-010.xlsx
      │   └── informe_medicos_MED-001_a_MED-005.xlsx
      │
      ├── Legal information/
      │   ├── READ.md                               # Información sobre el proyecto (de que trata, como usarlo, licencia mejoras, etc.).
      │   └── LICENSE                               # Información y advertencia de usos legales sobre el producto.
      │
      ├── Script DB - SQL/
      │   └── hospitalCitas.sql # Script de creación de base de datos.
      │
      ├── venv/                                     # Entorno virtual de Python (opcional)
      │   ├── .../
      │   └── ...
      │
      ├── db_connection.py                          # Módulo de conexión a MySQL
      ├── export_pdf.py                             # Módulo de exportación a PDF.
      ├── export_excel.py                           # Módulo de exportación a Excel.
      └── main.py                                   # Interfaz gráfica principal.


🛠️ Tecnologías Utilizadas
  ◆ Backend
    ◇ Python 3.13             - Lenguaje de programación principal.
    ◇ MySQL 8.0+              - Sistema de gestión de base de datos.
    ◇ MySQL Connector/Python  - Conector para Python-MySQL.

  ◆ Frontend
    ◇ Tkinter                 - Biblioteca para interfaz gráfica.
    ◇ ttk                     - Widgets temáticos de Tkinter.

  ◆ Librerías de Datos
    ◇ Pandas                  - Manipulación y análisis de datos.
    ◇ ReportLab               - Generación de documentos PDF.
    ◇ openpyxl                - Generación y manipulación de archivos Excel.

  ◆ Base de Datos
    ◇ MySQL Workbench         - Herramienta de administración (opcional).


📦 Requisitos Previos
  ◆ Software Necesario
    1. Python 3.10 o superior.
      ▫ Descarga: python.org      ↦ (https://www.python.org/downloads/)
      ▫ Verificar instalación     ↦ python --version (EJECUTAR en la Terminal y/o Windows PowerShell)

    2. MySQL Server 8.0 o superior
      ▫ Descarga: mysql.com       ↦ (https://dev.mysql.com/downloads/mysql/)
      ▫ Verificar instalación     ↦ mysql --version (EJECUTAR en la Terminal y/o Windows PowerShell)

    3. MySQL Workbench (Opcional, pero recomendado)
      ▫ Descarga: MySQL Workbench ↦ (https://dev.mysql.com/downloads/workbench/)

  ◆ Librerías de Python (EJECUTAR en la Terminal y/o Windows PowerShell) [+Recomendable]
    ▫ pip install mysql-connector-python
    ▫ pip install pandas
    ▫ pip install reportlab
    ▫ pip install openpyxl

  ◆ Librerías de Python (EJECUTAR en la Terminal y/o Windows PowerShell) [-Recomendable]
    ▫ pip install -r requirements.txt
    ◇ Contenido de ("requirements.txt"):
      ▫ mysql-connector-python==8.0.33
      ▫ pandas==2.0.3
      ▫ reportlab==4.0.4


⚙️ Configuración e Instalación
  1. Paso 1: Clonar o Descargar el Proyecto
    # Si usas Git:
      git clone https://github.com/Rodrigo-LPz/Generador-de-Informes.git
      cd Generador-de-Informes-PDF

    # O también puedes descargar el ZIP y extraer su contenido.

  2. Crear Entorno Virtual (Recomendado)
    # En Windows:
      python -m venv venv
      venv\Scripts\activate

    # En Linux/Mac:
      python3 -m venv venv
      source venv/bin/activate

  3. Instalar Dependencias
      pip install mysql-connector-python pandas reportlab openpyxl

  4. Configurar Base de Datos "MySQL"
    1º Iniciar MySQL Server
      # Windows (si está en servicios):
        net start MySQL80
      
      # O también puede realizarse abriendo MySQL Workbench y conectándose.

    2º Ejecutar el 'Script' de Base de Datos
      # Opción A - Desde MySQL Workbench:
        2.1º Abrir MySQL Workbench
        2.2º Conectar al servidor local
        2.3º File > Open SQL Script > Script DB - SQL/hospitalCitas.sql
        2.4º Ejecutar [icono de rayo (⚡) o Ctrl+Shift+Enter]

      # Opción B - Desde línea de comandos: (EJECUTAR en la Terminal y/o Windows PowerShell)
        mysql -u root -p < "Script DB - SQL/hospitalCitas.sql"
          # Ingresa tu contraseña cuando se solicite.

    3º Verificar que la base de datos se creó correctamente
          SHOW DATABASES;  -- Resultado: Debe aparecer 'HOSPITAL'.
          USE HOSPITAL;
          SHOW TABLES;     -- Resultado: Debe mostrar 'MEDICOS' y 'CITAS'.

  5. Configurar Credenciales de Conexión
      # Editar el contendio del archivo "db_connection.py" con tus credenciales de "MySQL":
        host_name = "localhost"     # Dirección del servidor.
        db_name = "HOSPITAL"        # Nombre de la base de datos.
        user_name = "root"          # Tu usuario de MySQL.
        user_password = "root"      # Tu contraseña de MySQL.


🚀 Ejecución del Programa
  ◆ Método 1: Desde la Terminal
    # Asegúrate de estar en el directorio del proyecto.
      python main.py

  ◆ Método 2: Desde un IDE
      2.1º Abrir el proyecto en tu IDE favorito (VS Code, PyCharm, etc.)
      2.2º Ejecutar el archivo "main.py"

  ◆ Método 3: Doble clic (Windows)
      3.1º Crear un archivo "ejecutar.bat" con el siguiente contenido:
        @echo off
        python main.py
        pause

      3.2º Hacer doble clic en "ejecutar.bat".


📖 Manual de Usuario
  ◈ Interfaz Principal
    Al ejecutar el programa, verás una ventana con dos botones principales:

    1. 🔄 Botón "Cargar Datos"
      - Función: Consulta la base de datos y muestra todos los médicos y citas correspondientes en la tabla.
      - Uso: Hacer clic para actualizar/cargar los datos.
      - Resultado: La tabla se llena con toda la información disponible.

    2. ⏬ Botón "Exportar Datos"
      - Función: Abre una ventana con tipos de formato y opciones de exportación.
      - Uso: Hacer clic para elegir de qué tipo será la exportación y cómo exportar los datos.
      
      - Resultado: Ventana de Opciones de Exportación.
        2.1. Opción 1: 📄 Exportar Todos los Médicos y Citas
          2.1.1. Opción de formato 1: 📄 PDF
            Genera un archivo de extensión ".pdf" con el informe completo con todos los médicos del hospital. Incluye todas las citas asociadas a cada médico.
            Resultado del archivo generado: "informe_medicos_citas_completo.pdf".

          2.1.2. Opción de formato 1: 📊 Excel
            Genera un archivo de extensión ".xlsx" con el informe completo con todos los médicos del hospital. Incluye todas las citas asociadas a cada médico.
            Resultado del archivo generado: "informe_medicos_citas_completo.xlsx".

        2.2. Opción 2: 📊 Exportar Médico(s) Específico(s)
          Posibles resultados según los datos ingresados en los campos de entrada:
          2.2.1. Código Médico Inicial (Obligatorio)
            ▪ Formato: MED-XXX
              ▫ Ejemplo: MED-010

          2.2.2. Código Médico Final (Opcional)
            ▪ Dejar vacío para exportar desde el inicial hasta el final del listado. O marcar/delimitar el final. O volver a poner el mismo número de identificación (ID) que el puesto en el parámetro inicial para imprimir sólo ese médico.
              ▫ Ejemplo: ""
              ▫ Ejemplo: MED-013
              ▫ Ejemplo: MED-010

              Resultados (tabla):
                Inicial       Final       Resultado
                MED-010                   Exporta todos los médicos desde "MED-010" hasta el último médico del listado.
                MED-010       MED-013     Exporta todos los médicos desde "MED-005" hasta "MED-010".
                MED-010       MED-010     Exporta solo el médico "MED-001".

            Opciones/Botones de formato:
              📄 Exportar a PDF: Genera el informe en formato de extensión ".pdf".
              📊 Exportar a Excel: Genera el informe en formato de extensión ".xlsx".


📄 Formato de los Informes PDF (Cada informe incluye)
  ◈ Encabezado
    - Título descriptivo según el tipo de exportación.
    - Fecha de generación (implícita).

  ◈ Información por Médico = ("MEDICO")
    - Código del Médico = ("COD_MEDICO").
    - Nombre Completo = ("NOMBRE_COMPLETO").
    - Especialidad = ("ESPECIALIDAD").
    - Turno (Mañana/Tarde/Noche) = ("TURNO").
    - Consultas Disponibles por día de la semana (de Lunes a Viernes) = ("CONSULTAS_DISPONIBLES_LUNES", "CONSULTAS_DISPONIBLES_MARTES", "CONSULTAS_DISPONIBLES_MIERCOLES", "CONSULTAS_DISPONIBLES_JUEVES" y "CONSULTAS_DISPONIBLES_VIERNES").
    - Años de Experiencia = ("ANOS_EXPERIENCIA").

  ◈ Información de Citas (si existen) = ("CITAS")
    - Número de Cita = ("NUM_CITA").
    - Fecha (formato: YYYY-MM-DD) = ("FECHA_CITA").
    - Hora (formato: HH:MM:SS) = ("HORA_CITA").
    - Modalidad (Presencial/Telemedicina) = ("MODALIDAD").
    - Urgente (Sí/No) = ("URGENTE").
    - Estado (Pendiente/Realizada/Cancelada) = ("ESTADO").

  ◈ Características del PDF
    - Paginación automática.
    - Separadores visuales entre registros.
    - Formato profesional y legible.
    - Tamaño de página A4.
    - Tipografía Helvetica clara.

📊 Formato de los Informes Excel (Cada informe incluye)
  ◈ Tablas
    - Fila 1: Título del informe (celdas combinadas A1:Q1)
    - Fila 3: Encabezados de columnas (con estilos)
    - Fila 4+: Datos de médicos y citas

  ◈ Información por Médico = ("MEDICO")
    - Código del Médico = ("COD_MEDICO").
    - Nombre Completo = ("NOMBRE_COMPLETO").
    - Especialidad = ("ESPECIALIDAD").
    - Turno (Mañana/Tarde/Noche) = ("TURNO").
    - Consultas Disponibles por día de la semana (de Lunes a Viernes) = ("CONSULTAS_DISPONIBLES_LUNES", "CONSULTAS_DISPONIBLES_MARTES", "CONSULTAS_DISPONIBLES_MIERCOLES", "CONSULTAS_DISPONIBLES_JUEVES" y "CONSULTAS_DISPONIBLES_VIERNES").
    - Años de Experiencia = ("ANOS_EXPERIENCIA").

  ◈ Información de Citas (si existen) = ("CITAS")
    - Número de Cita = ("NUM_CITA").
    - Fecha (formato: YYYY-MM-DD) = ("FECHA_CITA").
    - Hora (formato: HH:MM:SS) = ("HORA_CITA").
    - Modalidad (Presencial/Telemedicina) = ("MODALIDAD").
    - Urgente (Sí/No) = ("URGENTE").
    - Estado (Pendiente/Realizada/Cancelada) = ("ESTADO").

  ◈ Características de las tablas
    - Entablado automático.
    - Separadores visuales entre tablas.
    - Formato profesional y legible.
    - Ancho de columnas ajustado automáticamente al contenido
    - Paneles congelados (encabezados fijos al desplazarse).
    - Alineación centrada en encabezados y a la izquierda en datos.


🗃️ Estructura de la Base de Datos
  ◈ Tabla: MEDICOS
    Tabla:
      Campo                               Tipo              Descripción
      COD_MEDICO                          VARCHAR(10)       Código único del médico (PK).
      NOMBRE_COMPLETO                     VARCHAR(80)       Nombre y apellidos.
      ESPECIALIDAD                        VARCHAR(30)       Especialidad médica.
      TURNO                               VARCHAR(10)       Tipo de turno 'MAÑANA', 'TARDE' o 'NOCHE'.
      CONSULTAS_DISPONIBLES_LUNES         INT(3)            Consultas disponibles el lunes.
      CONSULTAS_DISPONIBLES_MARTES        INT(3)            Consultas disponibles el martes.
      CONSULTAS_DISPONIBLES_MIERCOLES     INT(3)            Consultas disponibles el miércoles.
      CONSULTAS_DISPONIBLES_JUEVES        INT(3)            Consultas disponibles el jueves.
      CONSULTAS_DISPONIBLES_VIERNES       INT(3)            Consultas disponibles el viernes.
      ANOS_EXPERIENCIA                    INT(2)            Años de experiencia profesional.

  ◈ Tabla: CITAS
    Tabla:
      Campo               Tipo                Descripción
      NUM_CITA            INT(7)              Número de cita (PK compuesta).
      COD_MEDICO          VARCHAR(10)         Código del médico (PK compuesta, FK).
      FECHA_CITA          DATE                Fecha de la cita.
      HORA_CITA           TIME                Hora de la cita.
      MODALIDAD           VARCHAR(15)         La consulta/cita es 'PRESENCIAL' o 'TELEMEDICINA'.
      URGENTE             VARCHAR(2)          Es urgente 'SI' o 'NO'.
      ESTADO              VARCHAR(15)         Estado de la consulta/cita 'PENDIENTE', 'REALIZADA' o 'CANCELADA'.

  ◈ Relaciones entre tablas y campos de estas
    - CITAS.COD_MEDICO → MEDICOS.COD_MEDICO (FK)
    - Relación 1:N (Un médico puede tener múltiples citas)


🧩 Arquitectura del Sistema
  ◈ Representación gráfica de los módulos del proyecto:
    ┌─────────────────────────────────────────────────┐
    │                   main.py                       │
    │  (Interfaz Gráfica - Tkinter)                   │
    │  • Ventana principal                            │
    │  • Botones de acción                            │
    │  • Tabla Treeview                               │
    └─────────────┬──────────────────┬────────────────┘
                  │                  │
                  ▼                  ▼
        ┌──────────────────┐  ┌──────────────────┐
        │ db_connection.py │  │  export_pdf.py   │
        │                  │  │  export_excel.py │
        │                  │  │                  │
        │ • Conexión MySQL │  │ • Generación PDF │
        │ • Gestión errores│  │ • Formato docs   │
        └─────────┬────────┘  └────────┬─────────┘
                  │                    │
                  ▼                    ▼
          ┌──────────────┐     ┌──────────────┐
          │ MySQL Server │     │  ReportLab   │
          │   (HOSPITAL) │     │   Library    │
          │              │     │   openpyxl   │
          └──────────────┘     └──────────────┘

  ◈ Flujo de Datos (en simulación ejecución) de los módulos del proyecto:
    1. Usuario → Hace clic en el botón "Cargar Datos".
    2. "main.py" → Llama a la función/método "consultarDatos()".
    3. "db_connection.py" → Establece conexión con "MySQL".
    4. "Pandas" → Ejecuta la consulta "SQL" y obtiene los datos.
    5. "main.py" → Muestra datos en "Treeview".
    
    6. Usuario → Hace clic en el botón "Exportar Datos"
    7. "main.py" → Abre la ventana emergente auxiliar/secundaria de los tipos de formato y opciones de exportación.
    
      8. Usuario → Selecciona un tipo de formato y un tipo de exportación.
      9. "export_pdf.py"/"export_excel.py" → Realiza la consulta (filtrada o completa) a la base de datos.
      10. "ReportLab"/"openpyxl" → Genera archivo de extension ".pdf" o ".xlsx" según el tipo de formato seleccionado.
      11. SO → Guarda la extracción de datos en la carpeta "Informes PDF impresos"
      12. Usuario → Recibe confirmación con ubicación del archivo.


🎨 Diseño de la Interfaz
  ◈ Paleta de Colores empleados
    Tabla:
      Elemento                Tipo                Código Hex
      Fondo Principal         Azul Oscuro       #2C3E50
      Fondo Secundario        Azul Grisáceo     #34495E
      Botón Cargar            Verde             #27AE60
      Botón PDF               Rojo              #E74C3C
      Botón Excel             Verde             #27AE60
      Botón Exportar          Azul              #3498DB
      Botón Cancelar          Gris              #95A5A6
      Texto                   Blanco            #FFFFFF

  ◈ Tipografía
    Fuente Principal: Arial y Helvetica
    Tamaños: 8pt (footer), 10pt (normal), 12pt (botones), 14pt-16pt (títulos)

  ◈ Resultado visual
    ┌─────────────────────────────────────────┐
    │   🔄 Cargar Datos   |   ⏬ Exportar    │
    ├─────────────────────────────────────────┤
    │                                         │
    │             [TABLA DE DATOS]            │
    │                                         │
    ├─────────────────────────────────────────┤
    │                                         │
    │ [Footer (desarollador, derechos, etc.)] │
    │                                         │
    └─────────────────────────────────────────┘


🚀 Mejoras Futuras
  ◈ Envío de informes por email               (📧).
  ◈ Gráficos estadísticos en PDF/Excel        (📊).
  ◈ Sistema de autenticación de usuarios      (🔐).
  ◈ Versión web con Flask/Django              (📱).
  ◈ API REST para consultas                   (🌐).
  ◈ Filtrado por fechas y rangos temporales   (📅).
  ◈ Sistema de notificaciones de citas        (🔔).
  ◈ Exportación a CSV adicional               (💾).
  ◈ Temas personalizables de interfaz         (🎨).
  ◈ Respaldo automático de base de datos      (🗄️).
  ◈ Dashboard con estadísticas visuales       (📈).
  ◈ Búsqueda avanzada por múltiples criterios (🔍).


👨🏻‍💻 Desarrollo y Mantenimiento
  ◈ Autor
    🧑🏻 Desarrollador: [Rodrigo López Pérez]
    📆 Fecha de Creación: Diciembre 2025
    👀 Versión Actual: 4.0.0

  ◈ Contacto
  📧 Email: rodrigo.lop.per@gmail.com
  🐙 GitHub: @Rodrigo-LPz
     Repositorio de este proyecto (https://github.com/Rodrigo-LPz/Generador-de-Informes).


📜 Licencia
  ◈ Texto informativo, legalidad del programa.
    Este proyecto está bajo la Licencia MIT. Consulta el archivo "LICENSE" para más detalles.
      Se concede permiso, de forma gratuita, a cualquier persona que obtenga una copia de este software y los archivos de documentación asociados (el "Software"), para usar el Software sin restricciones, incluyendo sin limitación los derechos de usar, copiar, modificar, fusionar, publicar, distribuir, sublicenciar y/o vender copias del Software, y permitir a las personas a quienes se les proporcione el Software hacer lo mismo, sujeto a las siguientes condiciones:

      El aviso de copyright anterior y este aviso de permiso se incluirán en todas las copias o partes sustanciales del Software.

      El software se proporciona "tal cual", sin garantía de ningún tipo, expresa o implícita, incluyendo pero no limitado a las garantías de comerciabilidad, idoneidad para un propósito particular y de no infracción. en ningún caso los autores o titulares del copyright serán responsables de ninguna reclamación, daños u otras responsabilidades, ya sea en una acción de contrato, agravio o de otro modo, que surja de, fuera de o en conexión con el software o el uso u otros tratos en el software.

  ◈ Derechos Reservados.
    Gestor de Informes ~ RODRISTARK.GAME$17
      © 2025 | ® Marca Registrada | ™ Producto Original

        - El nombre y logotipo son propiedad intelectual.
        - Diseño y desarrollo exclusivo.
        - Derechos de imagen respetados.

  ◈ Términos de Uso.
    ● Acciones aprobadas - Permitido: ✅
      ○ Uso personal y educativo.
      ○ Modificación del código fuente.
      ○ Distribución con atribución adecuada.
      ○ Uso comercial con licencia MIT.

    ● Acciones terminantemente prohibidas - No permitido: ❌
      ○ Eliminación de avisos de copyright
      ○ Representación falsa de autoría.
      ○ Uso del nombre/marca sin autorización.
      ○ Distribución sin incluir la licencia.


📝 Notas Adicionales
  ◆ Datos de Prueba
    ◇ 13 médicos de diferentes especialidades.
    ◇ 49 citas distribuidas entre los 13 médicos.
    ◇ Datos realistas para pruebas completas.

  ◆ Rendimiento
    ◇ Consultas optimizadas con "LEFT JOIN".
    ◇ Caché de "bytecode Python" para ejecución rápida.
    ◇ Generación de PDF, archivo de extensión ".pdf", eficiente con "ReportLab".

  ◆ Compatibilidad
    ◇ 💻 Windows 10/11.
    ◇ 🍎 macOS 10.15+.
    ◇ 🐧 Linux (Ubuntu 20.04+).
    ◇ 🐍 Python 3.10 - 3.13.


🆚 Diferencias PDF vs Excel
    Tabla:
      Característica        PDF                     Excel
      Editable              No ❌                   Sí ✅
      Tamaño archivo        Menor                   Mayor
      Impresión             ⭐⭐⭐⭐⭐           ⭐⭐⭐
      Análisis datos        ⭐⭐                  ⭐⭐⭐⭐⭐
      Seguridad             ⭐⭐⭐⭐⭐           ⭐⭐⭐
      Mejor para            Informes oficiales      Procesamiento datos


<hr>  
¡Gracias por usar Generador de Informes - Sistema de Gestión Hospitalaria! 🏥📄
Si encuentras algún problema o tienes sugerencias, no dudes en abrir un issue en GitHub.











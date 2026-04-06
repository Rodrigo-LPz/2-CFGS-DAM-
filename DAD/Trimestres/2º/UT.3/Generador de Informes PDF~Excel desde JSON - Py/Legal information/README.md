🕵🏻‍♂️ Buscador de Productos - DummyJSON - PDF~Excel - Sistema de Gestión de Almacenamiento sobre Diccionarios/Listas de Objetos



📋 Descripción del Proyecto
"Buscador de Productos - DummyJSON - PDF~Excel - Py" es una aplicación de escritorio desarrollada en total e integramente en Python que permite consultar/buscar, visualizar y exportar información de productos desde la API de DummyJSON a documentos PDF y Excel. El sistema ofrece una interfaz gráfica intuitiva para visualizar datos/objetos almacenados en tablas interactivas para gestionar productos y generar informes personalizados según las necesidades del usuario.


✨ Características Principales del Proyecto
  ◈ Consumo de API REST     (📡): Conexión con DummyJSON para obtener datos de productos en tiempo real.
  ◈ Visualización de datos  (📋): Interfaz gráfica con tabla interactiva que muestra productos con toda su información
  ◈ Búsqueda parametrizada  (🔍): Filtrado de productos por término de búsqueda.
  ◈ Exportación a PDF       (📄): Generación de informes profesionales en formato PDF con diseño elegante.
  ◈ Exportación a Excel     (📊): Generación de hojas de cálculo con formato profesional, estilos avanzados y datos editables.
  ◈ Organización automática (📁): Creación automática de carpetas "Informes PDF impresos" y "Informes Excel generados" para almacenar documentos con sus respectivos datos.
  ◈ Diseño moderno          (🎨): Interfaz gráfica con Tkinter y paleta de colores profesional.
  ◈ Carga asíncrona         (⚡): Indicadores visuales durante la carga de datos desde la API.


✨ Estructura del Proyecto
  ◊ Antes de Ejecutar (previo a la ejecución):
      Generador de Informes PDF~Excel desde JSON - Py/
      │
      ├── Legal information/
      │   ├── READ.md           # Información sobre el proyecto (de que trata, como usarlo, licencia mejoras, etc.).
      │   └── LICENSE           # Información y advertencia de usos legales sobre el producto.
      │
      ├── venv/                 # Entorno virtual de Python (opcional).
      │   ├── .../
      │   └── ...
      │
      ├── api_connection.py     # Módulo de conexión a la API de DummyJSON.
      ├── export_pdf.py         # Módulo de exportación a PDF.
      ├── export_excel.py       # Módulo de exportación a Excel.
      └── main.py               # Interfaz gráfica principal.

  ◊ Después de Ejecutar (previo a la ejecución):
      Generador de Informes PDF~Excel desde JSON - Py/
      │
      ├── __pycache__/                              # Archivos compilados de Python auto-generados (autogeneración).
      │   ├── api_connection.cpython-313.pyc
      │   ├── export_excel.cpython-313.pyc
      │   └── export_pdf.cpython-313.pyc
      │
      ├── Informes Excel generados/                    # Carpeta de informes exportados.
      │   ├── Informe_productos_DummyJSON_dog_2025-12-14_13-34-06.xlsx
      │   └── Informe_todos_los_productos_DummyJSON_2025-12-14_13-21-05.xlsx
      │
      ├── Informes PDF impresos/                    # Carpeta de informes exportados.
      │   ├── Informe_productos_DummyJSON_dog_2025-12-14_13-34-03.pdf
      │   └── Informe_todos_los_productos_DummyJSON_2025-12-02_22-54-17.pdf
      │
      ├── Legal information/
      │   ├── READ.md                               # Información sobre el proyecto (de que trata, como usarlo, licencia mejoras, etc.).
      │   └── LICENSE                               # Información y advertencia de usos legales sobre el producto.
      │
      ├── venv/                                     # Entorno virtual de Python (opcional)
      │   ├── .../
      │   └── ...
      │
      ├── api_connection.py                         # Módulo de conexión a la API de DummyJSON.
      ├── export_pdf.py         # Módulo de exportación a PDF.
      ├── export_excel.py       # Módulo de exportación a Excel.
      └── main.py               # Interfaz gráfica principal.


🛠️ Tecnologías Utilizadas
  ◆ Backend
    ◇ Python 3.13       - Lenguaje de programación principal.
    ◇ Requests          - Biblioteca para realizar peticiones HTTP a la API

  ◆ Frontend
    ◇ Tkinter           - Biblioteca para interfaz gráfica.
    ◇ ttk               - Widgets temáticos de Tkinter.

  ◆ Librerías de Datos
    ◇ ReportLab         - Generación de documentos PDF.
    ◇ openpyxl          - Generación y manipulación de archivos Excel.

  ◆ API Externa
    ◇ DummyJSON         - API REST para obtener datos de productos de prueba.


📦 Requisitos Previos
  ◆ Software Necesario
    1. Python 3.10 o superior.
      ▫ Descarga: python.org      ↦ (https://www.python.org/downloads/)
      ▫ Verificar instalación     ↦ python --version (EJECUTAR en la Terminal y/o Windows PowerShell)

  ◆ Librerías de Python: Instalación individual (EJECUTAR en la Terminal y/o Windows PowerShell) [+Recomendable]
    ▫ pip install requests
    ▫ pip install reportlab
    ▫ pip install openpyxl

  ◆ Librerías de Python: Usando "requirements.txt" (EJECUTAR en la Terminal y/o Windows PowerShell) [-Recomendable]
    ▫ pip install -r requirements.txt
    ◇ Contenido de ("requirements.txt"):
      ▫ requests==2.31.0
      ▫ reportlab==4.0.4


⚙️ Configuración e Instalación
  1. Paso 1: Clonar o Descargar el Proyecto
    # Si usas Git:
      git clone https://github.com/Rodrigo-LPz/Generador-de-Informes-desde-JSON.git
      cd Generador-de-Informes-desde-JSON

    # O también puedes descargar el ZIP y extraer su contenido manualmente.

  2. Crear Entorno Virtual (Recomendado)
    # En Windows:
      python -m venv venv
      venv\Scripts\activate

    # En Linux/Mac:
      python3 -m venv venv
      source venv/bin/activate

  3. Instalar Dependencias
      pip install requests reportlab


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
    Al ejecutar el programa, verás una ventana con cuatro botones principales:

    1. 🔍 Botón "Buscar por término"
      - Función: Busca productos específicos según el término ingresado.
      - Uso (Cuando el usuario previamente ha introducido un parámetro en el campo de entrada): Escribe el término en el campo de búsqueda (ej: "phone", "laptop", "beauty"). Después, simplemente haz clic en el botón
      - Resultado: La tabla muestra solo los productos que coinciden con la búsqueda.

    2. ⏬ Botón "Cargar todos los productos"
      - Función: Obtiene y muestra todos los productos disponibles en la API.
      - Uso: Simplemente haz clic en el botón.
      - Resultado: La tabla se llena con aproximadamente 30 productos (esto variará en función de la cantidad de entidades, objetos, que haya almacenados en la lista antes de ser tratada).

    3. 📄 Botón "Exportar a PDF"
      - Función: Genera un informe PDF con los productos actualmente visualizados.
      - Uso: Simplemente haz clic en el botón.

      - Resultado: Se crea un documento PDF en la carpeta "Informes PDF impresos" (creada al crer el archivo si esta no existía previamente) y después imprime un mensaje con la ubicación del archivo.
        3.1. Opción 1: Si hemos hecho una consulta o búsqueda parametrizada el nombre del archivo incuirá al final de este la palabra clave por la que se hizo una búsqueda/consulta de procustos más reducida (ejemplo con "Phone") "Informe_productos_DummyJSON_dog_+fechaActual".pdf.

        3.2. Opción 2: Si hemos hecho una consulta o búsqueda global, de todos los productos almacenados en la lista el nombre del archivo no se verá afectado "Informe_todos_los_productos_DummyJSON_+fechaActual".pdf.

    4. 📊 Botón "Exportar a Excel"
      - Función: Genera un informe Excel con los productos actualmente visualizados.
      - Uso: Simplemente haz clic en el botón.

      - Resultado: Se crea un documento Excel en la carpeta "Informes Excel generados" (creada al crer el archivo si esta no existía previamente) y después imprime un mensaje con la ubicación del archivo.
        3.1. Opción 1: Si hemos hecho una consulta o búsqueda parametrizada el nombre del archivo incuirá al final de este la palabra clave por la que se hizo una búsqueda/consulta de procustos más reducida (ejemplo con "Phone") "Informe_productos_DummyJSON_dog_+fechaActual.xlsx".

        3.2. Opción 2: Si hemos hecho una consulta o búsqueda global, de todos los productos almacenados en la lista el nombre del archivo no se verá afectado "Informe_todos_los_productos_DummyJSON_+fechaActual.xlsx".


📄 Formato de los Informes PDF (Cada informe incluye)
  ◈ Encabezado
    - Título descriptivo según el tipo de exportación.
    - Subtítulo que apocha al título y especificar porque producto se ha filtrado la lista de productos.
    - Fecha de generación (implícita).

  ◈ Nudo
    - Información por producto (campos utilizados)= ("products")
      - ID = ("id").
      - Nombre = ("title").
      - Categoría = ("category").
      - Marca = ("brand").
      - Precio $ (USD) = ("price").
      - Descuento (%) = ("discountPercentage").
      - Stock = ("stock").
      - Valoración (0-5) = ("rating").
      - Descripción = ("description").

  ◈ Pie de página
    - Total de productos incluidos en el informe.
    - Información del generador.

  ◈ Características del PDF
    - Paginación automática.
    - Separadores visuales entre productos.
    - Formato profesional y legible.
    - Tamaño de página A4.
    - Tipografía Helvetica clara.


📄 Formato de los Informes Excel (Cada informe incluye)
  ◈ Tabla
    - Fila 1: Título del informe (celdas combinadas A1:I1)
    - Fila 2: Subtítulo con fecha (celdas combinadas A2:I2)
    - Fila 4: Encabezados de columnas (con estilos)
    - Fila 5+: Datos de productos
    - Última fila: Pie de página con el número total de productos

  ◈ Nudo
    - Información por producto (campos utilizados) = ("products")
      - ID = ("id").
      - Nombre = ("title").
      - Categoría = ("category").
      - Marca = ("brand").
      - Precio ($) = ("price").
      - Descuento (%) = ("discountPercentage").
      - Stock = ("stock").
      - Valoración (0-5) = ("rating").
      - Descripción = ("description").

  ◈ Pie de página
    - Última fila: Pie de página con el número total de productos
      - Total de productos incluidos en el informe.
    - Información del generador.

  ◈ Características del PDF
    - Entablado automático.
    - Separadores visuales entre tablas, entre productos.
    - Formato profesional y legible.
    - Diferenciación y adición de título y subtítulo.
    - Ancho de columnas ajustado automáticamente al contenido
    - Paneles congelados (encabezados fijos al desplazarse).
    - Alineación centrada en encabezados y a la izquierda en datos.
    - Tipografía Helvetica clara.


🌐 Información sobre la API
  ◈ DummyJSON
    ● URL Base: https://dummyjson.com-
    ● Endpoint's (punto de acceso al servicio DummyJSON) usados: "/products" y "/products/search"-
    ● Método: "GET".
    ● Respuesta: "JSON" con array de productos.


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
        ┌────────────────────┐  ┌─────────────────────┐
        │ api_connection.py  │  │  export_pdf.py      │
        │                    │  │  export_excel.py    │
        │                    │  │                     │
        │                    │  │ • Generación PDF    │
        │                    │  │ • Generación Excel  │
        │ • Peticiones HTTP  │  │ • Formato docs      │
        │ • Procesamiento    │  │ • Formato excel     │
        └─────────┬──────────┘  └──────────┬──────────┘
                  │                        │
                  ▼                        ▼
          ┌───────────────┐        ┌───────────────┐
          │ DummyJSON API │        │ ReportLab     │
          │   (REST)      │        │   openpyxl    │
          └───────────────┘        └───────────────┘
          
  ◈ Flujo de Datos (en simulación ejecución) de los módulos del proyecto:
    ◆ Caso 1: Cargar todos los productos
      1. Usuario → Clic en "Cargar todos los productos"
      2. "main.py" → Llama a "cargar_todos_productos()"
      3. "api_connection.py" → Petición GET a https://dummyjson.com/products
      4. DummyJSON API → Responde con un JSON de 30 productos
      5. "api_connection.py" → Procesa y filtra datos necesarios
      6. "main.py" → Muestra productos en Treeview
      7. Usuario → Ve los 30 productos en la tabla
    
    ◆ Caso 2: Buscar y exportar a PDF
      1. Usuario → Escribe "laptop" y clic en "Buscar"
      2. "api_connection.py" → GET a /products/search?q=laptop
      3. DummyJSON API → Devuelve productos filtrados
      4. "main.py" → Muestra resultados en tabla
      5. Usuario → Clic en "Exportar a PDF"
      6. "export_pdf.py" → Genera un informe PDF con todos los productos actuales del filtrado
      7. ReportLab → Crea archivo PDF
      8. Sistema Operativo → Guarda en "Informes PDF impresos/"
      9. Usuario → Recibe confirmación con ubicación del archivo generado
    
    ◆ Caso 3: Buscar y exportar a Excel
      1. Usuario → Escribe "laptop" y clic en "Buscar"
      2. api_connection.py → GET a /products/search?q=laptop
      3. DummyJSON API → Devuelve productos filtrados
      4. main.py → Muestra resultados en tabla
      5. Usuario → Clic en "Exportar a Excel"
      6. "export_pdf.py" → Genera un documeto Excel con todos los productos actuales del filtrado
      7. ReportLab → Crea archivo Excel
      8. Sistema Operativo → Guarda en "Informes Excel generados/"
      9. Usuario → Recibe confirmación con ubicación del archivo generado


🎨 Diseño de la Interfaz
  ◈ Paleta de Colores empleados
    Tabla:
      Elemento                Tipo                Código Hex
      Fondo Principal         Azul Oscuro       #2C3E50
      Fondo Secundario        Azul Grisáceo     #34495E
      Botón Buscar            Azul              #3498DB
      Botón Cargar            Amarillo          #A0AE27
      Botón Exportar PDF      Rojo              #E74C3C
      Botón Exportar Excel    Verde             #27AE60
      Texto                   Blanco            #FFFFFF

  ◈ Tipografía
    Fuente Principal: Arial y Helvetica
    Tamaños: 8pt (footer), 10pt (normal), 11-12pt (botones), 14pt (títulos)

  ◈ Resultado visual
┌─────────────────────────────────────────────────┐
│  Buscar: [______] 🔍 ⬇️ 📄 📊                 │
├─────────────────────────────────────────────────┤
│                                                 │
│         📊 Resultados: 0 productos              │
│                                                 │
│     ┌────────────────────────────────────┐      │
│     │   [TABLA DE PRODUCTOS - TREEVIEW]  │      │
│     │                                    │      │
│     │  ID | Nombre | Categoría | Marca   │      │
│     │  --------------------------------  │      │
│     │  1  | iPhone | smartphones | Apple │      │
│     │  ...                               │      │
│     └────────────────────────────────────┘      │
│                                                 │
├─────────────────────────────────────────────────┤
│  Buscador de Productos - DummyJSON - PDF~Excel  │
│  © 2025 | ® RODRISTARK.GAME$17                  │
└─────────────────────────────────────────────────┘

🚀 Mejoras Futuras
  ◈ Envío de informes por email               (📧).
  ◈ Gráficos estadísticos en PDF/Excel        (📊).
  ◈ Exportación a CSV                         (💾).
  ◈ Caché local de productos                  (🔐).
  ◈ Interfaz gáfica con modo oscuro/claro     (🌙).
  ◈ Notificaciones de nuevos productos        (🔔).
  ◈ Versión responsive para tablets           (📱).
  ◈ Integración con otras APIs de productos   (🌐).
  ◈ Dashboard con estadísticas visuales       (📈).
  ◈ Búsqueda avanzada por múltiples criterios (🔍).


👨🏻‍💻 Desarrollo y Mantenimiento
  ◈ Autor
    🧑🏻 Desarrollador: [Rodrigo López Pérez]
    📆 Fecha de Creación: Diciembre 2025
    👀 Versión Actual: 2.0.1

  ◈ Contacto
  📧 Email: rodrigo.lop.per@gmail.com
  🐙 GitHub: @Rodrigo-LPz
     Repositorio de este proyecto (https://github.com/Rodrigo-LPz/Generador-de-Informes-desde-JSON).


📜 Licencia
  ◈ Texto informativo, legalidad del programa.
    Este proyecto está bajo la Licencia MIT. Consulta el archivo "LICENSE" para más detalles.
      Se concede permiso, de forma gratuita, a cualquier persona que obtenga una copia de este software y los archivos de documentación asociados (el "Software"), para usar el Software sin restricciones, incluyendo sin limitación los derechos de usar, copiar, modificar, fusionar, publicar, distribuir, sublicenciar y/o vender copias del Software, y permitir a las personas a quienes se les proporcione el Software hacer lo mismo, sujeto a las siguientes condiciones:

      El aviso de copyright anterior y este aviso de permiso se incluirán en todas las copias o partes sustanciales del Software.

      El software se proporciona "tal cual", sin garantía de ningún tipo, expresa o implícita, incluyendo pero no limitado a las garantías de comerciabilidad, idoneidad para un propósito particular y de no infracción. en ningún caso los autores o titulares del copyright serán responsables de ninguna reclamación, daños u otras responsabilidades, ya sea en una acción de contrato, agravio o de otro modo, que surja de, fuera de o en conexión con el software o el uso u otros tratos en el software.

  ◈ Derechos Reservados.
    Buscador de Productos - DummyJSON - PDF~Excel ~ RODRISTARK.GAME$17
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
  ◆ Datos de Prueba de la API
    ◇ 30 productos disponibles.
    ◇ 5 categorías principales.
    ◇ Datos realistas para pruebas completas.
    ◇ API gratuita y sin autenticación.

  ◆ Rendimiento
    ◇ Tiempo de carga: aprox. 1-2 segundos.
    ◇ Caché de "bytecode Python" para ejecución rápida.
    ◇ Generación de PDF, archivo de extensión ".pdf", eficiente con "ReportLab".

  ◆ Compatibilidad
    ◇ Windows 10/11.
    ◇ macOS 10.15+.
    ◇ Linux (Ubuntu 20.04+).
    ◇ Python 3.10 - 3.13.

<hr>  
¡Gracias por usar Buscador de Productos - DummyJSON - PDF~Excel - Sistema de Gestión de Almacenamiento sobre Diccionarios/Listas de Objetos! 🕵🏻‍♂️📄
Si encuentras algún problema o tienes sugerencias, no dudes en abrir un issue en GitHub.
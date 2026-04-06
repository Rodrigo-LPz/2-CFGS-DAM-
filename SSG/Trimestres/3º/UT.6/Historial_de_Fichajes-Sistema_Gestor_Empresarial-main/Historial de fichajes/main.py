# main.py
"""
Sistema de Gestión Empresarial - Backend API.
Punto de entrada principal de la aplicación.
"""

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from api import router
from db import init_db, seed_data

# Crea la aplicación FastAPI.
app = FastAPI(
    title="Sistema de Gestión Empresarial",
    description="API para gestión de empleados, fichajes e inventario",
    version="1.0.0"
)

# Configura CORS para permitir peticiones desde la app Android.
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # En producción, especifica los orígenes permitidos.
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.on_event("startup")
def startup_event():
    """
    Se ejecuta una vez cuando arranca la API.
    Inicializa la base de datos e inserta datos de prueba.
    """
    print("🚀 Iniciando Sistema de Gestión Empresarial...")
    init_db()
    seed_data()
    print("✅ Base de datos inicializada correctamente")
    print("📚 Documentación disponible en: http://127.0.0.1:8001/docs")

@app.get("/")
def root():
    """Endpoint raíz - información básica de la API."""
    return {
        "message": "Sistema de Gestión Empresarial API",
        "version": "1.0.0",
        "status": "active",
        "docs": "/docs",
        "modules": ["employees", "punches", "products", "stock_movements", "operations"]
    }

@app.get("/health")
def health_check():
    """Health check - verificar que la API está funcionando."""
    return {"status": "healthy", "service": "Sistema de Gestión Empresarial"}

# Registran todas las rutas definidas en api.py.
app.include_router(router)

# Para ejecutar en desarrollo:
# uvicorn main:app --reload
#
# Para ejecutar en producción:
# uvicorn main:app --host 0.0.0.0 --port 8000
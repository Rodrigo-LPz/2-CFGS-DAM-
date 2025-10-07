# Ejercicio 12: "Crea un programa que simule el funcionamiento de una máquina expendedora que debe permitir al usuario ver los productos disponibles, seleccionar un producto ingresando su código, insertar dinero para pagar, calcular el cambio si es necesario y dispensar el producto, validando en cada paso que el producto existe, que hay stock disponible y que el dinero insertado es suficiente, además de mantener actualizado el inventario y el dinero en la caja después de cada transacción."


# Clase "Producto" para representar cada producto en la máquina expendedora.
class Producto:
    # Método para inicializar los atributos de "Producto".
    def __init__(self, codigo, nombre, precio, stock):
        self.codigo = codigo
        self.nombre = nombre
        self.precio = precio
        self.stock = stock
    
    # Método para mostrar la información del producto.
    def __str__(self):
        return f"[{self.codigo}] {self.nombre} - {self.precio}€ (Stock: {self.stock})"

# Clase "MaquinaExpendedora" para gestionar los productos y las transacciones.
class MaquinaExpendedora:
    # Método para inicializar los atributos de "MaquinaExpendedora".
    def __init__(self):
        # Lista de , ya generados, disponibles en la máquina.
        self.productos ={
            "A1": Producto("A1", "Agua", 1.0, 5),
            "A2": Producto("A2", "Refresco", 1.5, 5),
            "B1": Producto("B1", "Chocolate", 2.0, 5),
            "B2": Producto("B2", "Patatas", 1.8, 5),
            "C1": Producto("C1", "Chicle", 0.5, 10),
        }
        self.dinero_en_caja = 0.0

    # Método para mostrar los productos disponibles.
    def mostrar_productos(self):
        print("\n=== PRODUCTOS DISPONIBLES ===")
        for producto in self.productos.values():
            print(producto)
    
    # Método para seleccionar un producto por su código.
    def seleccionar_producto(self, codigo):
        if codigo in self.productos:
            return self.productos[codigo]
        else:
            print("Error. El código introducido no es válido o no lo reconoce el sistema.")
            return None
        
    # Método para manejar la compra de un producto.
    def comprar(self, codigo, dinero_insertado):
        producto = self.seleccionar_producto(codigo)
        # Caso para cuando no existe tal prodcto.
        if not producto:
            return

        # Caso para cuando tal prodcto esté agotado, no hay existencias.
        if producto.stock <= 0:
            print("Error. Producto agotado.")
            return

        # Caso para cuando el dinero insertado sea insuficiente.
        if dinero_insertado < producto.precio:
            print(f"Error. Cantidad de dinero insuficiente. El {producto.nombre} cuesta {producto.precio}€.")
            return
        
        # Si todo lo demás no se cumple, se ejecuta el caso para cuando la compra se realiza con éxito.
        cambio = round(dinero_insertado - producto.precio, 2)
        producto.stock -= 1
        self.dinero_en_caja += producto.precio
        print(f"Has comprado {producto.nombre}. Tu cambio es {cambio}€.")
        return cambio


# ---------------- PROGRAMA PRINCIPAL ----------------
def menu():
    maquina = MaquinaExpendedora()

    # Bucle de tipo "while" para el menú interactivo.
    while True:
        print("\n<======= MÁQUINA EXPENDEDORA =======>")
        print("1. Ver productos")
        print("2. Comprar producto")
        print("3. Ver dinero en caja (admin)")
        print("0. Salir")

        opcion = input("Selecciona una opción: ")

        # Validación de la opción introducida por el usuario.
        if opcion == "1":
            maquina.mostrar_productos()
        elif opcion == "2":
            codigo = input("Introduce el código del producto: ").upper()
            try:
                dinero = float(input("Introduce el dinero: "))
                maquina.comprar(codigo, dinero)
            except ValueError:
                print("Debes introducir un número válido.")
        elif opcion == "3":
            print(f"Dinero en caja: {maquina.dinero_en_caja}€")
        elif opcion == "0":
            print("Gracias por usar la máquina expendedora. Vuelva pronto con más suerte (y dinero) ;).")
            break
        else:
            print("Error. La opción escogida no es válida o no la reconoce el sistema.")

# ----------------- EJECUCIÓN DEL PROGRAMA -----------------
if __name__ == "__main__":
    menu()
# Ejercicio 6: "Escribir un programa que pregunte el nombre de un producto, su precio y un numero de unidades y muestre por pantalla una cadena con el nombre del producto, el precio unitario y el precio total a pagar por las unidades compradas."

# Bienvenida al usuario y solicitud de datos (nombre, nombre del productos, precio y cantidad del producto).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de productos', por favor ingrese su nombre: ")
# Solicitar el nombre del producto, el precio y la cantidad del producto a comprar.
nombre_producto = (input(f"\nBienvenido a la aplicación {nombre}, ahora introduzca el nombre del poducto a comprar: "))
precio = int(input("\nSeguidamente, introduzca de cúanto es el precio del producto, individual: "))
cantidad = int(input("\nValor guardado/almacenado. Ahora introduzca cuántos va a adquirir/comprar: "))

# Calcular el precio total de la compra.
subtotal_compra = precio * cantidad

# Muestreo del resultado final al usuario.
print(f"\n\nEl producto comprado, {nombre_producto}, tiene un precio base por unidad de {precio}€. El total de la compra dada una cantidad solicitada de {cantidad} unidades por producto da un total de {subtotal_compra}.")
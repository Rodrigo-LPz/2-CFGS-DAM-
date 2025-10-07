# Ejercicio 4: "Una jugueteria tiene mucho exito en dos de sus productos: payasos y muñecas. Suele hacer venta por correo y la empresa de logistica les cobra por peso de cada paquete, asi que deben calcular el peso de los payasos y muñecas que van en cada paquete para poder calcular el precio del envio. Cada payaso pesa 112 g y cada muñeca 75 g. Escribe un programa que lea el numero de payasos y muñecas vendidos en el ultimo pedido y calcule el peso total del paquete que sera enviado."

# Bienvenida al usuario y solicitud de datos (nombre , cantidad a invertir, duración de la inversión e interés anual).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de pedidos, juguetería', por favor ingrese su nombre: ")
# Solicitar la cantidad de payasos y de muñescas compradas en el último pedido.
numero_payasos = int(input(f"\nBienvenido a la aplicación {nombre}, ahora introduzca cuántos payasos han sido comprados/demandados en el último pedido o paquete: "))
numero_muñecas = int(input("\nValor guardado/almacenado. De nuevo, ahora introduzca cuántas muñecas han sido compradas/demandadas en el último pedido o paquete: "))

# Calcular el capital final con interés compuesto.
peso_total_pedido = numero_payasos * 112 + numero_muñecas * 75

# Muestreo del resultado final al usuario.
print(f"\n\nEl peso total del último pedido realizado es de {peso_total_pedido}. Del total {numero_payasos * 112} corresponde al peso de los payasos y {numero_muñecas * 75} corresponde al peso de las muñecas.")
# Ejercicio 1: "Escribir un programa que pida al usuario su peso (en kg) y estatura (en metros), calcule el índice de masa corporal y lo almacene en una variable, e imprima por pantalla la frase. Tu índice de masa corporal es <imc> donde <imc> es el índice de masa corporal calculado redondeado con dos decimales."

# Bienvenida al usuario y solicitud de datos (nombre, peso y altura).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de IMC (Índice de Masa Corporal)', por favor ingrese su nombre: ")
# Solicitar el peso (kg) y la altura (m).
peso = float(input(f"\nBienvenido a la aplicación {nombre}, ahora introduzca su peso (kg): "))
altura = float(input(f"\nValor guardado/almacenado. De nuevo, ahora introduzca su altura (m): "))

# Calcular el IMC.
imc = peso / altura**2

# Muestreo del resultado final al usuario.
print(f"\n\nTu índice de masa corporal es de {imc:.2f} puntos.")
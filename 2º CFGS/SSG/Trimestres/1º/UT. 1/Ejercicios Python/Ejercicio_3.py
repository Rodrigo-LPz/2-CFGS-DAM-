# Ejercicio 3: "Suma de los n primeros números."


# Bienvenida al usuario y solicitud de datos (nombre y cadena de sucesion de numeros a sumar)
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Calculadora de Números triangulares', por favor ingrese su nombre: ")
# Solicitar el número correspondiente a la cadena.
numero = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca el número de la cadena hasta el que desea que llegue el cálculo de la suma: "))

# Solución con programación 1.
    # suma = 0

    #for i in range(1, numero + 1):
        # suma += i

# Solución con programación 2.
    #suma = sum(range(1, numero + 1)) # La función range genera una secuencia de números desde 1 hasta numero (inclusive), y sum los suma. No crea una lista fija, con objetos en memeoria, sino que genera los números sobre la marcha, lo que es más eficiente en términos de memoria.


# Solución matemática.
    #suma = (numero * (numero + 1)) // 2 # Se utiliza "//" en vez de "/" ya que el uso de "/" devuelve un valor float [con decimales (28.0)] y en este caso no es necesario, ya que el resultado siempre será un número entero.
suma = numero * (numero + 1) // 2

# Muestreo del resultado final.
print(f"\n\nEl resultado final de suma de los {numero} primeros números es de: {suma}.")
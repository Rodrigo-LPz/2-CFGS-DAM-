# Ejercicio 1: "Hacer un programa que tras la lectura por pantalla de la edad de una persona, determina si es o no mayor de edad."


# Bienvenida al usuario y solicitud de datos (nombre, edad).
# Solicitar el nombre al usuario.
    #print("Bienvenido al programa 'Calculadora de Edad', por favor ingrese su nombre: ")
    #nombre = input()
nombre = input("Bienvenido al programa 'Calculadora de Edad', por favor ingrese su nombre: ")
# Solicitar la edad actual al usuario.
    #print("Bienvenido a la aplicacion " + nombre + ", ahora introduzca su ano de nacimiento: ")
    #edad = int(input())
edad = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca su edad actual: "))

# Muestreo del resultado final al usuario. Clasificar la edad en mayor de edad (+18) o menores de edad.
if edad < 18:
    print(f"\n\n{nombre}, dada su edad de {edad} años, usted es menor de edad.")
else:
    print(f"\n\n{nombre}, dada su edad de {edad} años, usted es mayor de edad.")
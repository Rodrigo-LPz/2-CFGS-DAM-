# Ejercicio 1: "Calcula la edad a partir del año de nacimiento."


# Bienvenida al usuario y solicitud de datos (nombre, año de nacimiento y año actual)
# Solicitar el nombre al usuario.
    #print("Bienvenido al programa 'Calculadora de Edad', por favor ingrese su nombre: ")
    #nombre = input()
nombre = input("Bienvenido al programa 'Calculadora de Edad', por favor ingrese su nombre: ")
# Solicitar el año de nacimiento al usuario.
    #print("Bienvenido a la aplicacion " + nombre + ", ahora introduzca su ano de nacimiento: ")
    #ano_nacimiento = int(input())
ano_nacimiento = int(input(f"\nBienvenido a la aplicacion {nombre}, ahora introduzca su ano de nacimiento: "))
# Solicitar el año actual al usuario.
    #print("De nuevo, ahora introduzca el ano actual: ")
    #ano_actual = int(input())
ano_actual = int(input("\nDe nuevo, ahora introduzca el ano actual: "))

# Comprobar que el año actual no sea inferior al año de nacimiento.
if ano_actual < ano_nacimiento:
    print("\n\nError: El ano actual no puede ser menor que el ano de nacimiento.")
    #exit()
# Ejecutar el cálculo en caso de que los datos sean lógicos.
else:
    # Cálculo final de la edad del usuario.
    edad = ano_actual - ano_nacimiento
        #print("Sr./Sra. " + nombre + ", su edad correspondiente dados los datos aportados es de " + str(edad) + " anos.")
    print(f"\n\nSr./Sra. {nombre}, su edad correspondiente dados los datos aportados es de {edad} anos.")
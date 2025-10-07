# Ejercicio 8: "Programa que detecte si una cadena es palíndromo o no."


# Bienvenida al usuario y solicitud de datos (nombre y texto).
# Solicitar el nombre al usuario.
nombre = input("Bienvenido al programa 'Comprobador de Palíndromos', por favor ingrese su nombre: ")
# Solicitar el texto a contabilizar el carácter concreto.
texto = input(f"\n\tBienvenido a la aplicacion {nombre}, ahora introduzca el texto, la oración o la palabra a comprobar si es palíndromo o no: ")

# Comprobar si es palíndromo o no.
texto_normalizado = texto.lower()
texto_normalizado = ''.join(i for i in texto_normalizado if i.isalnum()) # Bucle de tipo "for" donde recorrer cada caracter de la cadena. ".isalnum" es un método que devuelve "True" si el carácer es una letra o un número, en caso contrario, devolverá "False" para todo aquello que no lo sea (espacios, signos de puntuación, símbolos, etc.). Por último, el método ".join()" crea una cadena espejo, donde toma el resultado obtenido entre paréntesis y la escribe sin separadores gracias al " '' " (Por ejemplo, si fuese '_'.join(...), tendría como resultado "H_o_l_a_M_u_n_d_o"). 

if (texto_normalizado == texto_normalizado[::-1]):
    # Muestreo del resultado final al usuario.
    print(f"\n\nDada la cadena de texto, oración o palabra \"{texto}\" ingresado es palíndromo {texto_normalizado}.")
else:
    # Muestreo del resultado final al usuario.
    print(f"\n\nDada la cadena de texto, oración o palabra \"{texto}\" ingresado no es palíndromo {texto_normalizado}.")
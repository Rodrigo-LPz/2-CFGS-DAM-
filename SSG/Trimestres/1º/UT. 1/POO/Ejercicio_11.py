# Ejercicio 11: "Enunciado: Crea un sistema de gestión para un departamento de desarrollo tecnológico donde se pueda administrar empleados y proyectos. Implementa una clase DepartamentoDesarrollo que gestione el presupuesto disponible, una clase Empleado con ID único, nombre, nivel (Junior/Senior/Lead) y salario correspondiente, y una clase Proyecto con ID único, nombre y presupuesto asignado. El sistema debe permitir contratar empleados (validando ID único y nivel correcto), crear proyectos (verificando que el presupuesto no supere el disponible) y asignar empleados a proyectos (comprobando que existan y no estén duplicados). Desarrolla un menú interactivo que muestre las opciones de gestión y presupuesto, impidiendo operaciones inválidas como exceder el presupuesto o asignar empleados inexistentes, y que muestre confirmaciones de cada operación con el estado actualizado."


# Clase "Empleado".
class Empleado:
    # Método para inicializar los atributos de "Empleado".
    def __init__(self, id_empleado, nombre, nivel, salario):
        self.id_empleado = id_empleado
        self.nombre = nombre
        self.nivel = nivel
        self.salario = salario
    
    # Método para mostrar la información del empleado.
    def __str__(self):
        return f"ID: {self.id_empleado}, Nombre: {self.nombre}, Nivel: {self.nivel}, Salario: {self.salario}€"

# Clase "Proyecto".
class Proyecto:
    # Método para inicializar los atributos de "Proyecto".
    def __init__(self, id_proyecto, nombre, presupuesto):
        self.id_proyecto = id_proyecto
        self.nombre = nombre
        self.presupuesto = presupuesto
        self.empleados = []

    # Método para asignar un empleado al proyecto.
    def asignar_empleado(self, empleado):
        if empleado not in self.empleados:
            self.empleados.append(empleado)
        else:
            print("Error. El empleado ya está asignado a este proyecto.")

    # Método para mostrar la información del proyecto.
    def __str__(self):
        return f"ID: {self.id_proyecto}, Proyecto: {self.nombre}, Presupuesto: {self.presupuesto}€, Empleados: {len(self.empleados)}"

# Clase "DepartamentoDesarrollo".
class DepartamentoDesarrollo:
    # Método para inicializar los atributos de "DepartamentoDesarrollo".
    def __init__(self, presupuesto):
        self.presupuesto = presupuesto
        self.empleados = {}
        self.proyectos = {}

    # Método para contratar un empleado.
    def contratar_empleado(self, empleado):
        if empleado.id_empleado in self.empleados:
            print("Error. El ID del empleado ya existe.")
        else:
            if empleado.nivel not in ["Junior", "Senior", "Lead"]:
                print("Error. Nivel inválido. Usa Junior, Senior o Lead.")
                return
            self.empleados[empleado.id_empleado] = empleado
            print(f"Empleado {empleado.nombre} contratado.")

    # Método para crear un proyecto.
    def crear_proyecto(self, proyecto):
        if proyecto.id_proyecto in self.proyectos:
            print("Error. El ID del proyecto ya existe.")
        elif proyecto.presupuesto > self.presupuesto:
            print("Error. No hay suficiente presupuesto para este proyecto.")
        else:
            self.proyectos[proyecto.id_proyecto] = proyecto
            self.presupuesto -= proyecto.presupuesto
            print(f"Proyecto {proyecto.nombre} creado. Presupuesto restante: {self.presupuesto}€")

    # Método para asignar un empleado a un proyecto.
    def asignar_empleado_a_proyecto(self, id_empleado, id_proyecto):
        if id_empleado not in self.empleados:
            print("Error. El empleado a buscar no existe o no lo reconoce el sistema.")
            return
        if id_proyecto not in self.proyectos:
            print("Error. El proyecto a buscar no existe o no lo reconoce el sistema.")
            return

        empleado = self.empleados[id_empleado]
        proyecto = self.proyectos[id_proyecto]
        proyecto.asignar_empleado(empleado)
        print(f"{empleado.nombre} asignado al proyecto {proyecto.nombre}.")
    
    # Método para mostrar empleados.
    def mostrar_empleados(self):
        print("\nLista de empleados:")
        for empleado in self.empleados.values():
            print(empleado)

    # Método para mostrar proyectos.
    def mostrar_proyectos(self):
        print("\nLista de proyectos:")
        for proyecto in self.proyectos.values():
            print(proyecto)


# ----------------- PROGRAMA PRINCIPAL -----------------
def menu():
    dep = DepartamentoDesarrollo(10000)  # Presupuesto inicial

    # Bucle de tipo "while" para el menú interactivo.
    while True:
        print("\n<======= MENÚ DEPARTAMENTO DESARROLLO =======>")
        print("\n\t1. Contratar empleado")
        print("\n\t2. Crear proyecto")
        print("\n\t3. Asignar empleado a proyecto")
        print("\n\t4. Mostrar empleados")
        print("\n\t5. Mostrar proyectos")
        print("\n\t6. Mostrar presupuesto disponible")
        print("\n\t0. Salir")

        opcion = input("\n\nSelecciona una opción: ")

        # Validación de la opción introducida por el usuario.
        if opcion == "1":
            try:
                id_emp = int(input("ID empleado: "))
                nombre = input("Nombre: ")
                nivel = input("Nivel (Junior/Senior/Lead): ")
                salario = float(input("Salario: "))
                emp = Empleado(id_emp, nombre, nivel, salario)
                dep.contratar_empleado(emp)
            except ValueError:
                print("Error. Los datos introducidos son inválidos o no los reconoce el sistema.")
        elif opcion == "2":
            try:
                id_proy = int(input("ID proyecto: "))
                nombre = input("Nombre del proyecto: ")
                presupuesto = float(input("Presupuesto del proyecto: "))
                proy = Proyecto(id_proy, nombre, presupuesto)
                dep.crear_proyecto(proy)
            except ValueError:
                print("Error. Los datos introducidos son inválidos o no los reconoce el sistema.")
        elif opcion == "3":
            try:
                id_emp = int(input("ID del empleado: "))
                id_proy = int(input("ID del proyecto: "))
                dep.asignar_empleado_a_proyecto(id_emp, id_proy)
            except ValueError:
                print("Error. Los datos introducidos son inválidos o no los reconoce el sistema.")
        elif opcion == "4":
            dep.mostrar_empleados()
        elif opcion == "5":
            dep.mostrar_proyectos()
        elif opcion == "6":
            print(f"\nPresupuesto disponible: {dep.presupuesto}€")
        elif opcion == "0":
            print("Saliendo del sistema.")
            break
        else:
            print("Error. La opción escogida no es válida o no la reconoce el sistema.")


if __name__ == "__main__":
    menu()
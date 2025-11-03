/**
 *
 * @author Rodrigo
 */
package com.mycompany.Hibernate_ej;


// Importa del paquete java "Hibernate_ej.modelo.Departamento" el archivo "Departamento".
import com.mycompany.Hibernate_ej.modelo.Departamento;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;
// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;
// Importa de la biblioteca/librería el paquete "Session".
import org.hibernate.Session;
// Importa de la biblioteca/librería el paquete "Query".
import org.hibernate.query.Query;

public class Hibernate_Ej{
    public static void main(String[] args){
        System.out.println("Inicializando la aplicación de Hibernate...");
        
        // Leer todos los departamentos.
        leerDepartamentos();
    }
    
    public static void leerDepartamentos(){
        Session session = null;
        
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Operación CRUD.
            Query<Departamento> query = session.createQuery("FROM Departamento", Departamento.class);
            List<Departamento> departamentos = query.list();
            
            System.out.println("\n\n\t<==================== LISTA de DEPARTAMENTOS ====================>\n\n");
            for (Departamento departamento : departamentos){
                System.out.println(departamento);
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error de sesiones", "'Session Factory' creation failed / La fabricación de sesiones ha fallado: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        } finally{
            if (session != null && session.isOpen()){ session.close(); }
        }
    }
}
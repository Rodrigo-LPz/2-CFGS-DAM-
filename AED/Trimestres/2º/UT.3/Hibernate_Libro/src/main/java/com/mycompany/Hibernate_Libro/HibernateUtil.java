/**
 *
 * @author Rodrigo
 */
package com.mycompany.Hibernate_Libro;


// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
// Importa de la biblioteca/librería el paquete "SessionFactory".
import org.hibernate.SessionFactory;
// Importa de la biblioteca/librería el paquete "StandardServiceRegistryBuilder".
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
// Importa de la biblioteca/librería el paquete "Configuration".
import org.hibernate.cfg.Configuration;

public class HibernateUtil{
    private static final SessionFactory sessionFactory;
    
    static{
        try{
            System.out.println("\n\n\tCreando \"SessionFactory\"...");
            
            // Carga la configuración y construye la"SessionFactory".
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            
            System.out.println("\n\n\tLa \"SessionFactory\" ha sido creada correctamente.");
            
            /*
            Configuration configuration = new Configuration();
            configuration = configuration.configure();
            
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder = builder.applySettings(configuration.getProperties());
            
            SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
            */
        } catch(HibernateException hex){
            JOptionPane.showMessageDialog(null, "Error de sesiones", "'Session Factory' creation failed / La fabricación de sesiones ha fallado: " + hex.getMessage(), JOptionPane.ERROR_MESSAGE);
            throw new ExceptionInInitializerError(hex);
        } catch(Throwable tw){
            JOptionPane.showMessageDialog(null, "Error de sesiones", "'Session Factory' creation failed / La fabricación de sesiones ha fallado: " + tw.getMessage(), JOptionPane.ERROR_MESSAGE);
            throw new ExceptionInInitializerError(tw);
        }
    }
    
    // Crea el método "get". Método para obtener la "SessionFactory".
    public static SessionFactory getSessionFactory(){ return sessionFactory; }
  
    // Crea el método "shutdown". Método para cerrar la "SessionFactory" al finalizar la aplicación.
    public static void shutdown(){
        // Cierra la "SessionFactory".
        if (sessionFactory != null && !sessionFactory.isClosed()){
            System.out.println("\n\tCerrando la aplicación de Hibernate...");
            sessionFactory.close();
        }
        //getSessionFactory().close();
    }
}
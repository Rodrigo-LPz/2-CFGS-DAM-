/**
 *
 * @author Rodrigo
 */
package com.dam.util;


// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;
// Importa de la biblioteca/librería el paquete "HibernateException".
import org.hibernate.HibernateException;
// Importa de la biblioteca/librería el paquete "SessionFactory".
import org.hibernate.SessionFactory;
// Importa de la biblioteca/librería el paquete "Configuration".
import org.hibernate.cfg.Configuration;

public class HibernateUtil{
    // Objeto privado para instanciar la apertura de sesión sobre Hibernate.
    private static final SessionFactory sessionFactory;
    
    static{
        try{
            // Crea una nueva configuración leyendo el contenido declarado en "hibernate.cfg.xml" y después construye el "SessionFactory".
            sessionFactory = new Configuration().configure().buildSessionFactory();
            
            System.out.println("\n\n\tSessionFactory creado exitosamente.");
            
            /*
            Configuration configuration = new Configuration();
            configuration = configuration.configure();
            
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder = builder.applySettings(configuration.getProperties());
            
            SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
            */
        } catch(HibernateException hex){
            // Captura de excepción/error específico de Hibernate
            System.err.println("\n\n\tError al crear SessionFactory (HibernateException): " + hex.getMessage());
            JOptionPane.showMessageDialog(null, "La fabricación de sesiones ha fallado.\nError inesperado al intentar inicializar Hibernate: " + hex.getMessage(), "Error de sesiones crítico 'Session Factory'", JOptionPane.ERROR_MESSAGE);
            throw new ExceptionInInitializerError(hex);
        } catch(Throwable tw){
            // Captura de cualquier otra excepción/error crítico.
            System.err.println("\n\n\tError al crear SessionFactory (HibernateException): " + tw.getMessage());
            JOptionPane.showMessageDialog(null, "La fabricación de sesiones ha fallado.\nError inesperado al intentar inicializar Hibernate: " + tw.getMessage(), "Error de sesiones crítico 'Session Factory'", JOptionPane.ERROR_MESSAGE);
            throw new ExceptionInInitializerError(tw);
        }
    }
    
    // Crea el método "get" con el que obtener la instancia única del "SessionFactory".
    public static SessionFactory getSessionFactory(){ return sessionFactory; }
    
    // Crea el método "shutdown" con el que cerrar el "SessionFactory" y liberar todos los recursos asociados.
    //public static void shutdown(){ getSessionFactory().close(); }
    public static void shutdown(){
        //getSessionFactory().close();
        try{
            if (sessionFactory != null && !sessionFactory.isClosed()){
                sessionFactory.close();
                System.out.println("\n\n\tSessionFactory cerrado correctamente.");
            }
        } catch (HibernateException hex){
            System.err.println("\n\n\tError al cerrar SessionFactory: " + hex.getMessage());
            JOptionPane.showMessageDialog(null, "El cierre de sesiones ha fallado.\nError inesperado al intentar cerrar Hibernate: " + hex.getMessage(), "Error de sesiones crítico 'Session Factory'", JOptionPane.ERROR_MESSAGE);
            hex.printStackTrace();
        }
  }
}
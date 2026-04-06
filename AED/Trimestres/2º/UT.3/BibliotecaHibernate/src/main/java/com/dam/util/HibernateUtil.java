/**
 *
 * @author Rodrigo
 */
package com.dam.util;


// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
// Importa de la biblioteca/librería el paquete "SessionFactory".
import org.hibernate.SessionFactory;
// Importa de la biblioteca/librería el paquete "Configuration".
import org.hibernate.cfg.Configuration;

public class HibernateUtil{
    private static final SessionFactory sessionFactory;
    
    static{
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
            /*
            Configuration configuration = new Configuration();
            configuration = configuration.configure();
            
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder = builder.applySettings(configuration.getProperties());
            
            SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
            */
        } catch(HibernateException tw){
            JOptionPane.showMessageDialog(null, "Error de sesiones", "'Session Factory' creation failed / La fabricación de sesiones ha fallado: " + tw.getMessage(), JOptionPane.ERROR_MESSAGE);
            throw new ExceptionInInitializerError(tw);
        } catch(Throwable tw){
            JOptionPane.showMessageDialog(null, "Error de sesiones", "'Session Factory' creation failed / La fabricación de sesiones ha fallado: " + tw.getMessage(), JOptionPane.ERROR_MESSAGE);
            throw new ExceptionInInitializerError(tw);
        }
    }
    
  // Crea el método "get".
  public static SessionFactory getSessionFactory(){ return sessionFactory; }
  
  // Crea el método "shutdown".
  public static void shutdown(){ getSessionFactory().close(); }
}
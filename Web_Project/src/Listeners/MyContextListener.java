package Listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Application Lifecycle Listener implementation class MyContextListener
 *
 */
@WebListener
public class MyContextListener implements ServletContextListener {
	
	public static SessionFactory sf=null;
	
	public void contextInitialized(ServletContextEvent sce)  { 
		Configuration cfg = new Configuration();
		cfg.configure("resources/hibernate.cfg.xml");
		sf = cfg.buildSessionFactory();
		System.out.println("ServletContextListener Started");
    }
   
    public void contextDestroyed(ServletContextEvent sce)  { 
		sf.close();    
		System.out.println("ServletContextListener Ended");

	}

}

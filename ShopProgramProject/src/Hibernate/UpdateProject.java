package Hibernate;




import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ShopProgram.Project;

public class UpdateProject {

		
	public void addProject(Project project) {
				
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(project);
		
		session.getTransaction().commit();
		session.close();
		}
	
}

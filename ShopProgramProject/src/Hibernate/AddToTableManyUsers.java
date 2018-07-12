package Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ShopProgram.Product;

public class AddToTableManyUsers {

	public void addProduct() {
					
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		for (int i=1;i<50000; i++) {
			
			Product product = new Product();
			product.setProductName("Product"+" "+i);
			product.setProductPrice(Double.parseDouble(Double.toString(i*10)));
			product.setQuantity(Double.parseDouble(Double.toString(i+50)));
			session.save(product);
			
		}
		session.getTransaction().commit();
		session.close();
		}
	
	public static void main(String[] args) {
		AddToTableManyUsers table = new AddToTableManyUsers();
		table.addProduct();
	}
}

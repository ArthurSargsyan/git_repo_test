package Hibernate;

import java.text.DecimalFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ShopProgram.Product;

public class AddToTable {
	

	public void addProduct(String n,String p,String q) {
		
		
		Product product = new Product();
		product.setProductName(n);
		product.setProductPrice(UpdateProduct.customFormat("###.##",Double.parseDouble(p)));
		product.setQuantity(UpdateProduct.customFormat("###.##",Double.parseDouble(q)));
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(product);
		
		session.getTransaction().commit();
		session.close();
		}	
}

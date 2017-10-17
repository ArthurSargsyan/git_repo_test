package Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ShopProgram.Product;

public class CheckAvailability {
	
	//Checking availability of product by name in warehouse. If there is this product return product id, else return 0.
	public int checkAvailabilityByName(String prodName) {
		
		int chechedId = 0;
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		for (int i=1;i>0;i++){
			try {
				Product checkedProduct = (Product) session.get(Product.class, i);
				
				if (checkedProduct.getProductName().equalsIgnoreCase(prodName)) {
				chechedId = i;
				System.out.println("Have " + checkedProduct.getProductName() +" //Quantity is - " +checkedProduct.getQuantity());
				}
			
			} catch (Exception e) {
				i=-1;
				System.out.println("Checked All By Name");
			}
		}
		session.getTransaction().commit();
		session.close();
		return chechedId;
	}
	
	//Checking availability of product by name and price in warehouse. If there is this product return product id, else return 0.
	public int checkAvailability(String n,double p) {
		
		int chechedId = 0;
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		for (int i=1;i>0;i++){
			try {
				Product checkedProduct = (Product) session.get(Product.class, i);
				
				if (checkedProduct.getProductName().equalsIgnoreCase(n)) {
				chechedId = i;
				System.out.println("Have " + checkedProduct.getProductName() +" //Quantity is - " +checkedProduct.getQuantity());
				}
			} catch (Exception e) {
				i=-1;
				System.out.println("Checked All");
			}
		}
		session.getTransaction().commit();
		session.close();
		return chechedId;
	}

	/*public static void main(String[] args) {
	CheckAvailability newCheck = new CheckAvailability();
	newCheck.checkAvailability("Mom");
	System.out.println(newCheck.checkAvailability("Mom"));
	}*/
}


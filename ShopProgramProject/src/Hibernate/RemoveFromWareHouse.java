package Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ShopProgram.Product;

public class RemoveFromWareHouse {
	
	public void removeFromWareHouse(int id,String removeQuantity) {
		double newQuantity;
			
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Product removedProduct = (Product) session.get(Product.class, id);
		newQuantity=removedProduct.getQuantity()-Double.parseDouble(removeQuantity);
		removedProduct.setQuantity(newQuantity);
		session.update(removedProduct);
		
		session.getTransaction().commit();
		session.close();
	}
	/*public static void main(String[] args) {
		RemoveFromWareHouse newRemove = new RemoveFromWareHouse();
		newRemove.removeFromWareHouse(4, "5");
	}*/
}


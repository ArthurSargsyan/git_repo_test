package Hibernate;

import java.text.DecimalFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ShopProgram.Product;

public class UpdateProduct {
	
	    //public static void main(String[] args) {
		//UpdateProduct newUpdate = new UpdateProduct();
		//newUpdate.updateProduct(3, 5);
	    //}
	
	static public double customFormat(String pattern, double value ) {
	      DecimalFormat myFormatter = new DecimalFormat(pattern);
	      String output = myFormatter.format(value);
	      return Double.parseDouble(output);
	   }
	
	
	
	
	
	//Update product quantity in warehouse using id and added quantity, plus update product price with average price.
	public void updateProduct(int id,String addedQuantity,double p) {
		double newQuantity;
			
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		double averagePrice=p;
		
		Product updatedProduct = (Product) session.get(Product.class, id);
		
		if (updatedProduct.getProductPrice()==p) {
			
		} else {
			averagePrice = (updatedProduct.getProductPrice()*updatedProduct.getQuantity()+Double.parseDouble(addedQuantity)*p)/(updatedProduct.getQuantity()+Double.parseDouble(addedQuantity));
			averagePrice = customFormat("###.#", averagePrice );
		}
		newQuantity=updatedProduct.getQuantity()+Double.parseDouble(addedQuantity);
		updatedProduct.setQuantity(newQuantity);
				
			
		updatedProduct.setProductPrice(averagePrice);
		
		session.update(updatedProduct);
		
		session.getTransaction().commit();
		session.close();
		
		
	}
	
	
	//Update product quantity in warehouse using id and added quantity.
		public void updateProduct(int id,String addedQuantity) {
			double newQuantity;
				
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
				
			
			Product updatedProduct = (Product) session.get(Product.class, id);
			newQuantity=updatedProduct.getQuantity()+Double.parseDouble(addedQuantity);
			updatedProduct.setQuantity(newQuantity);
					
			session.update(updatedProduct);
			
			session.getTransaction().commit();
			session.close();
			
			
		}



}


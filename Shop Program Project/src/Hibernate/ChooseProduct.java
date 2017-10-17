package Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ShopProgram.Product;

public class ChooseProduct {
	
	
	//public static void main(String[] args) {
	//ChooseProduct newChoose = new ChooseProduct();
	//System.out.println(newChoose.chooseProductId("Mom"));
	//System.out.println(newChoose.chooseProduct(2));
	//System.out.println(newChoose.chooseProductListFilteringByString("res"));
	//}
	
	
	//choose product by product name  and return id of product.
	public int chooseProductId(String n) {
		
		int chechedId = 0;
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		for (int i=1;i>0;i++){
			try {
				Product choosedProduct = (Product) session.get(Product.class, i);
				
				if (choosedProduct.getProductName().equalsIgnoreCase(n)) {
								
				chechedId = i;
				System.out.println("Choosed Product Id is - " + choosedProduct.getProductId() +" //Quantity is - " +choosedProduct.getQuantity());
				
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
	//choose product by product id  and return the product.
	public Product chooseProduct(int id) {
		
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		
		Product choosedProduct = (Product) session.get(Product.class, id);
				
		System.out.println("Choosen Product is - " + choosedProduct.getProductName());
					
		session.getTransaction().commit();
		session.close();
		return choosedProduct;
	}
	
	public String chooseAllProductListForTextArea() {
		
		String allProductList="";
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		  int i;
		try {
		 for (i=1;i>0; i++) {
		
		Product choosedProduct = (Product) session.get(Product.class, i);
		allProductList = allProductList +i+". "+choosedProduct.getProductName()+"\t\t"+choosedProduct.getProductPrice()+"\t"+choosedProduct.getQuantity()+"\n";
		
				
		System.out.println("Choosen Product is - " + choosedProduct.getProductName());
		
		 }
		} catch (Exception e) {
			i=0;
		}
					
		session.getTransaction().commit();
		session.close();
		return allProductList;
		
			
	}
	
	public String chooseProductListFilteringByString(String s) {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	
		String allProductList="";
		int i;
		int y=0;
		try {
		
			for (  i=1;i>0; i++) {
				
				Product searchingProduct =  (Product) session.get(Product.class, i);
				String searchingtext = searchingProduct.getProductName();
			
					if (searchingtext.toLowerCase().contains(s.toLowerCase())) {
						y=y+1;   
						allProductList = allProductList + y +". "+ searchingProduct.getProductName() + "\t\t" + searchingProduct.getQuantity() + "\t" + searchingProduct.getProductPrice()+"\n"; 
					}
			}
		
		
		} catch (Exception e) {
			i=0;
		}
		session.getTransaction().commit();
		session.close();
		return allProductList;
	}
	
	public int productQuantityInWareHouse() {
		
			
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
			int y=0;
		 	int i;
		try {
		 for (i=1;i>0; i++) {
		
		Product choosedProduct = (Product) session.get(Product.class, i);
		y = y +1;
		
				
		System.out.println("Choosen Product is - " + choosedProduct.getProductName());
		
		 }
		} catch (Exception e) {
			i=0;
		}
					
		session.getTransaction().commit();
		session.close();
		return y;
		
			
	}
	
	
}


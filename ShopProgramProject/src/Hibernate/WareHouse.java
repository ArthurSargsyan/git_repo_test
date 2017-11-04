package Hibernate;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ShopProgram.ChoosenProduct;
import ShopProgram.PresentationOfDouble;
import ShopProgram.Product;
import ShopProgram.Project;
import ShopProgram.ProjectData;

public class WareHouse implements PresentationOfDouble {
	//Add Product To Warehouse.
	public void addProduct(String n,String p,String q) {
	
		Product product = new Product();
		product.setProductName(n);
		product.setProductPrice(PresentationOfDouble.customFormat("###.##",Double.parseDouble(p)));
		product.setQuantity(PresentationOfDouble.customFormat("###.##",Double.parseDouble(q)));
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(product);
		
		session.getTransaction().commit();
		session.close();
		}	
	//Remove Product From Warehouse By Product Id and Removed Quantity
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
				averagePrice = PresentationOfDouble.customFormat("###.#", averagePrice );
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
	//return ArrayList of products in which name  have string s string.
	public ArrayList<Product> ProductListFilteringByString(String s) {
			
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
		
			ArrayList<Product> allProductList = new ArrayList<Product>();
			int i;
			try {
				for (  i=1;i>0; i++) {
					Product searchingProduct =  (Product) session.get(Product.class, i);
					String searchingtext = searchingProduct.getProductName();
						if (searchingtext.toLowerCase().contains(s.toLowerCase())) {
							allProductList.add(searchingProduct);
						}
				}
			} catch (Exception e) {
				i=0;
			}
			session.getTransaction().commit();
			session.close();
			return allProductList;
		}
	//Return List Of Products In Warehouse.
	public ArrayList<Product> chooseAllProductArrayList() {
			
			ArrayList<Product> listOfProducts = new ArrayList<Product>();
			
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			 int i;
			try {
				for (i=1;i>0; i++) {
			Product prod = (Product) session.get(Product.class, i);
			prod.getProductName();    //That throws Exception
			listOfProducts.add(prod);
			System.out.println(i);
			}
			} catch (Exception e) {
				i=0;
			}
			session.getTransaction().commit();
			session.close();
			return listOfProducts;
		}
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
	//Add Project To warehouse.
	public void addProject(Project project) {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(project);
		
		session.getTransaction().commit();
		session.close();
		}
	//Return List of All Projects In Warehouse.
	public ArrayList<Project> chooseAllProjectsArrayList() {
		
		ArrayList<Project> listOfProjects = new ArrayList<Project>();
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		 int i;
		try {
			for (i=1;i>0; i++) {
		Project proj = (Project) session.get(Project.class, i);
		proj.getProjectName();    //That throws Exception
		listOfProjects.add(proj);
		System.out.println(i);
		}
		} catch (Exception e) {
			i=0;
		}
		session.getTransaction().commit();
		session.close();
		return listOfProjects;
	}
	//Return List Of Products In Project By Project Name.	
	public ArrayList<ChoosenProduct> productListFilteringByProjectName(String s) {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	
		ArrayList<ChoosenProduct> allChoosenProductList = new ArrayList<ChoosenProduct>();
		int i;
		try {
			for (  i=1;i>0; i++) {
				Project searchingProject =  (Project) session.get(Project.class, i);
				String searchingtext = searchingProject.getProjectName();
					if (searchingtext.toLowerCase().contains(s.toLowerCase())) {
						
					allChoosenProductList.addAll(searchingProject.getProductList());
						
					}
			}
		} catch (Exception e) {
			i=0;
		}
		session.getTransaction().commit();
		session.close();
		return allChoosenProductList;
	}
	//Return List Of Projects Data By Project Name.
	public ArrayList<ProjectData> projectDataFilteringByProjectName(String s) {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	
		ArrayList<ProjectData> allProjectDataList = new ArrayList<ProjectData>();
		int i;
		try {
			for (i=1;i>0; i++) {
				Project searchingProject =  (Project) session.get(Project.class, i);
				String searchingtext = searchingProject.getProjectName();
				
				
					if (searchingtext.toLowerCase().equals(s.toLowerCase())) {
						
						ArrayList<ChoosenProduct> prodList= new ArrayList<ChoosenProduct>();
						prodList.addAll(searchingProject.getProductList());
						
						for (int j=0; j<prodList.size(); j++) {
							
						ProjectData projData = new ProjectData();
						projData.setDate(searchingProject.getDate());
						projData.setPersonName(searchingProject.getPersonName());
						projData.setProductName(prodList.get(j).getProductName());
						projData.setProductPrice(prodList.get(j).getProductPrice());
						projData.setQuantity(prodList.get(j).getQuantity());
						allProjectDataList.add(projData);
						System.out.println(projData.getQuantity());
						System.out.println(allProjectDataList.get(j).getQuantity());
						}
						
					}
			}
		} catch (Exception e) {
			i=0;
		}
		session.getTransaction().commit();
		session.close();
		return allProjectDataList;
	}
	
}

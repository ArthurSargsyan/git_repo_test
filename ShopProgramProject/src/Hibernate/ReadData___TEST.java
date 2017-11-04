package Hibernate;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ShopProgram.ChoosenProduct;
import ShopProgram.Project;
import ShopProgram.ProjectData;

public class ReadData___TEST {
	
	
	
	public ArrayList<ProjectData> projectDataFilteringByProjectName(String s) {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	
		ArrayList<ProjectData> allProjectDataList = new ArrayList<ProjectData>();
		int i;
		int k=0;
		try {
			for (i=1;i>0; i++) {
				Project searchingProject =  (Project) session.get(Project.class, i);
				String searchingtext = searchingProject.getProjectName();
								
					if (searchingtext.toLowerCase().equals(s.toLowerCase())) {
						ProjectData projData = new ProjectData();
						
						
						ArrayList<ChoosenProduct> prodList= new ArrayList<ChoosenProduct>();
						prodList.addAll(searchingProject.getProductList());
						int j;
						for (j=0; j<prodList.size(); j++) {
							
						projData.setDate(searchingProject.getDate());
						projData.setPersonName(searchingProject.getPersonName());
							
						projData.setProductName(prodList.get(j).getProductName());
						projData.setProductPrice(prodList.get(j).getProductPrice());
						projData.setQuantity(prodList.get(j).getQuantity());
						
						allProjectDataList.add(k,projData);
						
						
						System.out.println(projData.getDate());
						System.out.println(projData.getPersonName());
						System.out.println(projData.getQuantity());
						System.out.println(projData.getProductPrice());
						System.out.println(projData.getProductName());
						
						System.out.println(allProjectDataList.get(k).getDate());
						System.out.println(allProjectDataList.get(k).getPersonName());
						System.out.println(allProjectDataList.get(k).getQuantity());
						System.out.println(allProjectDataList.get(k).getProductPrice());
						System.out.println(allProjectDataList.get(k).getProductName());
						k=k+1;
						}
											
						/////////////////////////////////
						//ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
						///////////////////////////////
					}
			}
		} catch (Exception e) {
			i=0;
		}
		for (int h=0; h<allProjectDataList.size(); h++) {
			 System.out.println(h);	
			 System.out.println("***********************"+allProjectDataList.get(h));
		 System.out.println("***********************"+allProjectDataList.get(h).getQuantity());
		System.out.println("***********************"+allProjectDataList.get(h).getProductPrice());
		System.out.println("***********************"+allProjectDataList.get(h).getPersonName());
		System.out.println("***********************"+allProjectDataList.get(h).getDate());
	 }
		session.getTransaction().commit();
		session.close();
		return allProjectDataList;
	}
	
	
	public static void main(String[] args) {
	ReadData___TEST newChoose = new ReadData___TEST();
	newChoose.projectDataFilteringByProjectName("Project 1");
	}
}


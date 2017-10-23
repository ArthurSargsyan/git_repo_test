package Hibernate;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ShopProgram.ChoosenProduct;
import ShopProgram.Project;
import ShopProgram.ProjectData;

public class ReadDataFromProjects {
	
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
						ProjectData projData = new ProjectData();
						projData.setDate(searchingProject.getDate());
						projData.setPersonName(searchingProject.getPersonName());
						
						
						ArrayList<ChoosenProduct> prodList= new ArrayList<ChoosenProduct>();
						prodList.addAll(searchingProject.getProductList());
						
						for (int j=0; j<prodList.size(); j++) {
												
						projData.setProductName(prodList.get(j).getProductName());
						projData.setProductPrice(prodList.get(j).getProductPrice());
						projData.setQuantity(prodList.get(j).getQuantity());
						allProjectDataList.add(projData);
						System.out.println(projData.getQuantity());
						System.out.println(allProjectDataList.get(j).getProductPrice());
						}
						/////////////////////////////////
						//ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
						///////////////////////////////
					}
			}
		} catch (Exception e) {
			i=0;
		}
		session.getTransaction().commit();
		session.close();
		return allProjectDataList;
	}
	
	
	/*public static void main(String[] args) {
	ReadDataFromProjects newChoose = new ReadDataFromProjects();
	 ArrayList<ProjectData> projData = new ArrayList<ProjectData>();
	 projData.addAll(newChoose.projectDataFilteringByProjectName("Project 1"));
	 for (int i=0; i<projData.size(); i++) {
			 System.out.println("projData.size ***********************"+projData.get(i).getQuantity());
			System.out.println("projData.size ***********************"+projData.get(i).getProductPrice());
	 }

    }*/
}


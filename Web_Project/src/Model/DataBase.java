package Model;


import java.util.ArrayList;
import java.util.List;



import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import beans.Invoice;
import beans.Item;
import beans.ItemInShop;

public class DataBase {
	
	public void addItemToDB(SessionFactory sessionFuchtory,Item item) {
		Session session = sessionFuchtory.openSession();
		Query query= session.createQuery("from Item where vendercode =\'" + item.getVenderCode() +"\'");
		Item fetchedItem = (Item) query.uniqueResult();
		Transaction t = session.beginTransaction();
		if(!(fetchedItem==null)) {
			int updatedQuantity=fetchedItem.getQunatity()+item.getQunatity();
			System.out.println(item.getQunatity());
			System.out.println(fetchedItem.getQunatity());
			Query query1= session.createQuery("update Item set qunatity=\'" + updatedQuantity + "\' where itemID =\'" + fetchedItem.getItemID()+"\'");
			query1.executeUpdate();
		}else {		
			session.save(item);
		}
		t.commit();
		session.close();
	}
	
	public void addInvoiceToDB(Session session, Invoice invoice) {
		Transaction t = session.beginTransaction();
		session.save(invoice);
		t.commit();
	
	}
	
	public void addItemForShopToDB(SessionFactory sessionFactory, ItemInShop itemInShop) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		
		Query query= session.createQuery("from Item where vendercode =\'" + itemInShop.getVenderCode() +"\'");
		Item fetchedItem = (Item) query.uniqueResult();
		int updatedQuantity=fetchedItem.getQunatity()-itemInShop.getQunatity();
		Query query1= session.createQuery("update Item set qunatity=\'" + updatedQuantity + "\' where itemID =\'" + fetchedItem.getItemID()+"\'");
		query1.executeUpdate();
		
		
		Query query2= session.createQuery("from ItemInShop where vendercode =\'" + itemInShop.getVenderCode() +"\'");
		ItemInShop fetchedItemInShop = (ItemInShop) query2.uniqueResult();
		if(!(fetchedItemInShop==null)) {
			int updatedQuantityItemInShop=fetchedItemInShop.getQunatity()+itemInShop.getQunatity();
			
			Query query3= session.createQuery("update ItemInShop set qunatity=\'" + updatedQuantityItemInShop + "\' where itemID =\'" + fetchedItemInShop.getItemID()+"\'");
			query3.executeUpdate();
		}else {		
			session.save(itemInShop);
		}
		t.commit();
		session.close();
	  /////////////
		
	}
	
	public List<Item> returnItemsDepONIDs(Session session,ArrayList<Integer> iDs){
		List<Item> itemList= new ArrayList<>();
		Criteria c = session.createCriteria(Item.class);
		List<Item> items = c.list();
		for (Item item : items) {
			for (int i : iDs) {
				if(item.getItemID()==i) {
					itemList.add(item);
				}
			}
		}
		return itemList;
	}	
	
	public List<Invoice> returnInvoices(Session session){
		
		Criteria c = session.createCriteria(Invoice.class);
		List<Invoice> invoiceList = c.list();
		return invoiceList;
	}
	
	
	public List<Item> searchInDB(SessionFactory sf,String propertyName ,String property) {
		List<Item> itemList = null;
		if(propertyName.equals("invoiceNo")){
			itemList = new ArrayList<>();
			Session session = sf.openSession();
			Criteria c = session.createCriteria(Invoice.class);
			Criterion cr = Restrictions.eq(propertyName, property);
			c.add(cr);
			Invoice invoice = (Invoice) c.uniqueResult();
			if(invoice!=null){
				int invoiceID=invoice.getInvoiceID();
				Item item = session.load(Item.class, invoiceID);
				itemList.add(item);
			}
			session.close();
		}else {
			Session session = sf.openSession();
			Criteria c = session.createCriteria(Item.class);
			System.out.println(property);
	
			Criterion cr = Restrictions.eq(propertyName, property);
			c.add(cr);
			
			itemList = (List<Item>) c.list();  
			
			System.out.println("Item recived");
			System.out.println(itemList);
			session.close();
		}		
		return itemList;
	}
	

}

package Model;


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
	
		
	}
	
	public List<Item> searchInDB(Session session,String propertyName ,String property) {
		Criteria c = session.createCriteria(Item.class);
		System.out.println(property);

		Criterion cr = Restrictions.eq(propertyName, property);
		c.add(cr);
		
		
		List<Item> itemList = (List<Item>) c.list();  
		
		
		System.out.println("Item recived");
		System.out.println(itemList);
				
		return itemList;
	}
	

}

package Model;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import beans.Invoice;
import beans.Item;
import beans.ItemInShop;

public class DataBase {
	
	public void addItemToDB(Session session,Item item) {
	
		Transaction t = session.beginTransaction();
		session.persist(item);
		t.commit();
	}
	
	public void addInvoiceToDB(Session session, Invoice invoice) {
		Transaction t = session.beginTransaction();
		session.save(invoice);
		t.commit();
	}
	
	public Item searchInDB(Session session,String itemName) {
		Criteria c = session.createCriteria(Item.class);
		System.out.println(itemName);

		Criterion cr = Restrictions.eq("itemName", itemName);
		c.add(cr);
		Item item = (Item) c.uniqueResult();
		System.out.println("Item recived");
		System.out.println(item);
				
		return item;
	}
	

}

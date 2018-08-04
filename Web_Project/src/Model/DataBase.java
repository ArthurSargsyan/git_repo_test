package Model;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import beans.Invoice;
import beans.Item;
import beans.ItemsInShop;
import beans.ItemsInStore;
import beans.MoveToShopItems;

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
		
		Query<Invoice> q = session.createQuery  ("from Invoice where invoiceNo=\'"+invoice.getInvoiceNo()+"\'");
		Invoice returnedInvoice = q.uniqueResult();
		if(returnedInvoice!=null) {
			System.out.println("have invoice");
		int invoicereeturnedID = returnedInvoice.getInvoiceID();
		
		
		returnedInvoice=  session.load(Invoice.class, invoicereeturnedID);
		returnedInvoice.getItems().addAll(invoice.getItems());
		}else {
			System.out.println("have no invoice");
			returnedInvoice = invoice;
		}
		session.saveOrUpdate(returnedInvoice);
		System.out.println("before commit");
		t.commit();
	
	}
	
	public void addItemForShopToDB(SessionFactory sessionFactory, ItemsInShop itemInShop) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
			
		List<Invoice> fetchedInvoiceList = new ArrayList<>();
		List<Invoice> fetchedItems = this.returnInvoices(session);
		for(Invoice invoice:fetchedItems) {
			
			for(ItemsInStore item:invoice.getItems()) {
				if(item.getVenderCode().equals(itemInShop.getVenderCode())) {
					fetchedInvoiceList.add(invoice);
				}
			}
		}
	
		Invoice oldestInvoice= (Invoice) this.getOldestInvoice(fetchedInvoiceList);
		ItemsInStore fetchedItem = this.getItemInStore(oldestInvoice, itemInShop.getVenderCode());
		
		
		int updatedQuantity=fetchedItem.getQunatity()-itemInShop.getQunatity();
		Query query1= session.createQuery("update ItemsInStore set qunatity=\'" + updatedQuantity + "\' where itemID =\'" + fetchedItem.getItemID()+"\'");
		query1.executeUpdate();
		
		
		Query query2= session.createQuery ("from MoveToShopItems where vendercode =\'" + itemInShop.getVenderCode() +"\'");
		
		List<MoveToShopItems> fetchedMoveItemInShopItems = null;
		
		fetchedMoveItemInShopItems = query2.getResultList();
		
		
		if(fetchedMoveItemInShopItems.size()!=0) {
			int updatedQuantityItemInShop=fetchedMoveItemInShopItems.get(0).getQunatity()+itemInShop.getQunatity();
			
			Query query3= session.createQuery("update MoveToShopItems set qunatity=\'" + updatedQuantityItemInShop + "\' where itemID =\'" + fetchedMoveItemInShopItems.get(0).getItemID()+"\'");
			query3.executeUpdate();
		}else {		
			fetchedItem.setQunatity(itemInShop.getQunatity());
			MoveToShopItems moveToShopItem = new MoveToShopItems(fetchedItem,new Date());
			session.save(moveToShopItem);
		}
		t.commit();
		session.close();
	}
	
	
	public ItemsInStore getItemInStore(Invoice invoice,String venderCode) {
		ItemsInStore itemInStore=null;
		for(ItemsInStore item:invoice.getItems()) {
			if(item.getVenderCode().equals(venderCode)) {
				itemInStore = item;
				break;
			}
		}
		return itemInStore;
	}
	
	
	
	public List<Invoice> returnItemsDepONIDs(Session session,ArrayList<Integer> iDs){
		List<Invoice> invoiceList= new ArrayList<>();
		Criteria c = session.createCriteria(Invoice.class);
		List<Invoice> invoices = c.list();
		for (Invoice invoice : invoices) {
			for (int i : iDs) {
				if(invoice.getInvoiceID()==i) {
					invoiceList.add(invoice);
				}
			}
		}
		return invoiceList;
	}	
	
	public List<Invoice> returnInvoices(Session session){
		
		Criteria c = session.createCriteria(Invoice.class);
		List<Invoice> invoiceList = c.list();
		return invoiceList;
	}
	
	
	public List<Invoice> searchInDB(SessionFactory sf,String propertyName ,String property) {
		List<Invoice> invoiceList = new ArrayList<>();
		
		
		if(propertyName.equals("invoiceNo")){
			Session session = sf.openSession();
			Criteria c = session.createCriteria(Invoice.class);
			Criterion cr = Restrictions.eq(propertyName, property);
			c.add(cr);
			Invoice invoice = (Invoice) c.uniqueResult();
			if(invoice!=null){
			Set<ItemsInStore> items = new HashSet<>();
			for(ItemsInStore item:invoice.getItems()) {
			  items.add(item);
			}
			invoice.setItems(items);
				invoiceList.add(invoice);
			}
			session.close();
		}else {
			List<Invoice> list = new ArrayList<>();
			Session session = sf.openSession();
			Criteria c = session.createCriteria(Invoice.class);
			List<Invoice> invoice_List = (List<Invoice>) c.list();  
			for(Invoice invoice : invoice_List) {
				for(Item item :invoice.getItems()) {
					if(item.getItemName().equals(property)||item.getVenderCode().equals(property)) {
						list.add(invoice);
					}
				}
			}
			invoiceList.addAll(list);
			session.close();
		}
//																									System.out.println(invoiceList+"-------------------------------------------");
//																									for (Invoice invoice : invoiceList) {
//																										for(Item item:invoice.getItems()) {
//																											System.out.println(item.getItemName()+"*/-/-*-*/");
//																										}
//																									}
		return invoiceList;
	}
	
	public String checkAvalableItemQuantity(List<Invoice> invoiceList,String itemName) {
		String result = "";
		for(Item item:invoiceList.get(0).getItems()) {
			if(item.getItemName().equals(itemName)) {
					result = "{ \"searchResult\":\"" + item.getItemName() + "\",\"unit\":\"" + item.getUnit() + "\",\"category\":\"" + item.getCategory() + "\",\"venderCode\":\"" + item.getVenderCode() + "\",\"description\":\"" +item.getDescription() + "\",\"price\":\"" + item.getPrice() + "\",\"quantity\":\"" + item.getQunatity() +"\"}"; 
			}
		}
		return result;
	}
	
	
	public Invoice getOldestInvoice(List<Invoice> invoiceList) {
		String fetchedDate = invoiceList.get(0).getDate();
		Invoice oldestInvoice = invoiceList.get(0);
		for(Invoice invoice:invoiceList) {
			
			if(Integer.parseInt(invoice.getDate().substring(0,4)) < Integer.parseInt(fetchedDate.substring(0,4))){
				if(Integer.parseInt(invoice.getDate().substring(5,7)) < Integer.parseInt(fetchedDate.substring(5,7))) {
					if(Integer.parseInt(invoice.getDate().substring(8,10)) < Integer.parseInt(fetchedDate.substring(8,10))) {
						oldestInvoice=invoice;
					}
				}
			}
		}
		return oldestInvoice;
	}
	
	public Set<String> getVenderCodeList(List<Invoice> invoiceList,String itemName) {
		Set<String> venderCodeList = new HashSet<>();
		for(int i = 0; i<invoiceList.size(); i++) {
			for(Item item:invoiceList.get(i).getItems()) {
				boolean checkAvalability=false;
				if(item.getItemName().equals(itemName)) {
					venderCodeList.add(item.getVenderCode());
				}
			}
		}
		return venderCodeList;
	}
	
	
	
	
	public static void main(String[] args) {
		
		Configuration config = new Configuration();
		config.configure("resources/hibernate.cfg.xml");
		SessionFactory sf = config.buildSessionFactory();
		DataBase db = new DataBase();
		List list = db.searchInDB(sf, "venderCode", "EG-003");
		System.out.println(list.size());
		for (Object object : list) {
			System.out.println(((Invoice)object).getInvoiceNo());
		}
	}
	
	
	
}
